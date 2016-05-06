package com.github.chrisblutz.messenger.networking;

import com.github.chrisblutz.listeners.ClientListener;
import com.github.chrisblutz.messenger.Messenger;
import com.github.chrisblutz.packets.Packet;
import com.github.chrisblutz.relay.RelayClient;
import com.github.chrisblutz.sockets.Connection;

import java.io.IOException;


/**
 * @author Christopher Lutz
 */
public class MessengerClient {

    private String ip;
    private RelayClient client;
    private boolean disconnectAcknowledged = false, timedOut = false;

    public MessengerClient(String ip) {

        this.ip = ip;

        client = new RelayClient(ip, 12345, "Messenger-Client");
        client.addNetworkListener(new ClientListener() {

            @Override
            public void onConnect(Packet packet) {

                if (packet.hasData(NetConstants.CONNECTION_LIST_CURRENT_USERS) && packet.hasData(NetConstants.CONNECTION_NUMBER_CURRENTLY_CONNECTED)) {

                    int currentlyConnected = Integer.parseInt(packet.getData(NetConstants.CONNECTION_NUMBER_CURRENTLY_CONNECTED).toString());
                    String[] users = (String[]) packet.getData(NetConstants.CONNECTION_LIST_CURRENT_USERS);

                    String userStr = "";

                    for (int i = 0; i < users.length; i++) {

                        userStr += users[i];

                        if (i < users.length - 1) {

                            userStr += ", ";
                        }
                    }

                    Messenger.getUI().printInfo("Connected on '" + ip + "'!  There " + (currentlyConnected != 1 ? "are " : "is ") + currentlyConnected + (currentlyConnected != 1 ? " people" : " person") + " already connected." + (currentlyConnected > 0 ? " (" + userStr + ")" : ""));
                }

                Packet p = new Packet();
                p.putData(NetConstants.CONNECTION_NEW_REQUEST, Messenger.getMessengerName());

                client.sendPacket(p, false);
            }

            @Override
            public void onReceive(Connection connection, Packet packet) {

                String user = "?";

                if (packet.hasData(NetConstants.CONNECTION_NAME)) {

                    user = packet.getData(NetConstants.CONNECTION_NAME).toString();
                }

                // ERRORS
                if (packet.hasData(NetConstants.CONNECTION_MESSAGE_ERROR)) {

                    String message = packet.getData(NetConstants.CONNECTION_MESSAGE_ERROR).toString();
                    Messenger.getUI().printError(message);
                }

                // INFO
                if (packet.hasData(NetConstants.CONNECTION_MESSAGE_INFO)) {

                    String message = packet.getData(NetConstants.CONNECTION_MESSAGE_INFO).toString();
                    Messenger.getUI().printInfo(message);
                }

                // MESSAGE
                if (packet.hasData(NetConstants.CONNECTION_MESSAGE)) {

                    String message = packet.getData(NetConstants.CONNECTION_MESSAGE).toString();
                    Messenger.getUI().printIncoming(user, message);
                }

                // DISCONNECT FLAG
                if (packet.hasData(NetConstants.CONNECTION_DISCONNECT_FLAG)) {

                    Messenger.getUI().printInfo("User '" + user + "' disconnected!");
                }

                if (packet.hasData(NetConstants.CONNECTION_DISCONNECT_ACKNOWLEDGEMENT)) {

                    disconnectAcknowledged = true;
                }
            }

            @Override
            public void onTimeout(Connection connection) {

                Messenger.getUI().printError("Connection lost (timed out)!");
                timedOut = true;
            }
        });
    }

    public void sendMessage(String message) {

        Packet p = new Packet();
        p.putData(NetConstants.CONNECTION_NAME, Messenger.getMessengerName());
        p.putData(NetConstants.CONNECTION_MESSAGE, message);

        client.sendPacket(p, false);
    }

    public void sendError(String message) {

        Packet p = new Packet();
        p.putData(NetConstants.CONNECTION_NAME, Messenger.getMessengerName());
        p.putData(NetConstants.CONNECTION_MESSAGE_ERROR, message);

        client.sendPacket(p, false);
    }

    public void sendInfo(String message) {

        Packet p = new Packet();
        p.putData(NetConstants.CONNECTION_NAME, Messenger.getMessengerName());
        p.putData(NetConstants.CONNECTION_MESSAGE_INFO, message);

        client.sendPacket(p, false);
    }

    public void sendDisconnect() {

        if (client.getConnection().isConnected() && !client.getConnection().isClosed()) {

            Packet p = new Packet();
            p.putData(NetConstants.CONNECTION_NAME, Messenger.getMessengerName());
            p.putData(NetConstants.CONNECTION_DISCONNECT_FLAG, true);

            client.sendPacket(p, true);

            while (!disconnectAcknowledged && !timedOut) {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void connect() throws IOException {

        client.connect();
    }

    public void close() throws IOException {

        client.close();
    }
}
