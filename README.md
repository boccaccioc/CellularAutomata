## TO RUN:

Add the following through maven:

Junit: org.junit.jupiter:junit-jupiter-api:5.4.2

TestFX: org.testfx:testfx-junit5:4.0.16-alpha


simulation
====

This project implements a cellular automata simulator.

Names: Colin Boccaccio (cmb171), Isabella Knox (ik63), Malvika Jain (msj24)


### Timeline

Start Date: 10/02/20

Finish Date: 10/20/20

Hours Spent: 200+ hours

### Primary Roles

1. Isabella Knox (ik63) - Focused primarily on the front end of the simulation and Controller. Code focused mainly in the UserControlView, UserControlModel, GraphView, and associated test classes. Wrote tests for graph view, grid view, and visualizations. Aided in writing tests for simulation. Separated out styling and placed into CSS folders. Helped teammates plan class hierarchies, debug code, write tests, and helped facilitate communication and schedule team meetings.


2. Malvika Jain (msj24) - Focused on the back end -- mostly the file reader, controller, grid model, and the rules. Worked on designing the abstraction needed to create the relationship between the controller, model, view. Worked on making design decisions regarding how the cell model, grid model, rules, and controllers would have to work together to achieve the specifications in this project. Worked on how to check for errors in the code.


3. Colin Boccaccio (cmb171) - Focused primarily on Controller and Back-end. Created Rules class heirarchy to allow for reflection in the controller to create the correct rules class. Wrote code to detect neighbors for each combination of edge and neighborhood policy and used abstraction to reduce duplicated code. Abstracted the data structure holding CellModels to allow for modification of data structure used in the future. Contributed to debugging and tests.

### Resources Used
1. Professor Duvall
2. Class Discussion Page ([Piazza](https://piazza.com/class/kdz40c8ei8t6zw?cid=107))
3. [JavaFx Documentation](https://docs.oracle.com/javase/8/javafx/api/toc.htm)
4. [Java Documentation](https://docs.oracle.com/en/java/)
5. Code examples from CS 307 lectures/labs ([lab_javafx](https://coursework.cs.duke.edu/compsci307_2020fall/lab_javafx), [lab_browser](https://coursework.cs.duke.edu/compsci307_2020fall/lab_browser), [example_simplification](https://coursework.cs.duke.edu/compsci307_2020fall/example_simplification), [lab_calculator](https://coursework.cs.duke.edu/compsci307_2020fall/lab_calculator), [example_encapsulation](https://coursework.cs.duke.edu/compsci307_2020fall/example_encapsulation))
6. CS 307 TAs (office hours TAs and each member's personal Undergraduate TA)
7. [CSS Styling Text Node](https://stackoverflow.com/questions/35284816/css-for-javafx-scene-text-text)
8. [CSS Styling Grid Panes](https://stackoverflow.com/questions/26456560/javafx-css-class-for-gridpane-vbox-vbox)
9. [How to use TextFields](https://docs.oracle.com/javafx/2/ui_controls/text-field.htm)
10. [How to do Dialogs](https://code.makery.ch/blog/javafx-dialogs-official/)
11. [Styling with CSS](https://docs.oracle.com/javafx/2/layout/style_css.htm)
12. [XY Chart Documentation](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/chart/XYChart.html)
13. [Java String Integer checking](https://stackoverflow.com/questions/5439529/determine-if-a-string-is-an-integer-in-java)
14. [CSS Reference Guide](https://docs.oracle.com/javafx/2/api/javafx/scene/doc-files/cssref.html#node)
15. [ComboBox Usage](https://docs.oracle.com/javafx/2/ui_controls/combo-box.htm)
16. [Set action on ComboBox](https://stackoverflow.com/questions/44190087/javafx-how-to-set-an-action-to-a-combobox)
17. [CSS Guide](https://www.tutorialspoint.com/javafx/javafx_css.htm)


### Running the Program

#### Main class:
In order to run our program, run Controller.main().
This is the class was our controller in our program. It managed the relationships between the view and model system. The Controller manages the main stage and scene of the program where the user control panels and grid view are displayed.
By default, the animation is not playing. To begin simulation and stepping, click the "Play Button". 

#### Mouse/ Key Inputs
1. When a grid rectangle is clicked in the GridView of the simulation, the corresponding cell state is increased, and the color is updated. If the cell state is at the max state value, it will reset to state 0.

#### User Control Panel Buttons and How to Use:

NOTE: In order for Theme Drop Down, Language Drop down, to work -- simulation/animation but be playing. 

1. Play Button - Starts the simulation for the Grid View 
2. Pause Button - Pauses the simulation for the Grid View
3. Step Button - Steps through the simulation for the Grid View
4. Reset Button - Resets the grid to the properties file starting states and colors
5. Speed Slider - Speed of the grid view animation. Default speed is set to 1 second.
6. Theme Drop Down Box - Chooses the theme of the original window. Default theme is applied when program begins. Three options: Default, Duke, Dark. Simulation needs to be playing.
7. Language Drop Down Box - Chooses the language for the buttons displayed. Default language is English. Three options: English, Spanish, German. Simulation needs to be playing.
8. Show Graph Button - Creates the Graph View in a new window (will show up in front of the original Grid window, but simply move the Graph window over to see both windows open)
9. Change State Color - Change the color associated with one of the states. If images are currently being used in the Grid View simulation, must reset simulation to go back to using colors.
10. Change Colors to Images - Will change all Grid View rectangles to images given in the properties file. To go back to colors, user must reset the Simulation.
11. Change One State's Image - Change an image associated with a specific state. Assumes the image path is correct and in the program files (can be accessed). Will change remaining states to images provided in properties file as well. To go back to colors, user must reset the simulation.
12. Save File Button - Saves a CSV file and Properties file with current state of the simulation information and with given input.
13. Load Simulation File Button - Load a new property file. For easiest and successful use, add Properties file to top level of Resource folder (that has been marked as R)
14. Randomize Configuration Button - Randomizes the state cells and grid view configuration
15. Increment Configuration Button - Increments the state cells and grid view configuration. The first column will have state 0, second column will have state 1, third column will have state 2, etc. After the column with the max state, all remaining columns will be set to zero. For instance, in Game of Life, the third column (and all higher columns) cells would have state 0.



#### Data files needed: 
The datafiles needed are in the resources' directory in the projects file. The 
specific files used to test our program are in the 'Examples' folder. Due to the 
fact that each simulation had its unique states and rules the test files 
were made separately and each simulation has its own folder for csv files that
hold the initial configuration. 
1. Images are found in the image folder in resources
2. CSS files are found in the top level of resoruces
3. property files are found in the top level of resources
4. CSV files are in the Example folder under specific simulation folders

#### Features implemented:
The ability to read a csv file and turn it into a grid of CellModel objects that can 
hold the state of the cell and two information parameters. 


### Notes/Assumptions

Assumptions or Simplifications:
1. The original properties file used to run the initial Controller.main() is correct and has all needed values and a correct CSV file. (properties file and CSV file checking occurs with the use of the LoadSimulationButton).
2. If no edge policy or neighborhood exists in the properties file, a default policy is given based on
an constant at the top of the Rules class (DEFAULT_NEIGHBORHOOD_POLICY and DEFAULT_EDGE_POLICY).
3. Configurations (such as reading from CSV File, Randomizing, Incrementing etc.) are done through buttons. The default when the program is run is to display the appropriate grid from the CSV File. After this initialization, the Randomizing and Incrementing buttons can be used to change the simulation and states.
4. The given image path for the Change One State's image button is correct, and the image is able to used/available in the file.

##### Things that are checked in our code:
The exceptions are using a separate properties file because we wanted the front 
end and the back to be separated, and only if you had access to the code you would 
want to know what is wrong.

Loading: 
Empty files: an empty file exception is thrown 
A file that does not exist: a random file name is entered and a an exception is thrown 
If the neighborhood or Edge policy is incorrect a custom runtime exception is thrown. 


Before looking for the threshold value is looked for it seen if the simulation needs a threshold 
in the controller class. 


If a wrong simulation is entered, a runtime error is thrown. 
Max states is assummed to be right, because there is no way to check that. 
If any of the states of the grid are greater than the max number of states in the file 
the state of the cell is set to 0. 
If the row and col that is being accessed is out of the range of the rid, the row and col are chosen 
to be either the number of rows or cols (the max value is the value being searched is greater 
than the rows) or 0 is the desired value is less than 0. 

If the state for the chosen state color that wants to be changed is out of bounds then the state 
color doesn't change. The name of the new color can be lowercase or uppercase.

We struggled to figure out how to deal with having different 
games define neighbors differently. We ended up fixing this issues by creating an abstraction 
for different neighborhood policies and then using a property file to define the correct edge and 
neighbor policy. 

#### The error checking  

1. User inputs throw an alert dialog (except for the image path which is assummed to be correct).
2. When loading a new Simulation file via the Load Simulation button, alert dialogs will pop up is there is an error in finding the file or in finding information required.
3. We assumed for the Model of Segregation the threshold in the properties file is in decimals. In the segregation model we had to choose how the agents would be 
moved if the threshold was not met. We decided ot move the agent to the first open spot in the grid.
This decision was made because it allowed our simulation to be predictable and reach a point of stabilization.
after being run for a bit. The goof part about our simulation being predictable was that we could easily test it,
and the random movement logic in other simulations can then be applied to this simulation. This simulation working 
allowed to be a proof of concept for our simulation that can be extended to other types of simulations or 
variations of one simulation.    
4. For the Rock paper scissors simulation the threshold was assummed to be a integer. 
5. Our grid array classes allows for different data structure types -- allows for encapsulation 
and other types of data structures. So although having this flexibility isn't 
a requirement our code allows for a new class that extends Grid Data and allows 
for a new data structures. 

    GameOfLife:
    0-dead
    1-alive

    Percolation: 
    0-blocked 
    1-open
    2-full

    RockPaperScissors:
    0-rock 
    1-paper 
    2-scissors

    Trees: 
    0-empty
    1-tree
    2-Burning 

    Segregation: 
    0-empty
    1-agent 1
    2-agent 2 

    Wator:
    0-empty 
    1-predator(shark) //check  
    2 -prey (fish)


Interesting data files:
1. Examples/GameOfLife/Test1.csv - switches between horizontal and vertical line

Known Bugs:

Extra credit:
 1. Outline Grid button (alternate between outlining and no outlining for the grid view)
 2. Language selection dynamically throughout simulation (once play button has been pressed)
 3. Slider for changing speed dynamically throughout simulation
 4. Buttons for choosing file/grid configurations which allows for more user interaction in the window after Controller.main() has run
 5. Theme selection dynamically throughout simulation (once play button has been pressed)

### Impressions
Overall, this assignment was able to teach us the usefulness and need for encapsulation. System which utilizes a model, view, and controller was critical to this assignment. This showed us the vast flexibility and use for the separation between back-end and front-end code. How to use CSS styling was learned alongside how to use reflection.

"# CellularAutomata" 
