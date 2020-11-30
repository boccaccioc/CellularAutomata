package cellsociety.Models.SimulationRules;

import cellsociety.FileExceptions;
import cellsociety.FormattingExceptions;
import cellsociety.Models.CellModel;
import cellsociety.Models.GridModels.GridData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public abstract class Rules {

  private static Map<Integer, String> stateColors;
  private static Map<Integer, String> stateImages;
  private String myNeighborPolicy;
  private String myEdgePolicy;
  private double myThreshHold;
  private double myMaxStates;
  private static final List<String> policiesForNeighborhoods = new ArrayList<>(Arrays.asList("Surrounding", "Adjacent", "Side"));
  private static final List<String> policiesForEdges = new ArrayList<>(Arrays.asList("Finite", "Toroidal", "StateZero"));
  private static final String DEFAULT_NEIGHBORHOOD_POLICY = "Surrounding";
  private static final String DEFAULT_EDGE_POLICY = "Finite";


  /**
   * gets the neighborhood policy for the current simulation
   * @return
   */
  public String getMyNeighborPolicy() {
    return myNeighborPolicy;
  }

  /**
   * gets the edge policy of the current simulaiton
   * @return
   */
  public String getMyEdgePolicy() {
    return myEdgePolicy;
  }

  /**
   * gets the threshold of the simulation
   * @return
   */
  public double getMyThreshHold() {
    return myThreshHold;
  }

  /**
   * gets the max number of states a cell in a simulation can have
   * @return
   */
  public double getMyMaxStates() {
    return myMaxStates;
  }

  /**
   * sets all the rules for a given simulation
   * @param neighborPolicy
   * @param edgePolicy
   * @param threshHold
   * @param maxStates
   */
  public Rules(String neighborPolicy, String edgePolicy, Double threshHold, int maxStates) {
    if(!(policiesForNeighborhoods.contains(neighborPolicy))){
      neighborPolicy = DEFAULT_NEIGHBORHOOD_POLICY;
    }
    if(!(policiesForEdges.contains(edgePolicy))){
      edgePolicy = DEFAULT_EDGE_POLICY;
    }
    myNeighborPolicy = neighborPolicy;
    myEdgePolicy = edgePolicy;
    myThreshHold = threshHold;
    myMaxStates = maxStates;
    stateColors = new HashMap<>();
    stateImages = new HashMap<>();
  }

  /**
   * adds the color for a given state
   * @param state
   * @param color
   */
  public void addColor(int state, String color){
    stateColors.put(state, color);
  }

  /**
   * updates and changes the color associated with a state
   * @param state
   * @param color
   */
  public void changeStateColor(int state, String color){
    stateColors.put(state, color);
  }

  /**
   * updates the list of colors for associated for a cell in a simulation
   * @param newColorList
   */
  public void changeColorList(List<String> newColorList){
    stateColors.clear();
    for (int i =0; i< newColorList.size();i++ ){
      stateColors.put(i, (newColorList.get(i)));
    }
  }

  /**
   *gets the map that links the states of a cell to colors
   * @return Map with all the states of a simulation  mapped to colors
   */
  public Map getColorMap(){
    return stateColors;
  }

  /**
   * get the map that links states of a cell to images
   *
   * @return Map with all the states of a simulation mapped to images
   */
  public Map getImageMap(){return stateImages;}

  /**
   * gets the color associated with a certain state in a simulation
   * @param state
   * @return a string that is the color of the cell for a given state
   */
  public String getColor(int state){
    return stateColors.get(state);
  }

  /**
   * adds an image for a state
   * @param state
   * @param image
   */
  public void addImage(int state, String image){
    stateImages.put(state, "/images/"+image+".png");
  }

  /**
   * gets the image name associated with a state
   * @param state
   * @return
   */
  public String getImage(int state){
    return stateImages.get(state);
  }


  /**
   * gets the updated int (state( for a given cell model
   * @param thisCell
   * @param neighbors
   * @return the int that is associated with teh updated state
   */
  public abstract int getCellUpdate(CellModel thisCell, List<CellModel> neighbors);



  /**
   * updates the Grid based on the rules
   * @param myGrid
   */
  public abstract void update(GridData myGrid);

  /**
   * increments the state of the cell by 1 and then resets it to 0 if the max state has been reached
   * @param thisCell
   */
  public void incrementTheStateOfTheCell(CellModel thisCell){
    if(thisCell.getState()<myMaxStates-1){
      thisCell.setState(thisCell.getState()+1);
    }else {
      thisCell.setState(0);
    }
  }
  //checks to make sure all
  protected void checkAllCellsAreInBounds(GridData myGrid){
     for(int i =0; i<myGrid.getRowCount();i++){
       for(int j=0; j<myGrid.getColCount();j++){
         if(myGrid.getCell(i,j).getState()>myMaxStates){
           myGrid.setCell(i,j,0);
         }
       }
     }
  }

  /**
   * updates and changes the image associated with a state
   *
   * assumes the path to the image is correct
   * @param state
   * @param imagePath
   */
  public void changeStateImage(int state, String imagePath){
    stateImages.put(state, imagePath);
  }
}
