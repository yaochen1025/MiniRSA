package rsa;

import java.math.BigInteger;
import java.util.Random;

public class RSA {

	public static long coprime(long x) {
		Random rand = new Random();
		long temp;
		do {
			temp = rand.nextLong();
		} while (GCD(x,temp) != 1);
		return temp;
	}


	public static long GCD(long a, long b) {
		if (b == 0) {
			return a;
		}
		return GCD(b, a % b);
	}

	public static long modInverse(long a, long n) {
		long i = n, v = 0, d = 1;
		while (a > 0) {
			long t = i/a, x = a;
			a = i % x;
			i = x;
			x = d;
			d = v - t*x;
			v = x;
		}
		v %= n;
		if (v<0) v = (v+n)%n;
		return v;
	}
	

	public static BigInteger modulo(BigInteger a, BigInteger b, BigInteger c) {
		return a.modPow(b, c);
	}
	
	public static long modulo(long a, long b, long c) {
		BigInteger num1 = new BigInteger(a+"");
		BigInteger num2 = new BigInteger(b+"");
		BigInteger num3 = new BigInteger(c+"");
		return num1.modPow(num2, num3).longValue();
	}


	public static long totient(long n){
		long count = 0;
		for (long i = 2; i < n; i++) {
			if (GCD(n, i) == 1){ 
				count++;
			}
		}
		return count;
	}

}
