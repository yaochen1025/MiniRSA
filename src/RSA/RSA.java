package RSA;

import java.math.BigInteger;
import java.util.Random;

public class RSA {

	static int coprime(int x) {
		Random rand = new Random();
		int temp;
		do {
			temp = rand.nextInt();
		} while (GCD(x,temp) != 1);
		return temp;
	}


	static int GCD(int a, int b) {
		if (b == 0) {
			return a;
		}
		return GCD(b, a % b);
	}

//	static int modInverse(int a, int n) {
//		int i = n, v = 0, d = 1;
//		while (a > 0) {
//			int t = i/a, x = a;
//			a = i % x;
//			i = x;
//			x = d;
//			d = v - t*x;
//			v = x;
//		}
//		v %= n;
//		if (v<0) v = (v+n)%n;
//		return v;
//	}
	

	static BigInteger modulo(BigInteger a, BigInteger b, BigInteger c) {
		//System.out.println("a: "+a+" ,b: "+b + " ,c: "+c);
		return a.modPow(b, c);
	}

	static int totient(int n){
		int count = 0;
		for (int i = 2; i < n; i++) {
			if (GCD(i, n) == 1){ 
				count++;
			}
		}
		return count;
	}

}
