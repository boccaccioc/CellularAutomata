package cellsociety.Views;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.Controller;
import cellsociety.FileReader.ReadCSVFile;
import cellsociety.Models.GridModels.GridData;
import cellsociety.Models.GridModels.GridDataArray;
import cellsociety.Models.GridModels.GridModel;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeTest;

/**
 * Assumes that the properties file being used has GameOfLife simulation, Surrounding Neighbors
 * policy, Finite Edge Policy, Orange for State 0 Color, Green for State 1 Color, and CSV File of Examples/GameOfLife/Test1.csv
 *
 * @author Isabella Knox
 */
class GridViewTest extends DukeTest {

  private Controller gridViewTestController = new Controller();
  private ReadCSVFile thisFileReader;
  private GridView currentGridView;
  private GridData currentGridData;
  private Button testButton;

  @Override
  public void start(Stage stage) throws IOException {
    thisFileReader = new ReadCSVFile("Examples/GameOfLife/Test1.csv");
    currentGridData = new GridDataArray(thisFileReader.getGrid());
    gridViewTestController = new Controller();
    gridViewTestController.start(new Stage());
    currentGridView = gridViewTestController.getGridView();
  }

  private boolean areSame(int[][] a, int[][] b) {
    for (int i = 0; i < a.length; i++) {
      for (int j = 0; i < b[0].length; i++) {
        if (a[i][j] != b[i][j]) {
          return false;
        }

      }
    }
    return true;
  }


  @Test
  void testGridView_initialRectangleConfiguration() throws IOException {
    assertEquals(0, thisFileReader.getGrid()[0][0]);
    int[][] testArray = {{0, 1, 0}, {0, 1, 0}, {0, 1, 0}};
    assertTrue(areSame(testArray, thisFileReader.getGrid()));
  }


  @Test
  void testGridView_testRectangleOriginalColorDead() {
    GridView thisGridView = gridViewTestController.getGridView();
    Rectangle currentRectangle = thisGridView.getRectangle(0, 0);
    assertTrue(currentRectangle.getFill() == Color.ORANGE);
  }


  @Test
  void testGridView_testRectangleOriginalColorAlive() {
    GridView thisGridView = gridViewTestController.getGridView();
    Rectangle currentRectangle = thisGridView.getRectangle(1, 1);
    assertTrue(currentRectangle.getFill() == Color.GREEN);
  }

  //click rectangle
  @Test
  void testGridView_testClickingRectangle() {
    Rectangle currentRectangle = currentGridView.getRectangle(0, 0);
    assertTrue(currentRectangle.getFill() == Color.ORANGE);
    clickOn(currentRectangle);
    assertTrue(currentRectangle.getFill() == Color.GREEN);
  }

  //images
  @Test
  void testGridView_RectanglesShowImages() {
    Rectangle currentRectangle = currentGridView.getRectangle(0, 1);
    clickOn(currentRectangle);
    currentRectangle = currentGridView.getRectangle(1, 1);
    clickOn(currentRectangle);
    currentRectangle = currentGridView.getRectangle(2, 1);
    clickOn(currentRectangle);
    assertTrue(currentRectangle.getFill() == Color.ORANGE);
    testButton = lookup("#changeToImagesButton").query();
    clickOn(testButton);
    for (int i = 0; i < (currentGridData.getRowCount()); i++) {
      for (int j = 0; j < (currentGridData.getColCount()); j++) {
        assertFalse(currentGridView.getRectangle(i, i).getFill() == Color.ORANGE);
        assertFalse(currentGridView.getRectangle(i, i).getFill() == Color.GREEN);
      }
    }
  }

  //outlined
  @Test
  void testGridView_GridIsInitiallyNotOutlined() {
    GridView thisGridView = gridViewTestController.getGridView();
    assertFalse(thisGridView.getGridOutlined());
    Rectangle currentRectangle = thisGridView.getRectangle(0, 0);
    assertTrue(currentRectangle.getStroke() == null);
  }

  //outlined
  @Test
  void testGridView_GridIsOutlined() {
    GridView thisGridView = gridViewTestController.getGridView();
    assertFalse(thisGridView.getGridOutlined());
    Rectangle currentRectangle = thisGridView.getRectangle(0, 0);
    assertTrue(currentRectangle.getStroke() == null);
    thisGridView.setGridOutlined(true);
    assertTrue(thisGridView.getGridOutlined());
    thisGridView.outlineGrid(Color.BLACK);

    javafxRun(() -> gridViewTestController.step());
    thisGridView.setGridOutlined(true);
    assertTrue(thisGridView.getGridOutlined());
    assertTrue(currentRectangle.getStroke() == Color.BLACK);
  }


  //outlined added then changed to transparent
  @Test
  void testGridView_GridOutlineNoLongerVisibleWithSecondStep() {
    GridView thisGridView = gridViewTestController.getGridView();
    assertFalse(thisGridView.getGridOutlined());
    Rectangle currentRectangle = thisGridView.getRectangle(0, 0);
    assertTrue(currentRectangle.getStroke() == null);
    thisGridView.setGridOutlined(true);
    assertTrue(thisGridView.getGridOutlined());
    thisGridView.outlineGrid(Color.BLACK);

    javafxRun(() -> gridViewTestController.step());
    thisGridView.setGridOutlined(true);
    assertTrue(thisGridView.getGridOutlined());
    assertTrue(currentRectangle.getStroke() == Color.BLACK);

    javafxRun(() -> gridViewTestController.step());
    thisGridView.setGridOutlined(false);
    assertFalse(thisGridView.getGridOutlined());
    thisGridView.outlineGrid(Color.TRANSPARENT);
    assertTrue(currentRectangle.getStroke() == Color.TRANSPARENT);
  }


}