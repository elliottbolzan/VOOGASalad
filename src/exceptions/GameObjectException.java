package exceptions;

import java.util.ResourceBundle;

/**
 * Exception for GameObjects. Extends RuntimeException.
 * 
 * @author Kyle Finke
 *
 */
public class GameObjectException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param message
	 *            information that is provided by exception being thrown
	 */
	public GameObjectException(String message) {
		super(String.format(ResourceBundle.getBundle("resources/GameObjectExceptions").getString("GameObjectException"),
				message));
	}
}
