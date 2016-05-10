package com.github.chrisblutz.messenger.ui;

import com.github.chrisblutz.messenger.Message;
import com.github.chrisblutz.messenger.Messenger;
import com.github.chrisblutz.messenger.resources.Images;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;


/**
 * @author Christopher Lutz
 */
public class MessengerSystemTray {

    private static TrayIcon icon = null;

    public static void notify(int type, String user, String subject) {

        switch (type) {

            case Message.TYPE_INFO:

                if (SystemTray.isSupported() && icon != null) {

                    icon.displayMessage("Messenger: New information!", subject, TrayIcon.MessageType.INFO);
                }

                break;

            case Message.TYPE_ERROR:

                if (SystemTray.isSupported() && icon != null) {

                    icon.displayMessage("Messenger: Error!", subject, TrayIcon.MessageType.ERROR);
                }

                break;

            case Message.TYPE_INCOMING:

                if (SystemTray.isSupported() && icon != null) {

                    icon.displayMessage("New message from '" + user + "'!", "Subject: " + subject, TrayIcon.MessageType.NONE);
                }

                break;
        }
    }

    public static void show() {

        if (SystemTray.isSupported()) {

            if (icon == null) {

                Image i = Images.loadInternal("tray-icon");
                if (i == null) {

                    i = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
                    i.getGraphics().setColor(Color.BLACK);
                    i.getGraphics().fillRect(0, 0, 16, 16);
                }

                PopupMenu menu = new PopupMenu();
                MenuItem show = new MenuItem("Show");
                show.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        if (!Messenger.getUI().isVisible()) {

                            Messenger.getUI().setVisible(true);
                        }
                    }
                });
                MenuItem exit = new MenuItem("Exit");
                exit.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        if (Messenger.getUI().isVisible()) {

                            Messenger.getUI().setVisible(false);
                        }

                        MessengerSystemTray.hide();

                        if (Messenger.connectedFirst && Messenger.getClient() != null && Messenger.getClient().isOpenAndConnected()) {

                            Messenger.getClient().sendDisconnect();
                        }

                        System.exit(0);
                    }
                });
                menu.add(show);
                menu.addSeparator();
                menu.add(exit);

                icon = new TrayIcon(i, "Messenger", menu);
                icon.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        if (!Messenger.getUI().isVisible()) {

                            Messenger.getUI().setVisible(true);
                        }
                    }
                });
            }

            try {

                SystemTray.getSystemTray().add(icon);

            } catch (AWTException e) {

                e.printStackTrace();
            }
        }
    }

    public static void hide() {

        if (SystemTray.isSupported() && icon != null) {

            SystemTray.getSystemTray().remove(icon);
        }
    }
}
