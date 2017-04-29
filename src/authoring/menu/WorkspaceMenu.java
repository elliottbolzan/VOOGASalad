package authoring.menu;

import java.util.ResourceBundle;

import authoring.Workspace;
import authoring.components.ComponentMaker;
import javafx.scene.control.Menu;
import polyglot.Case;
import polyglot.Polyglot;

/**
 * @author Elliott Bolzan
 *
 */
public abstract class WorkspaceMenu extends Menu {
	
	private Workspace workspace;
	
	public WorkspaceMenu(Workspace workspace) {
		this.workspace = workspace;
		setTitle();
		setItems();
	}

	protected Polyglot getPolyglot() {
		return workspace.getPolyglot();
	}

	protected ResourceBundle getIOResources() {
		return workspace.getIOResources();
	}
	
	protected ComponentMaker getMaker() {
		return workspace.getMaker();
	}
	
	protected Workspace getWorkspace() {
		return workspace;
	}
	
	protected void setTitle(String property) {
		textProperty().bind(getPolyglot().get(property, Case.TITLE));
	}

	protected abstract void setTitle();
	
	protected abstract void setItems();

}
