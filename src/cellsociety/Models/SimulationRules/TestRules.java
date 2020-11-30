package cellsociety.Models.SimulationRules;

import cellsociety.Models.CellModel;
import cellsociety.Models.GridModels.GridData;
import java.util.List;

public class TestRules extends Rules{

  public TestRules(String neighborPolicy, String edgePolicy, Double threshHold, int maxStates){
    super(neighborPolicy, edgePolicy, threshHold, maxStates);
  }

 /* public TestRules() {
    //System.out.println("StateColors"+stateColors.get(0));
  }*/

  @Override
  public int getCellUpdate(CellModel thisCell, List<CellModel> neighbors) {
    return neighbors.size() * 4;
    //return (int)(Math.random()*8);
  }

  @Override
  public void update(GridData myGrid) {
    for (int row = 0; row < myGrid.getRowCount(); row++) {
      for (int col = 0; col < myGrid.getColCount(); col++) {
        myGrid.getCell(row, col).setState(getCellUpdate(myGrid.getCell(row, col),
            myGrid.getNeighbors(row, col, this.getMyNeighborPolicy(), this.getMyEdgePolicy())));
      }
    }
  }
}
