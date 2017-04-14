package authoring.panel.creation.pickers;

import authoring.Workspace;
import authoring.components.EditableContainer;
import authoring.panel.creation.EntityMaker;
import engine.GameObject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * @author Elliott Bolzan
 *
 */
public abstract class Picker extends EditableContainer {

	private EntityMaker maker;
	private String titleProperty;

	/**
	 * @param title
	 */
	public Picker(Workspace workspace, String titleProperty, EntityMaker maker) {
		super(workspace, "");
		this.titleProperty = titleProperty;
		this.maker = maker;
		setup();
	}

	public EntityMaker getEntityMaker() {
		return maker;
	}

	private void setup() {
		setPadding(new Insets(15));
		createTypeBox();
		createContainer();
	}

	private void createTypeBox() {
		Label label = new Label(getWorkspace().getResources().getString(titleProperty));
		label.setPadding(new Insets(5));
		HBox box = new HBox();
		box.getChildren().add(label);
		box.setAlignment(Pos.CENTER);
		setTop(box);
	}

	public <E> void setOnClick(ListView<E> list, Runnable single) {
		list.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getClickCount() == 1) {
					single.run();
				} else if (mouseEvent.getClickCount() == 2) {
					edit();
				}
			}
		});
	}

	public abstract void select(GameObject object);

	public abstract void createContainer();

	public abstract void createNew();

	public abstract <E> void add(E element);

	public abstract <E> void remove(E element);

	public abstract void delete();

	public abstract void update();

	public abstract void edit();

	public abstract void showEditor();

}
