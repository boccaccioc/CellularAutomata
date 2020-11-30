package cellsociety.Models.GridModels;

import cellsociety.Models.CellModel;
import cellsociety.Models.SimulationRules.Rules;
import java.util.List;
import java.util.Map;

/**
 * this class is the basic grid model for every
 * simulation this is the model that interacts
 * with the controller
 * @author Malvika Jain, Colin Boccaccio
 */
public class GridModel {

  /**
   *the GridData object that is for this grid model
   */
  private GridData myGrid;
  private Rules myRules;
  private boolean createRandomConfiguration;
  private boolean createIncrementConfiguration;


  /**
   * creates the grid based on the intial states in the cvs file
   * @param startingStates
   * @param ruleSet
   */
  public GridModel(int[][] startingStates, Rules ruleSet){
    myRules = ruleSet;
    myGrid = new GridDataArray(startingStates);
    createIncrementConfiguration=false;
    createIncrementConfiguration= false;

  }

  /**
   * return the grid
   * @return the Grid Data that is used for this simulation
   */
  public GridData getGridData(){return myGrid;}

  /**
   * sets a flag signalling the user wants to
   * change the inital configuration of the blocks
   * to be an incremental configuration
   * @param flag
   */
  public void setIncrementConfigurationFlag(boolean flag){
    createIncrementConfiguration=flag;
  }
  /**
   * sets a flag signalling the user wants to
   * change the inital configuration of the blocks
   * to be an random configuration
   * @param flag
   */
  public void setRandomConfigurationFlag(boolean flag){
    createRandomConfiguration=flag;
  }


  /**
   * gets the number of rows in the grid
   * @return int of the number of rows in the grid
   */
  public int getMyGridRowSize(){return myGrid.getRowCount();}
  /**
   * gets the number of columns in the grid
   * @return int of the number of columns in the grid
   */
  public int getMyGridColumnSize(){return myGrid.getColCount();}

  /**
   * gets the cellModel at a particular index of the gird
   * @param row
   * @param col
   * @return the cellModel at a particular index
   */
  public CellModel getMyGridCell(int row, int col){
    return myGrid.getCell(row, col);
  }

  /**
   * gets the cell state at a particular index of the grid
   * @param row
   * @param col
   * @return the state of the cell at a particular index
   */
  public int getMyGridValue(int row, int col){
    return myGrid.getCell(row, col).getState();}

  /**
   * updates the grid of the simulation based on the rules of the simulation
   * or based on what type of update the user wants
   */
  public void update(){
  if(createRandomConfiguration){
    randomUpdate();
    createRandomConfiguration=false;
   }else if(createIncrementConfiguration){
    incrementUpdate();
    createIncrementConfiguration=false;
  } else {
    ruleUpdate();
  }
  }

  /**
   * updates the grid based on the rules in the properties file
   */
  private void ruleUpdate(){
    myRules.update(myGrid);
  }

  /**
   * gets the cell in the
   * @param row
   * @param col
   * @return
   */
  public CellModel getCell(int row, int col){
    return myGrid.getCell(row, col);
  }

  /**
   * gets the rules for this grid
   * @return the rules associated with this simuation and grid
   */
  public Rules getRules(){
    return myRules;
  }

  /**
   * increments the state of the cell passed in
   * @param thisCell
   */
  public void incrementState(CellModel thisCell) {
    myRules.incrementTheStateOfTheCell(thisCell);
  }

  /**
   * changes the list of colors intially created by the rules
   * @param newColorList
   */
  public void changeColorList(List newColorList){
    myRules.changeColorList(newColorList);
  }

  /**
   * changes the colors of a particular state to one passed in
   * @param state
   * @param color
   */
  public void changeStateColor (int state, String color){
    myRules.changeStateColor(state, color);
  }

  /**
   * changes the image for a certain state based on what is passed in
   * @param state
   * @param imagePath
   */
  public void changeStateImage (int state, String imagePath){myRules.changeStateImage(state, imagePath);}

  /**
   * returns the map of colors that are associated with the states
   * @return
   */
  public Map getColorMap(){return myRules.getColorMap();}

  /**
   * gets a map of states associated with an image
   * @return
   */
  public Map getImageMap(){return myRules.getImageMap();}

  /**
   * gets the neighborhood policy of this simulation
   * @return
   */
  public String getMyNeighborPolicy() {
    return myRules.getMyNeighborPolicy();
  }

  /**
   * gets the edge policy for this simulation
   * @return
   */
  public String getMyEdgePolicy() {
    return myRules.getMyEdgePolicy();
  }

  /**
   * gets teh threshold of this simulation
   * @return
   */
  public double getMyThreshHold() {
    return myRules.getMyThreshHold();
  }

  /**
   * gets the max number of possible states for this simulation
   * @return
   */
  public double getMyMaxStates() {
    return myRules.getMyMaxStates();
  }

  /**
   * updates the states in the cell randomly (but still
   * takes into account the max number of states possible)
   */
  public void randomUpdate() {
    for(int i=0; i< myGrid.getRowCount();i++){
      for(int j =0; j<myGrid.getColCount();j++){
        myGrid.setCell(i,j,(int)(Math.random() * (myRules.getMyMaxStates())));
      }
    }
  }

  /**
   * does a incremental update when the state is based on the column the
   * cell is in and if the cell is greater than states it becomes 0
   */
  public void incrementUpdate() { //makes the value of the state based on the column if
    for(int i=0; i< myGrid.getRowCount();i++){
      for(int j =0; j<myGrid.getColCount();j++){
        int newStateVal =0;
        if(j<myRules.getMyMaxStates()){
          newStateVal=j;
        }
        myGrid.setCell(i,j,newStateVal);
      }
    }
  }
}





