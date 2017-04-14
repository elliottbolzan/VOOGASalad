Use Cases - Final Sprint

**Written by:** Elliott Bolzan (eab91), Jesse Yue (jty4), Jay Doherty (jld60), Mina Mungekar(mem94), Nikita Zemlevskiy (naz7), Jimmy Shackford (jas199), Kyle Finke (kwf10), Matthew Barbano (meb100), Dennis Ling (dl186)

---------------

Jimmy Shackford (jas199)<br>
**Add a Camera Entity and allow the User to Specify how to moves**
- The user goes to the `Entity Panel` in the left-side menu of the Authoring Environment.
- The user creates an `Entity` for the `Camera` Entity.
- In this same left-panel, the user creates `Action-Event` pairs for each type of movement.
    - 1. If the user wants the camera to always be moving (like in Mario), then assign the Action-Event pair: Action = Always, Event = Move (at given velocity)
    - 2. If the user wants the camera movement to be attached to another Entity (like in Doodle Jump_, then add the Action-Event pair: Action = Always, Event = Bind (bind camera position to entity position + offset factor so that entity is in middle of screen).
- The user can actually add the `Camera` Entity to the GAE canvas by dragging and dropping it.
- The user can resize the `Camera` by dragging on the edges. It can be moved by dragging the center.

**When user drags mouse on one entity, drag all currently selected entities**
- To select multiple entities, the user holds down `Shift` and clicks on each entity they wish to select.
- To move the selected entities, the user drags the mouse on one of them. Intuitively, all selected ones should move.
- Note: Need to consider whether the user can resize all selected entities through the same process.

**Add a Background to the Game and Specify that it should Wrap Around.**
- The user goes to the `Entity Panel` on the left-side menu of the Authoring Environment.
- The user creates a background `Entity`.
- In this same left-side panel, the user can add a different layer for a background and select it.
- The user drag-and-drops the background entity to the canvas.
- In the left side-panel, the user specifies that the currently selected layer should wrap around (the camera object) by clicking a check box.

**User wishes to Precisely Place an entity (or multiple) by using the arrow keys**
- The user selects one or more `Entities` by `shift-clicking` all of them (or clicking just one) in the GAE canvas.
- The user clicks any of the arrow keys, which causes the selected entities to move in that direction by one grid tile.

------------------

Nikita Zemlevskiy (naz7)<br>
**Two users play a game simultaneously**
- Necessary objects will be serialized to XML. These include the entities, along with their locations and properties. 
- XML files will be sent to all those connected to the network via the many to many sharing library.
- Other users will recieve the XML files and update properties and locations of entities accordingly. 
 
**A character interacts with a block so that the block drops a powerup**
- The block has an event for colliding with the character.
- This event is triggered, telling the `PowerupAction` to `act`
- The action knows apriori what kind of powerup to generate. 
- It creates this powerup (another entity), sets its properties (speed, position, etc appropriately) and adds it to the level, letting the rest of the gameplay take care of what happens next

**User presses a key to throw a moving object**
- Similar to the powerup above, the character will have an event for reacting to the key
- The event will be triggered, telling the `ThrowAction` to `act`
- The action will create a new entity, and sets its properties appropriately. Here the speed is set to that the entity is moving. The newly created entity is then added to the level.

**User creates an entity with the ThrowAction**
- The user has to add an event to which this action will respond
- The user adds this action to that event
- The user must specify what entity to create and the properties it will have.
- Specifically, the user must designate where to place it and what speed it will have.


------------

Elliott Bolzan (eab91)

**User plays the game directly from the Game Authoring Environment.**<br>
**User sends a chat message using the networking library.**<br>
**User edits the Entities on the board by editing the default Entities.**<br>
**Saving the game is done on a separate thread.**<br>

-----------

Kyle Finke (kwf10)

**Update the player's score when they complete a level**
- Create a CollisionEvent for the main character with the "end-of-level" Entity
- Add an NextLevelAction to the CollsionEvent
- Add an IncreaseScoreAction to the CollisionEvent with the intended increase in score added as a Parameter of the action

**The player runs out of time on a level causing them to lose a life**
- A DieAction must be added to the TimerEvent
- The DieAction is triggered when the time for the timer runs out

**The user sets borders for a level that cause the player to die when they go outside of the borders**
- Create individual events for the top, bottom, left, and right edge of the border
- Add a DieAction to each of these Events that are triggered if the main character Entity moves outside of the border

**The player collides with a power-up that gives them an extra life**
- A new Entity is created to represent the power-up
- The main character Entity is given a CollisionEvent that corresponds to hitting the power-up Entity
- Add an IncreaseLifeAction to the CollisionEvent for the main character Entity
- The power-up Entity is given a CollisionEvent that corresponds to hitting the main character Entity
- Add a RemoveEntityAction to the CollisionEvent for the power-up Entity to remove the power-up from the screen

---------------------

Mina Mungekar (mem94)

**User sets a default level layout**
- The user modifies Level 1 and gives it an appropriate layout. Whenever the user hits the tab to select a new level, he or she is asked whether Level 1 should be copied over to the new level. 
- If the user selects yes, `getEntities` will be called on Layer 1 and stored. This list of Entities will be used to initialize the levels that follow. 

**User sets layer names**
- In the Layer Panel, an editable text box will be displayed every time a layer is selected.
- The user will be prompted to set the layer name, then hit the`Enter` button. Once the user does this, the list of layers contained in the LevelEditor will be updated, and the string representing the current level will be replaced by the one the user has entered.
- The layer names are contained in an ObservableList that the combobox possesses. Upon the name change, the combobox will update.

**User selects where he/she wants the progress bar displayed**
- The user will drag and drop the progress bar display to whichever part of the screen he or she desires.
- The progress bar will update in the game loop to reflect the user's progress through the level, number of lives, and score.

**User wishes to create time-based events**
- In the Authoring Environment, the user will be able to select a Timer button and edit it. 
- The Timer button will be an Entity, but not interact with the characters on screen.
- Through this button, the user can set the game time and assign different events to take place at certain times.
- The Timer will be written to an XML file, and the information it contains passed to the Game Engine. When the Game Loop timer hits a point at which the user specified an event to occur, the event will be triggered.

------------

Jay Doherty (jld60)

**The user moves and the camera follows the character on screen**
**A powerup is picked up and needs to be removed from the visual display**
**A block is hit and its image changes as a result (cracks appear or something)**
**TODO Maybe something about displaying layers differently? **

------------

Jesse Yue (jty4)
**Creating achievements**
- User goes to the achievements panel of GAE
- User sets a title for the achievement
- User sets a description for the achievement
- User sets parameters for the achievement
- User hits save achievement then the authoring environment asks game data to save it
**Creating game info**
- User goes to the settings panel of GAE
- User types in any information they think the player will want to know
- User hits save to save to the game data
**Loading in achievements**
- In the player menu, the achievements will be extracted from the game data and placed inside the player menus.
**Completing an Achievement**
- In the player, when the user completes all the parameters of an achievement there will be a pop up for an achievement
- The achievement menu will tick that achievement as completed




