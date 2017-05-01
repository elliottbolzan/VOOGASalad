package authoring.networking;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;

import authoring.Workspace;
import authoring.command.AddInfo;
import authoring.command.EntityCommandInfo;
import authoring.command.EntityListInfo;
import authoring.command.MultiEntityInfo;
import authoring.panel.chat.Message;
import engine.entities.Entity;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import networking.io.Serializer;
import networking.io.Unserializer;
import networking.net.ObservableClient;
import networking.net.ObservableServer;
import networking.net.SocketConnection;

/**
 * @author Elliott Bolzan
 *
 */
public class Networking implements ConnectionObserver
{

	private Workspace workspace;
	private ObservableServer<Packet> server;
	private ObservableClient<Packet> client;
	Packet packet;
	private static final int PORT = 1337;

	public Networking(Workspace workspace)
	{
		this.workspace = workspace;
	}

	public void start()
	{
		Task<Void> task = new Task<Void>()
		{
			@Override
			public Void call() throws InterruptedException
			{
				startHelper();
				return null;
			}
		};
		workspace.getMaker().showProgressForTask(task, true);
	}

	@SuppressWarnings("unchecked")
	private void startHelper() throws InterruptedException
	{
		try {
			server = new ObservableServer<Packet>(null, PORT, Serializer.NONE, Unserializer.NONE, Duration.ofSeconds(5),
					this);
			Executors.newSingleThreadExecutor().submit(server);
			join(getIP());
		} catch (Exception e) {
			throw new InterruptedException();
		}
	}

	public void join()
	{
		Optional<String> IP = askForIP();
		if (IP.isPresent()) {
			Task<Void> task = new Task<Void>()
			{
				@Override
				public Void call() throws InterruptedException
				{
					join(IP.get());
					return null;
				}
			};
			workspace.getMaker().showProgressForTask(task, true);
		}
	}

	@SuppressWarnings("unchecked")
	private void join(String IP) throws InterruptedException
	{
		try {
			client = new ObservableClient<>(IP, PORT, Serializer.NONE, Unserializer.NONE, Duration.ofSeconds(5));
			client.addListener(client -> received(client));
			Executors.newSingleThreadExecutor().submit(client);
		} catch (IOException e) {
			throw new InterruptedException();
		}
	}

	public void showIP()
	{
		Task<Void> task = new Task<Void>()
		{
			@Override
			public Void call() throws InterruptedException
			{
				String IP = getIP();
				Platform.runLater(new Runnable()
				{
					@Override
					public void run()
					{
						Alert alert = workspace.getMaker().makeAlert(AlertType.INFORMATION, "IPTitle", "IPHeader",
								workspace.getPolyglot().get("IPContent").get() + " " + IP + ".");
						alert.show();
					}
				});
				return null;
			}
		};
		workspace.getMaker().showProgressForTask(task, false);
	}

	private String getIP()
	{
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return "Unavailable";
		}
	}

	public void close()
	{
		if (server != null && server.isActive())
			server.close();
		if (client != null && client.isActive())
			client.close();
	}

	public boolean isConnected()
	{
		return client != null && client.isActive() || server != null && server.isActive();
	}

	public void send(Packet packet)
	{
		client.addToOutbox(state -> packet);
	}

	/**
	 * Sends the packet if you are connected to the network. If you aren't
	 * connected, then receives the packet immediately.
	 * 
	 * @param packet
	 */
	public void sendIfConnected(Packet packet)
	{
		if (isConnected()) {
			this.send(packet);
		} else {
			this.received(packet);
		}
	}

	private void received(Packet packet)
	{
		if (packet != null) {
			if (packet instanceof Message) {
				workspace.getPanel().getChat().received(packet);
			} else if (packet instanceof EntityCommandInfo || packet instanceof MultiEntityInfo) {
				workspace.getLevelEditor().received(packet);
			} else if (packet instanceof EntityListInfo) {
				workspace.received(packet);
			}
		}
	}

	private Optional<String> askForIP()
	{
		TextInputDialog dialog = workspace.getMaker().makeTextInputDialog("JoinTitle", "JoinHeader", "JoinPrompt", "");
		return dialog.showAndWait();
	}

	@Override
	public void newConnection(SocketConnection connection)
	{
		if (server != null && server.isActive()) {
			List<? extends Entity> addedEntityList = workspace.getDefaults().getEntities();
			EntityListInfo currentEntities = new EntityListInfo(addedEntityList);
			List<AddInfo> addedEntities = new ArrayList<AddInfo>();
			workspace.getLevelEditor().getCurrentLevel().getLayers().forEach(layer -> {
				layer.getEntities().forEach(entityView -> {
					addedEntities.add(new AddInfo(entityView.getEntity().getName(), entityView.getTranslateX(),
							entityView.getTranslateY(), (int) entityView.getEntity().getZ(), entityView.getEntityId()));
				});
			});
			MultiEntityInfo<AddInfo> multiAdd = new MultiEntityInfo<AddInfo>(addedEntities);
			List<Packet> startingState = new ArrayList<Packet>();
			startingState.add(currentEntities);
			startingState.add(multiAdd);
			MultiEntityInfo<Packet> startingConfig = new MultiEntityInfo<Packet>(startingState);
			this.send(startingConfig);
		}
	}

}
