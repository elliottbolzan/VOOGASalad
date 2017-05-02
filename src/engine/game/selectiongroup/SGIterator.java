package engine.game.selectiongroup;

/**
 * 
 * @author Matthew Barbano
 *
 * @param <T>
 */
public interface SGIterator<T> {
	public boolean hasNext();

	public T next();
}
