package authoring.panel.chat;

import authoring.Workspace;
import authoring.networking.Packet;
import utils.views.View;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import polyglot.Case;

/**
 * @author Elliott Bolzan
 *
 *         A class representing a ChatView. Allows for a username, the viewing
 *         of sent messages, and the sending of messages. Makes use of a
 *         networking library.
 *
 */
public class Chat extends View {

	private Workspace workspace;
	private TextArea chat;
	private TextField sendField;
	private String username;

	/**
	 * Creates a Chat.
	 * 
	 * @param workspace
	 *            the workspace that owns the Chat.
	 */
	public Chat(Workspace workspace) {
		super(workspace.getPolyglot().get("ChatTitle", Case.TITLE));
		this.workspace = workspace;
		setup();
	}

	private void setup() {
		username = System.getProperty("user.name");
		viewSetup();
	}

	private void viewSetup() {
		chat = new TextArea();
		chat.promptTextProperty().bind(workspace.getPolyglot().get("ChatHeader"));
		chat.setEditable(false);
		chat.setPrefHeight(Double.MAX_VALUE);
		chat.setWrapText(true);
		setCenter(chat);
		setBottom(createSendBox());
	}

	private Node createSendBox() {
		HBox sendBox = new HBox();
		sendField = new TextField();
		sendField.promptTextProperty().bind(workspace.getPolyglot().get("ChatEntryPlaceholder"));
		sendField.setOnKeyPressed(e -> sendKeyPressed(e));
		Button sendButton = new Button();
		sendButton.textProperty().bind(workspace.getPolyglot().get("SendButton"));
		sendButton.setOnAction(e -> send());
		sendBox.getChildren().addAll(sendField, sendButton);
		sendBox.setAlignment(Pos.CENTER);
		HBox.setHgrow(sendField, Priority.ALWAYS);
		return sendBox;
	}

	private void sendKeyPressed(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER)) {
			event.consume();
			send();
		}
	}
	
	private void send() {
		try {
			workspace.getNetworking().send(new Message(username, sendField.getText()));
			sendField.setText("");
		}
		catch (Exception e) {
			System.out.println("The message could not be sent.");
		}
	}

	public void received(Packet packet) {
		appendToChat((Message) packet);
	}

	private void appendToChat(Message message) {
		chat.appendText(message.getUsername() + " > " + message.getMessage() + "\n");
	}

}
