package networking.net.requests;


/**
 * This class is sent to notify the remote host that the local host is in an error state.
 * <p>
 * This request contains a error code to help resolve the error that occurred.
 *
 * @author Created by th174 on 4/2/2017.
 * @see Request,Modifier,ObservableServer,ObservableServer.ServerDelegate,ObservableClient,ObservableHost
 */
public class ErrorRequest extends SerializableObjectRequest<Integer> {
	/**
	 * Creates a new request that contains a serialized object and a commit index.
	 *
	 * @param errorCode An error code related to the error that occured
	 */
	public ErrorRequest(int errorCode) {
		super(errorCode);
	}
}
