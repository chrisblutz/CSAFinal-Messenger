package com.github.chrisblutz.messenger.ui;

import com.github.chrisblutz.messenger.Message;

import javax.swing.*;
import java.awt.*;


/**
 * @author Christopher Lutz
 */
public class MessengerListCellRenderer implements ListCellRenderer<Message> {

    public static final Color INFO = new Color(50, 200, 255), ERROR = new Color(255, 150, 150), INCOMING = new Color(230, 230, 230), OUTGOING = Color.LIGHT_GRAY;

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
            if (value.getType() == Message.TYPE_OUTGOING) {
                userFont = userFont.deriveFont(Font.BOLD + Font.ITALIC);
                user.setText(value.getUser() + " (You): ");
            }
            user.setFont(userFont);
            user.setForeground(new Color(100, 100, 100));

            JLabel subject = new JLabel("     " + value.getSubject());
            subject.setOpaque(false);
            Font subjectFont = subject.getFont().deriveFont(Font.BOLD);
            subjectFont = subjectFont.deriveFont(subjectFont.getSize() + 3f);
            subject.setFont(subjectFont);

            JTextArea message = new JTextArea();
            message.setText(value.getMessage());
            message.setOpaque(false);
            //message.setBackground(null);
            message.setLineWrap(true);
            message.setWrapStyleWord(false);
            message.setColumns(30);
            message.setRows((int) Math.ceil(value.getMessage().length() / 30));
            message.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

            JPanel fullPanel = new JPanel(new BorderLayout());
            fullPanel.add(user, BorderLayout.WEST);
            fullPanel.add(subject, BorderLayout.CENTER);
            fullPanel.setOpaque(false);

            panel.add(fullPanel, BorderLayout.NORTH);
            panel.add(message, BorderLayout.CENTER);
        }

        //panel.setBorder(BorderFactory.createCompoundBorder(new FilledRoundedBorder(back.darker(), 2, 30), BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        panel.setBorder(new FilledRoundedBorder(back.darker(), 2, 20));
        panel.setPreferredSize(panel.getPreferredSize());
        return panel;
    }
}
