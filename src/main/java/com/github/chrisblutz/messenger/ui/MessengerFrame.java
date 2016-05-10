package com.github.chrisblutz.messenger.ui;

import com.github.chrisblutz.messenger.Message;
import com.github.chrisblutz.messenger.Messenger;
import com.github.chrisblutz.messenger.resources.Images;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;


/**
 * @author Christopher Lutz
 */
public class MessengerFrame extends JFrame {

    // Menu bar
    private JMenuBar menuBar;
    private JMenu fileMenu;

    // Window
    private JScrollPane outputScroll;
    private MessengerList outputList;
    private JPanel inputPanel, messagePanel;
    private JLabel subjectL, messageL;
    private JTextField inputField;
    private JTextArea messageField;
    private JButton inputButton;

    private boolean subjectIsEmpty = true;
    private boolean messageIsEmpty = true;

    public MessengerFrame() {

        super("Messenger");

        try {

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (Exception e) {

            System.err.println("Look and feel not set!");
            e.printStackTrace();
        }

        Image i = Images.loadInternal("icon");
        if (i == null) {

            i = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
            i.getGraphics().setColor(Color.BLACK);
            i.getGraphics().fillRect(0, 0, 32, 32);
        }

        setIconImage(i);

        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");

        menuBar.add(fileMenu);

        outputList = new MessengerList();

        outputScroll = new JScrollPane(outputList);

        subjectL = new JLabel("  Subject:   ");
        inputField = new JTextField();
        inputField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {

                if (inputField.getText().isEmpty()) {

                    subjectIsEmpty = true;

                } else {

                    subjectIsEmpty = false;
                }

                if (subjectIsEmpty || messageIsEmpty) {

                    inputButton.setEnabled(false);

                } else {

                    inputButton.setEnabled(true);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

                if (inputField.getText().isEmpty()) {

                    subjectIsEmpty = true;

                } else {

                    subjectIsEmpty = false;
                }

                if (subjectIsEmpty || messageIsEmpty) {

                    inputButton.setEnabled(false);

                } else {

                    inputButton.setEnabled(true);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

                if (inputField.getText().isEmpty()) {

                    subjectIsEmpty = true;

                } else {

                    subjectIsEmpty = false;
                }

                if (subjectIsEmpty || messageIsEmpty) {

                    inputButton.setEnabled(false);

                } else {

                    inputButton.setEnabled(true);
                }
            }
        });
        inputButton = new JButton("Send");
        inputButton.setEnabled(false);

        messageL = new JLabel("  Message: ");
        messageField = new JTextArea();
        messageField.setLineWrap(true);
        messageField.setWrapStyleWord(false);
        messageField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {

                if (messageField.getText().isEmpty()) {

                    messageIsEmpty = true;

                } else {

                    messageIsEmpty = false;
                }

                if (subjectIsEmpty || messageIsEmpty) {

                    inputButton.setEnabled(false);

                } else {

                    inputButton.setEnabled(true);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

                if (messageField.getText().isEmpty()) {

                    messageIsEmpty = true;

                } else {

                    messageIsEmpty = false;
                }

                if (subjectIsEmpty || messageIsEmpty) {

                    inputButton.setEnabled(false);

                } else {

                    inputButton.setEnabled(true);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

                if (messageField.getText().isEmpty()) {

                    messageIsEmpty = true;

                } else {

                    messageIsEmpty = false;
                }

                if (subjectIsEmpty || messageIsEmpty) {

                    inputButton.setEnabled(false);

                } else {

                    inputButton.setEnabled(true);
                }
            }
        });

        ActionListener listener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String subject = inputField.getText();
                String message = messageField.getText();

                if (!subject.trim().isEmpty() && !message.trim().isEmpty()) {

                    Messenger.getClient().sendMessage(subject, message);
                    Messenger.getUI().printMessage(Message.TYPE_OUTGOING, Messenger.getMessengerName(), subject, message);
                }

                inputField.setText("");
                messageField.setText("");
                inputButton.setEnabled(false);
            }
        };

        inputButton.addActionListener(listener);

        inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(subjectL, BorderLayout.WEST);
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(inputButton, BorderLayout.EAST);

        messagePanel = new JPanel(new BorderLayout());
        messagePanel.add(messageL, BorderLayout.WEST);
        messagePanel.add(inputPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(messageField);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(0, 120));

        messagePanel.add(scrollPane, BorderLayout.CENTER);
        messagePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 0));

        setLayout(new BorderLayout());

        add(outputScroll, BorderLayout.CENTER);
        add(messagePanel, BorderLayout.SOUTH);

        setJMenuBar(menuBar);

        setDefaultCloseOperation(HIDE_ON_CLOSE);
        addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {

                inputField.requestFocusInWindow();
            }

            @Override
            public void windowClosing(WindowEvent e) {

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

    public MessengerList getOutputList() {

        return outputList;
    }
}
