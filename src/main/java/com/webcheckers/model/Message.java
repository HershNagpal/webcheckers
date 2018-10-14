package com.webcheckers.model;

/**
 * 
 * @author Luis Gutierrez
 */
public class Message {

  public enum MessageType{
    INFO,
    ERROR
  }

  /**
   * text - the text for the message to display.
   * type - the context of the message (informational or error).
   */
  private String text;
  private MessageType type;
  
  //
  // Constructor
  //
  public Message(String text, MessageType type) {
    this.text = text;
    this.type = type;
  }

  public String getText(){
    return this.text;
  }

  public MessageType getType(){
    return this.type;
  }
}
