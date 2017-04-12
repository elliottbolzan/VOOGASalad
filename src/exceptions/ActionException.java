package exceptions;

import java.util.ResourceBundle;

/**
 * Exceptions for Action and its subclasses. Extends RuntimeException.
 * @author Kyle Finke
 *
 */
public class ActionException extends RuntimeException {

	public ActionException() {
	}

	public ActionException(String message) {
		super(String.format(ResourceBundle.getBundle("resources/ActionExceptions").getString("ActionException"),
				message));
	}

	public ActionException(Throwable cause) {
		super(cause);
	}

	public ActionException(String message, Throwable cause) {
		super(String.format(ResourceBundle.getBundle("resources/ActionExceptions").getString("ActionException"),
				message), cause);
	}

}
