package com.myapp.auth;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AuthGUI extends JFrame {

    private final Login loginLogic = new Login();

    
    private JTextField regFirstName;
    private JTextField regLastName;
    private JTextField regUsername;
    private JPasswordField regPassword;
    private JTextField regPhone;
    private JButton registerBtn;

    
    private JTextField loginUsername;
    private JPasswordField loginPassword;
    private JButton loginBtn;

    private JTextArea taskArea;
    private JCheckBox readTaskCheck;

    public AuthGUI() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Registration & Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 520);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.BOTH;

        
        JPanel regPanel = new JPanel(new GridBagLayout());
        regPanel.setBorder(BorderFactory.createTitledBorder("Register"));
        GridBagConstraints r = new GridBagConstraints();
        r.insets = new Insets(4,4,4,4);
        r.fill = GridBagConstraints.HORIZONTAL;
        r.gridx=0; r.gridy=0;
        regPanel.add(new JLabel("First name:"), r);
        r.gridx=1; regFirstName = new JTextField(12); regPanel.add(regFirstName, r);

        r.gridx=0; r.gridy++;
        regPanel.add(new JLabel("Last name:"), r);
        r.gridx=1; regLastName = new JTextField(12); regPanel.add(regLastName, r);

        r.gridx=0; r.gridy++;
        regPanel.add(new JLabel("Username:"), r);
        r.gridx=1; regUsername = new JTextField(12); regPanel.add(regUsername, r);

        r.gridx=0; r.gridy++;
        regPanel.add(new JLabel("Password:"), r);
        r.gridx=1; regPassword = new JPasswordField(12); regPanel.add(regPassword, r);

        r.gridx=0; r.gridy++;
        regPanel.add(new JLabel("Cellphone (+27...):"), r);
        r.gridx=1; regPhone = new JTextField(12); regPanel.add(regPhone, r);

        r.gridx=0; r.gridy++;
        r.gridwidth=2;
        registerBtn = new JButton("Register");
        registerBtn.setEnabled(false);
        regPanel.add(registerBtn, r);

        
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBorder(BorderFactory.createTitledBorder("Login"));
        GridBagConstraints l = new GridBagConstraints();
        l.insets = new Insets(4,4,4,4);
        l.fill = GridBagConstraints.HORIZONTAL;
        l.gridx=0; l.gridy=0;
        loginPanel.add(new JLabel("Username:"), l);
        l.gridx=1; loginUsername = new JTextField(12); loginPanel.add(loginUsername, l);

        l.gridx=0; l.gridy++;
        loginPanel.add(new JLabel("Password:"), l);
        l.gridx=1; loginPassword = new JPasswordField(12); loginPanel.add(loginPassword, l);

        l.gridx=0; l.gridy++;
        l.gridwidth=2;
        loginBtn = new JButton("Login");
        loginPanel.add(loginBtn, l);

        
        JPanel taskPanel = new JPanel(new BorderLayout());
        taskPanel.setBorder(BorderFactory.createTitledBorder("Please read the full task before registering"));
        taskArea = new JTextArea(10, 50);
        taskArea.setEditable(false);
        taskArea.setLineWrap(true);
        taskArea.setWrapStyleWord(true);
        
        
        taskArea.setText(
            "How to Register & Login:\n" +
            "1) Create account: username, password, South African cell phone number.\n" +
            "   - Username: contains underscore and less than or eqaul to 5 characters.\n" +
            "   - Password: More than 8 characters, a capital letter, a number, a special character.\n" +
            "   - Cellphone: Must include SA international code (+27...).\n" +
            "   - At successful registration, you will then be able to login.\n" +
            "2) At successful login you will see:\n" +
            "   \"Welcome firstName ,lastName it is great to see you.\"\n" +
            "Make sure you read the full specification before registering and check the box to register."
        );
        taskPanel.add(new JScrollPane(taskArea), BorderLayout.CENTER);
        readTaskCheck = new JCheckBox("I have read the full task");
        taskPanel.add(readTaskCheck, BorderLayout.SOUTH);

        
        gbc.gridx=0; gbc.gridy=0; gbc.weightx=0.6; gbc.weighty=0.7;
        add(regPanel, gbc);
        gbc.gridx=1; gbc.weightx=0.4;
        add(loginPanel, gbc);
        gbc.gridx=0; gbc.gridy=1; gbc.gridwidth=2; gbc.weighty=0.3;
        add(taskPanel, gbc);

        
        readTaskCheck.addItemListener(e -> registerBtn.setEnabled(readTaskCheck.isSelected()));

        registerBtn.addActionListener(e -> {
            String fn = regFirstName.getText().trim();
            String ln = regLastName.getText().trim();
            String uname = regUsername.getText().trim();
            String pass = new String(regPassword.getPassword());
            String phone = regPhone.getText().trim();

            String result = loginLogic.registerUser(fn, ln, uname, pass, phone);
            JOptionPane.showMessageDialog(this, result, "Registration Result", JOptionPane.INFORMATION_MESSAGE);
        });

        loginBtn.addActionListener(e -> {
            String uname = loginUsername.getText().trim();
            String pass = new String(loginPassword.getPassword());
            loginLogic.loginUser(uname, pass);
            String msg = loginLogic.returnLoginStatus();
            JOptionPane.showMessageDialog(this, msg, "Login Result", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AuthGUI gui = new AuthGUI();
            gui.setVisible(true);
        });
    }
}

