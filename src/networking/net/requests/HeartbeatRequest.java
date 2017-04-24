package networking.net.requests;


/**
 * This class provides a mechanism to communicate heartbeat messages between the client and the server to communicate that both are in a normally operating state.
 * <p>
 * It request has a commit message, which the host uses to determine whether or not the request is valid.
 *
 * @author Created by th174 on 4/2/2017.
 * @see Request,Modifier,ObservableServer,ObservableServer.ServerDelegate,ObservableClient,ObservableHost
 */
public class HeartbeatRequest extends Request {
	/**
	 * Creates a new heartbeat request
	 */
	public HeartbeatRequest() {
	}
}
