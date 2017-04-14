package discussion;

/**
 * @author Elliott Bolzan
 *
 *         A class designed to demonstrate the use of the discussion library.
 *         Shows several features: registering for a channel, sending messages,
 *         and making use of several channels.
 */
public class Example {

	/**
	 *  Called on startup.
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// Create an instance of the Discussion.
		Discussion discussion = new Discussion();
		// Listen on a specific channel.
		discussion.listenOnChannel(e -> received(e), "channel");
		// Send a String.
		discussion.send("This is a test.", "channel");
		// Send an integer. You can send any Object that implements
		// Serializable.
		discussion.send(2567, "channel");
		// Your receivers won't receive Objects from channels they are not
		// registered for.
		// But you can register for several different channels at once.
		discussion.send("Those registered to secondChannel will receive this..", "secondChannel");
	}

	/**
	 * This method is called when an object has been received over the network.
	 * @param e the object that has been received.
	 */
	public static void received(Object e) {
		// We recommend you cast the Object to the desired type.
		System.out.println(e);
	}

}
