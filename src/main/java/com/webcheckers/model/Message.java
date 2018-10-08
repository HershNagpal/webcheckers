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
  private MessageType messageType;

  public String getText(){
    return this.text;
  }

  public MessageType getType(){
    return this.messageType;
  }
}
