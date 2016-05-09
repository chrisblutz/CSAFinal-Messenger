package com.github.chrisblutz.messenger.ui;

import com.github.chrisblutz.messenger.Message;

import javax.swing.*;
import java.awt.*;


/**
 * @author Christopher Lutz
 */
public class MessengerListCellRenderer implements ListCellRenderer<Message> {

    public static final Color INFO = new Color(0, 150, 255), ERROR = Color.RED, INCOMING = Color.WHITE, OUTGOING = Color.LIGHT_GRAY;

    @Override
    public Component getListCellRendererComponent(JList<? extends Message> list, Message value, int index, boolean isSelected, boolean cellHasFocus) {

        JPanel panel = new JPanel(new BorderLayout());

        Color back;

        switch (value.getType()) {

            case Message.TYPE_INCOMING:
                back = INCOMING;
                break;
            case Message.TYPE_OUTGOING:
                back = OUTGOING;
                break;
            case Message.TYPE_INFO:
                back = INFO;
                break;
            case Message.TYPE_ERROR:
                back = ERROR;
                break;
            default:
                back = Color.WHITE;
                break;
        }

        panel.setBackground(back);

        if (value.getType() == Message.TYPE_ERROR || value.getType() == Message.TYPE_INFO) {

            JLabel message = new JLabel(value.getMessage());
            panel.add(message, BorderLayout.CENTER);

        } else if (value.getType() == Message.TYPE_INCOMING || value.getType() == Message.TYPE_OUTGOING) {

            JLabel user = new JLabel(value.getUser() + ": ");
            user.setOpaque(false);
            Font userFont = user.getFont().deriveFont(Font.BOLD);
            userFont = userFont.deriveFont(userFont.getSize() + 3f);
            user.setFont(userFont);
            user.setForeground(new Color(100, 100, 100));

            JLabel subject = new JLabel("     " + value.getSubject());
            subject.setOpaque(false);
            Font subjectFont = subject.getFont().deriveFont(Font.BOLD);
            subjectFont = subjectFont.deriveFont(subjectFont.getSize() + 3f);
            subject.setFont(subjectFont);

            JLabel message = new JLabel(value.getMessage());
            message.setOpaque(false);
            message.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

            JPanel fullPanel = new JPanel(new BorderLayout());
            fullPanel.add(user, BorderLayout.WEST);
            fullPanel.add(subject, BorderLayout.CENTER);
            fullPanel.setOpaque(false);

            panel.add(fullPanel, BorderLayout.NORTH);
            panel.add(message, BorderLayout.CENTER);
        }

        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return panel;
    }
}
