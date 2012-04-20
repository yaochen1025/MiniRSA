package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.*;

import RSA.Encryptor;

import message.MessageReceiver;
import modular.Server;


public class ChattingGUI extends JFrame {

	private ServerSocket serverSocket;
	private Socket socket;
	
	private int port;
	MessageReceiver msgReceiver;
	//MessageSender msgSender;
	Encryptor encryptor = new Encryptor(new BigInteger("451"),new BigInteger("2623"));


	public static void main(String[] args) {

//		if (args.length < 1) {
//			System.out.println("missing arguments");
//			System.exit(-1);
//		}

		int port = 8080;
//		try {
//			port = Integer.parseInt(args[0].trim());
//		}catch (NumberFormatException e) {
//			System.out.println("error in port number");
//			System.exit(-1);
//		}
		ChattingGUI c = new ChattingGUI();
		c.port = port;
		c.run();
	}

	private static final long serialVersionUID = 1L;

	public static JTextArea chattingArea = new JTextArea(""); 
	public JTextArea editingArea = new JTextArea("");

	private JButton sendButton = new JButton("send");
	
	
	public void run() {

		
		this.setPreferredSize(new Dimension(400, 800));
		//chattingArea.setEditable(false);
		this.setLayout(new BorderLayout());
		//chattingArea.setPreferredSize(new Dimension(400, 575));
	//	editingArea.setPreferredSize(new Dimension(400, 175));
	//	sendButton.setPreferredSize(new Dimension(400, 50));
		sendButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				send();
			}

		});
		this.add(sendButton, BorderLayout.CENTER);
		this.add(chattingArea, BorderLayout.NORTH);
		this.add(editingArea, BorderLayout.SOUTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setUpServer();

	}

	private void setUpServer() {
		try {
		serverSocket = new ServerSocket(port);
		socket = serverSocket.accept();
		
			this.msgReceiver = new MessageReceiver(socket.getInputStream());
			//this.msgSender = new MessageSender(this.server.getSocket().getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void send() {
		String current = this.editingArea.getText();
		this.editingArea.setText("");
		write(current);
		print("me:\n" + current + "\n\n");

	}

	synchronized public static void print(String message) {
		String current = chattingArea.getText();
		chattingArea.setText(current + message);
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
