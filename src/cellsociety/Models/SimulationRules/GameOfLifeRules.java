package cellsociety.Models.SimulationRules;

import cellsociety.Models.CellModel;
import cellsociety.Models.GridModels.GridData;
import java.util.List;


public class GameOfLifeRules extends Rules{
  private final int DEAD=0;
  private final int ALIVE=1;
  public GameOfLifeRules(String neighborPolicy, String edgePolicy, Double threshHold, int maxStates){
    super(neighborPolicy, edgePolicy, threshHold,maxStates);
  }

  /**
   * gets the state of the cell you are on in the Grid Model and updates the
   * value based on the rules of the current simulation
   * @param thisCell
   * @param neighbors
   * @return
   */
  /**
   * gets the updated value for the cell we are on (0 or 1)
   * @param thisCell
   * @param neighbors
   * @return
   */
  @Override
  public int getCellUpdate(CellModel thisCell, List<CellModel> neighbors) {
    int liveNeighbors = countOfLiveCells(neighbors);
    if(thisCell.getState() == ALIVE && (liveNeighbors == 2 || liveNeighbors == 3)|| (thisCell.getState()==DEAD
    && liveNeighbors==3)){
      return ALIVE;

    }
    else {
      return DEAD;
    }

  }

  /**
   * counts the number of live neighbors
   * @param neighbors
   * @return
   */
  private int countOfLiveCells(List<CellModel> neighbors) {
    int countOfLiveCells =0;
    for(CellModel currentCell: neighbors){
      if(currentCell.getState()==ALIVE){
        countOfLiveCells+=1;
      }
    }
    return countOfLiveCells;
  }

  /**
   * updates the grid based on the rules of the Game of Life
   * @param myGrid
   */
  @Override
  public void update(GridData myGrid) {
    checkAllCellsAreInBounds(myGrid);
    int[][] updatedState = new int[myGrid.getRowCount()][myGrid.getColCount()];
    for (int row = 0; row < myGrid.getRowCount(); row++) {
      for (int col = 0; col < myGrid.getColCount(); col++) {  //split up loops in private methods
       updatedState[row][col] = getCellUpdate(myGrid.getCell(row, col), myGrid.getNeighbors(row, col, this.getMyNeighborPolicy(), this.getMyEdgePolicy()));//split up chain of calls - readable
      }
    }
    for (int row = 0; row < myGrid.getRowCount(); row++) {
      for (int col = 0; col < myGrid.getColCount(); col++) {
        myGrid.getCell(row, col).setState(updatedState[row][col]);
      }
    }
  }





}
