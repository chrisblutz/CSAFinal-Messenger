package com.github.chrisblutz.messenger.ui;

import com.github.chrisblutz.messenger.Messenger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


/**
 * @author Christopher Lutz
 */
public class MessengerFrame extends JFrame {

    // Menu bar
    private JMenuBar menuBar;
    private JMenu fileMenu;

    // Window
    private JScrollPane outputScroll;
    private MessengerOutputPane outputPane;
    private JPanel inputPanel;
    private JTextField inputField;
    private JButton inputButton;

    public MessengerFrame() {

        super("Messenger");

        try {

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (Exception e) {

            System.err.println("Look and feel not set!");
            e.printStackTrace();
        }

        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");

        menuBar.add(fileMenu);

        outputPane = new MessengerOutputPane();

        outputScroll = new JScrollPane(outputPane);
        outputScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        inputField = new JTextField();
        inputButton = new JButton("Send");

        ActionListener listener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String text = inputField.getText();

                if (!text.trim().isEmpty()) {

                    Messenger.getClient().sendMessage(text);
                    Messenger.getUI().printOutgoing(Messenger.getMessengerName(), text);
                }

                inputField.setText("");
            }
        };

        inputField.addActionListener(listener);
        inputButton.addActionListener(listener);

        inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(inputButton, BorderLayout.EAST);

        setLayout(new BorderLayout());

        add(outputScroll, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        setJMenuBar(menuBar);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {

                inputField.requestFocusInWindow();
            }

            @Override
            public void windowClosing(WindowEvent e) {

                MessengerFrame.this.setVisible(false);
                Messenger.getClient().sendDisconnect();
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

    public MessengerOutputPane getOutputPane() {

        return outputPane;
    }
}
