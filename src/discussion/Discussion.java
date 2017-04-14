package discussion;

import java.io.Serializable;
import java.util.UUID;

import discussion.io.*;
import discussion.response.*;

/**
 * @author Elliott Bolzan
 *
 *         The central class in the Discussion library. Contains parameters
 *         (host, port, and buffer size for packets). Implements the Chain of
 *         Responsibility design pattern to delegate sending and receiving to
 *         lower-level classes.
 */
public class Discussion {

	private static final String HOST = "239.255.42.100";
	private static final int PORT = 9000;
	private static final int BUFFER_SIZE = 512;

	private Sender sender;
	private Receiver receiver;
	private String ID;

	/**
	 * Creates a Discussion object.
	 */
	public Discussion() {
		ID = UUID.randomUUID().toString();
		startReceiver();
		sender = new Sender(HOST, PORT, BUFFER_SIZE);
	}

	private void startReceiver() {
		receiver = new Receiver(ID, HOST, PORT, BUFFER_SIZE);
		Thread t = new Thread(receiver);
		t.start();
	}

	/**
	 * Send a message over the network.
	 * @param object the object to be sent.
	 * @param key the channel on which to send the object.
	 */
	public void send(Serializable object, String key) {
		Message message = new Message(ID, key, object);
		sender.send(message);
	}

	/**
	 * Register for a specific channel.
	 * @param connectable the class that is registering.
	 * @param key the channel the class is registering for.
	 */
	public void listenOnChannel(Connectable connectable, String key) {
		receiver.addReceiver(connectable, key);
	}

}
