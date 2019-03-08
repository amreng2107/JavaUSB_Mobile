package de.jservice.kidsgard.Components;

import de.jservice.kidsgard.data.Devices;
import org.springframework.stereotype.Component;

/**
 *
 * @author AmrReda
 */
@Component
public class DeviceTableModel extends ObjectTableModel<Devices> {

    @Override
    public String[] getColumnLabels() {
        return new String[]{
                ConstMessagesEN.Labels.ID,
                ConstMessagesEN.Labels.DEVICENAME,
                ConstMessagesEN.Labels.ACCOUNT,
                ConstMessagesEN.Labels.STATUS,
                };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Devices device = entities.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return device.getId();
            case 1:
                return device.getName();
            case 2:
                return device.getAccount();
            case 3:
                return device.getStatus();
            default:
                return "";
        }
    }
}