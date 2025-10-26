package com.myapp.auth;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.regex.Pattern;

public class Login {

    private String storedUsername;
    private String storedPassword;
    private String firstName;
    private String lastName;
    private String cellPhone;

    private boolean lastLoginSuccess = false;

    public Login() {
        loadUserFromFile();
    }

    public boolean checkUserName(String username) {
        if (username == null) return false;
        return username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {
        if (password == null) return false;
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$";
        return Pattern.matches(regex, password);
    }

    public boolean checkCellPhoneNumber(String phone) {
        if (phone == null) return false;
        String regex = "^\\+27\\d{9}$"; 
        return Pattern.matches(regex, phone);
    }

    public String registerUser(String firstName, String lastName, String username, String password, String phone) {
        StringBuilder sb = new StringBuilder();

        boolean usernameOk = checkUserName(username);
        boolean passwordOk = checkPasswordComplexity(password);
        boolean phoneOk = checkCellPhoneNumber(phone);

        if (usernameOk) sb.append("Username successfully captured.\n");
        else sb.append("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.\n");

        if (passwordOk) sb.append("Password successfully captured.\n");
        else sb.append("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.\n");

        if (phoneOk) sb.append("Cell phone number successfully added.\n");
        else sb.append("Cell phone number incorrectly formatted or does not contain international code.\n");

        if (usernameOk && passwordOk && phoneOk) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.storedUsername = username;
            this.storedPassword = password;
            this.cellPhone = phone;
            saveUserToFile();
            sb.append("Registration successful.");
        } else {
            sb.append("Registration failed - please correct the above and try again.");
        }

        return sb.toString().trim();
    }

    public boolean loginUser(String username, String password) {
        loadUserFromFile(); // ensure latest stored user is loaded
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

    @SuppressWarnings("unchecked")
    private void saveUserToFile() {
        try {
            JSONObject obj = new JSONObject();
            obj.put("username", storedUsername);
            obj.put("password", storedPassword);
            obj.put("firstName", firstName);
            obj.put("lastName", lastName);
            obj.put("cellPhone", cellPhone);

            try (FileWriter fw = new FileWriter("user.json")) {
                fw.write(obj.toJSONString());
                fw.flush();
            }
        } catch (Exception ex) {
            System.out.println("Error saving user: " + ex.getMessage());
        }
    }

    private void loadUserFromFile() {
        try {
            JSONParser parser = new JSONParser();
            Object o = parser.parse(new FileReader("user.json"));
            if (o instanceof JSONObject) {
                JSONObject obj = (JSONObject) o;
                storedUsername = (String) obj.get("username");
                storedPassword = (String) obj.get("password");
                firstName = (String) obj.get("firstName");
                lastName = (String) obj.get("lastName");
                cellPhone = (String) obj.get("cellPhone");
            }
        } catch (Exception ex) {
            
        }
    }

    
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
}
