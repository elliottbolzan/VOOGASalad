package engine.game.selectiongroup;

import java.util.Collection;
import java.util.List;

/**
 * Note: This is NOT part of any API since it is only used within the Game Engine.
 * @author Matthew Barbano
 *
 * @param <T>
 */
public interface SelectionGroup<T extends Selectable> {
	SGIterator<T> createIterator();
	int size();
	void add(T element);
	void add(int index, T element);
	void addAll(Collection<T> collection);
	void set(int index, T element);
	void remove(int index);
	T get(int index);
	List<T> getListRepresentation();
}
