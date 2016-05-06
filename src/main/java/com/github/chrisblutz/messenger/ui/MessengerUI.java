package com.github.chrisblutz.messenger.ui;

/**
 * @author Christopher Lutz
 */
public class MessengerUI {

    private MessengerFrame frame;

    public MessengerUI(){

        frame = new MessengerFrame();

        frame.setSize(650, 700);
        frame.setVisible(true);
    }

    public void printIncoming(String user, String message){

        frame.getOutputPane().printIncoming(user, message);
    }

    public void printOutgoing(String user, String message){

        frame.getOutputPane().printOutgoing(user, message);
    }

    public void printError(String message){

        frame.getOutputPane().printError(message);
    }

    public void printInfo(String message){

        frame.getOutputPane().printInfo(message);
    }

    public void printHorizontalRule(){

        frame.getOutputPane().printHorizontalRule();
    }
}
