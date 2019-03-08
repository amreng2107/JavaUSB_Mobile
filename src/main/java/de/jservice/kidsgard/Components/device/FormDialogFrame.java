package de.jservice.kidsgard.Components.device;

import de.jservice.kidsgard.Components.ConstMessagesEN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.swing.*;
import java.awt.*;

@Component
public class FormDialogFrame extends JDialog {

    private FormPanel formPanel;
    private FormBtnPanel formBtnPanel;

    @Autowired
    public FormDialogFrame(FormPanel formPanel, FormBtnPanel formBtnPanel) {
        this.formPanel = formPanel;
        this.formBtnPanel = formBtnPanel;
        setFrameUp();
        initComponents();
      pack();
    }

    private void setFrameUp() {
        setTitle(ConstMessagesEN.DialogTitles.ADD_DEVICE);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
    }

    private void initComponents() {
        add(formPanel, BorderLayout.CENTER);
        add(formBtnPanel, BorderLayout.SOUTH);
    }

    public FormPanel getFormPanel() {
        return formPanel;
    }

    public FormBtnPanel getFormBtnPanel() {
        return formBtnPanel;
    }
}
