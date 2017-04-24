package authoring.networking;

import javafx.util.Pair;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Models a chat log, which contains messages sent by different users.
 *
 * @author Created by th174 on 4/1/2017.
 */
public class ChatLog implements Serializable {
	
	private String header;
	private ArrayList<Pair<String, String>> log;

	public ChatLog(String header) {
		log = new ArrayList<>();
		this.header = header;
	}

	public String getChatLog() {
		return header + log.stream().map(e -> String.format("<%s>:  %s", e.getKey(), e.getValue()))
				.collect(Collectors.joining("\n"));
	}

	public ChatLog appendMessage(String message, String user) {
		log.add(new Pair<>(user, message));
		return this;
	}
}