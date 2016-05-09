package com.github.chrisblutz.messenger.ui;

import com.github.chrisblutz.messenger.Message;


/**
 * @author Christopher Lutz
 */
public class MessengerUI {

    private MessengerFrame frame;

    public MessengerUI() {

        frame = new MessengerFrame();

        frame.setSize(650, 700);
        frame.setVisible(true);

        MessengerSystemTray.show();
    }

    public void printMessage(int type, String user, String subject, String message) {

        frame.getOutputList().getListModel().addElement(new Message(type, user, subject, message));
        frame.getOutputList().revalidate();
        frame.getOutputList().repaint();

        MessengerSystemTray.notify(type, user, type == Message.TYPE_ERROR || type == Message.TYPE_INFO ? message : subject);
    }

    public MessengerList getOutputList() {

        return frame.getOutputList();
    }

    public boolean isVisible() {

        return frame.isVisible();
    }

    public void setVisible(boolean visible) {

        frame.setVisible(true);
    }
}
