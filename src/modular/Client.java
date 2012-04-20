package modular;

import java.net.*;

import rsa.*;

public class Client extends ChatProgram {

	public static void main(String[] args) {
		port = 8222;
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

		keySend();
		keyRecv();

		new MessageSender(socket, encryptor).start();
		new MessageRecver(socket, decryptor).start();

	}
}