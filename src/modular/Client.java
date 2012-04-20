package modular;

import java.net.*;

import rsa.*;
import message.*;

public class Client {

	private static int port;
	private static String ipAddress;
	private Socket socket;
	public static RSASet myRSASet;
	public static Encryptor encryptor;

	public static void main(String[] args) {

		port = 8282;
		ipAddress = "localhost";

		if (args.length >= 2) {
			try {
				ipAddress = args[0];
				port = Integer.parseInt(args[1].trim());
			}catch (NumberFormatException e) {
				System.out.println("error in port number");
				System.exit(-1);
			}
		}
		new Client().run();
	}

	public void run() {

		myRSASet = new RSASet();
		try {
			socket = new Socket(ipAddress, port);
		}catch (Exception e) {
			System.out.println("The server is not available!");
			System.exit(0);
		}
		System.out.println("connected to server!");
		
		
		new MessageSender(socket).start();
		new MessageReceiver(socket).start();

	}
}
