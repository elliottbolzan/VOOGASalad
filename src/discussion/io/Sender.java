package discussion.io;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @author Elliott Bolzan
 *
 */
public class Sender extends Actor
{

	public Sender(String host, int port, int bufferSize)
	{
		super(host, port, bufferSize);
		System.setProperty("java.net.preferIPv4Stack", "true");
	}

	public void send(Message message)
	{
		try {
			MulticastSocket socket = new MulticastSocket(getPort());
			ByteArrayOutputStream stream = createOutputStream(message);
			sendPacket(socket, makePacket(stream));
			socket.close();
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	private ByteArrayOutputStream createOutputStream(Message message) throws Exception
	{
		ByteArrayOutputStream stream = new ByteArrayOutputStream(getBufferSize());
		ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(stream));
		out.flush();
		out.writeObject(message);
		out.flush();
		return stream;
	}

	private DatagramPacket makePacket(ByteArrayOutputStream stream) throws Exception
	{
		InetAddress group = InetAddress.getByName(getHost());
		return new DatagramPacket(stream.toByteArray(), stream.toByteArray().length, group, getPort());
	}

	private void sendPacket(MulticastSocket socket, DatagramPacket packet) throws Exception
	{
		Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
		while (interfaces.hasMoreElements()) {
			NetworkInterface iface = interfaces.nextElement();
			if (iface.isLoopback() || !iface.isUp()) {
				continue;
			}
			Enumeration<InetAddress> addresses = iface.getInetAddresses();
			while (addresses.hasMoreElements()) {
				InetAddress address = addresses.nextElement();
				socket.setInterface(address);
				socket.send(packet);
				System.out.println("Sent on: " + address);
			}
		}
	}

}
