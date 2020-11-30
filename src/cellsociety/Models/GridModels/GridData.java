package cellsociety.Models.GridModels;

import cellsociety.FormattingExceptions;
import cellsociety.Models.CellModel;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * the GridData class determines how elements will be accessed
 * from the grid, this class is abstract to allow for the modularity of different data structures
 * to be used as a grid by extending this class
 * @author Colin Boccaccio
 *
 */
public abstract class GridData {

  /**
   * this variable is protected because it only needs to be accessed by the classes
   * in this package
   *
   */
  protected int[][] startingData;
  private static final String PROPERTIES_LANGUAGE_FILE = "errors.properties";
  private Properties prop;
  private static final String MAX = "Max";
  private static final String MIN = "Min";

  /**
   * takes the initial data given in the file and stores it in a 2D array
   * @param csvDATA data from file that is used to create the GridData starting values
   */
  public GridData(int[][] csvDATA) {
    prop = new Properties();
    startingData = csvDATA;
  }

  /**
   * abstract method that returns the cell at a given
   * position
   * @param row of desired cell
   * @param col of desired cell
   * @return a cell model that is at the given positon
   */
  public abstract CellModel getCell(int row, int col);

  /**
   * sets the state of a cell given a certain position
   * @param row of cell to be set
   * @param col of cell to be set
   * @param state that cell at the position will be set to
   */
  public abstract void setCell(int row, int col, int state);

  /**
   * gets the number of rows in the passed in grid
   * @return int of the number of rows
   */
  public abstract int getRowCount();

  /**
   * gets the number of columns in the passed in grid
   * @return the number of columns
   */
  public abstract int getColCount();

  private CellModel getCellToAdd(int row, int col){
    if(row == -1 || col == -1){
      return new CellModel(0);
    }
    return getCell(row, col);
  }

  /**
   * @param row row of cell
   * @param col col of cell
   * @param neighborhoodPolicy policy that will be used for determining neighbors
   * @param edgePolicy policy that will be used for determining neighbors
   * @return Collection of CellModels that are the neighbors of the cell at row,col using the two policies passed as parameters
   */
  public List<CellModel> getNeighbors(int row, int col, String neighborhoodPolicy, String edgePolicy) {
    int rowLimit = getRowCount() - 1;
    int columnLimit = getColCount() - 1;
    List<CellModel> neighbors = new ArrayList<>();
    int rowMax = getLimitForEdgePolicy(row-1, 0, MAX, edgePolicy);
    int colMax = getLimitForEdgePolicy(col-1, 0, MAX, edgePolicy);
    int rowMin = getLimitForEdgePolicy(row+1, rowLimit, MIN, edgePolicy);
    int colMin = getLimitForEdgePolicy(col+1, columnLimit, MIN, edgePolicy);
    for (int x = rowMax; x <= rowMin; x++) {
      for (int y = colMax; y <= colMin; y++) {
        if ((x != row || y != col) && getIfNeighborhoodPolicyAllows(Math.abs(x-row), Math.abs(y-col), neighborhoodPolicy)) {
          neighbors.add(getCellToAdd(getIfOutOfBounds(x, rowLimit, edgePolicy), getIfOutOfBounds(y, columnLimit, edgePolicy)));
        }
      }
    }
    return neighbors;
  }

  /**
   *
   * @param val Number that you want to determine the limit for
   * @param limit limit value
   * @param type type of limit, max or min
   * @param edgePolicy edge policy used to determine the limit
   * @return Limit for val determined by the type and edge policy, this value is used as a sentinel value in the for loops when finding neighbors
   * @author Colin Boccaccio
   */
  protected int getLimitForEdgePolicy(int val, int limit, String type, String edgePolicy){
    try {
      int ret = val; //stays as val if no method exists for edge policy, i.e. toroidal doesn't need one
      try{
        Method limitGetter = this.getClass()
            .getMethod("get"+type+"For"+edgePolicy, int.class, int.class);
        ret = (int) limitGetter.invoke(this, val, limit);
      } catch(NoSuchMethodException e){
        return ret;
      }
      return ret;
    } catch (Exception e){
      throw new FormattingExceptions((String)prop.get("NeighborHoodOrEdgePolicyError")); //neighbor or edge policy that doesn't exist
    }
  }

  /**
   *
   * @param val val we are finding limit of
   * @param limit limit value
   * @return minimum value for finite edge policy based of the val and limit
   * @author Colin Boccaccio
   */
  public int getMinForFinite(int val, int limit){
    return Math.min(val, limit);
  }

  /**
   *
   * @param val val we are finding limit of
   * @param limit limit value
   * @return maximum value for finite edge policy based of the val and limit
   * @author Colin Boccaccio
   */
  public int getMaxForFinite(int val, int limit){
    return Math.max(0, val);
  }

  /**
   *
   * @param rowDiff row of cell we are currently looking at minus row of cell we want to find neighbors of
   * @param colDiff col of cell we are currently looking at minus col of cell we want to find neighbors of
   * @param neighborhoodPolicy that is used to determine if this cell should be a neighbor
   * @return boolean value that represents if the cell with these rowDiff and colDiff should be in the neighborhood.
   * @auhtor Colin Boccaccio
   */
  protected boolean getIfNeighborhoodPolicyAllows(int rowDiff, int colDiff, String neighborhoodPolicy){
    try {
      boolean allows = true; //stays as true if no neighborhood method exists i.e. not needed for Surrounding
      try{
        Method limitGetter = this.getClass()
            .getMethod("getAllowsFor"+neighborhoodPolicy, int.class, int.class);
        allows = (boolean) limitGetter.invoke(this, rowDiff, colDiff);
      }catch(NoSuchMethodException e){
        return allows;
      }
      return allows;
    } catch (Exception e){
      throw new FormattingExceptions((String)prop.get("NeighborHoodOrEdgePolicyError")); //neighbor or edge policy that doesn't exist
    }
  }

  /**
   *
   * @param rowDiff rowDiff row of cell we are currently looking at minus row of cell we want to find neighbors of
   * @param colDiff colDiff col of cell we are currently looking at minus col of cell we want to find neighbors of
   * @return boolean value that represents if the cell with these rowDiff and colDiff should be in the neighborhood.
   * @author Colin Boccaccio
   */
  public boolean getAllowsForAdjacent(int rowDiff, int colDiff){
    return ((rowDiff == 1 && colDiff ==0) || (rowDiff == 0 && colDiff == 1));
  }

  /**
   *
   * @param rowDiff rowDiff row of cell we are currently looking at minus row of cell we want to find neighbors of
   * @param colDiff colDiff col of cell we are currently looking at minus col of cell we want to find neighbors of
   * @return boolean value that represents if the cell with these rowDiff and colDiff should be in the neighborhood.
   * @author Colin Boccaccio
   */
  public boolean getAllowsForSide(int rowDiff, int colDiff){
    return (rowDiff == 0 && colDiff == 1);
  }

  /**
   *
   * @param val row or col value of current cell
   * @param valLimit row/col size of gridData
   * @param edgePolicy that we will be using
   * @return determines which cell should be examined as a neighbor if a cell is on the edge of the simulation
   * @author Colin Boccaccio
   */
  protected int getIfOutOfBounds(int val, int valLimit, String edgePolicy){
    try {
      int ret = val; //edge policies without an implementation of this method will never deal with out of bounds values, so they can just return val
      try{
        Method limitGetter = this.getClass()
            .getMethod("getIfOutOfBounds"+edgePolicy, int.class, int.class);
        ret = (int) limitGetter.invoke(this, val, valLimit);
      }catch(NoSuchMethodException e){
        return ret;
      }
      return ret;
    } catch (Exception e){
      throw new FormattingExceptions((String)prop.get("NeighborHoodOrEdgePolicyError")); //neighbor or edge policy that doesn't exist
    }
  }

  /**
   *
   * @param val row or col value of current cell
   * @param limit row/col size of gridData
   * @return determines which cell should be examined as a neighbor if a cell is on the edge of the simulation
   * @author Colin Boccaccio
   */
  public int getIfOutOfBoundsToroidal(int val, int limit){
    if(val>limit){
      return 0;
    }
    if(val<0){
      return limit;
    }
    return val;
  }

  /**
   *
   * @param val row or col value of current cell
   * @param limit row/col size of gridData
   * @return determines which cell should be examined as a neighbor if a cell is on the edge of the simulation
   * @author Colin Boccaccio
   */
  public int getIfOutOfBoundsStateZero(int val, int limit){
    if(val>limit || val<0){
      return -1;
    }
    return val;
  }
}
