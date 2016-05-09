package com.github.chrisblutz.messenger.ui;

import com.github.chrisblutz.messenger.Message;

import javax.swing.*;


/**
 * @author Christopher Lutz
 */
public class MessengerList extends JList<Message> {

    private MessengerListModel model;
    private MessengerListCellRenderer renderer;

    public MessengerList() {

        super(new MessengerListModel());

        model = (MessengerListModel) getModel();
        renderer = new MessengerListCellRenderer();

        setCellRenderer(renderer);
        setOpaque(true);
    }

    public MessengerListModel getListModel(){

        return model;
    }
}
