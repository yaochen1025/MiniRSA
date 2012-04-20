package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.io.*;
import java.net.*;

import java.math.BigInteger;

import RSA.Encryptor;

public class ClientGUI extends JFrame {

	private ServerSocket serverSocket;
	private Socket socket;

	private int port;
	ReceiverForGUI msgReceiver;
	Encryptor encryptor = new Encryptor(new BigInteger("451"),new BigInteger("2623"));


	public static void main(String[] args) {
		ClientGUI c = new ClientGUI();
		c.port = 8080;
		c.run();
	}

	private static final long serialVersionUID = 1L;

	public static JTextArea chat = new JTextArea(""); 
	private JTextArea edit = new JTextArea("");
	private JScrollPane chattingPane = new JScrollPane(chat);
	private JScrollPane editingPane = new JScrollPane(edit);
	private JButton sendButton = new JButton("send");


	public void run() {

		this.setPreferredSize(new Dimension(400, 800));
		this.setLayout(new BorderLayout());
		JPanel editPanel = new JPanel();
		editPanel.setLayout(new BorderLayout());
		editPanel.add(sendButton, BorderLayout.WEST);
		editPanel.add(editingPane, BorderLayout.CENTER);
		
		chat.setEditable(false);
		chattingPane.setPreferredSize(new Dimension(400,600));
		editPanel.setPreferredSize(new Dimension(400,200));
		
		this.add(chattingPane, BorderLayout.NORTH);
		this.add(editPanel, BorderLayout.SOUTH);

		chattingPane.setBorder(new TitledBorder("Conversation"));
		chattingPane.setBorder(new TitledBorder("Input"));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		sendButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				send();
			}

		});
		this.setUpServer();

	}
	
	synchronized public static void print(String message) {
		String current = chat.getText();
		chat.setText(current + message);
	}

	private void setUpServer() {
		try {
			
			serverSocket = new ServerSocket(port);
			socket = serverSocket.accept();
			
			this.msgReceiver = new ReceiverForGUI(socket.getInputStream());
			this.msgReceiver.start();
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	void send() {
		String current = this.edit.getText();
		this.edit.setText("");
		//if ("".equals(current.trim())) return;
		write(current);
		print("me:\n" + current + "\n\n");

	}

	private void write(String s) {
		OutputStream out;
		try {
			out = this.socket.getOutputStream();
			for (int i = 0; i < s.length(); i++) {
				BigInteger x = this.encryptor.encrypt(s.charAt(i));
				out.write((x+"\\c").getBytes());			
			}
			out.write(("\n").getBytes());	
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
