package rsa;

import java.math.BigInteger;
import java.util.Random;

public class RSA {

	public static long coprime(long x) {
		Random rand = new Random();
		long temp;
		do {
			temp = rand.nextLong() % x;
		} while (GCD(x,temp) != 1 || temp <= 0);
		return temp;
	}


	public static long GCD(long a, long b) {
		if (b == 0) {
			return a;
		}
		return GCD(b, a % b);
	}

	public static long modInverse(long a, long n) {
		//TODO
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

	public static BigInteger modInverse(BigInteger base, BigInteger n) {
		return base.modInverse(n);
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
		for (long i = 1; i <= n; i++) {
			if (GCD(n, i) == 1){ 
				count++;
			}
		}
		return count;
	}

	public static long getNthPrime(long n) {
		long count = 0;
		long i = 1;
		while (count < n) {
			i++;
			if (isPrime(i)) {
				count++;
			}
			
		}
		return i;
	}

	private static boolean isPrime(long n) {
		
		if (n <= 1) return false;
		if (n == 2) return true;
		if (n % 2 == 0) return false;

		long end = (long) Math.sqrt(n) + 1;
		for (long i = 3; i < end; i = i + 2) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}
}
