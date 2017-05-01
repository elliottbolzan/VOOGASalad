# voogasalad

**Team DuWaldorf**: Game Authoring Engine Project.

**Genre**: Scrolling Platformer.

---

### Names

- Kyle Finke (kwf10)
- Elliott Bolzan (eab91)
- Mina Mungekar (mem94)
- Jimmy Shackford (jas199)
- Jay Doherty (jld60)
- Jesse Yue (jty4)
- Matthew Barbano (meb100)
- Nikita Zemlevskiy (naz7)
- Dennis Ling
- Michael Li (mxl3)

---------------

### Dates & Times

- **Start Date**: 21 March 2017.

- **End Date**: 30 April 2017.

- **Hours of Work**: 900.


### Each Person's Role in Developing the Project

**Kyle**

I worked primarily in the game engine. I created the algorithm and code for detecting when a collision happens and which side the collision occurs on for the two entities involved in the collision. I also worked on designing and coding Actions, Entities, and Events for the game. Additionally, I assisted in the testing and debugging the reflection used to create the lists of Actions, Entities, and Events displayed to the user in the front-end by looping through these classes in the back-end. I was also worked on exception handling within the engine. Outside of coding, I tested different game features and provided feedback to the respective areas of the team. I also organized the first demonstration and acted as the primary-spokesperson.

**Jesse**

I worked primarily on the game player. I created the menus in the player and coded the logic for populating them, along with helping to create the player itself. I also helped around in the authoring environment, game engine, and game data. Created a way to create and display game info in the authoring environment, helped make achievement entities to make displaying and updating achievements possible, and helped work on save states to save the game at any particular point. I also did game testing and helped debug various issues.

**Elliott Bolzan (eab91)**

During this project, I worked on the following parts:

- Created the Game Authoring Environment visual layout.
- Creating the Panel in the Authoring Environment.
- Allowed for the ability to create Entities, Actions, and Events graphically.
- Designed the icon and logos for our project. 
- Designed the project's visual identity and wrote the CSS files.
- Implemented the Menus in the starting window and Game Authoring Environment.
- Wrote a utility, `polyglot`, that translates a ResourceBundle into any language at runtime.
- Integrated another team's networking utility in our project.
- Set up a chat system in the Game Authoring Environment that functions between machines.
- Helped design Actions and Events towards the end of the project (specifically, `BooleanParser`, which lets the designer combine `Events`). 

### Resources Used

* http://vignette2.wikia.nocookie.net/nintendo/images/d/de/Princess_Peach_%28Fortune_Street%29.png/revision/latest?cb=20130625131449&path-prefix=en
* https://i.giphy.com/DGaZTLF390Z0s.gif
* https://i.giphy.com/hbxHb36GFCB5m.gif
* https://img.gifamerica.com/22cc9b1848e768b59f1be3c4f485655d_download-upload-gif-mario-walking-gif-8-bit_256-256.gif
* https://orangemangocraft64.files.wordpress.com/2012/03/doodle_monsters.png
* https://vignette2.wikia.nocookie.net/doodlejumpiphone/images/7/79/Doodle_Platforms.png/revision/latest?cb=20100411232504
* http://vignette2.wikia.nocookie.net/doodle-jump/images/5/5c/Doodler.png/revision/latest?cb=20130403202424
* http://vignette1.wikia.nocookie.net/fantendo/images/3/3f/SuperMushroom.png/revision/latest?cb=20130803025706
* https://cdn.instructables.com/FFB/WMFG/FWBNM65X/FFBWMFGFWBNM65X.MEDIUM.jpg
* http://ian-albert.com/games/super_mario_bros_maps/mario-1-1.gif
* http://stackoverflow.com/questions/31022269/collision-detection-between-two-rectangles-in-java
* https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/Pane.html
* http://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html
* http://www.javaworld.com/article/2077258/learn-java/observer-and-observable.html
* http://stackoverflow.com/questions/19383953/is-it-possible-to-evaluate-a-boolean-expression-for-string-comparions
* https://rterp.wordpress.com/2015/09/01/creating-custom-animated-transitions-with-javafx/


### Files Used to Start the Project (the class(es) containing main)

Our main class is located at: `src/starter/Main.java`.


### Files Used to Test the Project

All files in `src/testers`:

* `EntityConverterTest.java`.
* `TestGameEngineStartup.java`.
* `TestGameMarioMove.java`.
* `TestHTML.java`.
* `TestMultipleLevels.java`.

### Any data or resource files required by the project (including format of non-standard files)

All data and resource files are contained in the folders `src/resources` (resources used by code), `data/resources` (resources that can be used to make games) and `src/polyglot/resources` (resources used by Polyglot).

### Any information about using the program (i.e., command-line/applet arguments, key inputs, interesting example data files, or easter eggs)

See extra features below for a comprehensive list of cool features.


### Any known bugs, crashes, or problems with the project's functionality

None.

### Any extra features included in the project

* Chat between two computers.
* Simultaneous editing of a game between two computers.
* Tutorials for making a game.
* Automatic translation of the project into hundreds of languages at runtime.
* The ability to post status updates to Facebook.
* Parse Boolean expressions for Events. Trigger complex combinations of Events using &&, ||, and !.

### Impressions of the Assignment

**Someone**

The project was a good culmination for that class because it provided ample opportunity to apply the design concepts we learned during the course. The size of the 
teams was also a useful experience because it requires that everyone rely on each other because no one person can take over the entire assignment. It could improve the 
project to meet with our TAs more often to show off our project and get advice because although Piazza is useful for answering questions, it doesn't provide the same help
that having someone outside of the team look at the project. Going off of that idea, the user testing was a great tool and experience because it truly demonstrates the areas
of the project that need improvement or clarification to allow someone who has not worked on the project to actually use it.

**Elliott Bolzan (eab91)**

The project, while a considerable amount of work, was enjoyable. The final product is considerably more impressive than the previous projects, making the effort more worthwhile. Additionally, the challenge of working for an extended period of time with a large number of people was interesting: I developed better relationships with my team members than during previous projects.

The most interesting parts of the project were, by far, designing the utilities and implementing the extra features. In terms of class work, the lab during which we let other teams use our product was very beneficial. We were able to identify a number of problems and it pushed people to work more on our project.

One thing I would change in the future: maybe it would be beneficial to not dissuade people from trying to implement several game genres. Our project seems to cover a few, and had we not been told to focus exclusively on one game genre during our first coding sprint, we probably could have designed games for all genres. That would have been a great experience!