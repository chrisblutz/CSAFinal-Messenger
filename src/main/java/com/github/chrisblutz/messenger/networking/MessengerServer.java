package com.github.chrisblutz.messenger.networking;

import com.github.chrisblutz.packets.Packet;
import com.github.chrisblutz.relay.ConnectionGroup;
import com.github.chrisblutz.relay.RelayServer;
import com.github.chrisblutz.relay.listeners.RelayListener;
import com.github.chrisblutz.sockets.Connection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Christopher Lutz
 */
public class MessengerServer {

    private RelayServer server;
    private Map<Connection, String> users = new HashMap<>();

    public MessengerServer() {

        server = new RelayServer(12345, "Messenger-Server");
        server.addRelayListener(new RelayListener() {

            @Override
            public Packet onConnect(RelayServer server, Connection c, Packet data) {

                int currConn = 0;
                String[] currConnList = {};

                if (server.hasGroup(NetConstants.RELAY_GROUP)) {

                    ConnectionGroup group = server.getGroupForId(NetConstants.RELAY_GROUP);
                    currConn = group.size();

                    List<String> userList = new ArrayList<String>();
                    for (Connection conn : group) {

                        if (users.containsKey(conn)) {

                            userList.add(users.get(conn));
                        }
                    }

                    currConnList = userList.toArray(new String[userList.size()]);
                }

                data.putData(NetConstants.CONNECTION_NUMBER_CURRENTLY_CONNECTED, currConn);
                data.putData(NetConstants.CONNECTION_LIST_CURRENT_USERS, currConnList);

                return data;
            }

            @Override
            public void onClientFailure(RelayServer server, Connection c) {

                // Send to all
                if (server.hasGroup(NetConstants.RELAY_GROUP) && users.containsKey(c)) {

                    ConnectionGroup group = server.getGroupForId(NetConstants.RELAY_GROUP);
                    if (group.size() > 0) {

                        Packet p = new Packet();
                        p.putData(NetConstants.CONNECTION_MESSAGE, "Connection to user '" + users.get(c) + "' lost!");
                        p.putData(NetConstants.CONNECTION_ERROR, true);

                        group.sendPacket(c, p);
                    }
                }

                users.remove(c);
            }

            @Override
            public void onReceive(RelayServer server, Connection c, Packet data) {

                ConnectionGroup group;

                if (server.hasGroup(NetConstants.RELAY_GROUP)) {

                    group = server.getGroupForId(NetConstants.RELAY_GROUP);

                } else {

                    group = null;
                }

                Packet toBeSent = new Packet();

                if (data.hasData(NetConstants.CONNECTION_NEW_REQUEST)) {

                    if (group != null) {

                        if (!group.containsConnection(c)) {

                            group.addConnection(c);
                        }

                    } else {

                        server.group(NetConstants.RELAY_GROUP, c);
                    }

                    String name = data.getData(NetConstants.CONNECTION_NEW_REQUEST).toString();

                    users.put(c, name);

                    group = server.getGroupForId(NetConstants.RELAY_GROUP);

                    if (group != null) {

                        toBeSent.putData(NetConstants.CONNECTION_MESSAGE, "User '" + name + "' connected!");
                        toBeSent.putData(NetConstants.CONNECTION_INFO, true);
                    }
                }

                if (group != null) {

                    String user = users.containsKey(c) ? users.get(c) : "?";
                    String subject = "", message = "";

                    if (data.hasData(NetConstants.CONNECTION_MESSAGE)) {

                        message = data.getData(NetConstants.CONNECTION_MESSAGE).toString();
                        toBeSent.putData(NetConstants.CONNECTION_MESSAGE, message);

                        // ERRORS
                        if (data.hasData(NetConstants.CONNECTION_ERROR)) {

                            toBeSent.putData(NetConstants.CONNECTION_ERROR, true);

                        } else if (data.hasData(NetConstants.CONNECTION_INFO)) {

                            toBeSent.putData(NetConstants.CONNECTION_INFO, true);
                        }
                    }

                    if (data.hasData(NetConstants.CONNECTION_SUBJECT)) {

                        subject = data.getData(NetConstants.CONNECTION_SUBJECT).toString();
                        toBeSent.putData(NetConstants.CONNECTION_SUBJECT, subject);
                    }

                    // DISCONNECT FLAG
                    if (data.hasData(NetConstants.CONNECTION_DISCONNECT_FLAG)) {

                        System.out.println("Acknowledged disconnect!");

                        Packet response = new Packet();
                        response.putData(NetConstants.CONNECTION_DISCONNECT_ACKNOWLEDGEMENT, true);
                        c.sendPacket(response, false);

                        toBeSent.putData(NetConstants.CONNECTION_DISCONNECT_FLAG, true);
                        group.removeConnection(c);
                        users.remove(c);
                    }

                    if (!toBeSent.isEmpty()) {

                        toBeSent.putData(NetConstants.CONNECTION_NAME, user);

                        group.sendPacket(c, toBeSent);
                    }
                }
            }

            @Override
            public void onTimeout(RelayServer server, Connection c) {

                server.ungroupAll(c);

                // Send to all
                if (server.hasGroup(NetConstants.RELAY_GROUP) && users.containsKey(c)) {

                    ConnectionGroup group = server.getGroupForId(NetConstants.RELAY_GROUP);
                    if (group.size() > 0) {

                        Packet p = new Packet();
                        p.putData(NetConstants.CONNECTION_MESSAGE, "User '" + users.get(c) + "' timed out!");
                        p.putData(NetConstants.CONNECTION_ERROR, true);

                        group.sendPacket(c, p);
                    }
                }

                users.remove(c);
            }
        });
    }

    public void start() throws IOException {

        server.start();
    }

    public void stop() throws IOException {

        server.close();
    }
}
