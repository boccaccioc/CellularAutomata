package cellsociety.Models.SimulationRules;

import cellsociety.Models.CellModel;
import cellsociety.Models.GridModels.GridData;
import java.util.List;

public class WaTorRules extends Rules {

  private final int EMPTY=0;
  private final int FISH=1;
  private final int SHARK=2;
  private int sharkBreedThreshold;
  private int fishBreedThreshold;

  /**
   * intializes the value for the rules of the predator prey simulation
   * @param neighborPolicy
   * @param edgePolicy
   * @param threshHold
   * @param maxStates
   */
  public WaTorRules(String neighborPolicy, String edgePolicy, double threshHold, int maxStates) {
    super(neighborPolicy, edgePolicy, threshHold,maxStates);
    sharkBreedThreshold = (int)threshHold;
    fishBreedThreshold = (int)threshHold;
  }

  /**
   * gets the updates state for a certain cell
   * @param thisCell
   * @param neighbors
   * @return the int(state) that the current cell should be after updating the cell based on teh rules
   */
  @Override
  public int getCellUpdate(CellModel thisCell, List<CellModel> neighbors) {
    if(thisCell.getState()==EMPTY){
     return EMPTY;
    }
    if(thisCell.getState() == FISH)
    {
      fishUpdate(thisCell, neighbors);
      return EMPTY;
    } else if(thisCell.getState() == SHARK){
      sharkUpdate(thisCell, neighbors);
      return EMPTY;
    }
    throw new IllegalArgumentException("Invalid Input in File: "+ thisCell.getState());
  }

  /**
   * updates the position of a shark
   * @param thisCell
   * @param neighbors
   */
  private void sharkUpdate(CellModel thisCell, List<CellModel> neighbors) {
    breed(thisCell, neighbors);
    thisCell.setInfo(thisCell.getInfo()+1);//info (lives) incremented by one for surviving
    for(CellModel neighbor : neighbors){
      if(neighbor.getState() == FISH){
        neighbor.setState(SHARK);
        neighbor.setInfo(thisCell.getInfo());
        neighbor.setInfoTwo(thisCell.getInfoTwo()+1); //shark moves to new cell location with one more food/hunger (infoTwo)
        thisCell.reset();
        return;
      }
    }
    thisCell.setInfoTwo(thisCell.getInfoTwo()-1); //shark did not get a fish, so food is decremented
    if(thisCell.getInfoTwo() <= 0){
      thisCell.reset(); //food at zero so shark is killed
    }
    moveToEmptyCell(thisCell, neighbors);
  }

  /**
   * updates the position of a fish
   * @param thisCell
   * @param neighbors
   */
  private void fishUpdate(CellModel thisCell, List<CellModel> neighbors) {
    breed(thisCell, neighbors);
    moveToEmptyCell(thisCell, neighbors);
  }

  /**
   * moves a cell animal to an empty spot
   * @param thisCell
   * @param neighbors
   */
  private void moveToEmptyCell(CellModel thisCell, List<CellModel> neighbors) {
    for(CellModel neighbor : neighbors){
      if(neighbor.getState() == 0){
        neighbor.setState(thisCell.getState());
        neighbor.setInfo(thisCell.getInfo());
        neighbor.setInfoTwo(thisCell.getInfoTwo()); //shark moves to new cell location
        thisCell.reset();
        return;
      }
    }
  }

  /**
   * models an animal (shark or fish) to breed
   * @param thisCell
   * @param neighbors
   */
  private void breed(CellModel thisCell, List<CellModel> neighbors) {
    if(thisCell.getInfo()+1 >= sharkBreedThreshold){
      thisCell.setInfo(0); // progress towards breeding reset
      for(CellModel neighbor : neighbors){
        if(neighbor.getState() == 0){
          neighbor.setState(thisCell.getState());
          neighbor.setInfo(0);
          break;
        }
      }
    }
  }

  /**
   * updates the values in the grid based on the rules of the simulation
   * @param myGrid
   */
  @Override
  public void update(GridData myGrid) {
    checkAllCellsAreInBounds(myGrid);
    for (int row = 0; row < myGrid.getRowCount(); row++) {
      for (int col = 0; col < myGrid.getColCount(); col++) {
        //myGrid.getCell(row, col).setState(getCellUpdate(myGrid.getCell(row, col), myGrid.getNeighbors(row, col, myNeighborPolicy)));
        getCellUpdate(myGrid.getCell(row, col), myGrid.getNeighbors(row, col, this.getMyNeighborPolicy(), this.getMyEdgePolicy()));
      }
    }
  }
}
