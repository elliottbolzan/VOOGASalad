/**
 * 
 */
package player;

import java.util.ResourceBundle;

import authoring.components.ComponentMaker;
import javafx.beans.binding.StringBinding;
import javafx.scene.Scene;
import polyglot.Polyglot;
import utils.views.View;

/**
 * @author Elliott Bolzan
 *
 */
public class PlayerView extends View {

	private Polyglot polyglot;
	private ResourceBundle IOResources;
	private ComponentMaker factory;
	
	/**
	 * @param title
	 */
	public PlayerView(StringBinding title) {
		super(title);
	}

	/**
	 * 
	 */
	public PlayerView(Polyglot polyglot, ResourceBundle IOResources) {
		this.polyglot = polyglot;
		this.IOResources = IOResources;
		this.factory = new ComponentMaker(polyglot, IOResources.getString("StylesheetPath"));
		setMinSize(420, 600);
	}
	
	public Polyglot getPolyglot() {
		return polyglot;
	}
	
	public ResourceBundle getResources() {
		return IOResources;
	}
	
	public Scene createScene() {
		Scene scene = new Scene(this, 420, 600);
		scene.getStylesheets().add(IOResources.getString("StylesheetPath"));
		return scene;
	}
	
	public ComponentMaker getFactory() {
		return factory;
	}

}
