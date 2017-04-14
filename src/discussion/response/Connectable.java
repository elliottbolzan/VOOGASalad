package discussion.response;

/**
 * @author Elliott Bolzan
 * 
 *         This class represents a class that wishes to serve as a receiver for
 *         networking events. Such classes can register for certain channels,
 *         implement Connectable, and then receive messages.
 */
public interface Connectable {

	/**
	 * Called when an object has been received on the network.
	 * @param object the object that has been received.
	 */
	public void received(Object object);

}
