package modular;

import java.math.BigInteger;

import rsa.RSA;

/**
 * Encryptor for message sender and sender
 * @author Yao Chen
 * @author Sheng Huang
 */
public class Encryptor {

	BigInteger key1;
	BigInteger key2;
	
	public Encryptor(BigInteger key1, BigInteger key2) {
		this.key1 = key1;
		this.key2 = key2;
	}
	
	/**
	 * Encrypt a charater to an integer
	 * @param c 
	 * @return the encrypted integer
	 */
	public BigInteger encrypt(char c) {
		long value = c;
		BigInteger character = new BigInteger(value+"");
		return RSA.modulo(character, key1, key2);
	}
	
	/**
	 * Decrypt an integer to a charater
	 * @param c 
	 * @return the decrypted charater
	 */
	public char decrypt(BigInteger bi) {
		BigInteger charInASCII = RSA.modulo(bi, key1, key2);
		int character = charInASCII.intValue();
		return (char) character;
	}
	
	
}
