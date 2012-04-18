package miniRSA;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
/**
 * 
 * @author Yao Chen
 * @author Sheng Huang
 *
 */
public class Server {

	private static int port;
	private static String ipAddress;
	private ServerSocket serverSocket;

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
		new Server().run();
	}

	public void run() {
		try {
			serverSocket = new ServerSocket(port);

			while (true) {
				Socket socket = serverSocket.accept();
				InputStreamReader reader = new InputStreamReader(socket.getInputStream());
				BufferedReader br = new BufferedReader(reader);
				String line = br.readLine();
				System.out.println(line);
			}

		}catch (IOException e) {
			e.printStackTrace();
		}


	}


}
