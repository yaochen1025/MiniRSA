package miniRSA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import java.net.Socket;

public class Write {
	

	private static int port;
	private static String ipAddress;

	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("missing arguments");
			System.exit(-1);
		}

		try {
			port = Integer.parseInt(args[0].trim());
		}catch (NumberFormatException e) {
			System.out.println("error in port number");
			System.exit(-1);
		}
		ipAddress = args[1].trim();
		new Write().run();
	}

	public void run() {
		try {
			
			System.out.println(ipAddress + ":" + port);
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress(ipAddress, port));
			OutputStream out = socket.getOutputStream();
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
	
			String line = stdIn.readLine();
			System.out.println("write " + line);
			out.write(line.getBytes());
			out.flush();
			
		}catch (IOException e) {
			e.printStackTrace();
		}


	}

}
