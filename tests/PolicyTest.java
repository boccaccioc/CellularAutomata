import cellsociety.Controller;
import cellsociety.FileReader.ReadCSVFile;
import cellsociety.Models.CellModel;
import cellsociety.Models.GridModels.GridData;
import cellsociety.Models.GridModels.GridDataArray;
import cellsociety.Models.GridModels.GridModel;
import cellsociety.Views.GridView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
//import util.DukeApplicationTest;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import util.DukeTest;

public class PolicyTest extends DukeTest {
  private Controller policyTestController;
  private ReadCSVFile thisFileReader;
  private GridData thisGridData;

  @Override
  public void start(Stage stage) throws IOException {
    thisFileReader = new ReadCSVFile("Examples/GameOfLife/Test1.csv");
    thisGridData = new GridDataArray(thisFileReader.getGrid());
    policyTestController = new Controller();
    policyTestController.start(new Stage());
  }

  @Test
  public void testSurroundingNeighborhoodPolicy() throws IOException {
    //check cells in grid have proper values
    CellModel testCell = new CellModel(0);
    assertEquals(testCell.getState(),thisGridData.getCell(0,0).getState());
    assertEquals(testCell.getInfo(),thisGridData.getCell(0,0).getInfo());

    //test that the neighbors are correctly found using "Surrounding" with "Finite"
    List<Integer> knownNeighbors = new ArrayList<Integer>(Arrays.asList(1,0,1));
    List<CellModel> testNeighbors= thisGridData.getNeighbors(0,0,"Surrounding", "Finite");
    for(int i=0; i<testNeighbors.size();i++){
      assertEquals(knownNeighbors.get(i),testNeighbors.get(i).getState());
    }

    //test that the neighbors are correctly found using "Surrounding" with "Toroidal"
    knownNeighbors = new ArrayList<Integer>(Arrays.asList(0,0,1,0,1,0,0,1));
    testNeighbors= thisGridData.getNeighbors(0,0,"Surrounding", "Toroidal");
    for(int i=0; i<testNeighbors.size();i++){
      assertEquals(knownNeighbors.get(i),testNeighbors.get(i).getState());
    }

    //test that the neighbors are correctly found using "Surrounding" with "StateZero"
    knownNeighbors = new ArrayList<Integer>(Arrays.asList(0,0,0,0,1,0,0,1));
    testNeighbors= thisGridData.getNeighbors(0,0,"Surrounding", "StateZero");
    for(int i=0; i<testNeighbors.size();i++){
      assertEquals(knownNeighbors.get(i),testNeighbors.get(i).getState());
    }
  }

  @Test
  public void testAdjacentNeighborhoodPolicy() throws IOException {
    //check cells in grid have proper values
    CellModel testCell = new CellModel(0);
    assertEquals(testCell.getState(), thisGridData.getCell(0, 0).getState());
    assertEquals(testCell.getInfo(), thisGridData.getCell(0, 0).getInfo());

    //test that the neighbors are correctly found using "Adjacent" with "Finite"
    List<Integer> knownNeighbors = new ArrayList<Integer>(Arrays.asList(1,0));
    List<CellModel> testNeighbors= thisGridData.getNeighbors(0,0,"Adjacent", "Finite");
    for(int i=0; i<testNeighbors.size();i++) {
      assertEquals(knownNeighbors.get(i), testNeighbors.get(i).getState());
    }
    //test that the neighbors are correctly found using "Adjacent" with "Toroidal"
    knownNeighbors = new ArrayList<Integer>(Arrays.asList(0,0,1,0));
    testNeighbors = thisGridData.getNeighbors(0, 0, "Adjacent", "Toroidal");
    for (int i = 0; i < testNeighbors.size(); i++) {
      assertEquals(knownNeighbors.get(i), testNeighbors.get(i).getState());
    }

    //test that the neighbors are correctly found using "Adjacent" with "StateZero"
    knownNeighbors = new ArrayList<Integer>(Arrays.asList(0,0,1,0));
    testNeighbors = thisGridData.getNeighbors(0, 0, "Adjacent", "StateZero");
    for (int i = 0; i < testNeighbors.size(); i++) {
      assertEquals(knownNeighbors.get(i), testNeighbors.get(i).getState());
    }
  }
  @Test
  public void testSideNeighborhoodPolicy() throws IOException {
    //check cells in grid have proper values
    CellModel testCell = new CellModel(0);
    assertEquals(testCell.getState(), thisGridData.getCell(0, 0).getState());
    assertEquals(testCell.getInfo(), thisGridData.getCell(0, 0).getInfo());

    //test that the neighbors are correctly found using "Side" with "Finite"
    List<Integer> knownNeighbors = new ArrayList<Integer>(Arrays.asList(1));
    List<CellModel> testNeighbors= thisGridData.getNeighbors(0,0,"Side", "Finite");
    for(int i=0; i<testNeighbors.size();i++) {
      assertEquals(knownNeighbors.get(i), testNeighbors.get(i).getState());
    }
    //test that the neighbors are correctly found using "Side" with "Toroidal"
    knownNeighbors = new ArrayList<Integer>(Arrays.asList(0,1));
    testNeighbors = thisGridData.getNeighbors(0, 0, "Side", "Toroidal");
    for (int i = 0; i < testNeighbors.size(); i++) {
      assertEquals(knownNeighbors.get(i), testNeighbors.get(i).getState());
    }

    //test that the neighbors are correctly found using "Side" with "StateZero"
    knownNeighbors = new ArrayList<Integer>(Arrays.asList(0,1));
    testNeighbors = thisGridData.getNeighbors(0, 0, "Side", "StateZero");
    for (int i = 0; i < testNeighbors.size(); i++) {
      assertEquals(knownNeighbors.get(i), testNeighbors.get(i).getState());
    }
  }
}
