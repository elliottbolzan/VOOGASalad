package authoring.views;

import javafx.beans.binding.StringBinding;

/**
 * @author Elliott Bolzan
 *
 */
public class ConcreteView extends View {

	/**
	 * @param title
	 */
	public ConcreteView(StringBinding title) {
		super(title);
	}
	
	public ConcreteView() {
		
	}

}
