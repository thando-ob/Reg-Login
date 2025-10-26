package com.myapp.auth;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;

public class Message {

    private String messageID;
    private String recipientCell;
    private String message;
    private int messageNumber;

    private static int totalMessagesSent = 0;

    private static final Random RAND = new Random();

    
    public Message(String messageID, String recipientCell, String message) {
        this.messageID = (messageID == null || messageID.isEmpty()) ? generateMessageID() : messageID;
        this.recipientCell = recipientCell;
        this.message = message;
    }

    public Message() {
       
    }

    
    public String generateMessageID() {
        long id = 1_000_000_000L + (long) (RAND.nextDouble() * 9_000_000_000L);
        this.messageID = String.valueOf(id);
        return this.messageID;
    }

    
    public String checkMessageLength() {
        if (message == null) message = "";
        if (message.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = message.length() - 250;
            return "Message exceeds 250 characters by " + excess + ", please reduce size.";
        }
    }

    
    public String checkRecipientCell() {
        if (recipientCell != null && recipientCell.matches("^\\+\\d{9,13}$")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    
    public String createMessageHash() {
        String first = "";
        String last = "";
        if (message != null && !message.trim().isEmpty()) {
            String[] words = message.trim().split("\\s+");
            first = words[0].toUpperCase();
            last = words[words.length - 1].toUpperCase();
        }
        String prefix = (messageID != null && messageID.length() >= 2) ? messageID.substring(0, 2) : messageID;
        return prefix + ":" + messageNumber + ":" + first + " " + last;
    }

    
    public String sendMessage(String option) {
        switch (option) {
            case "1":
                totalMessagesSent++;
                appendMessageToJSON("sent");
                return "Message successfully sent.";
            case "2":
                return "Press 0 to delete message.";
            case "3":
                appendMessageToJSON("stored");
                return "Message successfully stored.";
            default:
                return "Invalid option selected.";
        }
    }

    public String printMessages() {
        return "MessageID: " + messageID + "\n" +
                "Message Hash: " + createMessageHash() + "\n" +
                "Recipient: " + recipientCell + "\n" +
                "Message: " + message;
    }

    public int getMessageNumber() { return messageNumber; }
    public void setMessageNumber(int n) { this.messageNumber = n; }

    public int returnTotalMessagess() { return totalMessagesSent; }

    @SuppressWarnings("unchecked")
    private void appendMessageToJSON(String status) {
        try {
            File f = new File("messages.json");
            JSONArray array;
            if (f.exists()) {
                JSONParser parser = new JSONParser();
                Object o = parser.parse(new FileReader(f));
                if (o instanceof JSONArray) array = (JSONArray) o;
                else array = new JSONArray();
            } else {
                array = new JSONArray();
            }

            JSONObject obj = new JSONObject();
            obj.put("MessageID", messageID);
            obj.put("MessageHash", createMessageHash());
            obj.put("Recipient", recipientCell);
            obj.put("Message", message);
            obj.put("MessageNumber", messageNumber);
            obj.put("Status", status);

            array.add(obj);

            try (FileWriter fw = new FileWriter(f)) {
                fw.write(array.toJSONString());
                fw.flush();
            }
        } catch (Exception ex) {
            System.out.println("Error writing messages.json: " + ex.getMessage());
        }
    }

    
    public static Message createPreparedMessage(String recipientInput, String messageTextInput, int seqNumber) {
        Message m = new Message(null, recipientInput, messageTextInput);
        m.setMessageNumber(seqNumber);
        String rc = m.checkRecipientCell();
        String mc = m.checkMessageLength();
        if (!"Cell phone number successfully captured.".equals(rc)) return null;
        if (!"Message ready to send.".equals(mc)) return null;
        m.createMessageHash(); 
        return m;
    }
}


