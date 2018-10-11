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
  private String text;
  private MessageType type;

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
