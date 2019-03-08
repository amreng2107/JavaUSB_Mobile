package de.jservice.kidsgard.Components.device;

import de.jservice.kidsgard.Components.Borders;
import de.jservice.kidsgard.Service.DevicesWrapperService;
import de.jservice.kidsgard.Service.TimeAccountService;
import de.jservice.kidsgard.data.DeviceWrapper;
import de.jservice.kidsgard.data.Devices;
import de.jservice.kidsgard.data.TimeAccount;
import de.jservice.kidsgard.data.Warning;
import de.jservice.kidsgard.integration.EmailRepository;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class FormPanel extends JPanel {
                
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel minuteLabel;
    private javax.swing.JLabel kindEmailLabel;
    private javax.swing.JLabel warningLabel;
    private javax.swing.JLabel parentEmailLabel;
    private javax.swing.JLabel titleLabel;

    private javax.swing.JTextField nameField;
    private javax.swing.JTextField kindEmailTextField;
    private javax.swing.JTextField minutesTextField;

    private JPanel minutesLayout;
    private javax.swing.ButtonGroup group;
    private javax.swing.JRadioButton dailyButton;
    private javax.swing.JRadioButton weeklButton;
  
    private javax.swing.JScrollPane warningScroll;
    private javax.swing.JScrollPane jScrollPane2;

    private javax.swing.JTree jTree1;

    protected javax.swing.JTextField parentEmailTextField;
    private javax.swing.JTextField warningTextField;

    @Autowired
    private EmailRepository emailRepo;
    @Autowired
    private TimeAccountService timeAccountService;
    @Autowired
    private DevicesWrapperService deviceWrapperService;

    private String vendorSelected = "";
    private String productSelected = "";
  
    // End of variables declaration                
    private static final int LAYOUT_ROWS = 7;
    private static final int LAYOUT_COLS = 1;
    private static final int HORIZONTAL_GAP = 0;
    private static final int VERTICAL_GAP = 6;
    private static final int TEXT_FIELD_COLUMNS = 5;

    public FormPanel() {
        setPanelUp();
        initComponents();
    }

    private void setPanelUp() {
        setBorder(Borders.createEmptyBorder());
        setLayout(new GridLayout(LAYOUT_ROWS, LAYOUT_COLS, HORIZONTAL_GAP, VERTICAL_GAP));
    }

    @SuppressWarnings("unchecked")                   
    private void initComponents() {

        group = new javax.swing.ButtonGroup();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();

        nameLabel = new javax.swing.JLabel();
        minuteLabel = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField(TEXT_FIELD_COLUMNS);
        minutesLayout = new JPanel();
        minutesTextField = new javax.swing.JTextField(TEXT_FIELD_COLUMNS);
        dailyButton = new javax.swing.JRadioButton();
        weeklButton = new javax.swing.JRadioButton();
        kindEmailLabel = new javax.swing.JLabel();
      
   
        warningLabel = new javax.swing.JLabel();
        warningScroll = new javax.swing.JScrollPane();

        warningTextField = new javax.swing.JTextField();
  
        kindEmailTextField = new javax.swing.JTextField(TEXT_FIELD_COLUMNS);
        parentEmailLabel = new javax.swing.JLabel();
        parentEmailTextField = new javax.swing.JTextField(TEXT_FIELD_COLUMNS);


        titleLabel = new javax.swing.JLabel();

        jScrollPane2.setViewportView(jTree1);

        nameLabel.setFont(new java.awt.Font("Dubai", 0, 14)); // NOI18N
        nameLabel.setText("Device Name");

        minuteLabel.setFont(new java.awt.Font("Dubai", 0, 14)); // NOI18N
        minuteLabel.setText("Minutes Account");

        nameField.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        nameField.setText("Device Name");

        minutesTextField.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        minutesTextField.setText("Minutes");

        group.add(dailyButton);
        dailyButton.setFont(new java.awt.Font("Dubai", 0, 14)); // NOI18N
        dailyButton.setText("Daily");

        group.add(weeklButton);
        weeklButton.setFont(new java.awt.Font("Dubai", 0, 14)); // NOI18N
        weeklButton.setText("Weekly");

        kindEmailLabel.setFont(new java.awt.Font("Dubai", 0, 14)); // NOI18N
        kindEmailLabel.setText("Kind Email");

        warningLabel.setFont(new java.awt.Font("Dubai", 0, 14)); // NOI18N
        warningLabel.setText("Warning ");

        warningTextField.setColumns(20);
        warningTextField.setFont(new java.awt.Font("Dubai", 0, 14)); // NOI18N

        warningScroll.setViewportView(warningTextField);

        kindEmailTextField.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        kindEmailTextField.setText("Email");

        parentEmailLabel.setFont(new java.awt.Font("Dubai", 0, 14)); // NOI18N
        parentEmailLabel.setText("Parent Email");

        parentEmailTextField.setFont(new java.awt.Font("Dubai", 0, 14)); // NOI18N
        parentEmailTextField.setText("Email");


        titleLabel.setFont(new java.awt.Font("Dubai", 0, 24)); // NOI18N
        titleLabel.setText("Mobile Control");

        add(nameLabel);
        add(nameField);
        minutesLayout.add(minutesTextField);
        minutesLayout.add(dailyButton);
        minutesLayout.add(weeklButton);
        add(minuteLabel);
        add(minutesLayout);
        add(kindEmailLabel);
        add(kindEmailTextField);
        add(parentEmailLabel);
        add(parentEmailTextField);
        add(warningLabel);
        add(warningTextField);
        add(new Label());
        add(new Label());  
        add(new Label());
    }

    private Warning createWarning() {
        Warning warning = new Warning();
        warning.setContent(warningTextField.getText());
        warning.setSender("jticket@jservice.de");
        Set<String> emailList = new HashSet<>();
        emailList.add(kindEmailTextField.getText());
        emailList.add(parentEmailTextField.getText());
        warning.setEmailList(emailList);
        return warning;
    }

  
    /* private void vendorComboAction(java.awt.event.ActionEvent evt){
    vendorSelected = "";
    productSelected = "";
    if(vendorCombo.getSelectedItem()!= null){
    int selectedindex = vendorCombo.getSelectedIndex();
    productCombo.setEnabled(true);
    productCombo.setSelectedIndex(selectedindex);
    vendorSelected = vendorCombo.getSelectedItem().toString();
    productSelected = productCombo.getSelectedItem().toString();
    }*/


    private TimeAccount createTimeAccount() {
        TimeAccount account = new TimeAccount();
        account.setUseDay(LocalDate.now());
        int timeAccountLimit = Integer.parseInt(minutesTextField.getText());
        if (dailyButton.isSelected()) {
            account.setLimits(timeAccountLimit);
        } else {
            if (weeklButton.isSelected()) {
                account.setLimits(timeAccountLimit / 7);
            } else {
                account.setLimits(timeAccountLimit / 30);
            }
        }

        account.setMinutes(0);
        return account;
    }

    public Devices getEntityFromForm() {
        Devices device = new Devices();
        device.setName(nameField.getText());
        TimeAccount account = timeAccountService.save(createTimeAccount());
        device.setAccount(account);
        DeviceWrapper wrapper = new DeviceWrapper();
        wrapper.setProductId(productSelected);
        wrapper.setVendorId(vendorSelected);
        DeviceWrapper deviceWrapper = deviceWrapperService.save(wrapper);
        System.out.print("deviceWrapper.toString()"+deviceWrapper.toString());
        device.setDevice(deviceWrapper);
        Warning saved = emailRepo.save(createWarning());
        device.setWarningList(saved);

        return device;
    }

    public void clearForm() {
        productSelected = "";
        vendorSelected = "";
        nameField.setText("");
        minutesTextField.setText("");
        minutesTextField.setText("");
        warningTextField.setText("");
        kindEmailTextField.setText("");
        parentEmailTextField.setText("");

    }
    
     public String getVendorSelected() {
        return vendorSelected;
    }

    public void setVendorSelected(String vendorSelected) {
        this.vendorSelected = vendorSelected;
    }

    public String getProductSelected() {
        return productSelected;
    }

    public void setProductSelected(String productSelected) {
        this.productSelected = productSelected;
    }

}
