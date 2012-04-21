package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import rsa.Encryptor;


public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	static DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	
	private ServerSocket serverSocket;
	private Socket socket;
	private static String ipAddress = "localhost";
	private static int port = 8282;

	Reader msgReceiver;
	Encryptor encryptor = new Encryptor(new BigInteger("451"),new BigInteger("2623"));


	public static void main(String[] args) {
		if (args.length >= 2) {
			try {
				ipAddress = args[0];
				port = Integer.parseInt(args[1].trim());
			}catch (NumberFormatException e) {
				System.out.println("error in port number");
				System.exit(-1);
			}
		}
		GUI end = new GUI();
		end.run();
	}

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
		editPanel.add(sendButton, BorderLayout.EAST);
		editPanel.add(editingPane, BorderLayout.CENTER);

		chat.setEditable(false);
		chattingPane.setPreferredSize(new Dimension(400,500));
		editPanel.setPreferredSize(new Dimension(400,200));

		this.add(chattingPane, BorderLayout.CENTER);
		this.add(editPanel, BorderLayout.SOUTH);

		chattingPane.setBorder(new TitledBorder("Conversation"));
		editingPane.setBorder(new TitledBorder("Input"));

		this.sendButton.setEnabled(false);

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
			try {
				this.socket = new Socket(ipAddress, port);
				this.setTitle("Client");
				print("Connected to server.\n\n");
				this.msgReceiver = new Reader(socket.getInputStream(), "server");
				
			} catch (Exception e){
				this.setTitle("Server");
				serverSocket = new ServerSocket(port);
				socket = serverSocket.accept();
				print("Client connected.\n\n");
				this.msgReceiver = new Reader(socket.getInputStream(), "client");
			}
			this.msgReceiver.start();
			this.sendButton.setEnabled(true);

		} catch (IOException e) {
			shutDown();
		}
	}

	void send() {
		String current = this.edit.getText();
		this.edit.setText("");
		//if ("".equals(current.trim())) return;
		write(current);
		Date date = new Date();
		GUI.print("me" + "   " + GUI.dateFormat.format(date) + ":\n" + current + "\n");
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
			shutDown();
		}

	}
	
	void shutDown() {
		try {
			if(serverSocket != null) {
				serverSocket.close();
			}
			if(serverSocket != null) {
				serverSocket.close();
			}
		} catch (IOException e) {
			//
		} finally {
			System.out.println("Done");
		}
		System.exit(0);
	}
}
