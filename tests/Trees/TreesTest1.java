package Trees;

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


public class TreesTest1 extends DukeTest {
  private  Controller test1TreesController;
  private  ReadCSVFile thisFileReader;
  private  GridData thisGridData;


  @Override
  public void start(Stage stage) throws IOException {
    thisFileReader = new ReadCSVFile("Examples/Trees/Test1.csv");
    thisGridData = new GridDataArray(thisFileReader.getGrid());
    test1TreesController = new Controller();
    test1TreesController.start(new Stage());
  }

  private boolean areSame(int[][] a,int[][] b){
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
    assertEquals(1,thisFileReader.getGrid()[0][0]);
    int[][] testArray = {{1,1,1},{1,1,1},{1,1,1}};
    assertTrue(areSame(testArray,thisFileReader.getGrid()));
  }


  //check that the Grid Data class is holding the data correctly
  @Test
  public void testGridData() throws IOException {
    //check cells in grid have proper values
    CellModel testCell = new CellModel(1);
    assertEquals(testCell.getState(),thisGridData.getCell(0,0).getState());
    assertEquals(testCell.getInfo(),thisGridData.getCell(0,0).getInfo());

    //test that the neighbors are correctly found using "Surrounding"
    List<Integer> knownNeighbors = new ArrayList<Integer>(Arrays.asList(1,1,1,1,1,1,1,1));
    List<CellModel> testNeighbors= thisGridData.getNeighbors(1,1,"Surrounding", "Finite");
    for(int i=0; i<testNeighbors.size();i++){
      assertEquals(knownNeighbors.get(i),testNeighbors.get(i).getState());
    }

    //test that the neighbors are correctly found using "adjacent"
    List<Integer> knownNeighbors2 = new ArrayList<Integer>(Arrays.asList(1,1,1,1));
    List<CellModel> testNeighbors2= thisGridData.getNeighbors(1,1,"Adjacent", "Finite");
    for(int i=0; i<testNeighbors2.size();i++){
      assertEquals(knownNeighbors2.get(i),testNeighbors2.get(i).getState());
    }

    //test The functions in the Grid Data Array class dimensions
    assertEquals(3,thisGridData.getColCount());
    assertEquals(3,thisGridData.getRowCount());

    //setters
    assertEquals(1,thisGridData.getCell(0,0).getState());
    thisGridData.setCell(0,0,0);
    assertEquals(0,thisGridData.getCell(0,0).getState());

  }
  @Test
  public void testGridModel() throws IOException {
    //test1GameofLifeController.start(new Stage());
    //test1GameofLifeController.step();
    //javafxRun(() -> test1GameofLifeController.step());

    javafxRun(() -> {
      try {
        test1TreesController.restartSimulationWithNewProp("Trees.properties");
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    GridModel thisGridModel = test1TreesController.getGridOfCells();

    assertEquals(1, thisGridModel.getCell(0,0).getState());
    assertEquals(1, thisGridModel.getCell(0,1).getState());
    assertEquals(1, thisGridModel.getCell(0,2).getState());
    assertEquals(1, thisGridModel.getCell(1,0).getState());
    assertEquals(2, thisGridModel.getCell(1,1).getState());
    assertEquals(1, thisGridModel.getCell(1,2).getState());
    assertEquals(1, thisGridModel.getCell(2,0).getState());
    assertEquals(1, thisGridModel.getCell(2,1).getState());
    assertEquals(1, thisGridModel.getCell(2,2).getState());

    //assume code steps foward
    javafxRun(() -> test1TreesController.step());

    assertEquals(1, thisGridModel.getCell(0,0).getState());
    assertEquals(1, thisGridModel.getCell(0,1).getState());
    assertEquals(1, thisGridModel.getCell(0,2).getState());
    assertEquals(1, thisGridModel.getCell(1,0).getState());
    assertEquals(0, thisGridModel.getCell(1,1).getState());
    assertEquals(1, thisGridModel.getCell(1,2).getState());
    assertEquals(1, thisGridModel.getCell(2,0).getState());
    assertEquals(1, thisGridModel.getCell(2,1).getState());
    assertEquals(1, thisGridModel.getCell(2,2).getState());


    //step foward again here
    javafxRun(() -> test1TreesController.step());


    assertEquals(1, thisGridModel.getCell(0,0).getState());
    assertEquals(1, thisGridModel.getCell(0,1).getState());
    assertEquals(1, thisGridModel.getCell(0,2).getState());
    assertEquals(1, thisGridModel.getCell(1,0).getState());
    assertEquals(0, thisGridModel.getCell(1,1).getState());
    assertEquals(1, thisGridModel.getCell(1,2).getState());
    assertEquals(1, thisGridModel.getCell(2,0).getState());
    assertEquals(1, thisGridModel.getCell(2,1).getState());
    assertEquals(1, thisGridModel.getCell(2,2).getState());

  }

  @Test
  public void testGridView() throws IOException {
    //test1GameofLifeController.start(new Stage());
    //test1GameofLifeController.step();
    //javafxRun(() -> test1GameofLifeController.step());
    javafxRun(() -> {
      try {
        test1TreesController.restartSimulationWithNewProp("Trees.properties");
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    Paint grass = Color.YELLOW;
    Paint tree = Color.GREEN;
    Paint fire = Color.RED;


    GridView thisGridView = test1TreesController.getGridView();

    assertEquals(tree, thisGridView.getRectangle(0,0).getFill());
    assertEquals(tree, thisGridView.getRectangle(0,1).getFill());
    assertEquals(tree, thisGridView.getRectangle(0,2).getFill());
    assertEquals(tree, thisGridView.getRectangle(1,0).getFill());
    assertEquals(fire, thisGridView.getRectangle(1,1).getFill());
    assertEquals(tree, thisGridView.getRectangle(1,2).getFill());
    assertEquals(tree, thisGridView.getRectangle(2,0).getFill());
    assertEquals(tree, thisGridView.getRectangle(2,1).getFill());
    assertEquals(tree, thisGridView.getRectangle(2,2).getFill());

    //assume code steps foward
    javafxRun(() -> test1TreesController.step());


    assertEquals(tree, thisGridView.getRectangle(0,0).getFill());
    assertEquals(tree, thisGridView.getRectangle(0,1).getFill());
    assertEquals(tree, thisGridView.getRectangle(0,2).getFill());
    assertEquals(tree, thisGridView.getRectangle(1,0).getFill());
    assertEquals(grass, thisGridView.getRectangle(1,1).getFill());
    assertEquals(tree, thisGridView.getRectangle(1,2).getFill());
    assertEquals(tree, thisGridView.getRectangle(2,0).getFill());
    assertEquals(tree, thisGridView.getRectangle(2,1).getFill());
    assertEquals(tree, thisGridView.getRectangle(2,2).getFill());

    //step foward again here
    javafxRun(() -> test1TreesController.step());


    assertEquals(tree, thisGridView.getRectangle(0,0).getFill());
    assertEquals(tree, thisGridView.getRectangle(0,1).getFill());
    assertEquals(tree, thisGridView.getRectangle(0,2).getFill());
    assertEquals(tree, thisGridView.getRectangle(1,0).getFill());
    assertEquals(grass, thisGridView.getRectangle(1,1).getFill());
    assertEquals(tree, thisGridView.getRectangle(1,2).getFill());
    assertEquals(tree, thisGridView.getRectangle(2,0).getFill());
    assertEquals(tree, thisGridView.getRectangle(2,1).getFill());
    assertEquals(tree, thisGridView.getRectangle(2,2).getFill());
  }
}
