package de.jservice.kidsgard.Components.device;

import de.jservice.kidsgard.Components.TablePanel;
import de.jservice.kidsgard.Components.ConstMessagesEN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author AmrReda
 */
@Component
public class DeviceTableFrame extends JFrame {

    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 500;

    private TablePanel tablePanel;
    private AddANDRemoveBtnPanel tableBtnPanel;

    @Autowired
    public DeviceTableFrame(AddANDRemoveBtnPanel tableBtnPanel, TablePanel tablePanel) {
        this.tablePanel = tablePanel;
        this.tableBtnPanel = tableBtnPanel;
        setFrameUp();
        initComponents();
    }

    private void setFrameUp() {
        setTitle(ConstMessagesEN.Labels.DEVICES);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(true);
    }

    private void initComponents() {
        add(tablePanel, BorderLayout.CENTER);
        add(tableBtnPanel, BorderLayout.SOUTH);
    }

    public AddANDRemoveBtnPanel getTableBtnPanel() {
        return tableBtnPanel;
    }

    public TablePanel getTablePanel() {
        return tablePanel;
    }
}
