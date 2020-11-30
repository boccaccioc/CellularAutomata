package cellsociety;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.FileReader.ReadCSVFile;
import cellsociety.Models.GridModels.GridData;
import cellsociety.Models.GridModels.GridDataArray;
import cellsociety.Views.GridView;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeTest;

/**
 * Assumes that the properties file being used has GameOfLife simulation, Surrounding Neighbors
 * policy, Finite Edge Policy, Orange for State 0 Color, Green for State 1 Color, and CSV File of Examples/GameOfLife/Test1.csv
 *
 * @author Isabella Knox
 */
class GraphViewTest extends DukeTest {

  //Make at least three tests to verify your new Graph View displays
  // the correct counts (including one which showing it agrees with a simultaneously open Grid View)
  private Controller myController = new Controller();
  private ReadCSVFile thisFileReader;
  private Scene currentScene;
  private GridView currentGridView;
  private GridData currentGridData;
  private Button testButton;
  private ComboBox testComboBox;
  private UserControlView currentUserControlView;
  private GraphView myGraphView;

  @Override
  public void start(Stage stage) throws IOException {
    thisFileReader = new ReadCSVFile("Examples/GameOfLife/Test1.csv");
    currentGridData = new GridDataArray(thisFileReader.getGrid());
    myController = new Controller();
    myController.start(new Stage());
    currentGridView = myController.getGridView();
    currentUserControlView = myController.getMyUserControlView();
    currentScene = myController.getScene();
    myGraphView = new GraphView(myController.getGridOfCells());
  }

  @Test
  void testUserControlView_testNumberOfSeriesOnGraph(){
    testButton = lookup("#graphButton").query();
    clickOn(testButton);
    assertTrue(myGraphView.getSeriesMap().size() == 2);
  }

  @Test
  void testUserControlView_testValueOfStartingDataPoints(){
    testButton = lookup("#graphButton").query();
    clickOn(testButton);
    XYChart.Data dataPoint = new XYChart.Data(1, myGraphView.getStateToCountMap().get(0));
    assertTrue(dataPoint.getYValue().equals(6));
  }

  @Test
  void testUserControlView_testGraphMatchesGrid(){
    testButton = lookup("#graphButton").query();
    clickOn(testButton);
    XYChart.Data dataPoint = new XYChart.Data(1, myGraphView.getStateToCountMap().get(0));

    int deadGridCellCounter = 0;
    for (int i = 0; i< (currentGridData.getRowCount()); i++){
      for (int j = 0; j< (currentGridData.getColCount()); j++){
        if(currentGridView.getRectangle(i,i).getFill()== Color.ORANGE){
          deadGridCellCounter++;
        }
      }
    }
    assertTrue(dataPoint.getYValue().equals(deadGridCellCounter));

  }




}
