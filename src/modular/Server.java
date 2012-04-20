package modular;

import java.net.*;

import rsa.*;
import message.*;

/**
 * 
 * @author Yao Chen
 * @author Sheng Huang
 *
 */
public class Server extends ChatProgram {


	public static void main(String[] args) {

		port = 8222;
		if (args.length >= 1) {
			try {
				port = Integer.parseInt(args[0].trim());
			}catch (NumberFormatException e) {
				System.out.println("error in port number");
				System.exit(-1);
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
			e.printStackTrace();
		}
		System.out.println("client connected");
		
		keyRecv();
		keySend();
		
		new MessageSender(socket, encryptor).start();
		new MessageReceiver(socket, decryptor).start();
	
	}
}
