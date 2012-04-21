package modular;
 
import java.net.*;

import rsa.*;

/**
 * Client run from terminal
 * @author Yao Chen
 * @author Sheng Huang
 */
public class Client extends ChatProgram {

	public static void main(String[] args) {

		//reserved for invalid inputs
		port = 8080;
		ipAddress = "localhost";
		
		if (args.length >= 2) {
			try {
				ipAddress = args[0];
				port = Integer.parseInt(args[1].trim());
			}catch (NumberFormatException e) {
				System.out.println("error in port number.");
				System.exit(0);
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
			ChatProgram.shutDown();
		}
		System.out.println("connected to server!");

		keySend();
		keyRecv();

		new MessageSender(socket, encryptor).start();
		new MessageRecver(socket, decryptor).start();
	}

}