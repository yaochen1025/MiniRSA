package modular;

import java.net.*;

import rsa.*;

import message.MessageReceiver;
import message.MessageSender;

/**
 * 
 * @author Yao Chen
 * @author Sheng Huang
 *
 */
public class Server {

	private static int port;
	private ServerSocket serverSocket;
	private Socket socket;
	public static RSASet myRSASet;
	public static Encryptor encryptor;

	public static void main(String[] args) {

		port = 8282;
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
			socket = serverSocket.accept();
			System.out.println("Waiting for client...");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("client connected");
		
		
		
		new MessageSender(socket).start();
		new MessageReceiver(socket).start();
		
	}
}
