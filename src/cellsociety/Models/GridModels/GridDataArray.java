package cellsociety.Models.GridModels;

import cellsociety.FormattingExceptions;
import cellsociety.Models.CellModel;
import java.util.ArrayList;
import java.util.List;
/*
this class allows for our code to be modular and follow encapsulation
this is the specific gridData instance using a 2d array
this allows for the code to function even if you want to switch to using a
different data type
@author Colin Boccaccio
 */
public class GridDataArray extends GridData{

  private CellModel[][] myGrid;


  /**
   * transfers the data in the cvs file to a
   * 2d of cell models  array that holds the states of the different cells
   * @param csvData
   */
  public GridDataArray(int[][] csvData) {
    super(csvData);
   // int[][] startingData = getStartingData();
    myGrid = new CellModel[startingData.length][startingData[0].length];
    for(int row = 0; row < myGrid.length; row++){
      for(int col = 0; col < myGrid[0].length; col++){
        myGrid[row][col] = new CellModel(startingData[row][col]);
      }
    }
  }

  /**
   *  method that returns the cell at a given
   * position
   * @param row of cell desired
   * @param col of cell desired
   * @return a cell model that is at the given positon
   */
  @Override
  public CellModel getCell(int row, int col) {
    row = checkBoundsOfAccessRow(row);
    col = checkBoundsOfAccessCol(col);
    return myGrid[row][col];
  }

  /**
   * sets the state of a cell given a certain position
   * @param row of cell being set
   * @param col of cell being set
   * @param state that cell will be set to
   */
  @Override
  public void setCell(int row, int col, int state) {
    row = checkBoundsOfAccessRow(row);
    col = checkBoundsOfAccessCol(col);
    myGrid[row][col].setState(state);
  }

  private int checkBoundsOfAccessRow(int row){
    if( row >= myGrid.length){
     return myGrid.length-1;
    }
    else if( row < 0){
      return  0;
    }
    return row;
  }

  private int checkBoundsOfAccessCol(int col){
    if( col >= myGrid[0].length){
      return myGrid[0].length-1;
    }
    else if( col < 0){
      return  0;
    }
    return col;
  }
  /**
   * gets the number of rows in the passed in grid
   * @return int of the number of rows
   */
  @Override
  public int getRowCount() {
    return myGrid.length;
  }

  /**
   * gets the number of columns in the passed in grid
   * @return the number of columns
   */
  @Override
  public int getColCount() {
    return myGrid[0].length;
  }

}
