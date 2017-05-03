/**
 * 
 */
package authoring.networking;

import networking.net.SocketConnection;

/**
 * @author Elliott Bolzan
 *
 */
public interface ConnectionObserver
{

	public void newConnection(SocketConnection connection);

}
