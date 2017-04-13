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

