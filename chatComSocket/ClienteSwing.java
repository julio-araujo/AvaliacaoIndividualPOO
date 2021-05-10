package chatComSocket;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.nio.file.attribute.UserPrincipal;
import java.util.Scanner;

public class ClienteSwing extends JFrame {


    private static final long serialVersionUID = -5261903818373181455L;
    private JTextArea taEditor = new JTextArea("digite aqui sua mensagem");
    private JTextArea taVisor = new JTextArea();
    private JList liUsarios = new JList();

    public void ClienteSwing() {
        setTitle("Chat com socket");

        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        add(taEditor, BorderLayout.NORTH);
        add(taVisor, BorderLayout.CENTER);
        add(liUsarios, BorderLayout.EAST);
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {
        new ClienteSwing();
    }


}





