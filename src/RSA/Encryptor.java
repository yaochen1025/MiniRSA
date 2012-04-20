package RSA;

import java.math.BigInteger;

public class Encryptor {

	BigInteger key1;
	BigInteger key2;
	
	public Encryptor(BigInteger key1, BigInteger key2) {
		this.key1 = key1;
		this.key2 = key2;
	}
	
	public BigInteger encrypt(char c) {
		long value = c;
		BigInteger character = new BigInteger(value+"");
		return RSA.modulo(character, key1, key2);
	}
	
	public char decrypt(BigInteger bi) {
		BigInteger charInASCII = RSA.modulo(bi, key1, key2);
		int character = charInASCII.intValue();
		return (char) character;
	}
	
	
}
