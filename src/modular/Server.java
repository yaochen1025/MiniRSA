package modular;
import java.net.*;

import message.MessageReceiver;
//import message.MessageSender;
import message.MessageSender;

/**
 * 
 * @author Yao Chen
 * @author Sheng Huang
 *
 */
public class Server {

	private int port;
	private ServerSocket serverSocket;
	private Socket socket;

	public static void main(String[] args) {
		
		if (args.length < 1) {
			System.out.println("missing arguments");
			System.exit(-1);
		}

		int port = 8282;
		try {
			port = Integer.parseInt(args[0].trim());
		}catch (NumberFormatException e) {
			System.out.println("error in port number");
			System.exit(-1);
		}
		new Server(port).run();
	}
	
	
	public Socket getSocket() {
		return socket;
	}

	public Server(int port){
		this.port = port;
	}

	public void run() {
		
		try {
			
			serverSocket = new ServerSocket(port);
			socket = serverSocket.accept();
			
			new MessageSender(socket.getOutputStream()).start();
			new MessageReceiver(socket.getInputStream()).start();


		}catch (Exception e) {
			e.printStackTrace();
		}
	}


}
