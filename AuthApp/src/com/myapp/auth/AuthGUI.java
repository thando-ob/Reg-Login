package com.myapp.auth;

import javax.swing.*;

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
        setLayout(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.insets = new java.awt.Insets(10,10,10,10);
        gbc.fill = java.awt.GridBagConstraints.BOTH;

        
        JPanel regPanel = new JPanel(new java.awt.GridBagLayout());
        regPanel.setBorder(BorderFactory.createTitledBorder("Register"));
        java.awt.GridBagConstraints r = new java.awt.GridBagConstraints();
        r.insets = new java.awt.Insets(4,4,4,4);
        r.fill = java.awt.GridBagConstraints.HORIZONTAL;

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

        
        JPanel loginPanel = new JPanel(new java.awt.GridBagLayout());
        loginPanel.setBorder(BorderFactory.createTitledBorder("Login"));
        java.awt.GridBagConstraints l = new java.awt.GridBagConstraints();
        l.insets = new java.awt.Insets(4,4,4,4);
        l.fill = java.awt.GridBagConstraints.HORIZONTAL;
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

        
        JPanel taskPanel = new JPanel(new java.awt.BorderLayout());
        taskPanel.setBorder(BorderFactory.createTitledBorder("Please read the full task before registering"));
        taskArea = new JTextArea(10, 50);
        taskArea.setEditable(false);
        taskArea.setLineWrap(true);
        taskArea.setWrapStyleWord(true);

        taskArea.setText(
                "How to Register & Login:\n" +
                        "1) Create account: username, password, South African cell phone number.\n" +
                        "   - Username: contains underscore and <= 5 characters.\n" +
                        "   - Password: > 8 chars, capital, number, special.\n" +
                        "   - Cellphone: Must include SA country code (+27...)\n" +
                        "2) After successful registration, you can login.\n" +
                        "3) Successful login displays welcome message.\n"
        );
        taskPanel.add(new JScrollPane(taskArea), java.awt.BorderLayout.CENTER);

        readTaskCheck = new JCheckBox("I have read the full task");
        taskPanel.add(readTaskCheck, java.awt.BorderLayout.SOUTH);

        
        gbc.gridx=0; gbc.gridy=0; gbc.weightx=0.6; gbc.weighty=0.7;
        add(regPanel, gbc);
        gbc.gridx=1; gbc.weightx=0.4;
        add(loginPanel, gbc);
        gbc.gridx=0; gbc.gridy=1; gbc.gridwidth=2; gbc.weighty=0.3;
        add(taskPanel, gbc);

        
        readTaskCheck.addItemListener(e -> registerBtn.setEnabled(readTaskCheck.isSelected()));

        registerBtn.addActionListener(e -> {
            String result = loginLogic.registerUser(
                    regFirstName.getText().trim(),
                    regLastName.getText().trim(),
                    regUsername.getText().trim(),
                    new String(regPassword.getPassword()),
                    regPhone.getText().trim()
            );
            JOptionPane.showMessageDialog(this, result, "Registration Result", JOptionPane.INFORMATION_MESSAGE);
        });

        loginBtn.addActionListener(e -> {
            String uname = loginUsername.getText().trim();
            String pass = new String(loginPassword.getPassword());

            if (loginLogic.loginUser(uname, pass)) {
                JOptionPane.showMessageDialog(this, loginLogic.returnLoginStatus(),
                        "Welcome", JOptionPane.INFORMATION_MESSAGE);

                dispose();
                startQuickChat();
            } else {
                JOptionPane.showMessageDialog(this,
                        loginLogic.returnLoginStatus(),
                        "Login Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void startQuickChat() {
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");

        boolean running = true;
        while (running) {
            String menu = "Please choose an option:\n" +
                    "1) Send Messages\n" +
                    "2) Show recently sent messages\n" +
                    "3) Quit";
            String choice = JOptionPane.showInputDialog(menu);
            if (choice == null) break;

            switch (choice.trim()) {
                case "1":
                    String numStr = JOptionPane.showInputDialog("How many messages would you like to enter?");
                    if (numStr == null) break;
                    int num;
                    try {
                        num = Integer.parseInt(numStr.trim());
                        if (num <= 0) {
                            JOptionPane.showMessageDialog(null, "Please enter a positive number.");
                            break;
                        }
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid number.");
                        break;
                    }

                    for (int i = 1; i <= num; i++) {
                        String recipient = JOptionPane.showInputDialog("Message " + i + " - Enter recipient (include international code, e.g. +27...):");
                        if (recipient == null) break;

                        String messageText = JOptionPane.showInputDialog("Message " + i + " - Enter message (max 250 chars):");
                        if (messageText == null) break;

                        Message prepared = Message.createPreparedMessage(recipient, messageText, i - 1);
                        if (prepared == null) {
                            if (recipient == null || recipient.isEmpty() || (recipient != null && !recipient.startsWith("+"))) {
                                JOptionPane.showMessageDialog(null, "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
                            } else if (messageText.length() > 250) {
                                JOptionPane.showMessageDialog(null, "Please enter a message of less than 250 characters.");
                            } else {
                                JOptionPane.showMessageDialog(null, "Invalid message data. Please try again.");
                            }
                            continue;
                        }

                        JOptionPane.showMessageDialog(null, prepared.printMessages(), "Message Details", JOptionPane.INFORMATION_MESSAGE);

                       
                        Object[] options = {"Send Message", "Disregard Message", "Store Message"};
                        int sel = JOptionPane.showOptionDialog(null,
                                "Choose what to do with this message:",
                                "Send / Store / Disregard",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[0]);

                        String result;
                        if (sel == 0) result = prepared.sendMessage("1");
                        else if (sel == 1) result = prepared.sendMessage("2");
                        else if (sel == 2) result = prepared.sendMessage("3");
                        else result = "No action taken.";

                        JOptionPane.showMessageDialog(null, result);
                    }

                   
                    Message summary = new Message();
                    JOptionPane.showMessageDialog(null, "Total messages sent: " + summary.returnTotalMessagess());
                    break;

                case "2":
                    JOptionPane.showMessageDialog(null, "Coming Soon.");
                    break;

                case "3":
                    running = false;
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Invalid option. Enter 1, 2 or 3.");
                    break;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AuthGUI().setVisible(true));
    }
}
