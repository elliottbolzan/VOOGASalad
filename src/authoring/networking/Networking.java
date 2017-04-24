/**
 * 
 */
package authoring.networking;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.Executors;

import authoring.panel.chat.Message;

import java.time.Duration;

import networking.io.*;
import networking.net.ObservableClient;
import networking.net.ObservableServer;

/**
 * @author Elliott Bolzan
 *
 */
public class Networking {

	private static final int PORT = 1337;

	/**
	 * 
	 */
	public Networking() {
		// TODO Auto-generated constructor stub
	}

	public void startServer() {
		try {
			ObservableServer<Message> voogaServer = new ObservableServer<Message>(new Message("username", "message"),
					PORT, Serializer.NONE, Unserializer.NONE, Duration.ofSeconds(30));
			Executors.newSingleThreadExecutor().submit(voogaServer);
			joinClient(getIP());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getIP() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return "Not Found";
		}
	}

	public void joinClient(String IP) {
		ObservableClient<Message> voogaClient;
		try {
			voogaClient = new ObservableClient<>(IP, PORT, Serializer.NONE, Unserializer.NONE, Duration.ofSeconds(30));
			Scanner stdin = new Scanner(System.in);
			voogaClient.addListener(client -> System.out.print(client.getMessage() + "\n\n>>  "));
			Executors.newSingleThreadExecutor().submit(voogaClient);
			while (voogaClient.isActive()) {
				String input = stdin.nextLine();
				String user = System.getProperty("user.name");
				voogaClient.addToOutbox(state -> {
					return new Message(user, input);
				});
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
