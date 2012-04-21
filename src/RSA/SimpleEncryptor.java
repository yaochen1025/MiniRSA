package rsa;

import java.util.Scanner;

public class SimpleEncryptor {
	Scanner s = new Scanner(System.in);

	long e; // prime to m
	long c; // a * b

	public static void main(String[] args) {
		SimpleEncryptor end = new SimpleEncryptor();
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
		System.out.println("Please enter the public key value (e, c): first e, then c");
		String line = s.nextLine();
		String[] keyvalue = line.split("[ ]+");
		this.e = Long.parseLong(keyvalue[0]);
		this.c = Long.parseLong(keyvalue[1]);

	}

	private void encrypt() {
		System.out.println("Please enter a sentence to encrypt");
		String line = s.nextLine();
		for (int i = 0; i < line.length(); i++) {
			long value = line.charAt(i);
			long secured = RSA.modulo(value, this.e, this.c);
			System.out.println(secured);		
		}
	}
}
