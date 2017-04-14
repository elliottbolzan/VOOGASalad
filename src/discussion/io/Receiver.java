package discussion.io;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.Collection;

import discussion.response.Connectable;
import discussion.response.Delegate;

/**
 * @author Elliott Bolzan
 *
 */
public class Receiver extends Actor implements Runnable
{

	private String ID;
	private Collection<Delegate> delegates;

	public Receiver(String ID, String host, int port, int bufferSize)
	{
		super(host, port, bufferSize);
		this.ID = ID;
		delegates = new ArrayList<Delegate>();
		System.setProperty("java.net.preferIPv4Stack", "true");
	}

	public void addReceiver(Connectable connectable, String key)
	{
		delegates.add(new Delegate(key, connectable));
	}

	@Override
	public void run()
	{
		try {
			byte[] buffer = new byte[getBufferSize()];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			MulticastSocket socket = setupSocket();
			while (true) {
				try {
					socket.receive(packet);
					System.out.println("Received something.");
					Message message = getMessageFromBuffer(buffer);
					processMessage(message);
					packet.setLength(buffer.length);
				} catch (Exception e) {
					continue;
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	private MulticastSocket setupSocket() throws Exception
	{
		MulticastSocket socket = new MulticastSocket(getPort());
		socket.setReuseAddress(true);
		/*
		 * Enumeration<NetworkInterface> interfaces =
		 * NetworkInterface.getNetworkInterfaces(); while
		 * (interfaces.hasMoreElements()) { try { NetworkInterface iface =
		 * interfaces.nextElement(); if (iface.isLoopback() || !iface.isUp()) {
		 * continue; } Enumeration<InetAddress> addresses =
		 * iface.getInetAddresses(); while (addresses.hasMoreElements()) { try {
		 * InetAddress address = addresses.nextElement();
		 * System.out.println("Trying to sign up for: " + address);
		 * socket.setInterface(address);
		 * socket.joinGroup(InetAddress.getByName(getHost())); } catch
		 * (Exception e) { e.printStackTrace(); continue; } } } catch (Exception
		 * e) { e.printStackTrace(); continue; } }
		 */
		socket.setInterface(InetAddress.getByName("10.188.18.167"));
		socket.joinGroup(InetAddress.getByName(getHost()));
		return socket;
	}

	private Message getMessageFromBuffer(byte[] buffer) throws Exception
	{
		ByteArrayInputStream byteStream = new ByteArrayInputStream(buffer);
		ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(byteStream));
		Message message = (Message) is.readObject();
		is.close();
		return message;
	}

	private void processMessage(Message message)
	{
		for (Delegate delegate : delegates) {
			if (message.getKey().equals(delegate.getKey()) && !message.getSenderID().equals(ID)) {
				delegate.getConnectable().received(message.getObject());
				return;
			}
		}
	}

}
