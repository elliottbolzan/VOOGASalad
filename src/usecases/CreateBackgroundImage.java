package usecases;

import authoring.AuthoringInternalAPI;
import engine.actions.Action;
import engine.actions.ActionInterface;
import engine.entities.Entity;
import engine.entities.EntityInterface;
import engine.events.Event;
import engine.events.EventInterface;

/**
 * @author Jimmy Shackford
 *
 *         This is an implementation of the following use-case: Add a background
 *         to the game and specify the speed it moves at
 */
public class CreateBackgroundImage
{
	/**
	 * This constructor contains the necessary API calls to create a new
	 * background image. It is only in this constructor for demonstration
	 * purposes. The background scrolls as the character moves (as user presses
	 * left or right)
	 * 
	 * @param authoring
	 *            Instance of a class implementing AuthoringInternalAPI
	 */
	public CreateBackgroundImage(AuthoringInternalAPI authoring)
	{
		// adds the background layer, represented as layer 1
		authoring.newLayer();
		// create the background image entity for the background layer (layer 1)
		EntityInterface background = authoring.createEntity("Backgrond", 1);
		// create the events for pressing the left or right arrow keys
		EventInterface rightKey = authoring.createEvent("RightKey");
		EventInterface leftKey = authoring.createEvent("LeftKey");
		// create the move left/right actions
		ActionInterface moveRightAction = authoring.createAction("MoveRight");
		ActionInterface moveLeftAction = authoring.createAction("MoveLeft");
		// attach the left/right keys to the move left/right actions
		authoring.addActionToEvent(rightKey, moveRightAction);
		authoring.addActionToEvent(leftKey, moveLeftAction);
		// attach the right/left keys and movement actions
		// to the background image
		authoring.addEventToEntity(background, rightKey);
		authoring.addEventToEntity(background, leftKey);
	}

	/**
	 * This method creates a background which scrolls at a constant speed (as in
	 * Mario)
	 * 
	 * @param authoring
	 *            Instance of a class implementing AuthoringInternalAPI.
	 */
	public void CreateConstantlyScrollingBackground(AuthoringInternalAPI authoring)
	{
		// adds the background layer, represented as layer 1
		authoring.newLayer();
		// create the background image entity for the background layer (layer 1)
		EntityInterface background = authoring.createEntity("Background", 1);
		// set the speed of the background image to a constant velocity (as in
		// Mario).
		// this constant velocity is randomly determined to be 10.
		background.setXSpeed(10);
	}
}
