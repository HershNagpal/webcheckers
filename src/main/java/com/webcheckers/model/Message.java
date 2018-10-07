package com.webcheckers.model;

public class Message {

  private enum MessageType{
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
