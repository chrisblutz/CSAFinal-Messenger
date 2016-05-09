package com.github.chrisblutz.messenger;

/**
 * @author Christopher Lutz
 */
public class Message {

    public static final int TYPE_INCOMING = 0;
    public static final int TYPE_OUTGOING = 1;
    public static final int TYPE_INFO = 2;
    public static final int TYPE_ERROR = 3;

    private int type = TYPE_INCOMING;
    private String user, subject, message;

    public Message(int type, String user, String subject, String message) {

        this.type = type;
        this.user = user;
        this.subject = subject;
        this.message = message;
    }

    public int getType() {

        return type;
    }

    public String getUser() {

        return user;
    }

    public String getSubject() {

        return subject;
    }

    public String getMessage() {

        return message;
    }
}
