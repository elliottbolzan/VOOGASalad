package engine.game.timer;

/**
 * 
 * @author Matthew Barbano
 *
 */
public interface TickStrategy {
	public int tick(int milliseconds);
	public boolean timeIsUp(int milliseconds);
}
