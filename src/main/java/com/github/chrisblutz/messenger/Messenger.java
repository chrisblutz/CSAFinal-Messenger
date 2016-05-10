package com.github.chrisblutz.messenger;

import com.github.chrisblutz.messenger.networking.MessengerClient;
import com.github.chrisblutz.messenger.ui.IPSelector;
import com.github.chrisblutz.messenger.ui.MessengerUI;

import java.io.IOException;


/**
 * @author Christopher Lutz
 */
public class Messenger {

    private static MessengerUI ui;
    private static String name;
    private static MessengerClient client;
    public static boolean connectedFirst = false;

    public static void setup(String name) {

        Messenger.name = name;

        new IPSelector();
    }

    public static void continueSetup(String ip) {

        ui = new MessengerUI();

        client = new MessengerClient(ip);
        try {

            client.connect();

            connectedFirst = true;

        } catch (IOException e) {

            getUI().printMessage(Message.TYPE_ERROR, "", "", "Unable to connect on '" + ip + "'!");
        }
    }

    public static MessengerUI getUI() {

        return ui;
    }

    public static String getMessengerName() {

        return name;
    }

    public static MessengerClient getClient() {

        return client;
    }
}
