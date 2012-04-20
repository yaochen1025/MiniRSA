package modular;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;

import rsa.Encryptor;


public class MessageSender extends Thread {

	OutputStream out;
	Encryptor encryptor;
	Socket socket;

	public MessageSender(Socket socket, Encryptor encryptor){
		this.socket = socket;
		this.encryptor = encryptor;
	}

	/**
	 * Overrides the run methods for thread
	 */
	@Override
	public void run() {
		
		try {
			this.out = socket.getOutputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		BufferedReader userInput = new BufferedReader(
				new InputStreamReader(System.in));
		
		String line;
		
		try {
			while((line = userInput.readLine()) != null) {
				System.out.println("-------------\nYou can say something:\n");
				write(line);
			}
			System.out.println("-------------\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void write(String s) throws IOException {
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < s.length(); i++) {
			BigInteger x = this.encryptor.encrypt(s.charAt(i));
			sb.append(x+"\\c");			
		}
		out.write((sb.toString() + "\n").getBytes());	
	}
	
	

}