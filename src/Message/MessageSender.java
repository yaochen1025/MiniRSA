package message;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;

import rsa.Encryptor;
import rsa.RSASet;


public class MessageSender extends Thread {

	OutputStream out;
	Encryptor encryptor;
	Socket socket;

	public MessageSender(Socket socket){
		this.socket = socket;
		this.encryptor = new Encryptor(new BigInteger("451"),new BigInteger("2623"));
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
				write(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void write(String s) throws IOException {
		for (int i = 0; i < s.length(); i++) {
			BigInteger x = this.encryptor.encrypt(s.charAt(i));
			out.write((x+"\\c").getBytes());			
		}
		out.write(("\n").getBytes());	
	}
	
	

}