package modular;

import java.net.*;

import rsa.*;

/**
 * 
 * @author Yao Chen
 * @author Sheng Huang
 *
 */
public class Server extends ChatProgram {

	static void setUpShutDownHook() {
		System.out.println("sdh setup");
		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run() {
				try {
					System.out.println("Done");
//					if (socket != null) {
//						socket.close();
//					}
//					if (serverSocket != null) {
//						serverSocket.close();
//					}
				} catch (Exception e) {
					//this is for quit;
				}
			}
		});
	}
	public static void main(String[] args) {

		setUpShutDownHook();
		
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
		new MessageRecver(socket, decryptor).start();

	}
}
