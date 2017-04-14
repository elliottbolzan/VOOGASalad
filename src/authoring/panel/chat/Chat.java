package authoring.panel.chat;

import java.util.Random;

import authoring.Workspace;
import authoring.views.View;
import discussion.Discussion;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * @author Elliott Bolzan
 *
 *         A class representing a ChatView. Allows for a username, the viewing
 *         of sent messages, and the sending of messages. Makes use of a
 *         networking library.
 *
 */
public class Chat extends View {

	private static final String CHANNEL = "authoringChat";

	private Workspace workspace;
	private Discussion discussion;
	private TextArea chat;
	private TextField usernameField;
	private TextField sendField;
	private String username;

	/**
	 * Creates a Chat.
	 * @param workspace the workspace that owns the Chat.
	 */
	public Chat(Workspace workspace) {
		super(workspace.getResources().getString("ChatTitle"));
		this.workspace = workspace;
		setup();
	}

	private void setup() {
		username = "User_" + Integer.toString(new Random().nextInt(1000));
		// discussion = new Discussion();
		// discussion.listenOnChannel(e -> receivedMessage(e), CHANNEL);
		viewSetup();
	}

	private void viewSetup() {
		setTop(createUsernameBox());
		chat = new TextArea();
		chat.setPromptText(workspace.getResources().getString("ChatHeader"));
		chat.setEditable(false);
		chat.setPrefHeight(Double.MAX_VALUE);
		chat.setWrapText(true);
		setCenter(chat);
		setBottom(createSendBox());
	}

	private Node createUsernameBox() {
		HBox usernameBox = new HBox();
		usernameField = new TextField();
		usernameField.setPromptText("Username: " + username);
		usernameField.setOnKeyPressed(e -> saveKeyPressed(e));
		Button saveButton = new Button(workspace.getResources().getString("SaveButtonChat"));
		saveButton.setOnAction(e -> save());
		usernameBox.getChildren().addAll(usernameField, saveButton);
		usernameBox.setAlignment(Pos.CENTER);
		HBox.setHgrow(usernameField, Priority.ALWAYS);
		return usernameBox;
	}

	private Node createSendBox() {
		HBox sendBox = new HBox();
		sendField = new TextField();
		sendField.setPromptText(workspace.getResources().getString("ChatEntryPlaceholder"));
		sendField.setOnKeyPressed(e -> sendKeyPressed(e));
		Button sendButton = new Button(workspace.getResources().getString("SendButton"));
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

	private void saveKeyPressed(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER)) {
			event.consume();
			save();
		}
	}

	private void save() {
		username = usernameField.getText();
	}

	private void send() {
		Message message = new Message(username, sendField.getText());
		appendToChat(message);
		// discussion.send(message, CHANNEL);
		sendField.setText("");
	}

	private void receivedMessage(Object object) {
		Message message = (Message) object;
		appendToChat(message);
	}

	private void appendToChat(Message message) {
		chat.appendText(message.getUsername() + " > " + message.getMessage() + "\n");
	}

}
