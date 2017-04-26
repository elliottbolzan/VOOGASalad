package authoring.networking;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;

import authoring.Workspace;
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

	private Workspace workspace;
	private ObservableServer<Packet> server;
	private ObservableClient<Packet> client;
	private String identifier;
	private static final int PORT = 1337;

	public Networking(Workspace workspace) {
		this.workspace = workspace;
	}

	@SuppressWarnings("unchecked")
	public void start(String identifier) throws InterruptedException {
		try {
			server = new ObservableServer<Packet>(null, PORT, Serializer.NONE,
					Unserializer.NONE, Duration.ofSeconds(5));
			Executors.newSingleThreadExecutor().submit(server);
			join(getIP(), "");
			this.identifier = identifier;
		} catch (Exception e) {
			throw new InterruptedException();
		}
	}

	public String getIP() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return "Unavailable";
		}
	}

	@SuppressWarnings("unchecked")
	public void join(String IP, String identifier) throws InterruptedException {
		try {
			this.identifier = identifier;
			client = new ObservableClient<>(IP, PORT, Serializer.NONE, Unserializer.NONE, Duration.ofSeconds(5));
			client.addListener(client -> received(client));
			Executors.newSingleThreadExecutor().submit(client);
		} catch (IOException e) {
			throw new InterruptedException();
		}
	}
	
	public void close() {
		if (server != null && server.isActive())
			server.close();
		if (client != null && client.isActive())
			client.close();
	}

	public void send(Packet packet) {
		packet.setIdentifier(identifier);
		client.addToOutbox(state -> packet);
	}

	private void received(Packet packet) {
		if (packet != null && packet.getIdentifier().equals(identifier)) {
			if (packet instanceof Message) {
				workspace.getPanel().getChat().received(packet);
			}
		}
	}

}
