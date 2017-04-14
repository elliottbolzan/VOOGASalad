Design Specifications
===========

**Written by:** Elliott Bolzan (eab91), Matthew Barbano (meb100), Jimmy Shackford (jas199), Nikita Zemlevskiy (naz7), Jay Doherty (jld60), Jesse Yue (jty4), Mina Mungekar (mem94), Kyle Finke (kwf10).

## Introduction

Our team is trying to solve the following problems:
*	Allow a user to create their own platform scroller game through a game authoring environment
*	Allow a user to edit a previously created game
*	Allow a user to play a game that they created

The design/flexibility goals of the project are:
*	Allow future programmers to add new types of field objects
*	Easily allow users to change the actions that occur for each of the following cases:
    *	Mouse interactions (mouse click, drag, move, etc.)
    *	Object collisions from multiple directions (ex. mario collides with a block from the bottom)
    *	Timer expirations (game clock runs out, koopa shell turns into koopa monster after certain amount of time)
*	Easily allow users to save the state of the game
*	Easily allow users to save/load a game they created in the authoring environment
*	Add animations to objects.
*	Add different levels to a game that has already been created.

The closed/open portions of the design architecture are:
*	Open (change these classes to allow for future extensibility): 
    *	Field object inheritance structure so that new ones can be added
    *	Actions (for mouse interactions, object collisions, timer expirations)
    *	The game display in the game player
    *	Game settings
*	Closed (don't need to change these classes to allow for future extensibility):
    *	How the game authoring environment saves/loads a created game
    *	Adding a new element to the game through the authoring environment
    *	Selecting actions in the authoring environment to assign to a certain event
    
The scrolling platformer game genre is one where the screen is focused on a main character, who is in a stage much larger than the screen depicts at a given moment. As the character moves, the screen moves around with the character to depict other parts of the stage. Within the game, there are elements that the main character can interact with (blocks that the user can hit to get power-ups, a floor which the main character can run on, etc.). Moreover, there are certain winning/losing conditions. Our game will need to support interactions between different objects, interactions between the mouse and the field, and the win/lose conditions. Moreover, we will need to design the game to support a screen which moves with the character. In some scrolling platformer games, the background moves at different speeds than the foreground. Thus, we will need to support the addition of multiple foreground/background layers.

At a very high-level, we need to have a game-authoring environment which allows users to create their own games/levels and store them to a file. Then, we need to be able to load these levels and play them within a game player. The game engine determines how the game changes when certain buttons or pressed or certain actions occur.  

## Overview

On the highest level, our project will include the standard four subgroups, each of which contains several modules:
*	Game Authoring Environment
*	Game Engine
*	Game Player
*	Game Data Manager

The Game Player, which will play a game with a configuration that the user has saved, will communicate with the Game Engine to obtain data for running the game. It will not communicate with any other of the four subgroups. The Game Engine will communicate with both the Game Authoring Environment, which will take care of updating the UI, and the Game Data Manager, which will save games that the user has created.

The Game Engine will be divided into the following modules:
*	Game Module- The highest-level module, manages the flow of time, organization of levels, and interaction with the Game Player. It will contain the following classes:
*	Entity Module. - Anything drawn on the game screen.
*   Event Module (attached to Object). Defined as one of the following:
*	Action Module (attached to Event). - The consequence of each Event. Examples are:

The GAE will be divided into the following modules:

* Entity Display Module:
        *   Displays Entities that the user has created. Allows the user to edit these Entities, or to request the creation of new ones. 
* Entity Editing Module:
        *	A module that closely relates to the Entity module. Here, the user will have the opportunity to create and edit Entities by assigning Events and Actions to them.
*	Settings Module - For editing game-wide settings and saving the game to disk.
*	Canvas Module - For visualizing the Entities added to the game and interacting with them. The user should be able to move these Entities around.

The Game Player will consist of three modules:

*	I/O Module – For sending user input to the Game Engine and receiving user output from the Game Engine.

*	Save Progress Module - For saving the users progress in the game.

*	Play Module – For displaying the game and any auxiliary GUI components. This includes the actual display for the game as well as displays for saving, loading, and seeing high-scores.


The Game Data will consist of the following modules:
*	Input Module
*	Output Module

Diagram of Modules and Their Relationships:

![](images/VoogaModules.png)



## User Interface

The User Interface for this project will be composed of three primary units:

1. The Starter Window.
2. The Authoring Environment.
3. The Game Player.

These three units are described below:

1\. **The Starter Window**

The Starter Window is a very simple window that has the title and 5 buttons:
1. A New button that opens up the Game Authoring Environment from scratch.
3. An Edit button to go into the GAE with the chosen game.
4. A Play button that goes into the Game Player with the chosen game.

![](images/starter.png)

2\. **The Authoring Environment**

The Authoring Environment will be further subdivided into three parts:

1. The Settings (on the bottom left in the image below).
2. The Canvas (in the center in the image below).
3. The Entity Panel (on the left in the image below).

![](images/authoring.png)

- **The Settings**

The **Settings** panel will harbor all game-wide settings. 

These settings may include: the scrolling platformer's orientation, whether the game scrolls automatically or when prompted by the character, the game's song, etc.

In addition, the Settings will contain a `Save Game` button, that allows for the saving of the designed game to file. 

Generally speaking, this section will only include "traditional" user interface elements: there should be no surprises in how the user should interact with the Settings.

- **The Canvas**

The **Canvas** is the section in which the constructed game will be visualized. 

The **Canvas** will be tabbed: each tab will correspond to one **Level** in the game. Furthermore, any new **Level** should be created as a duplicate of the current **Level**, in order to minimize the user's design time.

The user will be able to add **Entities** to the **Canvas** by clicking on an **Entity** in the **Entity Panel** and then clicking on the **Canvas**. Once the **Entity** is added to the **Canvas**, the user will be able to move it to its appropriate location by dragging it.

Clicking on an existing **Entity** will open it in the **Entity Panel**'s editor.

The **Canvas** should be able to extend infinitely, either horizontally or vertically. This way, the user can create games of any size. 

- **The Entity Panel**

This component's purpose is the creation, display, and editing of **Entities**, the basic building blocks of games. This component is split into two parts: the created entity display, and the entity editor.

The created entity display will show the user which entities have been created, and provide a way for creating new entities. Upon clicking on an entity, the user will be able to click on the **Canvas** to add that entity to the game. 

The entity editor will allow the user to create a new **Entity**. The user should be able to set the type of this new object; its image; and **Events** to which this **Entity** responds, associated to **Actions**. Most of the game's functionality should be created from this subpanel.

*Errors will be indicated to the user through a JavaFX Dialog. Possible errors include*:

- Incorrect parameters when an **Action** is created.
- Incorrect parameters for game-wide settings.
- Seeking to delete the game's only existing level.

3\. **The Game Player**

The Game Player will load a main menu with buttons that are appropriate for the games. It will have 5 options:

1. Play Game
2. Highscores
3. Options
4. Game Data
5. Achievements


- **Play Game**
This button leads to a menu where they can either start a new game or from a previous save point.
Upon selecting a game they will be given the option of starting from the beginning or from a previous save point. 

- **Highscores**
This button leads to a menu of all the highscores in the game.

- **Options**
This button leads to a menu where the user can adjust things like audio volume, in game controls, etc.

- **Game Data**
This button leads to a menu that holds descriptions of the game. This is a hub that holds useful information for a game, backstory, character data, or anything the game developer wants to include.

**Achievements**
This button leads to a menu that holds achievements for the game. Achievements can be completed by meeting the required parameters and will create a pop up once achieved.

![](images/mainmenu.png)

Inside the actual game, there will be a toolbar at the top with 5 controls:

1. Exit
2. Save
3. Restart
4. Play/Pause

- **Exit**
Exits the game and takes you back to the Game Player main menu.

- **Save**
Saves the game into a data file for continued play. Up to 5 saves per game, once there are more than 5 saves the oldest one will be overwritten.

- **Restart**
Restarts the game from the very beginning. Reloads the data file.

- **Play/Pause**
Pauses or plays the game.

There will also be a HUD for the score, lives, and time.

![](images/gamerunner.png)

## Design Details

**Game Authoring Environment**

* The `Canvas Module` will be the canvas on which the user edits and creates the game. It will provide the functionality of adding multiple backgrounds/foregrounds to the game as well as serving as a visual interface for the user to add and modify entities. The `Canvas Module` will directly communicate with the `Entity Display Module` and the `Entity Editing Module` -- once an entity has been customized, the user should directly be able to add it to the canvas while maintaining its user-defined properties.

* The `Entity Editing Module` will serve as a creation point for Entities. In this panel, the user will be able to mix and match Events and Actions, before assigning them to the Entity being created. Once an Entity is created in this module, it will appear in the `Entity Display Module`. Likewise, an Entity in the `Entity Display Module` should be editable using this module.

* The `Entity Display Module` will provide the user with the ability to select and drag entities to the canvas. In addition, the module will give the user the option of launching the `Entity Editing Module` to edit an existing Entity, and will also allow the user to delete a created Entity.

* The `Settings Module` will allow the user to change game-wide settings. It contains a save button that directly communicates to the Game Data module (through a listener) when a game needs to be written to an XML file. The Settings Module will be different from the other modules in the sense that whatever is selected through it will be applied to the entire game (i.e., background music and scrolling direction). New settings can be added to the module through the addition of specific text fields/combo-boxes that directly reference valid features of the game. 

* All information contained in the authoring environment (game layout, custom entities created, etc.) will be passed to `Game Data` and saved. This way, not only will the user be able to access a saved game, he/she would also be able to save anything customized in the authoring environment. 

**Game Player**
Has 3 modules

*	I/O Module – For sending user input to the Game Engine and receiving user output from the Game Engine.
*	Play Module – For displaying the game and any auxiliary GUI components. This includes the actual display for the game as well as displays for saving, loading, and seeing high-scores.
*   SaveProgress Module - For saving the current state of the game and restoring it later.

- The Game Player has a menu that holds all the highscores of the current game taken from a data file loaded up with the I/O module. When the user completes the game, their score will be added into the file and sorted with the rest of the highscores.
- The HUD will be created in the Play module and is overlayed on top of the game.
- There is a reset button in the Play module that restarts the file to its original position so that the user doesn't have to quit to restart.
- There is an exit button in the Play module that allows the user to return to the main menu to choose a new game without closing the program.
- A file chooser at start up that goes through the I/O module allows the user to see the default set of games but also allow the user to choose any game that they download from anywhere else in their computer.
- There is a save button in the Play module that saves the data into a new file that can be loaded up later.
- There is an options menu in the Play module where the user can adjust preferences for the game.

The only other module it interacts with is the Game Engine, where it gets all the resources it needs to run the game.

**Game Engine**
*	Game Module- The highest-level module, manages the flow of time, organization of levels, and interaction with the Game Player. It will contain the following classes/packages.
   *	TimerManager - Information about the current time. Uses the <b>Strategy</b> Design Pattern to substitute implemetation of tick() based on whether clock ticks up or down.
   *	GameLoop - Game loop. Contains Screen and StepStrategy hierarhcy, which uses the <b>Strategy</b> Design pattern to substitute implementation of step() based on the screen displayed.
   *	LevelManager - Contains a Collection of all existing levels. Responsible for creating/deleting new levels.
   *	SelectionGroup - Inheritance hierarhcy encapsulating a data structure containing all existing levels in the game. Allows for different implementations of this data structure, such as
        Lists or graphs (the latter is for the second sprint so the player can have a map to navigate on). Could implement <b>Iterator</b> Design pattern in future.
   *	Level - Contains classes from Player and Object modules pertaining to that level. Manages interaction between Players and Objects.
    *   Module created to have a centralized location for dealing with issues beyond the scope of a single level. Unifies the processing of all existing levels with that of level-wide settings (ex: song).
*	Entity Module. - Anything drawn on the game screen.
    *	GamerControlledEntity - The character(s) controlled by the human player. Separate from the human player (in case can switch between characters), but interacts with the Player module. 
    *	ComputerControlledEntity - Anything appearing on screen not controlled by the human player. This includes wall, blocks, and enemies. Divided between those that move (the user will be able to choose left/right, or up/down and the distance), and those that do not.
    *	Background.
    *	Note: For GamerControlledEntity and ComputerControlledEntity, contains inheritance hierarchies for different types. Also attaches Events to each object to handle interactions between Characters and Blocks. Contains a single class for the Background.
*   Event Module (attached to Entity). Defined as one of the following:
    *	User input.
    *	Collision (each side, to distinguish between possible ones). 
    *	Timer.
    *	Entity Location.
    *	Uses an <b>Observable</b> Design Pattern to allow Entities to listen to these events.
    *	Example: Mario hits a block. The block releases a prize. The block has an event; Mario also has an event.  They each have an event from their own point-of-view.
*	Action Module (attached to Event). - The consequence of each Event. Examples are:
    *	Instantiate new object.
    *	Destroy (character or other object).
    *	Win. 
    *	Lose (life or game).
    *	Bounce (move).
    *	Change image.
    *   Power-up.
    *	Next level.
    *	Make sound.
    *	Movement. - This includes the physics of jumping/gravity.
    *  	Note: Obtains the list of these Actions through reflection.

Entity, Event, and Action modules were all created to allow single Entities to have multiple Events, and those in turn to have Actions. They
are an especially good example of substitution, as a single Entity can have ANY Events, and in turn they can have ANY Actions; this is
especially important to allow for the most flexibility when designing a game.

**Game Data**

The Game Data consists of Loading and Saving modules to separate the logic behind each task. We supply to other classes the `saveGame` and `loadGame` methods that are in the `GameData` class, which uses the same named methods from `GameSaver.java` and `LoadGame.java` respectively. These classes each use several other classes to help complete the serialization and parsing/unserializing process. Specifically, these modules each use the `EntityConverter` object, which implements the `Converter` interface provided by xStream. Here, entities are converted to valid XML and entities are created from XML. The format is consistent with xStream. We also use the specific `GameXMLFactory` to create the XML files and classes like the `LevelSaver`.


## Example Games


-   **Doodle Jump**

    Doodle Jump is an infinite scrolling game that randomizes the blocks as the player progresses. The player progresses vertically, and is affected by gravity. If the player lands on a block, the player jumps, and progresses upwards. With each bounce, more blocks appears, and as the player progresses, their score increases. Powerups can also cause the player to fly up, and blocks exist that suddenly disappear when jumped on. If the player misses a block, and falls down the stage, then the player loses. There is no win state in Doodle Jump. Instead, the point is to rack up as many points as possible.
    
    To implement this, the GameAuthoring environment has to support randomization in such a way where it is still possible to progress. In order to do so, we use a randomization engine that esbalishes the possible bounds in which "blocks" can be randomized. From there, each block will have an event, which is the collision of the doodle character with the block, and that will cause the block to do its own respective behavior, and also cause the doodle jump character to bounce. At the bottom of the stage, we have an invisible object that, where if the character collides with the invisible object, the lose state of the game is reached. Not only that, but the doodle character player will have a gravity component. Any powerup collisions will do its respective powerup action. For example, a "helicopter hat" powerup causes the doodle-jump character to hover for a set period of time. To implement this with our game creator, a collision with the helicopter hat causes a change in the visual appearance of the game character, and causes an upward movement of the game character for a set amount of seconds. As the doodle jump character progresses upwards, a "high score" object will increase in score, increasing the character's score.
    
    
-   **Space Shooter**

    Space Shooters is also an infinite scrolling game. In this scenario, a space ship flies horizontally through space, and has to both shoot down enemies, and avoid asteroids. The spaceship has a set amount of lives, and if it collides into an asteroid, or if an enemy shoots down and/or collides with the spaceship, then the spaceship loses a life. If the spaceship runs out of lives, then the player loses. Once the spaceship reaches the end of the game, the game is won, and the game ends.

    A randomization engine is still necessary for this game, and different objects are now necessary. The primary objects would be bullets, enemies, asteroids, and spacheship. The spaceship would have no gravity component, and would have a component that is simply an integer, called "lives". Bullets would have a collision event, where both the bullets themselves, and the things they collide themselves, would be destroyed. Enemies would have their own behavior, where they would move in a certain pattern, and they would shoot (instantiate) bullets that move forward. If these bullets collide with the spaceship, the spaceship loses a life. Asteroids have a simple moving behavior, and only destroy themselves if they collide with a bullet and/or the spaceship. Once the spaceship travels a set amount of distance (in pixels), then the win state object is instantiated, and once the spaceship collides with that, the person wins the game. However, if the lives component of the spaceship reaches 0, then the person loses the game, and has to start over.
   
    
-   **Super Mario Bros** 

    Super Mario Bros. is a scrolling platformer where Mario has to traverse past enemies and obstacles in order to reach the end, which is a flag. Mario has to make sure not to fall off the edge, nor get killed by enemies, such as turtles and goombas. In order to kill enemies, Mario jumps on them, which causes them to flatten, and disappear. Once Mario reaches the end, a flag animation pops up, and he raises a flag. Mario can also jump under power-up blocks, causing powerups such as mushrooms to appear. 1-up mushroom grant mario an extra life, and regular mushroom causes mario to grow, and prevents him from dying once if he gets hit.
   	
    Out of all the example games, Mario would probably take the longest time to implement. We essentially have 4 objects in this game: powerups, mario, enemies, and blocks. However, there are subsets of the objects, and the manner that Mario collides with them is also important. For example, if Mario touches a goomba from the side, he loses a life. However, if he jumps on the goomba, i.e. collides with the goomba from above, the goomba is flattened, and Mario does not die. This means that the collision must also check from which side he has collided the goomba with. When Mario collides with a powerup-block, the action is fairly complicated. First, a powerup is instantiated, behind the block (the powerup is therefore in a different layer, and then transfers to the front layer). Then, the powerup follows its own behavior, which is a gravity-based horizontal movement. If Mario collides with the powerup, then the powerup action occurs. Mario also must have a set amount of lives, similar to spaceshooters.  Everything for Mario is preplanned, so no randomization is necessary.

## Design Considerations

- Events and Actions<br>
One of the points that was actively discussed by our team was the basic structure of games. The goal was to create a design that was general enough to support many kinds of scrolling platformer games, while being flexible and extensible. We identified that a game can be expressed by several things. Entities (objects that are in the game) and how these `Entity` elements interact. The interaction can be very different depending on what causes it (ie user input vs collision) and can be different per game. Thus, a general but flexible design was created. Interactions, called `Action`s will be triggered by `Event`s. Thus, an `Entity` owns a number of `Events`, which trigger their respective `Actions` when they occur. Examples of this can be seen in the use cases for the game engine. This is a flexible design, as it accounts for many aspects of games at once. This way, user input, collisions, clock events can all be some kind of `Event` in that hierarchy. If an extendor needs to add a new kind of event games might want to respond to, one would just extend the `Event` superclass. The `Action` hierarchy is flexible and extensible in a similar way. `Action`s could be winning a game, releasing a powerup (if the object is block), dying, etc. It is equally easy to add new `Action`s to the game engine if desired.

- Where to keep JavaFX Nodes<br>
Another point of discussion of our whole team was where to keep and generate JavaFX Nodes. Our initial idea was to have JavaFX Nodes stored or generated inside of every `Entity`. The advantage of this design is that no outside classes would have to know about the inner workings of each `Entity`. An initial idea about this design was that there would be less code duplication, since otherwise both the authoring environment and the gameplay environment would have to make their own JavaFX  Nodes representing the `Entity` objects. The design our group chose in the end was to make a `NodeFactory` class, which would generate JavaFX Nodes out of `Entity` objects. Utilizing the Factory design pattern we were able to avoid mixing backend and frontend code, which seemed like a bad idea from the start. Additionally, we could add functionality that is needed for these nodes specifically for the authoring environment and gameplay environment after the nodes have been already generated by the `NodeFactory`. For example, if we need to add `onClick` functionality for the authoring environment but not for the gameplay, we can do that after making the node. This design streamlined the creation and transformation of `Entities` to nodes.

- How much responsibility to give the Player (related to the above)<br>
Our team discussed how much functionality to embed in the player. At first, our idea was to generate all JavaFX components in the game engine. Under this design, the Player (the gameplay environment, not the user) would only have play, pause, and other such functionality. However, this seemed like an unreasonable distribution of responsibility due to the reasons outlined above. In order to avoid mixing backend and frontend code as much as possible, our team opted for a design that gave more functionality to the Player. Now, the Player is responsible for getting the JavaFX Nodes from the `NodeFactory` and placing them correctly in the visualization. In addition to the responsibilities the Player had before, it has to also talk to the `Game` in the game engine about running the game and receive updates that resulted from the screen scrolling, from user input, and from gameplay collisions. This design will result in a cleaner, more closed and more organized code. Separating the model from the view according to the MVC design pattern is one of the goals of this project's design.

- Where to do creation of entities<br>
There was a question about where to create the entities. There are two options. Either the `Entity` objects that make up levels in the game will be created directly by the authoring environment when a user is making a game and when a game is being loaded from a saved file, or the `Entity` objects can be created in the game engine by a `EntityFactory`, and the authoring environment and the game data loader will just call a method `getEntity` in the `EntityFactory` when these components identify that an `Entity` is required. The second design was chosen initially, for the reason of avoiding duplicated code. The disadvantage of the first design is that the authoring environment and game data loader would contain similar functionality, which is avoided through the second design. A possible disadvantage of the second design is that it requires passing strings to the game engine, which could lead to magic values in the code. However, the use of reflection will solve that problem and both reduce the `if` statements in the `EntityFactory` code, and will streamline the creation of `Entity` objects when a string is given. However, after a class lecture, we realized that the `Entity` objects would be serialized by xstream in our design. This would enable easy saving, loading and creating of `Entity` objects. Thus, the creation for `Entity` objects in the authoring environment and the game data is not very similar, as one has to create objects from scratch, while the other has to load serialized objects through xstream. Because of this, the first design was chosen in the end.

- Should `Action`s and `Event`s have parameters<br>
A point of debate was whether actions and events have parameters, or whether there should be a separate action and event for every possible parameter. The original design was following the latter idea, where `Action` and `Event` would be subclassed numerous times to capture all possible combinations of user input when creating games (within reasonable bounds, for instance). However, this design was quickly debunked when the idea of the timer being an `Event` that triggers an `Action` (which would work according to our flexible design of `Action`s and `Event`s) was brought up. This design would require creating an event for every possible millisecond to allow the user to select any amount of time as the maximum amount for a level that is being created. This was absurd, and the point was proven that `Action` and `Event` instances should have parameters. For example, when the user clicks the up arrow, there should be an option of how much to move the character (if the move up `Action` is selected). This has implications for communicating between the authoring environment and the game engine as well, as the authoring environment needed a way to see all of the parameters that needed to be set for a given `Action` or `Event`. This was solved by providing `getParams` and `setParams` methods in both classes. This design is the better one, as it is more extensible and flexible, and will surely result in much less code duplication and clutter.

- How should the `Player` get access to the JavaFx Node objects and how will they be updated?<br>
Our group discussed two different options for having the `Player` receive the JavaFx Nodes and how they will be updated on every step of the game. The first and most primitive idea that could have worked was for the `Player` to call the `NodeFactory` on every single step of the game and replace the JavaFx Nodes that existed in the Scene with the new ones. These new ones would get the updated positions from the game engine, and the game would thus be moving. However, this would require the regeneration of the same nodes every single step just with new locations. This seemed like a poor choice to us, and a new design was chosen. Under this new design, we would only generate the JavaFx Node objects from `NodeFactory` once. The locations and images and all other necessary properties of these nodes will be binded using bindings to the locations and other vital properties of the `Entity` objects. These JavaFx Nodes will be then placed in the scene and will update automatically. Doing so eliminates having to update on every step and create new JavaFx Nodes. Only the game engine has to know about the stepping of the game, the `Player` just has to place the nodes properly in the scene, and that's it.

