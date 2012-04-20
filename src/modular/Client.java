package modular;

import java.io.*;
import java.net.*;

import message.MessageReceiver;
import message.MessageSender;


public class Client {


	private static int port;
	private static String ipAddress;

	public static void main(String[] args) {
		
		if (args.length < 2) {
			System.out.println("missing arguments");
			System.exit(-1);
		}

		try {
			ipAddress = args[0];
			port = Integer.parseInt(args[1].trim());
		}catch (NumberFormatException e) {
			System.out.println("error in port number");
			System.exit(-1);
		}
		
		new Client().run();
	}

	public void run() {
		try {
			//TODO
			Socket socket = new Socket(InetAddress.getLocalHost(), port);
			System.out.println("client:connected");

			new MessageSender(socket.getOutputStream()).start();
			new MessageReceiver(socket.getInputStream()).start();

		}catch (IOException e) {
			e.printStackTrace();
		}


	}

}
