package com.example.encrypto.models;

public class Message {

    private String senderId;
    private String receiverId;
    private String textMessage;
    private String imageMessage;

    public Message() {
    }

    public Message(String senderId, String receiverId, String textMessage, String imageMessage) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.textMessage = textMessage;
        this.imageMessage = imageMessage;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public String getImageMessage() {
        return imageMessage;
    }

    @Override
    public String toString() {
        return "Message{" +
                "senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", textMessage='" + textMessage + '\'' +
                ", imageMessage='" + imageMessage + '\'' +
                '}';
    }
}
