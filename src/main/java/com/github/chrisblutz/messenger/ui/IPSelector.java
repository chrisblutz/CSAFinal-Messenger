package com.github.chrisblutz.messenger.ui;

import com.github.chrisblutz.messenger.Messenger;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;


/**
 * @author Christopher Lutz
 */
public class IPSelector extends JFrame {

    private JLabel ipL;
    private JTextField ipField;
    private JPanel inputPanel;
    private JButton submit;

    public IPSelector() {

        super("Messenger - Select IP");

        try {

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (Exception e) {

            System.err.println("Look and feel not set!");
            e.printStackTrace();
        }

        ipL = new JLabel("IP: ");
        ipField = new JTextField();
        ipField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {

                if (!ipField.getText().isEmpty()) {

                    submit.setEnabled(true);

                } else {

                    submit.setEnabled(false);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

                if (!ipField.getText().isEmpty()) {

                    submit.setEnabled(true);

                } else {

                    submit.setEnabled(false);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

                if (!ipField.getText().isEmpty()) {

                    submit.setEnabled(true);

                } else {

                    submit.setEnabled(false);
                }
            }
        });
        inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(ipL, BorderLayout.WEST);
        inputPanel.add(ipField, BorderLayout.CENTER);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 0));

        submit = new JButton("Select");
        submit.setEnabled(false);

        ActionListener listener = e -> {

            if (!ipField.getText().isEmpty()) {

                String ip = ipField.getText();

                setVisible(false);
                dispose();

                Messenger.continueSetup(ip);
            }
        };

        ipField.addActionListener(listener);
        submit.addActionListener(listener);

        add(inputPanel, BorderLayout.CENTER);
        add(submit, BorderLayout.SOUTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(300, 120);
        setResizable(false);
        setVisible(true);
    }
}
