package player;

import java.util.ResourceBundle;

import javafx.stage.Stage;
import polyglot.Polyglot;

public class OptionsMenu extends AbstractMenu {

	public OptionsMenu(Stage stage, Loader loader, Polyglot polyglot, ResourceBundle IOResources) {
		super(stage, loader, "OptionsTitle", polyglot, IOResources);
	}

}
