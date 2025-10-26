package com.myapp.auth;

import static org.junit.Assert.*;
import org.junit.Test;

public class MessageTest {

    @Test
    public void testMessageLengthSuccess() {
        Message msg = new Message("0123456789", "+27831234567", "Hi Mike, can you join us for dinner tonight");
        String result = msg.checkMessageLength();
        assertEquals("Message ready to send.", result);
    }

    @Test
    public void testMessageLengthFailure() {
        String longMessage = new String(new char[260]).replace("\0", "a");
        Message msg = new Message("0123456789", "+27831234567", longMessage);
        String result = msg.checkMessageLength();
        assertTrue(result.startsWith("Message exceeds 250 characters by"));
    }

    @Test
    public void testRecipientFormatSuccess() {
        Message msg = new Message("0123456789", "+27718693002", "Hi Mike");
        String result = msg.checkRecipientCell();
        assertEquals("Cell phone number successfully captured.", result);
    }

    @Test
    public void testRecipientFormatFailure() {
        Message msg = new Message("0123456789", "08575975889", "Hi Keegan");
        String result = msg.checkRecipientCell();
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", result);
    }

    @Test
    public void testMessageHash() {
        Message msg = new Message("0012345678", "+27718693002", "Hi Mike, can you join us for dinner tonight");
        msg.setMessageNumber(0);
        String hash = msg.createMessageHash();
        assertEquals("00:0:HI TONIGHT", hash);
    }

    @Test
    public void testMessageIDGenerated() {
        Message msg = new Message(null, "+27831234567", "Hi");
        String id = msg.generateMessageID();
        assertNotNull(id);
        assertEquals(10, id.length());
    }

    @Test
    public void testSendMessageOptions() {
        Message msg1 = new Message("0123456789", "+27831234567", "Hi");
        assertEquals("Message successfully sent.", msg1.sendMessage("1"));

        Message msg2 = new Message("0123456789", "+27831234567", "Hi");
        assertEquals("Press 0 to delete message.", msg2.sendMessage("2"));

        Message msg3 = new Message("0123456789", "+27831234567", "Hi");
        assertEquals("Message successfully stored.", msg3.sendMessage("3"));
    }
}
