package rsa;

import java.util.Scanner;

public class RSASet {

	long e; // prime to m
	long d; // modular inverse of e mod m

	long a; // prime 1
	long b; // prime 2

	long c; // a * b
	long m; // a-1 * b-1

	public RSASet(){
		generate();
	}

	public void generate(){
		try {
			Scanner s = new Scanner(System.in);
			System.out.println("Enter the nth prime to compute:");
			String num1 = s.nextLine();
			this.a = Long.parseLong(num1);
			System.out.println("Enter the mth prime to compute:");
			String num2 = s.nextLine();
			this.b = Long.parseLong(num2);
			this.c = a * b;
			this.m = (a - 1) * (b - 1);
			this.e = RSA.coprime(m);
			this.d = RSA.modInverse(e, m);
		} catch (Exception ex) {
			System.out.println("You entered a invalid number");
			ex.printStackTrace();
			System.exit(0);
		}
	}
}
