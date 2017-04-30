package networking.client_server_network;


import java.io.IOException;
import java.net.Socket;
import java.time.Duration;

import networking.io.*;
import networking.net.ObservableHost;
import networking.net.SocketConnection;
import networking.net.requests.Request;

/**
 * This class provides a simple implementation of a client that connects to a server with a given server name and port.
 * <p>
 * The client can request changes to the state, but cannot modify the state directly unless instructed to do so by the server.
 *
 * @param <T> The type of variable used to represent network shared state.
 * @author Created by th174 on 4/1/2017.
 * @see Request,Modifier,ObservableServer,ObservableServer.ServerDelegate,ObservableClient,ObservableHost
 */
public class ObservableClient<T> extends ObservableHost<T> {
    private final SocketConnection connection;

    /**
     * Creates a client connected to a server located at host:port, and starts listening for requests sent from the server
     *
     * @param port {@inheritDoc}
     * @throws IOException {@inheritDoc}
     */
    public ObservableClient(int port) throws IOException {
        this(LOCALHOST, port, Serializer.NONE, Unserializer.NONE);
    }

    /**
     * Creates a client connected to a server located at host:port, and starts listening for requests sent from the server
     *
     * @param host {@inheritDoc}
     * @param port {@inheritDoc}
     * @throws IOException {@inheritDoc}
     */
    public ObservableClient(String host, int port) throws IOException {
        this(host, port, Serializer.NONE, Unserializer.NONE);
    }

    /**
     * Creates a client connected to a server located at host:port, and starts listening for requests sent from the server
     *
     * @param host         {@inheritDoc}
     * @param port         {@inheritDoc}
     * @param serializer   {@inheritDoc}
     * @param unserializer {@inheritDoc}
     * @throws IOException {@inheritDoc}
     */
    public ObservableClient(String host, int port, Serializer<T> serializer, Unserializer<T> unserializer) throws IOException {
        this(host, port, serializer, unserializer, NEVER_TIMEOUT);
    }

    /**
     * Creates a client connected to a server located at host:port, and starts listening for requests sent from the server
     *
     * @param host         {@inheritDoc}
     * @param port         {@inheritDoc}
     * @param serializer   {@inheritDoc}
     * @param unserializer {@inheritDoc}
     * @throws IOException {@inheritDoc}
     */
    public ObservableClient(String host, int port, Serializer<T> serializer, Unserializer<T> unserializer, Duration timeout) throws IOException {
        super(serializer, unserializer, timeout);
        setCommitIndex(Integer.MIN_VALUE);
        this.connection = new SocketConnection(new Socket(host, port), getTimeout());
    }

    @Override
    public void run() {
        connection.listen(this::handleRequest);
    }

    /*@Override
    protected boolean validateRequest(Request incomingRequest) {
        if (incomingRequest.getCommitIndex() >= this.getCommitIndex()) {
            setCommitIndex(incomingRequest.getCommitIndex());
            return true;
        } else {
            handleError();
        }
        return false;
    }*/

    //@Override
    /*protected boolean handleRequest(Request request) {
	    if (Request.isHeartbeat(request)) {
	    	if(request.getCommitIndex() == this.getCommitIndex()) {
			    return handleHeartBeat();
		    } else {
	    		return handleError();
		    }
        } else return super.handleRequest(request);
    }*/

    /**
     * Responds the heartbeat requests sent by the server by sending back another heartbeat request
     *
     * @return Returns true if heartbeat was handled successfully
     */
    /*protected boolean handleHeartBeat() {
        return send(getHeartBeatRequest());
    }*/

    @Override
    protected boolean send(Request request) {
        return connection.send(request);
    }

    @Override
    public boolean isActive() {
        return connection.isActive();
    }

	@Override
	protected void handleError(Request request) {
        send(getErrorRequest());
	}
	
	public void close() {
		connection.shutDown();
	}
	
}

