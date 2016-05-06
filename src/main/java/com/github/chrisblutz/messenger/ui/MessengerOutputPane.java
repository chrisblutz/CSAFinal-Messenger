package com.github.chrisblutz.messenger.ui;

import com.github.chrisblutz.messenger.Formatter;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Christopher Lutz
 */
public class MessengerOutputPane extends JEditorPane {

    private HTMLEditorKit editorKit;
    private List<String> messages = new ArrayList<>();

    public MessengerOutputPane() {

        setEditable(false);

        editorKit = new HTMLEditorKit();

        setupCSS();

        setContentType("text/html");
        setEditorKit(editorKit);
        setDocument(editorKit.createDefaultDocument());
    }

    public void printIncoming(String user, String message) {

        Color incoming = Color.BLACK;
        printMessage(incoming, formatUserString(user, message));
    }

    public void printOutgoing(String user, String message) {

        Color outgoing = Color.GRAY;
        printMessage(outgoing, formatUserString(user, message));
    }

    public void printError(String message) {

        Color error = Color.RED;
        printMessage(error, message);
    }

    public void printInfo(String message) {

        Color info = Color.BLUE.brighter();
        printMessage(info, message);
    }

    public void printHorizontalRule() {

        Color horizontalRule = Color.DARK_GRAY;
        printMessage(horizontalRule, "<hr>");
    }

    private void printMessage(Color color, String message) {

        String colorHex = Formatter.formatColorAsHexadecimal(color);
        String text = "<div style='color: " + colorHex + ";'>" + message + "</div>";

        messages.add(text);

        setText("<html>" + compileMessages() + "</html>");
        revalidate();
        repaint();
    }

    private void setupCSS() {

        StyleSheet styleSheet = editorKit.getStyleSheet();

        String hr = "hr { " +
                "border: none; " +
                "height: 0px; " +
                "background: " + Formatter.formatColorAsHexadecimal(Color.DARK_GRAY) + "; " +
                "}";
        styleSheet.addRule(hr);
        String div = "div { " +
                "padding: 4px; " +
                "padding-left: 8px; " +
                "padding-bottom: 0px; " +
                "}";
        styleSheet.addRule(div);
    }

    private String compileMessages() {

        String messageStr = "";

        for (String message : messages) {

            messageStr += message + "\n";
        }

        return messageStr;
    }

    public static String formatUserString(String user, String message) {

        return "[" + user + "] " + message;
    }
}
