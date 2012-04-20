package message;

//import gui.ServerGUI;

import java.io.*;
import java.math.BigInteger;
import java.net.*;

import rsa.*;


public class MessageReceiver extends Thread {

	BufferedReader in;
	Socket socket;
	Encryptor decryptor;
	
	
	public MessageReceiver(Socket socket){
		this.socket = socket;
		this.decryptor = new Encryptor(new BigInteger("1531"),new BigInteger("2623"));
	}

	/**
	 * Overrides the run methods for thread
	 */
	@Override
	public void run() {
		try {
			this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		String line;
		try {
			while ((line = in.readLine()) != null) {
				print(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void print(String s) {
		StringBuilder sb = new StringBuilder("");
		String[] received = s.split("\\\\c");
		for (String temp : received) {
			BigInteger receivedNumber = new BigInteger(temp);
			char x = this.decryptor.decrypt(receivedNumber);
			sb.append(x);
		}
		System.out.println("other:\n" + sb.toString() + "\n\n");
	}
}