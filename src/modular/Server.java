package modular;
import java.net.*;

import Message.MessageReceiver;
import Message.MessageSender;
/**
 * 
 * @author Yao Chen
 * @author Sheng Huang
 *
 */
public class Server {

	private static int port;
	private ServerSocket serverSocket;

	public static void main(String[] args) {
		
		if (args.length < 1) {
			System.out.println("missing arguments");
			System.exit(-1);
		}

		try {
			port = Integer.parseInt(args[0].trim());
		}catch (NumberFormatException e) {
			System.out.println("error in port number");
			System.exit(-1);
		}
		new Server().run();
	}

	public void run() {
		
		try {
			
			serverSocket = new ServerSocket(port);
			Socket socket = serverSocket.accept();
			
			System.out.println("server:connected");
						
			new MessageReceiver(socket.getInputStream()).start();
			new MessageSender(socket.getOutputStream()).start();


		}catch (Exception e) {
			e.printStackTrace();
		}
	}


}
