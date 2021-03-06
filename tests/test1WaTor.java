import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import org.junit.Test;
//import util.DukeApplicationTest;
import util.DukeTest;

public class test1WaTor extends DukeTest {
  private static Controller test1GameofLifeController;
  private static ReadCSVFile thisFileReader;
  private static GridData thisGridData;



  public test1WaTor() throws IOException {
    thisFileReader = new ReadCSVFile("Examples/OtherTestFiles/test1.csv");
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
    int[][] testArray = {{0,1,0},{0,1,0},{0,1,0}};
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
    List<Integer> knownNeighbors = new ArrayList<Integer>(Arrays.asList(0,1,0,0,0,0,1,0));
    List<CellModel> testNeighbors= thisGridData.getNeighbors(1,1,"Surrounding", "Finite");
    for(int i=0; i<testNeighbors.size();i++){
      assertEquals(knownNeighbors.get(i),testNeighbors.get(i).getState());
    }

    //test that the neighbors are correctly found using "adjacent"
    List<Integer> knownNeighbors2 = new ArrayList<Integer>(Arrays.asList(1,0,1,0));
    List<CellModel> testNeighbors2= thisGridData.getNeighbors(1,1,"Adjacent", "Finite");
    for(int i=0; i<testNeighbors.size();i++){
      assertEquals(knownNeighbors.get(i),testNeighbors.get(i).getState());
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
    //test1GameofLifeController.start(new Stage());
    //test1GameofLifeController.step();
    //javafxRun(() -> test1GameofLifeController.step());

    GridModel thisGridModel = test1GameofLifeController.getGridOfCells();

    assertEquals(0, thisGridModel.getCell(0,0).getState());
    assertEquals(1, thisGridModel.getCell(0,1).getState());
    assertEquals(0, thisGridModel.getCell(0,2).getState());
    assertEquals(0, thisGridModel.getCell(1,0).getState());
    assertEquals(1, thisGridModel.getCell(1,1).getState());
    assertEquals(0, thisGridModel.getCell(1,2).getState());
    assertEquals(0, thisGridModel.getCell(2,0).getState());
    assertEquals(1, thisGridModel.getCell(2,1).getState());
    assertEquals(0, thisGridModel.getCell(2,2).getState());

    //assume code steps foward

    assertEquals(0, thisGridModel.getCell(0,0).getState());
    assertEquals(0, thisGridModel.getCell(0,1).getState());
    assertEquals(0, thisGridModel.getCell(0,2).getState());
    assertEquals(1, thisGridModel.getCell(1,0).getState());
    assertEquals(1, thisGridModel.getCell(1,1).getState());
    assertEquals(1, thisGridModel.getCell(1,2).getState());
    assertEquals(0, thisGridModel.getCell(2,0).getState());
    assertEquals(0, thisGridModel.getCell(2,1).getState());
    assertEquals(0, thisGridModel.getCell(2,2).getState());

    //step foward again here

    assertEquals(0, thisGridModel.getCell(0,0).getState());
    assertEquals(1, thisGridModel.getCell(0,1).getState());
    assertEquals(0, thisGridModel.getCell(0,2).getState());
    assertEquals(0, thisGridModel.getCell(1,0).getState());
    assertEquals(1, thisGridModel.getCell(1,1).getState());
    assertEquals(0, thisGridModel.getCell(1,2).getState());
    assertEquals(0, thisGridModel.getCell(2,0).getState());
    assertEquals(1, thisGridModel.getCell(2,1).getState());
    assertEquals(0, thisGridModel.getCell(2,2).getState());
  }

  @Test
  public void testGridView() throws IOException {
    //test1GameofLifeController.start(new Stage());
    //test1GameofLifeController.step();
    //javafxRun(() -> test1GameofLifeController.step());
    Paint alive = Color.BLACK;
    Paint dead = Color.WHITE;


    GridView thisGridView = test1GameofLifeController.getGridView();

    assertEquals(dead, thisGridView.getRectangle(0,0).getFill());
    assertEquals(alive, thisGridView.getRectangle(0,1).getFill());
    assertEquals(dead, thisGridView.getRectangle(0,2).getFill());
    assertEquals(dead, thisGridView.getRectangle(1,0).getFill());
    assertEquals(alive, thisGridView.getRectangle(1,1).getFill());
    assertEquals(dead, thisGridView.getRectangle(1,2).getFill());
    assertEquals(dead, thisGridView.getRectangle(2,0).getFill());
    assertEquals(alive, thisGridView.getRectangle(2,1).getFill());
    assertEquals(dead, thisGridView.getRectangle(2,2).getFill());

    //assume code steps foward

    assertEquals(dead, thisGridView.getRectangle(0,0).getFill());
    assertEquals(dead, thisGridView.getRectangle(0,1).getFill());
    assertEquals(dead, thisGridView.getRectangle(0,2).getFill());
    assertEquals(alive, thisGridView.getRectangle(1,0).getFill());
    assertEquals(alive, thisGridView.getRectangle(1,1).getFill());
    assertEquals(alive, thisGridView.getRectangle(1,2).getFill());
    assertEquals(dead, thisGridView.getRectangle(2,0).getFill());
    assertEquals(dead, thisGridView.getRectangle(2,1).getFill());
    assertEquals(dead, thisGridView.getRectangle(2,2).getFill());

    //step foward again here

    assertEquals(dead, thisGridView.getRectangle(0,0).getFill());
    assertEquals(alive, thisGridView.getRectangle(0,1).getFill());
    assertEquals(dead, thisGridView.getRectangle(0,2).getFill());
    assertEquals(dead, thisGridView.getRectangle(1,0).getFill());
    assertEquals(alive, thisGridView.getRectangle(1,1).getFill());
    assertEquals(dead, thisGridView.getRectangle(1,2).getFill());
    assertEquals(dead, thisGridView.getRectangle(2,0).getFill());
    assertEquals(alive, thisGridView.getRectangle(2,1).getFill());
    assertEquals(dead, thisGridView.getRectangle(2,2).getFill());
  }
}
