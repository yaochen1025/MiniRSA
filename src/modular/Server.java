package modular;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
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
	private RSASet myRSASet;
	private Encryptor encryptor;
	private Encryptor decryptor;

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
	
	private void keySend() {
		OutputStream out;
		try {
			out = socket.getOutputStream();
			out.write((myRSASet.e + "\\k" + myRSASet.c + "\n").getBytes());
			System.out.println("Sending out my public key: (" + myRSASet.e + ", " + myRSASet.c + ")");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void keyRecv() {
		BufferedReader in;
		String s;
		try {
			in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			s = in.readLine();
			String[] received = s.split("\\\\k");
			
			BigInteger recvNum1 = new BigInteger(received[0]);
			BigInteger recvNum2 = new BigInteger(received[1]);

			encryptor = new Encryptor(recvNum1, recvNum2);
			decryptor = new Encryptor(new BigInteger(myRSASet.d+""), new BigInteger(myRSASet.c+""));
			System.out.println("Receiving the other one's public key: (" + recvNum1 + ", " + recvNum2 + ")");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
}
