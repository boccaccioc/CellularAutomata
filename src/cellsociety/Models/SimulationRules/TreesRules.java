package cellsociety.Models.SimulationRules;

import cellsociety.Models.CellModel;
import cellsociety.Models.GridModels.GridData;
import java.sql.SQLOutput;
import java.util.List;


public class TreesRules extends Rules {
  private final int OPEN=0;
  private final int TREE=1;
  private final int BURNING=2;

  /**
   * initializes all the states for the tree burning simulation
   *
   * @param neighborPolicy
   * @param edgePolicy
   * @param threshHold
   * @param maxStates
   */
  public TreesRules(String neighborPolicy, String edgePolicy, Double threshHold, int maxStates){
    super(neighborPolicy, edgePolicy, threshHold, maxStates);
  }

  /**
   * gets the updates state for a cell
   * @param thisCell
   * @param neighbors
   * @return the int that is the updated state of a cell
   */
  @Override
  public int getCellUpdate(CellModel thisCell, List<CellModel> neighbors) {
    if(thisCell.getState() ==OPEN || thisCell.getState() ==BURNING){
      return OPEN;
    }
    for(CellModel surroundingCell:neighbors){
      System.out.println(surroundingCell.getState());
       if(surroundingCell.getState() ==BURNING){
         if(isProbReachedOfBuring()){
           return BURNING;
         }
       }
    }
    return TREE;
  }

  /**
   *updates the cells of the grid based on rules of the simulation
   * @param myGrid
   */
  @Override
  public void update(GridData myGrid) {
    checkAllCellsAreInBounds(myGrid);
    int[][] updatedState = new int[myGrid.getRowCount()][myGrid.getColCount()];
    for (int row = 0; row < myGrid.getRowCount(); row++) {
      for (int col = 0; col < myGrid.getColCount(); col++) {
          updatedState[row][col] = getCellUpdate(myGrid.getCell(row, col), myGrid.getNeighbors(row, col, this.getMyNeighborPolicy(), this.getMyEdgePolicy()));
      }
    }
    for (int row = 0; row < myGrid.getRowCount(); row++) {
      for (int col = 0; col < myGrid.getColCount(); col++) {
        myGrid.getCell(row, col).setState(updatedState[row][col]);
      }
    }
  }

  /**
   * randomly decides if the near by tree will catch on fire
   * @return
   */
  private boolean isProbReachedOfBuring(){
    return (Math.random()>this.getMyThreshHold());
  }
}
