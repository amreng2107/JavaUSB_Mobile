package de.jservice.kidsgard.Components;

import javax.swing.*;
import java.awt.event.ActionListener;
/**
 *
 * @author AmrReda
 */
public abstract class AbstractFrameController {

    public abstract void prepareAndOpenFrame();

    protected void registerAction(JButton button, ActionListener listener) {
        button.addActionListener(listener);
    }

}
