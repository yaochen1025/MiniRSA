package rsa;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.Random;

import org.junit.Test;

public class RSATest {

	Random r = new Random();
	boolean debug = false;

	@Test
	public final void testGCD() {
		debug("GCD");
		assertEquals(RSA.GCD(100,10),10);
		assertEquals(RSA.GCD(10,100),10);
		assertEquals(RSA.GCD(100,13),1);
		assertEquals(RSA.GCD(100,100),100);
		assertEquals(RSA.GCD(13,39),13);
		assertEquals(RSA.GCD(120,35),5);
		assertEquals(RSA.GCD(1001,77),77);
		assertEquals(RSA.GCD(1001,33),11);
	}

	@Test
	public final void testCoprime() {
		debug("Coprime");
		for (long i = 0; i < 100; i++) {
			long x = r.nextInt();
			long y = RSA.coprime(x);
			assertEquals(RSA.GCD(x, y), 1);
		}
	}

	@Test
	public final void testmodInverseerse() {
		debug("Modular Inverse");
		assertEquals(RSA.modInverse(3,17),6);
		assertEquals(RSA.modInverse(55,123),85);
		assertEquals(RSA.modInverse(45,223),114);
		assertEquals(RSA.modInverse(2342,92830457),75588250);
		assertEquals(RSA.modInverse(3,30),0);
	}

	@Test
	public final void testExtendedEuclid() {
		debug("Extended Euclid");
		assertArrayEquals(RSA.extendedEuclid(77,33), new long[] {11,1,-2});
		assertArrayEquals(RSA.extendedEuclid(33,77), new long[] {11,-2,1});
		assertArrayEquals(RSA.extendedEuclid(13,37), new long[] {1,-17,6});
		assertArrayEquals(RSA.extendedEuclid(142312,2385), new long[] {1,-227,13545});
		assertArrayEquals(RSA.extendedEuclid(442,2278), new long[] {34,31,-6});
	}


	@Test
	public final void testEncrypt() {
		Encryptor encryptor = new Encryptor(new BigInteger("451"),new BigInteger("2623"));
		Encryptor decryptor = new Encryptor(new BigInteger("1531"),new BigInteger("2623"));
		for (int i = 0; i < 100; i++) {
			char origin = (char) r.nextInt(256);
			BigInteger intermediate = encryptor.encrypt(origin);
			char result = decryptor.decrypt(intermediate);
			debug(origin + " " + result);
			assertEquals(origin, result);
		}
	}

	@Test
	public final void testModulo() {
		debug("Modulo");
		assertEquals(RSA.modulo(2,0,55),1);
		assertEquals(RSA.modulo(2,4,1000),16);
		assertEquals(RSA.modulo(2,6,5),4);
		assertEquals(RSA.modulo(78,45,37),36);
		assertEquals(RSA.modulo(89,1232,4623),1);
		assertEquals(RSA.modulo(254,234,123),4);
		assertEquals(RSA.modulo(2349723,423424,12345),696);
	}
	
	@Test
	public final void testTotient() {
		debug("Totient");
		assertEquals(RSA.totient(2),1);
		assertEquals(RSA.totient(3),2);
		assertEquals(RSA.totient(4),2);
		assertEquals(RSA.totient(5),4);
	}

	void debug(String msg) {
		if(debug) System.out.println(msg);
	}


}
