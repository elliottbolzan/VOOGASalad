package authoring;

import java.util.List;

import engine.actions.ActionInterface;
import engine.entities.EntityInterface;
import engine.events.EventInterface;
import javafx.scene.Node;

/**
 * @author Elliott Bolzan
 * @author Jimmy Shackford
 *
 *         This interface represents the main methods that will be used by the
 *         Authoring Environment internally. These methods will be used in the
 *         Authoring Environment's three components: the Settings panel, the
 *         Canvas, and the Entity panel. They deal with user-input; Entity,
 *         Event, and Action creating, editing, and saving; and the visualizing
 *         of Entities on the Canvas.
 */
public interface AuthoringInternalAPI
{

	/**
	 * Adds a Node representing an Entity to the Canvas for visualization and
	 * editing.
	 * 
	 * @param node
	 *            the Node to be added to the Canvas.
	 */
	public void addNodeToCanvas(Node node);

	/**
	 * Creates an Entity from its name using reflection
	 * 
	 * @param entityName
	 *            the name of the Entity to be created
	 * @return an Entity
	 */
	public EntityInterface createEntity(String entityName);

	/**
	 * Creates an Entity from its name using reflection.
	 * 
	 * @param entityName
	 *            the name of the Entity to be created.
	 * @param layer
	 *            the layer to which the Entity will be added.
	 * @return an Entity.
	 */
	public EntityInterface createEntity(String entityName, int layer);

	/**
	 * Creates an Event from its name using reflection.
	 * 
	 * @param eventName
	 *            the name of the Event to be created.
	 * @return an Event.
	 */
	public EventInterface createEvent(String eventName);

	/**
	 * Creates an Action from its name using reflection.
	 * 
	 * @param actionName
	 *            the name of the Action to be created.
	 * @return an Action.
	 */
	public ActionInterface createAction(String actionName);

	/**
	 * Adds an Event to an Entity.
	 * 
	 * @param entity
	 *            the Entity that the Event will be added to.
	 * @param event
	 *            the Event to add to the Entity.
	 */
	public void addEventToEntity(EntityInterface entity, EventInterface event);

	/**
	 * Adds an Action to an Event.
	 * 
	 * @param event
	 *            the Event to add the Action to.
	 * @param action
	 *            the Action to be added to the Event.
	 */
	public void addActionToEvent(EventInterface event, ActionInterface action);

	/**
	 * Returns a List of Entities added to one specific level by the user.
	 * 
	 * @param level
	 *            the number of the level to be considered.
	 */
	public List<EntityInterface> getEntitiesForLevel(int level);

	/**
	 * Edit a specific Entity.
	 * 
	 * @param entity
	 *            the Entity to be edited.
	 */
	public void editEntity(EntityInterface entity);

	/**
	 * Edit a specific Event.
	 * 
	 * @param event
	 *            the Event to be edited.
	 */
	public void editEvent(EventInterface event);

	/**
	 * Edit a specific Action.
	 * 
	 * @param action
	 *            the Action to be edited.
	 */
	public void editAction(ActionInterface action);

	/**
	 * Create a new Level when requested by the user. Display a new tab in which
	 * the user can add new Entities. Create the new Level from the previous
	 * one: this way, the user has a minimal amount of work when designing.
	 */
	public void newLevel();

	/**
	 * Creates a new layer. The layer's number is one plus the last layer's
	 * number. The default foreground's layer number is 0.
	 */
	public void newLayer();

	/**
	 * Save the game to file. Makes use of the Game Data module.
	 */
	public void saveGame();

	/**
	 * Detect a mouse click on the Canvas. Necessary to add new Entities to the
	 * Canvas: when the user has selected an Entity and clicks on the Canvas.
	 * the Entity should be added to the Canvas at that location.
	 */
	public void handleMouseClick();

	/**
	 * Detect a mouse hover on the Canvas. Necessary to support adding multiple
	 * Entities at once. The user can hold down the SHIFT key, press the mouse,
	 * and drag over the Canvas to create Entities on the path it has followed.
	 */
	public void handleMouseHover();

	/**
	 * Returns the Entity that the user has selected in the Entity panel.
	 * 
	 * @return the currently selected Entity.
	 */
	public EntityInterface getSelectedEntity();

	/**
	 * Let the user pick game-wide song. Implemented using a FileChooser. The
	 * resulting File is sent to the Game Data module for saving.
	 */
	public void pickSong();

}
