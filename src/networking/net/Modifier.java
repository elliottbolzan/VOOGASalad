package networking.net;

import java.io.Serializable;

/**
 * This interface provides a way for the client and server to communicate by sending lambda expressions that modifies or creates new game state.
 * <p>
 * This interface represents some kind of to the shared state, which if approved by the server, is propagated across all other clients.
 *
 * @param <T> The type of variable used to represent networked shared state.
 * @author Created by th174 on 4/1/2017.
 * @see Request,Modifier,ObservableServer,ObservableServer.ServerDelegate,ObservableClient,ObservableHost
 */
@FunctionalInterface
public interface Modifier<T> extends Serializable {
	/**
	 * Modifies and returns either a new state or the modified state
	 *
	 * @param state State before the action is applied.
	 * @return Returns the new state after the action is applied.
	 * @throws ModifierException Thrown if the implementation throws an exception
	 */
	default T modify(T state) throws ModifierException {
		try {
			return doModify(state);
		} catch (Exception e) {
			throw new ModifierException(e);
		}
	}

	/**
	 * Modifies and returns either a new state or the modified state
	 *
	 * @param state State before the action is applied.
	 * @return Returns the new state after the action is applied.
	 * @throws Exception Thrown if the implementation throws an exception
	 */
	T doModify(T state) throws Exception;

	/**
	 * Wraps exceptions thrown by doModify
	 */
	class ModifierException extends RuntimeException {
		private ModifierException(Exception e) {
			super("Error occurred processing incoming request: " + e.getMessage(), e);
		}
	}
}
