package modular;
 
import java.io.*;
import java.math.BigInteger;
import java.net.Socket;

/**
 * Message Sender that is run on its own thread
 * @author Yao Chen
 * @author Sheng Huang
 */

public class MessageSender extends Thread {

	OutputStream out;
	Encryptor encryptor;
	Socket socket;

	public MessageSender(Socket socket, Encryptor encryptor){
		this.socket = socket;
		this.encryptor = encryptor;
	}

	/**
	 * Overrides the run methods for thread
	 */
	@Override
	public void run() {

		try {
			this.out = socket.getOutputStream();
		} catch (IOException e1) {
			ChatProgram.shutDown();
			//e1.printStackTrace();
		}

		BufferedReader userInput = new BufferedReader(
				new InputStreamReader(System.in));

		String line;
		System.out.println("-------------\nYou can say something: (type \"\\bye\" to quit)");
		try {
			while((line = userInput.readLine()) != null) {
				
				write(line);
			}
			System.out.println("-------------");
		} catch (IOException e) {
			ChatProgram.shutDown();
			//e.printStackTrace();
		}
	}

	/**
	 * Write the string to the output stream
	 * @param s
	 * @throws IOException
	 */
	private void write(String s) throws IOException {
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < s.length(); i++) {
			BigInteger x = this.encryptor.encrypt(s.charAt(i));
			sb.append(x+"\\c");			
		}
		out.write((sb.toString() + "\n").getBytes());
		if("\\bye".equals(s)) {
			ChatProgram.shutDown();
		}
	}



}