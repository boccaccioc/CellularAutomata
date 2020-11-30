package cellsociety.Models.SimulationRules;

import cellsociety.Models.CellModel;
import cellsociety.Models.GridModels.GridData;
import java.util.List;

public class RockPaperScissorsRules extends Rules{
  private int numberOfRockWins;
  private int numberOfPaperWins;
  private int numberOfScissorsWins;
  private Double desiredThreshold;
  private final int INTIAL_NUM_OF_WINS=0;
  private final int ROCK=0;
  private final int PAPER=1;
  private final int SCISSORS=2;



  /**
   * sets up the parameters based on the properties file for a simulation
   * @param neighborPolicy
   * @param edgePolicy
   * @param threshHold
   * @param maxStates
   */
  public RockPaperScissorsRules(String neighborPolicy, String edgePolicy, Double threshHold, int maxStates){
    super(neighborPolicy, edgePolicy, threshHold,maxStates);
    numberOfRockWins=INTIAL_NUM_OF_WINS;
    numberOfPaperWins=INTIAL_NUM_OF_WINS;
    numberOfScissorsWins=INTIAL_NUM_OF_WINS;
    desiredThreshold = threshHold;
  }

  /**
   * gets how a certain cell should be updated based on the rules for the simulation
   * @param thisCell
   * @param neighbors
   * @return the int that represents the new state of the cell based on the rules
   */
  @Override
  public int getCellUpdate(CellModel thisCell, List<CellModel> neighbors) {
    numberOfRockWins=0;
    numberOfPaperWins=0;
    numberOfScissorsWins=0;
   for(CellModel opponentNeighbor: neighbors){
     playGame(thisCell,opponentNeighbor);
   }
   return isThreasholdReached(thisCell,neighbors);

  }

  /**
   * updates the grid based on the rules of the simualtion
   * this method increments through the cells of a grid
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
   * each cell plays rock paper scissors with the surrounding cells to keep track
   * of a score
   * @param thisCell
   * @param opponent
   */
  private void playGame(CellModel thisCell, CellModel opponent){
    if(thisCell.getState()==ROCK && opponent.getState()==PAPER){
      numberOfPaperWins+=1;
    }
    if(thisCell.getState()==PAPER && opponent.getState()==SCISSORS){
      numberOfScissorsWins+=1;
    }
    if(thisCell.getState()==SCISSORS && opponent.getState()==ROCK){
      numberOfRockWins+=1;
    }
  }

  /**
   * if the threshold is reached this method returns the state that
   * the cell should be
   * @param thisCell
   * @param neighbors
   * @return
   */
  private int isThreasholdReached(CellModel thisCell,List<CellModel> neighbors){
   if(numberOfRockWins>desiredThreshold){
     return ROCK;
   }
    if(numberOfPaperWins>desiredThreshold){
      return PAPER;
    }
    if(numberOfScissorsWins>desiredThreshold){
      return SCISSORS;
    }else {
      return thisCell.getState();
    }

  }
}
