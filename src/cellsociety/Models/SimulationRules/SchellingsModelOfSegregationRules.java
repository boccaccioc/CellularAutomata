package cellsociety.Models.SimulationRules;

import cellsociety.Controller;
import cellsociety.Models.CellModel;
import cellsociety.Models.GridModels.GridData;
import java.util.List;

public class SchellingsModelOfSegregationRules extends Rules {
  private boolean moveAgent;
  private int savedState;
  private final int OPEN=0;
  private final int AGENT1=1;
  private final int AGENT2=2;

  /**
   * intializes all the parameters needed
   * @param neighborPolicy
   * @param edgePolicy
   * @param threshHold
   * @param maxStates
   */
  public SchellingsModelOfSegregationRules(String neighborPolicy, String edgePolicy, Double threshHold, int maxStates){
    super(neighborPolicy, edgePolicy, threshHold, maxStates);
  }


  /**
   * updtaes the states in the grid one by one
   * @param myGrid
   */
  @Override
  public void update(GridData myGrid) {
    checkAllCellsAreInBounds(myGrid);
    int[][] updatedState = new int[myGrid.getRowCount()][myGrid.getColCount()];
    for (int row = 0; row < myGrid.getRowCount(); row++) {
      for (int col = 0; col < myGrid.getColCount(); col++) {
          updatedState[row][col] = getCellUpdate(myGrid.getCell(row, col),
              myGrid.getNeighbors(row, col, this.getMyNeighborPolicy(), this.getMyEdgePolicy()));
          if (moveAgent) {
            placeAgent(myGrid, updatedState);
          }
      }
    }
    for (int row = 0; row < myGrid.getRowCount(); row++) {
      for (int col = 0; col < myGrid.getColCount(); col++) {
        myGrid.getCell(row, col).setState(updatedState[row][col]);
      }
    }
  }

  /**
   * if the agent needs to be moved the simulation places it in the
   * first open spot
   * @param myGrid
   * @param updatedState
   */
  private void placeAgent(GridData myGrid, int[][] updatedState){
    outerloop:
    for (int row = 0; row < myGrid.getRowCount(); row++) {
      for (int col = 0; col < myGrid.getColCount(); col++) {
        if(myGrid.getCell(row, col).getState() == OPEN && updatedState[row][col] == OPEN){
          updatedState[row][col] = savedState;
          myGrid.getCell(row, col).setState(savedState);
          break outerloop;
          //myGrid.getCell(row, col).setState(savedState);
        }
      }
    }
  }

  @Override
  public int getCellUpdate(CellModel thisCell, List<CellModel> neighbors) {
    moveAgent = false;
    double currentThreshold= getPercentageOfSimilarCells(countOfSimilarCells(thisCell,neighbors),countOfOppositeCells(thisCell, neighbors));

    if(currentThreshold >= this.getMyThreshHold() && thisCell.getState() != OPEN){
     return thisCell.getState();
   }
    else if(currentThreshold < this.getMyThreshHold() && thisCell.getState() != OPEN){
     moveAgent = true;
     savedState = thisCell.getState();
     return OPEN;
    }
   return OPEN;
  }


  /**
   * counts the number of similar neighbors
   *
   * @param neighbors
   * @return
   */
  private int countOfSimilarCells(CellModel thisCell, List<CellModel> neighbors) {
    int countOfSameStateCells = 0;
    for (CellModel currentNearCell : neighbors) {
      if(thisCell.getState() == currentNearCell.getState() && currentNearCell.getState() !=OPEN){
        countOfSameStateCells+=1;
      }
    }
    return countOfSameStateCells;
  }

  private int countOfOppositeCells(CellModel thisCell, List<CellModel> neighbors){
    int countOfOppositeStateCells = 0;
    for (CellModel currentCell : neighbors) {
      if(thisCell.getState() != currentCell.getState() && currentCell.getState() != OPEN){
        countOfOppositeStateCells+=1;
      }
    }
    return countOfOppositeStateCells;
  }

  private double getPercentageOfSimilarCells(int countOfSimilarCells,int countOfOppositeCells ){
    int nonEmptyNeighbors = countOfOppositeCells + countOfSimilarCells;
    if (nonEmptyNeighbors == 0){
      return 0;
    }
    double percent = (double)countOfSimilarCells/ (double)nonEmptyNeighbors;
    return percent;
  }




}
