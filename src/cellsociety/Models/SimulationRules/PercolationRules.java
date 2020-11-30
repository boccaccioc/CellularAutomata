package cellsociety.Models.SimulationRules;

import cellsociety.Models.CellModel;
import cellsociety.Models.GridModels.GridData;
import java.util.List;

public class PercolationRules extends Rules {

  /**
   * intializes the values needed for the simulation
   * @param neighborPolicy
   * @param edgePolicy
   * @param threshHold
   * @param maxStates
   */
  public PercolationRules(String neighborPolicy, String edgePolicy, Double threshHold, int maxStates){
    super(neighborPolicy, edgePolicy, threshHold,maxStates);
  }
  private final int BLOCKED=0;
  private final int OPEN =1;
  private final int FILLED=2;

  /**
   * updates the current cell based on the rules of the simulation
   * @param thisCell
   * @param neighbors
   * @return
   */
  @Override
  public int getCellUpdate(CellModel thisCell, List<CellModel> neighbors) {
    for (CellModel neighborCell : neighbors) {
      if(neighborCell.getState() ==FILLED){
        return FILLED;
      }
    }
    return OPEN;
  }

  /**
   * the grid updates based on the rules of the simulation
   * @param myGrid
   */
  @Override
  public void update(GridData myGrid) {
    checkAllCellsAreInBounds(myGrid);
    int[][] updatedState = new int[myGrid.getRowCount()][myGrid.getColCount()];
    for (int row = 0; row < myGrid.getRowCount(); row++) {
      for (int col = 0; col < myGrid.getColCount(); col++) {
        if(myGrid.getCell(row, col).getState()==OPEN){
          updatedState[row][col] = getCellUpdate(myGrid.getCell(row, col), myGrid.getNeighbors(row, col, this.getMyNeighborPolicy(), this.getMyEdgePolicy()));
        }else{
          updatedState[row][col] = myGrid.getCell(row, col).getState();
        }
      }
    }
    for (int row = 0; row < myGrid.getRowCount(); row++) {
      for (int col = 0; col < myGrid.getColCount(); col++) {
        myGrid.getCell(row, col).setState(updatedState[row][col]);
      }
    }
  }
}