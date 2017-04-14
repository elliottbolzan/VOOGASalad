package discussion.response;

/**
 * @author Elliott Bolzan
 * 
 *         A class designed to help implement the Delegate pattern. A Delegate
 *         has a key: when the channel with that key receives a message, the
 *         Connectable is called.
 */
public class Delegate {

	private String key;
	private Connectable connectable;

	/**
	 * Returns a Delegate.
	 * @param key the channel for this delegate.
	 * @param connectable the instance to be called when the channel is activated.
	 */
	public Delegate(String key, Connectable connectable) {
		this.key = key;
		this.connectable = connectable;
	}

	/**
	 * @return the Delegate's channel.
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @return the Connectable that is called when the channel is activated.
	 */
	public Connectable getConnectable() {
		return connectable;
	}

}
