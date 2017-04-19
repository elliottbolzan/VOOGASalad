package player;

import java.util.ResourceBundle;

import javafx.stage.Stage;
import polyglot.Polyglot;

public class InfoMenu extends AbstractMenu {

	public InfoMenu(Stage stage, Loader loader, Polyglot polyglot, ResourceBundle IOResources) {
		super(stage, loader, "InfoTitle", polyglot, IOResources);
	}

}
