package authoring.networking;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.concurrent.Executors;

import authoring.Workspace;
import authoring.panel.chat.Message;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;

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
			server = new ObservableServer<Packet>(null, PORT, Serializer.NONE, Unserializer.NONE,
					Duration.ofSeconds(5));
			Executors.newSingleThreadExecutor().submit(server);
			join(getIP(), identifier);
			this.identifier = identifier;
		} catch (Exception e) {
			throw new InterruptedException();
		}
	}

	public void start() {
		Optional<String> identifier = askForGameIdentifier();
		if (identifier.isPresent()) {
			Task<Void> task = new Task<Void>() {
				@Override
				public Void call() throws InterruptedException {
					start(identifier.get());
					return null;
				}
			};
			workspace.getMaker().showProgressForTask(task, true);
		}
		// tell them which IP to start;
		// hide join server, show stop server
	}

	@SuppressWarnings("unchecked")
	public void join(String IP, String identifier) throws InterruptedException {
		try {
			client = new ObservableClient<>(IP, PORT, Serializer.NONE, Unserializer.NONE, Duration.ofSeconds(5));
			client.addListener(client -> received(client));
			Executors.newSingleThreadExecutor().submit(client);
			this.identifier = identifier;
		} catch (IOException e) {
			throw new InterruptedException();
		}
	}

	public void join() {
		Optional<String> IP = askForIP();
		if (IP.isPresent()) {
			Optional<String> identifier = askForGameIdentifier();
			if (identifier.isPresent()) {
				Task<Void> task = new Task<Void>() {
					@Override
					public Void call() throws InterruptedException {
						join(IP.get(), identifier.get());
						return null;
					}
				};
				workspace.getMaker().showProgressForTask(task, true);
			}
		}
	}

	public void showIP() {
		Task<Void> task = new Task<Void>() {
			@Override
			public Void call() throws InterruptedException {
				String IP = getIP();
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						Alert alert = workspace.getMaker().makeAlert(AlertType.INFORMATION, "IPTitle", "IPHeader",
								workspace.getPolyglot().get("IPContent").get() + " " + IP + ".");
						alert.show();
					}
				});
				return null;
			}
		};
		workspace.getMaker().showProgressForTask(task, false);
	}

	private String getIP() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return "Unavailable";
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

	private Optional<String> askForIP() {
		TextInputDialog dialog = workspace.getMaker().makeTextInputDialog("JoinTitle", "JoinHeader", "JoinPrompt", "");
		return dialog.showAndWait();
	}

	private Optional<String> askForGameIdentifier() {
		TextInputDialog dialog = workspace.getMaker().makeTextInputDialog("GameIDTitle", "GameIDHeader", "GameIDPrompt",
				"");
		return dialog.showAndWait();
	}

}
