package discussion.io;

/**
 * @author Elliott Bolzan
 *
 *         An abstract class that represents two types of network actor: a
 *         sender or a receiver. Because these two roles have elements in common
 *         (a host, a port, and a buffer size for packets), they inherit from
 *         this common superclass.
 */
public abstract class Actor {

	private String host;
	private int port;
	private int bufferSize;

	/**
	 * Creates an Actor.
	 * @param host the IP address of the host.
	 * @param port the port for this Actor.
	 * @param bufferSize the buffer size for packets.
	 */
	public Actor(String host, int port, int bufferSize) {
		this.host = host;
		this.port = port;
		this.bufferSize = bufferSize;
	}

	/**
	 * @return the IP address for this actor.
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @returnt the port for this actor.
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @return the buffer size for packets for this host.
	 */
	public int getBufferSize() {
		return bufferSize;
	}

}
