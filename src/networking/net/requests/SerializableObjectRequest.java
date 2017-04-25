package networking.net.requests;

import java.io.Serializable;

/**
 * This class provides contains communications between the client and the server.
 * Each request has a commit message, which the host uses to determine whether or not the request is valid.
 * <p>
 * This request contains a serializable object to be sent over the network.
 *
 * @param <T> The type of the object that the modifier modifies.
 * @author Created by th174 on 4/2/2017.
 * @see Request,Modifier,ObservableServer,ObservableServer.ServerDelegate,ObservableClient,ObservableHost
 */
public class SerializableObjectRequest<T extends Serializable> extends Request {
	private T serializedObject;

	/**
	 * Creates a new request that contains a serialized object and a commit index.
	 *
	 * @param serializedObject serialized object contained by this request
	 */
	public SerializableObjectRequest(T serializedObject) {
		super();
		this.serializedObject = serializedObject;
	}

	/**
	 * @return Returns the serializedObject contained in this request
	 */
	public T get() {
		return serializedObject;
	}

	@Override
	public String toString() {
		return super.toString() + "\n\tContent:\n" + serializedObject.toString().replaceAll("(?m)^", "\t\t");
	}
}
