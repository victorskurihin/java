package ru.otus.l151.servlet;

/**
 * Our goal would be to build a WebSockets server which accepts messages from
 * the clients and broadcasts them to all other clients currently connected.
 * To begin with, let's define the message format, which server and client will
 * be exchanging, as this simple Message class.
 */
public class Message {
    private String username;
    private String message;

    public Message() { /* None */ }

    public Message( final String username, final String message ) {
        this.username = username;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    public void setMessage( final String message ) {
        this.message = message;
    }

    public void setUsername( final String username ) {
        this.username = username;
    }
}
