package networking.net.requests;

import java.io.Serializable;

/**
 * This class provides contains communications between the client and the server.
 * Each request has a commit message, which the host uses to determine whether or not the request is valid.
 *
 * @author Created by th174 on 4/2/2017.
 * @see Request,Modifier,ObservableServer,ObservableServer.ServerDelegate,ObservableClient,ObservableHost
 */
public abstract class Request implements Serializable {
	private int commitIndex;

	/**
	 * Creates a new request.
	 */
	public Request() {
		this.commitIndex = Integer.MIN_VALUE;
	}

	/**
	 * @return Returns the commitIndex that this request was sent with
	 */
	public int getCommitIndex() {
		return commitIndex;
	}

	/**
	 * Sets a commit index to send with this request.
	 *
	 * @param commitIndex commit index of the sender of this request
	 */
	public Request setCommitIndex(int commitIndex) {
		this.commitIndex = commitIndex;
		return this;
	}

	@Override
	public String toString() {
		return String.format("Request:\n\tType:\t%s\n\tCommit:\t%d", getClass().getSimpleName(), commitIndex);
	}
}
