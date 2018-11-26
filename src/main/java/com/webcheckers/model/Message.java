package com.webcheckers.model;

/**
 * Used to display a message from the server.
 * @author Luis Gutierrez
 */
public class Message {

    /**
     * The text that this message contains.
     */
    private String text;

    /**
     * The type of this message (Error or Info).
     */
    private MessageType type;

    /**
     * Construct a message.
     */
    public Message(String text, MessageType type) {
        this.text = text;
        this.type = type;
    }

    /**
     * Return the text of this message.
     * 
     * @return the String text of this message.
     */
    public String getText(){
        return this.text;
    }

    /**
     * Return whether this message is an error or info.
     * 
     * @return the MessageType of this message.
     */
    public MessageType getType(){
        return this.type;
    }
}
