package WaTor;

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

public class WaTorTest2 extends DukeTest {
  private Controller test1GameofLifeController;
  private ReadCSVFile thisFileReader;

  private GridData thisGridData;

  @Override
  public void start(Stage stage) throws IOException {
    thisFileReader = new ReadCSVFile("Examples/WaTor/Test2.csv");
    thisGridData = new GridDataArray(thisFileReader.getGrid());
    test1GameofLifeController = new Controller();
    test1GameofLifeController.PROPERTIES_FILE = "WaTor/WaTor2.properties";
    test1GameofLifeController.start(new Stage());
  }


  public WaTorTest2() throws IOException {
    thisFileReader = new ReadCSVFile("Examples/WaTor/Test2.csv");
    thisGridData = new GridDataArray(thisFileReader.getGrid());
    test1GameofLifeController = new Controller();
  }

  private boolean aresame(int[][] a,int[][] b){
    for(int i = 0;i<a.length;i++){
      for(int j=0; i<b[0].length;i++){
        if(a[i][j]!=b[i][j]){
          return false;
        }

      }
    }
    return true;
  }
  //test reading the file
  @Test
  public void testFile() throws IOException {
    assertEquals(0,thisFileReader.getGrid()[0][0]);
    int[][] testArray = {{0,0,0},{0,2,0},{0,1,0}};
    assertTrue(aresame(testArray,thisFileReader.getGrid()));
  }


  //check that the Grid Data class is holding the data correctly
  @Test
  public void testGridData() throws IOException {
    //check cells in grid have proper values
    CellModel testCell = new CellModel(0);
    assertEquals(testCell.getState(),thisGridData.getCell(0,0).getState());
    assertEquals(testCell.getInfo(),thisGridData.getCell(0,0).getInfo());

    //test that the neighbors are correctly found using "Surrounding"
    List<Integer> knownNeighbors = new ArrayList<Integer>(Arrays.asList(0,0,0,0,0,0,1,0));
    List<CellModel> testNeighbors= thisGridData.getNeighbors(1,1,"Surrounding", "Finite");
    for(int i=0; i<testNeighbors.size();i++){
      assertEquals(knownNeighbors.get(i),testNeighbors.get(i).getState());
    }

    for(int row = 0; row < thisGridData.getRowCount(); row++){
      System.out.println();
      for(int col = 0; col < thisGridData.getColCount(); col++){
        System.out.print(thisGridData.getCell(row, col).getState());
      }
    }
    System.out.println();

    //test that the neighbors are correctly found using "adjacent"
    List<Integer> knownNeighbors2 = new ArrayList<Integer>(Arrays.asList(0,0,0,1));
    List<CellModel> testNeighbors2= thisGridData.getNeighbors(1,1,"Adjacent", "Finite");
    for(int i=0; i<testNeighbors2.size();i++){
      assertEquals(knownNeighbors2.get(i),testNeighbors2.get(i).getState());
    }

    //test The functions in the Grid Data Array class dimensions
    assertEquals(3,thisGridData.getColCount());
    assertEquals(3,thisGridData.getRowCount());

    //setters
    assertEquals(0,thisGridData.getCell(0,0).getState());
    thisGridData.setCell(0,0,1);
    assertEquals(1,thisGridData.getCell(0,0).getState());

  }
  @Test
  public void testGridModel() throws IOException {

    javafxRun(() -> {
      try {
        test1GameofLifeController.restartSimulationWithNewProp("WaTor/WaTor2.properties");
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    GridModel thisGridModel = test1GameofLifeController.getGridOfCells();

    assertEquals(0, thisGridModel.getCell(0,0).getState());
    assertEquals(0, thisGridModel.getCell(0,1).getState());
    assertEquals(0, thisGridModel.getCell(0,2).getState());
    assertEquals(0, thisGridModel.getCell(1,0).getState());
    assertEquals(2, thisGridModel.getCell(1,1).getState());
    assertEquals(0, thisGridModel.getCell(1,2).getState());
    assertEquals(0, thisGridModel.getCell(2,0).getState());
    assertEquals(1, thisGridModel.getCell(2,1).getState());
    assertEquals(0, thisGridModel.getCell(2,2).getState());

    javafxRun(() -> test1GameofLifeController.step());

    assertEquals(0, thisGridModel.getCell(0,0).getState());
    assertEquals(0, thisGridModel.getCell(0,1).getState());
    assertEquals(0, thisGridModel.getCell(0,2).getState());
    assertEquals(0, thisGridModel.getCell(1,0).getState());
    assertEquals(2, thisGridModel.getCell(1,1).getState());
    assertEquals(0, thisGridModel.getCell(1,2).getState());
    assertEquals(0, thisGridModel.getCell(2,0).getState());
    assertEquals(0, thisGridModel.getCell(2,1).getState());
    assertEquals(0, thisGridModel.getCell(2,2).getState());
  }

  @Test
  public void testGridView() throws IOException {
    javafxRun(() -> {
      try {
        test1GameofLifeController.restartSimulationWithNewProp("WaTor/WaTor2.properties");
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    Paint water = Color.BLUE;
    Paint fish = Color.GREEN;
    Paint shark = Color.PINK;

    GridView thisGridView = test1GameofLifeController.getGridView();

    assertEquals(water, thisGridView.getRectangle(0,0).getFill());
    assertEquals(water, thisGridView.getRectangle(0,1).getFill());
    assertEquals(water, thisGridView.getRectangle(0,2).getFill());
    assertEquals(water, thisGridView.getRectangle(1,0).getFill());
    assertEquals(shark, thisGridView.getRectangle(1,1).getFill());
    assertEquals(water, thisGridView.getRectangle(1,2).getFill());
    assertEquals(water, thisGridView.getRectangle(2,0).getFill());
    assertEquals(fish, thisGridView.getRectangle(2,1).getFill());
    assertEquals(water, thisGridView.getRectangle(2,2).getFill());

    javafxRun(() -> test1GameofLifeController.step());

    assertEquals(water, thisGridView.getRectangle(0,0).getFill());
    assertEquals(water, thisGridView.getRectangle(0,1).getFill());
    assertEquals(water, thisGridView.getRectangle(0,2).getFill());
    assertEquals(water, thisGridView.getRectangle(1,0).getFill());
    assertEquals(shark, thisGridView.getRectangle(1,1).getFill());
    assertEquals(water, thisGridView.getRectangle(1,2).getFill());
    assertEquals(water, thisGridView.getRectangle(2,0).getFill());
    assertEquals(water, thisGridView.getRectangle(2,1).getFill());
    assertEquals(water, thisGridView.getRectangle(2,2).getFill());
  }
}
