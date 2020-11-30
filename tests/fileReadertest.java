
import cellsociety.Controller;
import cellsociety.FileExceptions;
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
import static org.testfx.api.FxAssert.verifyThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.matcher.base.NodeMatchers;
import util.DukeTest;

public class fileReadertest extends DukeTest {
  private Controller test1GameofLifeController;
  private ReadCSVFile thisFileReader;

  private GridData thisGridData;

  @Override
  public void start(Stage stage) throws IOException {
    thisFileReader = new ReadCSVFile("Examples/GameOfLife/Test1.csv");
    thisGridData = new GridDataArray(thisFileReader.getGrid());
    test1GameofLifeController = new Controller();
    test1GameofLifeController.start(new Stage());
  }
  //chekcs all files can be read and that the first value is being read correctly and the dimensions
  //are correct
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

  @Test
  public void testExample1() throws IOException {
    ReadCSVFile thisFileReader = new ReadCSVFile("Examples/OtherTestFiles/example2.csv");
    assertEquals(1,thisFileReader.getGrid()[0][0]);
    int[][] testArray = {{1,0,1},{1,1,1},{1,1,1}};
    assertTrue(areSame(testArray,thisFileReader.getGrid()));
  }
  //larger dimension file
  @Test
  public void testExample3() throws IOException {
    ReadCSVFile thisFileReader = new ReadCSVFile("Examples/OtherTestFiles/example3.csv");
    assertEquals(1,thisFileReader.getGrid()[0][0]);
    int[][] testArray = {{1,0,1,1},{1,1,1,1},{1,1,1,0},{0,0,0,0}};
    assertTrue(areSame(testArray,thisFileReader.getGrid()));
  }
  @Test
  public void testExample4() throws IOException {
    ReadCSVFile thisFileReader = new ReadCSVFile("Examples/OtherTestFiles/example3.csv");
    assertEquals(1,thisFileReader.getGrid()[0][0]);
  }

  /**
   * tests for a file that is empty that is passed in does not throw an error as desired
   * @throws IOException
   */
  @Test
  public void testExceptionForEmptyFile() throws IOException {
    //ReadCSVFile thisFileReader;
      ReadCSVFile thisFileReader = new ReadCSVFile("Examples/ErrorTests/errorFile.csv");

  }

  /**
   * tests for a file that is empty that is passed in and a custom error is thrown
   * when a file is intially loaded (not for manually added)
   * @throws IOException
   */
  @Test
  public void testExceptionForWrongFile() throws IOException {
    //ReadCSVFile thisFileReader;
    Assertions.assertThrows(FileExceptions.class, () -> {
      ReadCSVFile thisFileReader = new ReadCSVFile("Examples/wrong.csv");
    });
  }

  /**
   * the file is incorrectly formatted a default file is used
   * @throws IOException
   */
  @Test
  public void testExceptionForWrongFileFormatWithLetters() throws IOException {
    thisFileReader = new ReadCSVFile("Examples/ErrorTests/letters.csv");
    thisGridData = new GridDataArray(thisFileReader.getGrid());
    int[][] testArray = {{0,1,0},{0,1,0},{0,1,0},{0,1,0}};
    assertTrue(areSame(testArray,thisFileReader.getGrid()));

//    test1GameofLifeController = new Controller();
//    test1GameofLifeController.start(new Stage());

  }

  /**
   * a properties file with no correct files is given adn the
   * test makes sure that a alert box is created
   * @throws IOException
   */
  @Test
  public void testExceptionForWrongPropertiesFile() throws IOException {
    //ReadCSVFile thisFileReader = new ReadCSVFile("Examples/GameOfLife/Test1.csv");
    //thisGridData = new GridDataArray(thisFileReader.getGrid());
    javafxRun(() -> test1GameofLifeController.step());
    test1GameofLifeController.changePropertiesFile("wrong.properties");
    javafxRun(() -> test1GameofLifeController.step());

  }

  /**
   * a properties file with no missing information is given and the
   * test makes sure that a alert box is created
   * these methods make sure the program does not crash
   * @throws IOException
   */
  @Test
  public void testExceptionForMissingPropertiesFile() throws IOException {
    //ReadCSVFile thisFileReader = new ReadCSVFile("Examples/GameOfLife/Test1.csv");
    //thisGridData = new GridDataArray(thisFileReader.getGrid());
    javafxRun(() -> test1GameofLifeController.step());
    test1GameofLifeController.changePropertiesFile("missingValues.properties");
    javafxRun(() -> test1GameofLifeController.step());
    test1GameofLifeController.changePropertiesFile("GameOfLife.properties");
    javafxRun(() -> test1GameofLifeController.step());


  }

  /**
   * if a state is looked for that is before the starting index or after the last index
   * @throws IOException
   */
  @Test
  public void testLookingForValuesOutsideBounds() throws IOException {
    //out of bounds row
    assertEquals(0,thisGridData.getCell(-10,2).getState());
    //out of bounds column
    assertEquals(0,thisGridData.getCell(0,-1000).getState());
    //in bounds
    assertEquals(1,thisGridData.getCell(1,1).getState());
    //out of bounds row
    assertEquals(0,thisGridData.getCell(10000,10000).getState());
    //out of bounds column
    assertEquals(0,thisGridData.getCell(0,1000).getState());

  }
  @Test
  public void testInCorrectBoundsInFile() throws IOException {
    javafxRun(() -> test1GameofLifeController.step());
    test1GameofLifeController.changePropertiesFile("wrongDimensionsProperty.properties");
    javafxRun(() -> test1GameofLifeController.step());
  }





}
