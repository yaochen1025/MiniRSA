package message;

import gui.ChattingGUI;

import java.io.*;
import java.math.BigInteger;

import RSA.Encryptor;

public class MessageReceiver extends Thread {

	BufferedReader in;
	InputStream xin;
	Encryptor decryptor;

	public MessageReceiver(InputStream in){
		this.xin = in;
		this.in = new BufferedReader(new InputStreamReader(in));
		this.decryptor = new Encryptor(new BigInteger("1531"),new BigInteger("2623"));
	}

	/**
	 * Overrides the run methods for thread
	 */
	@Override
	public void run() {
		String line;
		byte[] buf = new byte[100];
		int len;
		try {
			while ((line = in.readLine()) != null) {
				print(line);
			}
			
			
//			while((len = xin.read(buf)) != 0) {
//				line = new String(buf, 0, len);
//				print(line);
//			}
			
			
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
		ChattingGUI.print("s/he:\n"+sb.toString()+"\n\n");
//		BigInteger receivedNumber = new BigInteger(s);
//		char x = this.decryptor.decrypt(receivedNumber);
//		System.out.print(x);
	}
}