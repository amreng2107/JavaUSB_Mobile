package de.jservice.kidsgard.Components.device;

import de.jservice.kidsgard.Components.ConstMessagesEN;
import org.springframework.stereotype.Component;

import javax.swing.*;

/**
 *
 * @author AmrReda
 */
@Component
public class AddANDRemoveBtnPanel extends JPanel {

    private JButton addBtn;
    private JButton removeBtn;

    public AddANDRemoveBtnPanel() {
        initComponents();
    }

    private void initComponents() {
        addBtn = new JButton(ConstMessagesEN.Labels.ADD_BTN);
        add(addBtn);

        removeBtn = new JButton(ConstMessagesEN.Labels.REMOVE_BTN);
        add(removeBtn);
    }

    public JButton getAddBtn() {
        return addBtn;
    }

    public JButton getRemoveBtn() {
        return removeBtn;
    }

}
