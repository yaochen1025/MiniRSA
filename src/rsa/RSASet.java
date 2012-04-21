package rsa;
 
import java.util.Scanner;
 
public class RSASet {

	public long e; // prime to m
	public long d; // modular inverse of e mod m

	public long a; // prime 1
	public long b; // prime 2

	public long c; // a * b
	public long m; // a-1 * b-1

	public RSASet(){
		do {
			generate();
		} while(this.d == 1);
	}

	public RSASet(long e, long d, long c, long m){
		this.e = e;
		this.d = d;
		this.c = c;
		this.m = m;
	}

	public void generate(){
		try {
			Scanner s = new Scanner(System.in);
			System.out.println("Enter the nth prime to compute:(a number greater than 5)");
			String num1 = s.nextLine();
			long n1 = Long.parseLong(num1);
			while(n1 < 5) {
				System.out.println("Enter the nth prime to compute:(a number greater than 5)");
				num1 = s.nextLine();
				n1 = Long.parseLong(num1);
			}
			this.a = RSA.getNthPrime(n1);
			System.out.println("Enter the mth prime to compute:(a number greater than 5)");
			String num2 = s.nextLine();
			long n2 = Long.parseLong(num2);
			while(n2 < 5) {
				System.out.println("Enter the nth prime to compute:(a number greater than 5)");
				num2 = s.nextLine();
				n2 = Long.parseLong(num1);
			}
			this.b = RSA.getNthPrime(n2);
			this.c = a * b;
			this.m = (a - 1) * (b - 1);
			this.e = RSA.coprime(m);
			this.d = RSA.modInverse(e, m);
			System.out.println(n1 + "th prime = " + a + ", "
					+ n2 + "th prime = " + b + ", "
					+ "c = " + c + ", "
					+ "m = " + m + ", "
					+ "e = " + e + ", "
					+ "d = " + d + ", "
					+ "Public Key = (" + e + ", " + c + "), "
					+ "Private Key = (" + d + ", " + c + "), ");
		} catch (Exception ex) {
			System.out.println("You entered a invalid number");
			ex.printStackTrace();
			System.exit(0);
		}
	}
}
