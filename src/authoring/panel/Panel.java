package authoring.panel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import authoring.Workspace;
import authoring.components.Direction;
import authoring.panel.chat.Chat;
import authoring.panel.display.EntityDisplay;
import authoring.panel.info.InfoPanel;
import authoring.panel.settings.Settings;
import javafx.beans.binding.StringBinding;
import javafx.scene.control.Accordion;
import utils.views.CollapsibleView;
import utils.views.View;
import polyglot.Case;

/**
 * @author Elliott Bolzan
 *
 *         A class that encapsulates a panel architecture. New subpanels can be
 *         added using a single line of code, placed in the createSubviews()
 *         method. This system seeks to facilitate the addition of new subviews
 *         and to reduce duplicated boilerplate code.
 */
public class Panel extends CollapsibleView {

	private Workspace workspace;
	private List<View> subviews;
	private EntityDisplay entityDisplay;
	private Chat chat;
	private Settings settings;
	private LayerPanel layerPanel;
	private InfoPanel info;

	/**
	 * Returns a Panel.
	 * 
	 * @param workspace
	 *            the Workspace that owns the Panel.
	 * @param index
	 *            the index of the divider that the Panel collapses to, in the
	 *            SplitPane that owns it.
	 */
	public Panel(Workspace workspace, int index) {
		super(workspace, workspace.getPane(), workspace.getPolyglot().get("PanelTitle", Case.TITLE), index,
				Direction.LEFT, false);
		this.workspace = workspace;
		entityDisplay = new EntityDisplay(workspace);
		chat = new Chat(workspace);
		settings = new Settings(workspace);
		layerPanel = new LayerPanel(workspace);
		info = new InfoPanel(workspace);
		createSubviews();
		setup();
	}

	/**
	 * Populate the subviews List, which dictates which subviews appear in the
	 * Accordion.
	 */
	private void createSubviews() {
		subviews = new ArrayList<View>();
		subviews.add(entityDisplay);
		subviews.add(chat);
		subviews.add(layerPanel);
		subviews.add(info);
	}

	/**
	 * Create the Accordion and add it to the view.
	 */
	private void setup() {
		List<StringBinding> info = new ArrayList<StringBinding>(
				Arrays.asList(workspace.getPolyglot().get("EntityInfo"), workspace.getPolyglot().get("ChatInfo"),
						workspace.getPolyglot().get("LayerPanelInfo"), workspace.getPolyglot().get("GameInfo")));
		Accordion accordion = workspace.getMaker().makeAccordion(subviews, info);
		accordion.getStyleClass().add("gae-tile");
		setCenter(accordion);
	}

	/**
	 * @return the EntityDisplay.
	 */
	public EntityDisplay getEntityDisplay() {
		return entityDisplay;
	}

	/**
	 * @return the Settings subview.
	 */
	public Settings getSettings() {
		return settings;
	}

	public Chat getChat() {
		return chat;
	}

	/**
	 * When the user switches between level tabs or selects a new level, the
	 * layerPanel must be notified so that the combobox will show only the names
	 * of the layers contained in the new level.
	 * 
	 * @param layerNum
	 */

	/**
	 * Select an existing level.
	 * 
	 * @param layerNum
	 *            the layer to be selected.
	 */
	public void selectExistingLevelBox(String oldLevel, String newLevel) {
		layerPanel.selectLevelBox(oldLevel, newLevel);
	}

	public void selectLoadedLevelBox(List<String> nameList) {
		layerPanel.selectLevelBox(nameList);
	}

	public void selectLoadedLevelBox(int layerCount) {
		layerPanel.selectLevelBox(layerCount);
	}
}
