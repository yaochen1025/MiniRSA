package rsa;

import java.util.Scanner;

public class SimpleDecryptor {
	Scanner s = new Scanner(System.in);

	long d; // prime to m
	long c; // a * b

	public static void main(String[] args) {
		SimpleDecryptor end = new SimpleDecryptor();
		end.run();
	}

	private void run() {
		try {
			getKeys();
			encrypt();
		} catch (Exception ex) {
			System.out.println("The input is invalid");
		}
	}

	private void getKeys() {
		System.out.println("Please enter the public key value (d, c): first d, then c");
		String line = s.nextLine();
		String[] keyvalue = line.split("[ ]+");
		this.d = Long.parseLong(keyvalue[0]);
		this.c = Long.parseLong(keyvalue[1]);

	}

	private void encrypt() {
		while(true) {
			System.out.println("Enter next char cipher value as an int, type quit to quit");
			String line = s.nextLine();

			if("quit".equals(line)) break;

			long secured = Long.parseLong(line);
			long real = RSA.modulo(secured, this.d, this.c);
			System.out.println((char)real + " " + real);		

		}
	}
}
