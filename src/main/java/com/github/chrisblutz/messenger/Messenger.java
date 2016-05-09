package com.github.chrisblutz.messenger;

import com.github.chrisblutz.messenger.networking.MessengerClient;
import com.github.chrisblutz.messenger.ui.MessengerUI;

import java.io.IOException;


/**
 * @author Christopher Lutz
 */
public class Messenger {

    private static MessengerUI ui;
    private static String name;
    private static MessengerClient client;

    public static void setup(String name) {

        Messenger.name = name;

        ui = new MessengerUI();

        // DEMO
        client = new MessengerClient("localhost");
        try {

            client.connect();

        } catch (IOException e) {

            e.printStackTrace();
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
