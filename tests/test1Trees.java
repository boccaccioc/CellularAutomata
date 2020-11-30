import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cellsociety.Controller;
import cellsociety.FileReader.ReadCSVFile;
import cellsociety.Models.CellModel;
import cellsociety.Models.GridModels.GridData;
import cellsociety.Models.GridModels.GridDataArray;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class test1Trees{
  private Controller test1GameofLifeController;
  public test1Trees(){

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
    ReadCSVFile thisFileReader = new ReadCSVFile("Examples/OtherTestFiles/test1.csv");
    assertEquals(0,thisFileReader.getGrid()[0][0]);
    int[][] testArray = {{0,1,0},{0,1,0},{0,1,0}};
    assertTrue(aresame(testArray,thisFileReader.getGrid()));
  }


  //check that the Grid Data class is holding the data correctly
  @Test
  public void testGridData() throws IOException {
    ReadCSVFile thisFileReader = new ReadCSVFile("Examples/OtherTestFiles/test1.csv");
    GridData thisGridData = new GridDataArray(thisFileReader.getGrid());
    //check cells in grid have proper values
    CellModel testCell = new CellModel(0);
    assertEquals(testCell.getState(),thisGridData.getCell(0,0).getState());
    assertEquals(testCell.getInfo(),thisGridData.getCell(0,0).getInfo());

    //test that the neighbors are correctly found using "Surrounding"
    //test that the neighbors are correctly found using "Surrounding"
    List<Integer> knownNeighbors = new ArrayList<Integer>(){{
      add(0);
      add(1);
      add(0);
      add(0);
      add(1);
      add(0);
      add(0);
      add(0);

    }};
    List<CellModel> testNeighbors= thisGridData.getNeighbors(1,1,"Surrounding", "Finite");
    for(int i=0; i<testNeighbors.size();i++){
      assertEquals(knownNeighbors.get(i),testNeighbors.get(i).getState());
    }

    //test that the neighbors are correctly found using "adjecent"
    List<Integer> knownNeighbors2 = new ArrayList<Integer>(){{
      add(1);
      add(0);
      add(1);
      add(0);

    }};
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


  }

}