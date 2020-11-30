package cellsociety.Models;

public class CellModel {

  private int myState;
  private int info;
  private int infoTwo;
  private final int INITAL_STATE = 0;
  private final int INITAL_INFO_ONE = 0;
  private final int INITAL_INFO_TWO = 5;
  /**
   * this is the basic cell for ever simulation
   * each cell has a state and then some additional optional information for more complex
   * information
   * @param state
   * @author Malvika Jain, Colin Boccaccio
   */

  /**
   *initializes the basic parts of each cell: the state, and addtional information
   * @param state
   */
  public CellModel(int state){ //add String shape as parameter?
    myState = state;
    info=INITAL_INFO_ONE; //get rid of magic number
    infoTwo=INITAL_INFO_TWO;
  }

  /**
   * changes the state of the cell to the desired state that is passed in
   * @param newState
   */
  public void setState(int newState){
    myState = newState;
  }

  /**
   * returns the current state of the cell
   * @return the int of the current state of the cell
   */
  public int getState(){
    return myState;
  }

  /**
   * sets the info field of the cell to the desired value that is passed in
   * to the method
   * @param info
   */
  public void setInfo(int info){ this.info=info; }

  /**
   *returns the info that the cell currently have
   * @return the int that holds the info for the cell
   */
  public int getInfo(){ return info; }

  /**
   * sets the other info filed for the cell
   * @param info
   */
  public void setInfoTwo(int info){ this.infoTwo=info; }

  /**
   * gets the second info field for the cell
   * @return the info in the second info field for the cell
   */
  public int getInfoTwo(){ return infoTwo; }

  /**
   * resets all the parameters of a cell
   */
  public void reset(){
    myState=INITAL_STATE;
    info=INITAL_INFO_ONE;
    infoTwo=INITAL_INFO_ONE;
  }
}
