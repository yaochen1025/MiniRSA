package gui;

import java.io.*;
import java.math.BigInteger;

import RSA.Encryptor;

public class ReceiverForGUI extends Thread {

	BufferedReader in;
	InputStream xin;
	Encryptor decryptor;

	public ReceiverForGUI(InputStream in){
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
		ServerGUI.print("s/he:\n"+sb.toString()+"\n\n");
	}
}