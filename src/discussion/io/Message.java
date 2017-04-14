package discussion.io;

import java.io.Serializable;

/**
 * @author Elliott Bolzan
 *
 *         Represents a Message to be sent through Sockets. A Message is
 *         comprised of a senderID, which is a username; a channel key; and the
 *         Object to send.
 *
 */
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	private String senderID;
	private String key;
	private Object object;

	/**
	 * Creates a Message.
	 * 
	 * @param senderID
	 *            the username.
	 * @param key
	 *            the channel to send on.
	 * @param object
	 *            the object to send.
	 */
	public Message(String senderID, String key, Object object) {
		this.senderID = senderID;
		this.key = key;
		this.object = object;
	}

	/**
	 * Returns the username.
	 * 
	 * @return the username.
	 */
	public String getSenderID() {
		return senderID;
	}

	/**
	 * Returns the channel that this message is to be sent on.
	 * 
	 * @return the channel.
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Returns the object to be sent.
	 * 
	 * @return the object to be sent.
	 */
	public Object getObject() {
		return object;
	}

}
