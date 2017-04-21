package authoring.canvas;

import java.util.ResourceBundle;

import authoring.Workspace;
import engine.Entity;
import engine.entities.CharacterEntity;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import polyglot.Polyglot;
import polyglot.PolyglotException;

/**
 * Some code based off of
 * http://stackoverflow.com/questions/16680295/javafx-correct-scaling
 * 
 * @author jimmy
 *
 */
public class GridScrollTest extends Application
{
	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(final Stage stage)
	{
		final Group group = new Group();
		ExpandablePane exp = new ExpandablePane();
		Entity ent = new CharacterEntity();
		ResourceBundle IOResources = ResourceBundle.getBundle("resources/IO");
		final String KEY = "AIzaSyCOWQRgYSfbiNnOdIRPBcuY6iLTqwfmOc4";
		Polyglot polyglot = null;
		try {
			polyglot = new Polyglot(KEY, "resources/Strings");
		} catch (PolyglotException e) {
		}
		EntityView entity = new EntityView(ent, new Canvas(new Workspace(null, polyglot, IOResources)), 25, 0, 0);
		EntityView entity2 = new EntityView(ent, new Canvas(new Workspace(null, polyglot, IOResources)), 25, 25, 25);
		// exp.addEntity(entity, 200, 200);
		// exp.addEntity(entity2, 300, 300);
		group.getChildren().add(exp);
		ZoomablePane zoomPane = new ZoomablePane(group);

		VBox layout = new VBox();
		layout.getChildren().setAll(zoomPane);

		VBox.setVgrow(zoomPane, Priority.ALWAYS);

		Scene scene = new Scene(layout);

		stage.setScene(scene);
		stage.show();
	}

}