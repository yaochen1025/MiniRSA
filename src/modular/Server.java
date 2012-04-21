package modular;

import java.net.*;

import rsa.*;

/**
 * Server run from terminal
 * @author Yao Chen
 * @author Sheng Huang
 */
public class Server extends ChatProgram {

	public static void main(String[] args) {

		//reserved for invalid inputs
		port = 8222;
		if (args.length >= 1) {
			try {
				port = Integer.parseInt(args[0].trim());
			}catch (NumberFormatException e) {
				System.out.println("error in port number");
				ChatProgram.shutDown();
				System.exit(0);
			}
		}
		new Server().run();
	}

	public void run() {

		myRSASet = new RSASet();		

		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Waiting for client...");
			socket = serverSocket.accept();

		} catch (Exception e) {
			System.out.println("The server cannot be set up");
			ChatProgram.shutDown();
		}
		System.out.println("client connected");

		keyRecv();
		keySend();

		new MessageSender(socket, encryptor).start();
		new MessageRecver(socket, decryptor).start();

	}
}
