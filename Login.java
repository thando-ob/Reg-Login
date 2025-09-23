package com.myapp.auth;

import java.util.regex.Pattern;


public class Login {

    private String storedUsername;
    private String storedPassword;
    private String firstName;
    private String lastName;
    private String cellPhone;

   
    private boolean lastLoginSuccess = false;
    private String lastAttemptUsername = "";

    public Login() {
    }

    
    public boolean checkUserName(String username) {
        if (username == null) return false;
        return username.contains("_") && username.length() <= 5;
    }

   //Used ChatGPT to get regex as per instruction
    public boolean checkPasswordComplexity(String password) {
        if (password == null) return false;
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$";
        return Pattern.matches(regex, password);
    }

    
    public boolean checkCellPhoneNumber(String phone) {
        if (phone == null) return false;
        String regex = "^\\+27\\d{9,10}$";
        return Pattern.matches(regex, phone);
    }

    
    public String registerUser(String firstName, String lastName, String username, String password, String phone) {
        StringBuilder sb = new StringBuilder();

        boolean usernameOk = checkUserName(username);
        boolean passwordOk = checkPasswordComplexity(password);
        boolean phoneOk = checkCellPhoneNumber(phone);

        if (usernameOk) {
            sb.append("Username successfully captured.\n");
        } else {
            sb.append("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.\n");
        }

        if (passwordOk) {
            sb.append("Password successfully captured.\n");
        } else {
            sb.append("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.\n");
        }

        if (phoneOk) {
            sb.append("Cell phone number successfully added.\n");
        } else {
            sb.append("Cell phone number incorrectly formatted or does not contain international code.\n");
        }

        if (usernameOk && passwordOk && phoneOk) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.storedUsername = username;
            this.storedPassword = password;
            this.cellPhone = phone;
            sb.append("Registration successful.");
        } else {
            sb.append("Registration failed - please correct the above and try again.");
        }

        return sb.toString().trim();
    }

    public boolean loginUser(String username, String password) {
        lastAttemptUsername = username == null ? "" : username;
        if (storedUsername == null || storedPassword == null) {
            lastLoginSuccess = false;
            return false;
        }
        lastLoginSuccess = storedUsername.equals(username) && storedPassword.equals(password);
        return lastLoginSuccess;
    }


    public String returnLoginStatus() {
        if (lastLoginSuccess) {
            return "Welcome " + this.firstName + " ," + this.lastName + " it is great to see you.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    
    public String getStoredUsername() { return storedUsername; }
    public String getStoredPassword() { return storedPassword; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getCellPhone() { return cellPhone; }
}

