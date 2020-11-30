## Lab Discussion
### Team 
15
## Names 
Colin Boccaccio (cmb171), Isabella Knox (ik63), Malvika Jain (msj24)


### Issues in Current Code

#### Method or Class
Inheritence heirachies (GridData and Rules)
 * Design issues
 Protected variables should be replaced with getter methods that help access the desired values. 

 * Design issue
 Methods that are pubic and only need ot be used by other classes in that package can be changed to protected rather than public. 

#### Method or Class
User Control(GridView and UserControlView)
 * Design issues
 All the design elements have to be put in the css file. 

 * Design issue
Repreated code needs to be refactored.

### Refactoring Plan

 * What are the code's biggest issues?
 Getting rid of protected instance variables. 
 USing Css files to do the design elements in our porject. 
 Fixing testing (getting the step method to work).
 Enums -- passing around values  
 * Which issues are easy to fix and which are hard?
 Getting the css files to work will take time but should be straight forward.
 The hardest issue is changing our code to work with enums (a lot of our code 
 is dependent on states being integer values).
 * What are good ways to implement the changes "in place"?


### Refactoring Work

 * Issue chosen: Fix and Alternatives
 Protected variables should be replaced with getter methods that help access the desired values. 
 Changed protected instance variables to be private and create gretter methods.

 * Issue chosen: Fix and Alternatives
 Debugged why our tests were not working.
 
 * Issue chosen: Fix and Alternatives
  Methods that are pubic and only need ot be used by other classes in that package can be changed to protected rather than public. 
  Change public methods to protected in thses classes.
