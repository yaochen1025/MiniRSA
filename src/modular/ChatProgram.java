package modular;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.*;

import rsa.*;

public abstract class ChatProgram {

	static int port;
	static String ipAddress;
	ServerSocket serverSocket;
	Socket socket;
	RSASet myRSASet;
	Encryptor encryptor;
	Encryptor decryptor;

	void keySend() {
		OutputStream out;
		try {
			out = socket.getOutputStream();
			out.write((myRSASet.e + "\\k" + myRSASet.c + "\n").getBytes());
			System.out.println("Sending out my public key: (" + myRSASet.e + ", " + myRSASet.c + ")");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	void keyRecv() {
		BufferedReader in;
		String s;
		try {
			in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			s = in.readLine();
			String[] received = s.split("\\\\k");

			BigInteger recvNum1 = new BigInteger(received[0]);
			BigInteger recvNum2 = new BigInteger(received[1]);

			encryptor = new Encryptor(recvNum1, recvNum2);
			decryptor = new Encryptor(new BigInteger(myRSASet.d+""), new BigInteger(myRSASet.c+""));
			System.out.println("Receiving the other one's public key: (" + recvNum1 + ", " + recvNum2 + ")");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

