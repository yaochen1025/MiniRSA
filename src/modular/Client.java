package modular;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.*;

import rsa.*;
import message.*;

public class Client {

	private static int port;
	private static String ipAddress;
	private Socket socket;
	private RSASet myRSASet;
	private Encryptor encryptor;
	private Encryptor decryptor;


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