package de.jservice.kidsgard.Components.device;

import de.jservice.kidsgard.Components.ConstMessagesEN;
import org.springframework.stereotype.Component;
import javax.swing.*;

@Component
public class FormBtnPanel extends JPanel {

    private JButton saveBtn;
    private JButton cancelBtn;

    public FormBtnPanel() {
        initComponents();
    }

    private void initComponents() {
        saveBtn = new JButton(ConstMessagesEN.Labels.ADD_BTN);
        add(saveBtn);

        cancelBtn = new JButton(ConstMessagesEN.Labels.CANCEL_BTN);
        add(cancelBtn);
    }

    public JButton getSaveBtn() {
        return saveBtn;
    }

    public JButton getCancelBtn() {
        return cancelBtn;
    }

}
