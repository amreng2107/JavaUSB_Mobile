package de.jservice.kidsgard.Components;

import de.jservice.kidsgard.Service.DevicesServices;
import de.jservice.kidsgard.Service.EmailService;
import de.jservice.kidsgard.Service.TimeAccountService;
import de.jservice.kidsgard.data.Devices;
import de.jservice.kidsgard.data.Status;
import de.jservice.kidsgard.data.TimeAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import javax.swing.table.AbstractTableModel;
import org.springframework.scheduling.annotation.Scheduled;
import org.usb4java.Context;
import org.usb4java.Device;
import org.usb4java.DeviceDescriptor;
import org.usb4java.DeviceList;
import org.usb4java.LibUsb;
import org.usb4java.LibUsbException;

/**
 *
 * @author AmrReda
 */
@Component
public class TablePanel extends JPanel {

    @Autowired
    private EmailService emailService;
    @Autowired
    private TimeAccountService timeAccountService;

    private final DeviceTableModel tableModel;
    private final DevicesServices deviceService;
    private JTable table;
    private final Context context;

    @Autowired
    TablePanel(DeviceTableModel tableModel, DevicesServices deviceService) {
        this.tableModel = tableModel;
        this.deviceService = deviceService;
        this.context = new Context();
        final int result = LibUsb.init(this.context);
        if (result != 0) {
            throw new LibUsbException("Unable to initialize libusb", result);
        }
        setPanelUp();
        initComponents();
    }

    private void setPanelUp() {
        setLayout(new BorderLayout());
    }

    private void initComponents() {
        refreshTable();

        JScrollPane paneWithTable = new JScrollPane(table);
        add(paneWithTable, BorderLayout.CENTER);
    }

    public JTable getTable() {
        return table;
    }

    @Scheduled(fixedRate = 50000)
    public void checkStatus() {
        System.out.print("\n checkStatus() is called");
        try {
            JTable clientTable = getTable();
            int rowccount = clientTable.getRowCount();
            System.out.print("Row Account \n" + rowccount);
            if (rowccount < 0) {
                JOptionPane.showMessageDialog(null,
                        ConstMessagesEN.Messages.NON_ROW_SELECTED,
                        ConstMessagesEN.Messages.ALERT_TILE,
                        JOptionPane.ERROR_MESSAGE);
            } else {
                for (int i = 0; i < rowccount; i++) {
                    ((AbstractTableModel) getTable().getModel()).fireTableCellUpdated(i, 0);
                    getTable().repaint();

                    Devices entity = tableModel.getEntityByRow(i);
                    System.out.print("entity \n " + entity.getName());
                    System.out.print("checkDevice(entity) \n" + checkDevice(entity));
                    if (checkDevice(entity)) {
                        Devices d = deviceService.load(entity.getId());
                        TimeAccount account = timeAccountService.load(d.getAccount().getId());
                        if (account.getUseDay() != LocalDate.now()) {
                            account.setMinutes(0);
                            account.setUseDay(LocalDate.now());
                            TimeAccount newAccount = timeAccountService.save(account);
                            d.setAccount(newAccount);
                        }
                        d.setStatus(Status.ONLINE);
                        deviceService.save(d);
                        ((AbstractTableModel) getTable().getModel()).fireTableCellUpdated(i, 0);
                        getTable().repaint();

                    } else {
                        Devices d = deviceService.load(entity.getId());
                        TimeAccount account = timeAccountService.load(d.getAccount().getId());
                        d.setStatus(Status.OFFLINE);
                        Devices savedDevice;
                        if (account.getUseDay() != LocalDate.now()) {
                            account.setMinutes(account.getMinutes() + 1);
                            account.setUseDay(LocalDate.now());
                            TimeAccount newAccount = timeAccountService.save(account);
                            d.setAccount(newAccount);
                            savedDevice = deviceService.save(d);
                            ((AbstractTableModel) getTable().getModel()).fireTableCellUpdated(i, 0);
                            getTable().repaint();
                        } else {
                            account.setMinutes(account.getMinutes() + 1);
                            TimeAccount newAccount = timeAccountService.save(account);
                            d.setAccount(newAccount);
                            savedDevice = deviceService.save(d);
                            ((AbstractTableModel) getTable().getModel()).fireTableCellUpdated(i, 0);
                            getTable().repaint();
                        }
                        ((AbstractTableModel) getTable().getModel()).fireTableCellUpdated(i, 0);
                        getTable().repaint();
                        sendDeviceEmail(savedDevice);
                    }
                }
            }
        } catch (HeadlessException e) {
            System.out.print("\n There is a problem here");
        }
    }

    private boolean checkDevice(Devices device) {
        return findDevice(device.getDevice().getVendorId(), device.getDevice().getProductId()) != null;
    }

    public Device findDevice(String vendorId, String productId) {

        DeviceList list = new DeviceList();
        int result = LibUsb.getDeviceList(this.context, list);
        if (result < 0) {
            throw new LibUsbException("Unable to get device list", result);
        }
        // Read the USB device list
        try {
            // Iterate over all devices and scan for the right one
            for (Device device : list) {
                DeviceDescriptor descriptor = new DeviceDescriptor();
                result = LibUsb.getDeviceDescriptor(device, descriptor);

                if (result != LibUsb.SUCCESS) {
                    throw new LibUsbException("Unable to read device descriptor", result);
                }

                String descriptorVendorId = String.format("%04x", descriptor.idVendor());
                String descriptorProductId = String.format("%04x", descriptor.idProduct());
                boolean found = descriptorVendorId.equals(vendorId) && descriptorProductId.equals(productId);

                if (found) {
                    return device;
                }
            }
        } finally {
            // Ensure the allocated device list is freed
            LibUsb.freeDeviceList(list, true);

        }

        return null;
    }

    public void dispose() {
        LibUsb.exit(this.context);
    }

    private void sendDeviceEmail(Devices device) {
        System.out.print("\n sendDeviceEmail is called");
        int minutes = device.getAccount().getMinutes();
        int limits = device.getAccount().getLimits();
        System.out.print("minutes + limits \n" + minutes + " " + limits);
        if (device.getWarningList() != null) {
            if (minutes == limits) {
                emailService.sendWarningMail(device.getWarningList());
            }
        }
    }

    public void refreshTable() {
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
}
