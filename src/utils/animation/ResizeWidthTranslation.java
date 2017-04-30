package utils.animation;

import javafx.animation.Transition;
import javafx.scene.layout.Region;
import javafx.util.Duration;

/**
 * @author Elliott Bolzan
 * 
 *         Adapted from:
 *         https://rterp.wordpress.com/2015/09/01/creating-custom-animated-transitions-with-javafx/..
 *
 */
public class ResizeWidthTranslation extends Transition {

	protected Region region;
	protected double startWidth;
	protected double newWidth;
	protected double widthDiff;

	public ResizeWidthTranslation(Duration duration, Region region, double newWidth) {
		setCycleDuration(duration);
		this.region = region;
		this.newWidth = newWidth;
		this.startWidth = region.getWidth();
		this.widthDiff = newWidth - startWidth;
	}

	@Override
	protected void interpolate(double fraction) {
		region.setMinWidth(startWidth + (widthDiff * fraction));
	}

}