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

Jesse Yue (jty4)<br>
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
- In the player menu, the achievements will be extracted from the game data and placed inside the achievement menu.
**Completing an Achievement**
- In the player, when the user completes all the parameters of an achievement there will be a pop up for an achievement
- The achievement menu will tick that achievement as completed

