package player.menus;

import java.util.Random;

import javafx.animation.FadeTransition;
import javafx.beans.binding.StringBinding;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import utils.views.View;

/**
 * @author Elliott Bolzan
 *
 */
public class Tile extends View {

	/**
	 * 
	 */
	public Tile(StringBinding titleBinding, String color, EventHandler<Event> handler) {
		getStyleClass().add(color);
		GridPane.setHgrow(this, Priority.ALWAYS);
		GridPane.setVgrow(this, Priority.ALWAYS);
		GridPane.setValignment(this, VPos.TOP);
		setOnMouseClicked(handler);
		createTitle(titleBinding);
		setOpacity(0);
		animate();
	}

	public void setOnAction(EventHandler<Event> handler) {
		setOnMouseClicked(handler);
	}

	private void createTitle(StringBinding titleBinding) {
		Label title = new Label();
		title.getStyleClass().add("tile-title");
		title.textProperty().bind(titleBinding);
		title.setWrapText(true);
		title.setPadding(new Insets(10));
		title.setTextAlignment(TextAlignment.CENTER);
		setCenter(title);
	}

	private void animate() {
		FadeTransition ft = new FadeTransition(Duration.millis(new Random().nextInt(1000) + 500), this);
		ft.setFromValue(0.0);
		ft.setToValue(1.0);
		ft.play();
	}

}
