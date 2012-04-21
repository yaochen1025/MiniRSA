package gui;

import java.io.*;
import java.math.BigInteger;
import java.util.Date;

import modular.Encryptor;



public class Reader extends Thread {

	BufferedReader in;
	Encryptor decryptor;
	String agentName;

	public Reader(InputStream in, String name){
		this.in = new BufferedReader(new InputStreamReader(in));
		this.agentName = name;
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
		Date date = new Date();
		System.out.println();
		GUI.print(agentName + "   " + GUI.dateFormat.format(date) + ":\n" + sb.toString() + "\n");
		GUI.print("(Original Message: " + s + ")\n\n");
	}
}