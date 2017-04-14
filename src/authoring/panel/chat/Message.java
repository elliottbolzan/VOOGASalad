/**
 * 
 */
package authoring.panel.chat;

import java.io.Serializable;

/**
 * @author Elliott Bolzan
 *
 *         This class is part of the Chat system. Represents one Message, and as
 *         such, has a username and actual content (a String). Implements
 *         Serializable to be sent over networks.
 */
public class Message implements Serializable {

	private static final long serialVersionUID = 296549524151151475L;
	private String username;
	private String message;

	/**
	 * Creates a Message.
	 * @param username the username of the Message's sender.
	 * @param message the content of the Message.
	 */
	public Message(String username, String message) {
		this.username = username;
		this.message = message;
	}

	/**
	 * @return the Message's username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the Message's content.
	 */
	public String getMessage() {
		return message;
	}

}
