package rsa;

import java.util.Scanner;
/**
 * This class is the cracker
 * @author Yao Chen
 * @author Sheng Huang
 */
public class Cracker {

	Scanner s = new Scanner(System.in);

	long e; // prime to m
	long d; // modular inverse of e mod m

	long a; // prime 1
	long b; // prime 2

	long c; // a * b
	long m; // a-1 * b-1

	long totient;

	public static void main(String[] args) {
		Cracker end = new Cracker();
		end.run();
	}

	private void run() {
		try {
			getPublicKey();
			crackPrivateKey();
			decrypt();
		} catch (Exception ex) {
			System.out.println("The thing you entered is not a number. ");
			ex.printStackTrace();
			System.exit(0);
		}

	}

	private void crackPrivateKey() {
		boolean isCracked = true;
		long i = 2;
		if (this.c % 2 != 0) {
			long end = (long) Math.sqrt(this.c) + 1;
			for (i = 3; i < end; i = i + 2) {
				if (c % i == 0) {
					break;
				}
			}
			if (i == end) isCracked = false;
		}
		this.a = i;
		this.b = this.c / i;
		this.m = (a - 1) * (b - 1);
		this.d = RSA.modInverse(e, m);
		this.totient = RSA.totient(m);
		
		if(!isCracked) {
			System.out.println("The c you entered is a prime number. " +
					"Therefore, it cannot be cracked with RSA algoritm.");
			System.exit(0);
		} else {
			System.out.println("a was " + this.a + " b was " + this.b);
			System.out.println("The totient is " + this.totient);//TODO
			System.out.println("D was found to be " + this.d);
		}
	}

	private void getPublicKey() {
		System.out.println("Enter the public key value (e):");
		String keyvalue = s.nextLine();
		this.e = Long.parseLong(keyvalue);
		System.out.println("Enter the c that goes with the public key:");
		String cvalue = s.nextLine();
		this.c = Long.parseLong(cvalue);
	}

	private void decrypt() {
		System.out.println("Enter a number to decrypt, or \"quit\" to exit:");
		String line = s.nextLine();
		while (!"quit".equals(line)) {
			long num = Long.parseLong(line);
			long letterAscii = RSA.modulo(num, d, c);
			System.out.println("This char is decrypted to " + letterAscii);
			char letter = (char)letterAscii;
			System.out.println("This letter is " + letter);
			System.out.println("Enter a number to decrypt, or \"quit\" to exit:");
			line = s.nextLine();
		}
		System.out.println("Done");
	}


}
