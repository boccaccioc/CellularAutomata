# Simulation Design Plan
### Team Number
### Colin Boccaccio (cmb171), Malvika Jain (msj24), Isabella Knox (ik63)


## Design Overview

For this project, we will utilize a Model, View, and Controller hierarchy structure. 
1. Model - Will manage all the backend/what is happening, defines the simulation rules/cell states, should contain no JavaFx code (e.g. GridModel, CellModel, etc.)
2. View - Will display information that is given, take in new updates and display updated information, manage the frontend (what is viewed by the user), will contain JavaFx (e.g.GridView, CellView, etc.)
3. Controller - Will be the mediator between the Simulation models and views

## Design Details
Below are the possible classes and purposes/possible methods for our Simulation project: 

    1. CellModel (abstract) - should have no JavaFx if model- shouldn’t worry about other cells - just one cell and its states 
        Subclass: GameOfLifeCell
        Specific types of cells extend the cell class
        Possible Methods:
        public int getState()
        public void setState(int newState) 
    2. CellView-
        Allows for different colors of cell based on state
    3. Controller - take input from frontend and give to backend (and vice versa)
        Input the specific file → uses fileReader
        Passes fileReader into to GridModel 
        Updates grid()
        Controls steps and scene

    4. GridModel -- follows open close principle 
        Creates all the methods that can be used in the grid
        getNeighbors()
        Neighbors (move away 2d array for later iterations of Simulation ) 
        GridView 
        Creates a grid of cells based on desired shape and size?
        Shouldn’t change information in model

    5. FileReader -- will output 2d array of original states 
        Reads through the given CSV 
        Public method to get a 2D array 
        Header row: two numbers representing the grid's dimensions (number of rows and columns of cells)
        Remaining rows: initial states for the cells in the grid, whose values represent the different states a cell can be in (e.g., 0 = DEAD and 1 = ALIVE)
    6. Simulation rules (abstract)  -
        How the cells will interact with each other (will vary based on the simulation type)
        Methods: based on the cell states and specific simulation (GameofLife etc.)
        How each neighbor is effected 
        Left, right, top, bottom, 
        Do the cells update/change color
    7. UserControlModel - 
        Gives the user controls usability 
    8. UserControlView - 
        Creates all the user controls buttons (step, play, pause)


## Design Considerations

The things we were considering when making our design was the scalability of our simulation.  We wanted to be able to switch out the simulation in a way that followed the open close principle as much as possible and required minimal change to our code. Additionally, we wanted our code to be able to handle different block shapes and configurations. The view should be separate from the model (the view should be able to be modified without having to change the model) for changes such as grid shape etc. 


## User Interface

Play, Pause, and Step buttons on the bottom of the window.


## Team Responsibilities

All team members will rotate with debugging, refactoring, and writing various tests for sections of code they author.
 
 * Team Member #1: Colin Boccaccio: will manage the Controller

 * Team Member #2: Malvika Jain: will manage the Models

 * Team Member #3: Isabella Knox: will manage the Views


