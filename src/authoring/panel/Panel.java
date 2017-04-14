package authoring.panel;

import java.util.ArrayList;
import java.util.List;

import authoring.Workspace;
import authoring.components.ComponentMaker;
import authoring.panel.achievements.Achievements;
import authoring.panel.chat.Chat;
import authoring.panel.display.EntityDisplay;
import authoring.panel.settings.Settings;
import authoring.utils.Direction;
import authoring.views.CollapsibleView;
import authoring.views.View;
import engine.Entity;
import javafx.scene.layout.VBox;

public class Panel extends CollapsibleView {

	private Workspace workspace;
	private List<View> subviews;
	private EntityDisplay entityDisplay;
	private Settings settings;
	private LayerPanel layerPanel;
	private Achievements achievements;

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
		super(workspace, workspace.getPane(), workspace.getResources().getString("PanelTitle"), index, Direction.LEFT,
				true);
		this.workspace = workspace;
		entityDisplay = new EntityDisplay(workspace);
		settings = new Settings(workspace);
		layerPanel = new LayerPanel(workspace);
		achievements = new Achievements(workspace);
		createSubviews();
		setup();
	}

	/**
	 * Populate the subviews Lists, which dictates which subviews appear in the
	 * Accordion.
	 */
	private void createSubviews() {
		subviews = new ArrayList<View>();
		subviews.add(entityDisplay);
		subviews.add(new Chat(workspace));
		subviews.add(layerPanel);
		subviews.add(achievements);
		subviews.add(settings);
	}

	/**
	 * Create the Accordion and add it to the view.
	 */
	private void setup() {
		ComponentMaker maker = new ComponentMaker(workspace.getResources());
		setCenter(maker.makeAccordion(subviews));
		setBottom(new VBox(maker.makeButton("SaveButtonSettings", e -> workspace.save(), true),
				maker.makeButton("TestButtonSettings", e -> workspace.test(workspace.getGame()), true)));
	}

	public EntityDisplay getEntityDisplay() {
		return entityDisplay;
	}

	public Settings getSettings() {
		return settings;
	}

	public void updateLayerPanel(String newLayer) {
		layerPanel.updateBox(newLayer);
	}

	public void selectExistingLevelBox(int layerNum) {
		layerPanel.selectLevelBox(layerNum);
	}

}