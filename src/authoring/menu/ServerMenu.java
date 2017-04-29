package authoring.menu;

import authoring.Workspace;
import javafx.scene.control.MenuItem;

/**
 * @author Elliott Bolzan
 *
 */
public class ServerMenu extends WorkspaceMenu {

	/**
	 * @param workspace
	 */
	public ServerMenu(Workspace workspace) {
		super(workspace);
		setOnShowing(e -> serverMenuWillShow());
		serverMenuWillShow();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see authoring.menu.WorkspaceMenu#setTitle()
	 */
	@Override
	protected void setTitle() {
		setTitle("ServerMenu");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see authoring.menu.WorkspaceMenu#setItems()
	 */
	@Override
	protected void setItems() {
	}

	private void serverMenuWillShow() {
		MenuItem IPItem = getMaker().makeMenuItem("IP", e -> getWorkspace().getNetworking().showIP(), true);
		MenuItem startItem = getMaker().makeMenuItem("StartServer", e -> getWorkspace().getNetworking().start(), true);
		MenuItem joinItem = getMaker().makeMenuItem("JoinServer", e -> getWorkspace().getNetworking().join(), true);
		MenuItem disconnectItem = getMaker().makeMenuItem("Disconnect", e -> getWorkspace().getNetworking().close(), true);
		getItems().clear();
		if (getWorkspace().getNetworking().isConnected()) {
			getItems().addAll(IPItem, disconnectItem);
		} else {
			getItems().addAll(IPItem, startItem, joinItem);
		}
	}

}
