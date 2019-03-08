package de.jservice.kidsgard.Components.device;

import de.jservice.kidsgard.Components.AbstractFrameController;
import de.jservice.kidsgard.Components.ConstMessagesEN;
import de.jservice.kidsgard.Components.DeviceTableModel;
import de.jservice.kidsgard.Components.ValidationError;
import de.jservice.kidsgard.Components.util.Notifications;
import de.jservice.kidsgard.Service.DevicesServices;
import de.jservice.kidsgard.data.Devices;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.usb4java.Context;
import org.usb4java.DeviceDescriptor;
import org.usb4java.DeviceList;
import org.usb4java.LibUsb;
import org.usb4java.LibUsbException;
/**
 *
 * @author AmrReda
 */
@Controller
public class DeviceController extends AbstractFrameController {

    private final DeviceTableFrame tableFrame;
    private final FormDialogFrame formFrame;
    private final DeviceTableModel tableModel;
    private final DevicesServices deviceService;
    private final DeviceValidator validator;

    private DeviceList connectedDeviceList = null;

    /**
     * The libusb context.
     */
    private Context context;

    @Autowired
    public DeviceController(DeviceTableFrame tableFrame,
            FormDialogFrame formFrame,
            DeviceTableModel tableModel,
            DevicesServices addressService,
            DeviceValidator validator
    ) {
        this.tableFrame = tableFrame;
        this.formFrame = formFrame;
        this.tableModel = tableModel;
        this.deviceService = addressService;
        this.validator = validator;

        this.context = new Context();
        final int result = LibUsb.init(this.context);
        if (result != 0) {
            throw new LibUsbException("Unable to initialize libusb", result);
        }

    }

    @PostConstruct
    private void prepareListeners() {
        AddANDRemoveBtnPanel tableBtnPanel = tableFrame.getTableBtnPanel();
        FormBtnPanel formBtnPanel = formFrame.getFormBtnPanel();

        registerAction(tableBtnPanel.getAddBtn(), (e) -> showAddModal());
        registerAction(tableBtnPanel.getRemoveBtn(), (e) -> removeEntity());
        registerAction(formBtnPanel.getSaveBtn(), (e) -> saveEntity());
        registerAction(formBtnPanel.getCancelBtn(), (e) -> closeModalWindow());
    }

    @Override
    public void prepareAndOpenFrame() {
        loadEntities();
        showTableFrame();
    }

    private void loadEntities() {
        List<Devices> entities = deviceService.findAll();
        tableModel.clear();
        tableModel.addEntities(entities);
    }

    private void showTableFrame() {
        connectedDeviceList = checkUSBConnect();
        tableFrame.setVisible(true);
    }

    private void showAddModal() {
        getNewConnectedDevice(getConnectedDeviceList(), checkUSBConnect());
    }

    private void getNewConnectedDevice(DeviceList oldList, DeviceList newList) {
        Map<String, String> oldListMap = new HashMap<>();
        for (int i = 0; i < oldList.getSize(); i++) {
            DeviceDescriptor olddescriptor = new DeviceDescriptor();
            LibUsb.getDeviceDescriptor(oldList.get(i), olddescriptor);
            String olddescriptorVendorId = String.format("%04x", olddescriptor.idVendor());
            String olddescriptorProductId = String.format("%04x", olddescriptor.idProduct());
            oldListMap.put(olddescriptorVendorId, olddescriptorProductId);
        }
        for (int j = 0; j < newList.getSize(); j++) {
            DeviceDescriptor descriptor = new DeviceDescriptor();
            LibUsb.getDeviceDescriptor(newList.get(j), descriptor);
            String descriptorVendorId = String.format("%04x", descriptor.idVendor());
            String descriptorProductId = String.format("%04x", descriptor.idProduct());
            if (oldListMap.containsKey(descriptorVendorId)) {
             System.out.print("device is available");
            } else {
                showConfirmationMessage(descriptorVendorId,descriptorProductId);
            }
        }
    }

    private void saveEntity() {
        FormPanel formPanel = formFrame.getFormPanel();
        Devices entity = formPanel.getEntityFromForm();
        Optional<ValidationError> errors = validator.validate(entity);
        if (errors.isPresent()) {
            ValidationError validationError = errors.get();
            Notifications.showFormValidationAlert(validationError.getMessage());
        } else {
            deviceService.save(entity);
            tableModel.addEntity(entity);
            closeModalWindow();
        }
    }

    private void closeModalWindow() {
        formFrame.getFormPanel().clearForm();
        formFrame.dispose();
    }

    private void removeEntity() {
        try {
            JTable clientTable = tableFrame.getTablePanel().getTable();
            int selectedRow = clientTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(null,
                        ConstMessagesEN.Messages.NON_ROW_SELECTED,
                        ConstMessagesEN.Messages.ALERT_TILE,
                        JOptionPane.ERROR_MESSAGE);
            } else {
                Devices entity = tableModel.getEntityByRow(selectedRow);
                deviceService.delete(entity.getId());
                tableModel.removeRow(selectedRow);
            }
        } catch (Exception e) {
            Notifications.showDeleteRowErrorMessage();
        }
    }

    private DeviceList checkUSBConnect() {
        // Read the USB device list
        DeviceList list = new DeviceList();
        context = new Context();
        LibUsb.init(context);
        int result = LibUsb.getDeviceList(context, list);
        if (result < 0) {
            throw new LibUsbException("Unable to get device list", result);
        }
        // Deinitialize the libusb context
        System.out.print("List Size : " + list.getSize());
        dispose();
        return list;
    }

    public DeviceList getConnectedDeviceList() {
        return connectedDeviceList;
    }

    public void setConnectedDeviceList(DeviceList connectedDeviceList) {
        this.connectedDeviceList = connectedDeviceList;
    }

    public void dispose() {
        LibUsb.exit(this.context);
    }

    private void showConfirmationMessage(String descriptorVendorId,String descriptorProductId) {
       
        String message = venderAndProductLookup(descriptorVendorId, descriptorProductId);
        JOptionPane pane = new JOptionPane();
        pane.setMessage(message);
        JDialog d = pane.createDialog(null, "New Device");
        d.setVisible(true);
        int selection = getSelection(pane);

        switch (selection) {
            case JOptionPane.OK_OPTION:
                formFrame.getFormPanel().setVendorSelected(descriptorVendorId);
                formFrame.getFormPanel().setProductSelected(descriptorProductId);
                formFrame.setVisible(true);
                break;
            case JOptionPane.CANCEL_OPTION:
                System.out.println("CANCEL");
                break;
            default:
                System.out.println("Others");
        }
    }

    public static int getSelection(JOptionPane optionPane) {
        int returnValue = JOptionPane.CLOSED_OPTION;

        Object selectedValue = optionPane.getValue();
        if (selectedValue != null) {
            Object options[] = optionPane.getOptions();
            if (options == null) {
                if (selectedValue instanceof Integer) {
                    returnValue = ((Integer) selectedValue).intValue();
                }
            } else {
                for (int i = 0, n = options.length; i < n; i++) {
                    if (options[i].equals(selectedValue)) {
                        returnValue = i;
                        break; // out of for loop
                    }
                }
            }
        }
        return returnValue;
    }

    // Example Devices
    private String venderAndProductLookup(String venderId, String productId) {
        Map<String, String> venderIdMap = new HashMap<>();
        venderIdMap.put("05ac", "Apple");
        venderIdMap.put("04e8", "Samsung");
        venderIdMap.put("18d1", "Google");
        venderIdMap.put("12d1", "Huawei");
        venderIdMap.put("0421", "Nokia Mobile Phones");
        venderIdMap.put("22b8","Motorola PCS");
        venderIdMap.put("045e", "Microsoft Corp");
                
        Map<String, String> productIdMap = new HashMap<>();
        productIdMap.put("12a8", "iPhone5/5C/5S/6");
        productIdMap.put("6602", "Galaxy");
        productIdMap.put("6603", "Galaxy");
        productIdMap.put("6868", "Android Phones: Modem+Diagnostic+ADB");
        productIdMap.put("687a", "GT-E2370 mobile phone");
        productIdMap.put("6877", "Galaxy S");
        productIdMap.put("4ee1", "Nexus Device (MTP)");
        productIdMap.put("1501", "Pulse");
        productIdMap.put("6860", "Galaxy (MTP)");
        productIdMap.put("2e82", "E (4) Plus");
        productIdMap.put("0a00","Lumia 950 Dual SIM (RM-1118)");
        String output = "";

        if (venderIdMap.containsKey(venderId)) {
            output = venderIdMap.get(venderId) + " / ";
        }

        if (productIdMap.containsKey(productId)) {
            output = output + productIdMap.get(productId);
        } else {
            output = output + productId;
        }
        return output;
    }
    
}
