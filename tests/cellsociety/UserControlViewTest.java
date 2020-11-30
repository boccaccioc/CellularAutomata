package cellsociety;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.FileReader.ReadCSVFile;
import cellsociety.Models.GridModels.GridData;
import cellsociety.Models.GridModels.GridDataArray;
import cellsociety.Models.GridModels.GridModel;
import cellsociety.Views.GridView;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
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
class UserControlViewTest extends DukeTest {

  private Controller userControlViewTestController = new Controller();
  private ReadCSVFile thisFileReader;
  private Scene currentScene;
  private GridView currentGridView;
  private GridData currentGridData;
  private Button testButton;
  private ComboBox testComboBox;
  private UserControlView currentUserControlView;

  @Override
  public void start(Stage stage) throws IOException {
    thisFileReader = new ReadCSVFile("Examples/GameOfLife/Test1.csv");
    currentGridData = new GridDataArray(thisFileReader.getGrid());
    userControlViewTestController = new Controller();
    userControlViewTestController.start(new Stage());
    currentGridView = userControlViewTestController.getGridView();
    currentUserControlView = userControlViewTestController.getMyUserControlView();
    currentScene = userControlViewTestController.getScene();
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


  @Test
  void testUserControlView_initialRectangleConfiguration() throws IOException {
    assertEquals(0,thisFileReader.getGrid()[0][0]);
    int[][] testArray = {{0,1,0},{0,1,0},{0,1,0}};
    assertTrue(areSame(testArray,thisFileReader.getGrid()));
  }

  @Test
  void testUserControlView_testPlayButton(){
    testButton = lookup("#playButton").query();
    clickOn(testButton);
    Rectangle currentRectangle = currentGridView.getRectangle(0,1);
    assertTrue(currentRectangle.getFill()== Color.GREEN);
    javafxRun(() -> userControlViewTestController.step());
    testButton = lookup("#playButton").query();
    clickOn(testButton);
    javafxRun(() -> userControlViewTestController.step());
    assertTrue(currentRectangle.getFill()== Color.GREEN);
  }

  @Test
  void testUserControlView_testPauseButton(){
    Rectangle currentRectangle = currentGridView.getRectangle(0,1);
    assertTrue(currentRectangle.getFill()== Color.GREEN);
    testButton = lookup("#playButton").query();
    clickOn(testButton);
    javafxRun(() -> userControlViewTestController.step());
    assertTrue(currentRectangle.getFill()== Color.ORANGE);
    testButton = lookup("#pauseButton").query();
    clickOn(testButton);
    assertTrue(currentRectangle.getFill()== Color.ORANGE);

  }

  @Test
  void testUserControlView_testStepButton(){
    Rectangle currentRectangle = currentGridView.getRectangle(0,1);
    assertTrue(currentRectangle.getFill()== Color.GREEN);
    testButton = lookup("#stepButton").query();
    clickOn(testButton);
    assertTrue(currentGridView.getRectangle(0,1).getFill()== Color.ORANGE);
  }

  @Test
  void testUserControlView_testResetButton(){
    Rectangle currentRectangle = currentGridView.getRectangle(0,1);
    assertTrue(currentRectangle.getFill()== Color.GREEN);
    javafxRun(() -> userControlViewTestController.step());
    assertTrue(currentRectangle.getFill()== Color.ORANGE);
    testButton = lookup("#restartButton").query();
    clickOn(testButton);
    testButton = lookup("#pauseButton").query();
    clickOn(testButton);
    currentRectangle = currentGridView.getRectangle(0,1);
    assertFalse(currentRectangle.getFill()== Color.GREEN);
  }

  @Test
  void testUserControlView_testSpeedSlider(){
    assertEquals(1.0,userControlViewTestController.getMainAnimation().getRate() );
    Slider speedSlider = lookup("#speedSlider").query();
    speedSlider.setValue(2.3);
    assertEquals(2.3,userControlViewTestController.getMainAnimation().getRate() );
  }

  @Test
  void testUserControlView_testChangeAllRectanglesToImages(){
    Rectangle currentRectangle = currentGridView.getRectangle(0,1);
    assertTrue(currentRectangle.getFill()== Color.GREEN);
    testButton = lookup("#changeToImagesButton").query();
    clickOn(testButton);
    for (int i = 0; i< (currentGridData.getRowCount()); i++){
      for (int j = 0; j< (currentGridData.getColCount()); j++){
        assertFalse(currentGridView.getRectangle(i,i).getFill()==Color.ORANGE);
        assertFalse(currentGridView.getRectangle(i,i).getFill()==Color.GREEN);
      }
    }
  }


  @Test
  void testUserControlView_testChangeOneStateImage_DialogShows(){
    Rectangle currentRectangle = currentGridView.getRectangle(0,1);
    assertTrue(currentRectangle.getFill()== Color.GREEN);
    testButton = lookup("#changeStateImageButton").query();
    clickOn(testButton);
    Dialog testDialog = currentUserControlView.getImageStateBoxDialog();
    testDialog.isShowing();
  }


  @Test
  void testUserControlView_testChangeOneStateColor_DialogShows(){
    testButton = lookup("#changeStateImageButton").query();
    clickOn(testButton);
    Dialog testDialog = currentUserControlView.getColorBoxDialog();
    testDialog.isShowing();
  }

  @Test
  void testUserControlView_testShowGraphButton_CanBePressed_AndAnimationStillRuns(){
    testButton = lookup("#graphButton").query();
    clickOn(testButton);
    assertEquals(1.0,userControlViewTestController.getMainAnimation().getRate());
  }

  @Test
  void testUserControlView_testRandomConfigurationButton_CanBePressed(){
    testButton = lookup("#randomizeConfigurationButton").query();
    clickOn(testButton);
    assertEquals(1.0,userControlViewTestController.getMainAnimation().getRate() );
  }


  @Test
  void testUserControlView_testIncrementConfigurationButton_CanBePressed(){
    testButton = lookup("#incrementConfigurationButton").query();
    clickOn(testButton);
    assertEquals(1.0,userControlViewTestController.getMainAnimation().getRate() );
  }

  @Test
  void testUserControlView_testOutlineGridButton(){
    GridView thisGridView = userControlViewTestController.getGridView();
    assertFalse(thisGridView.getGridOutlined());
    Rectangle currentRectangle = thisGridView.getRectangle(0,0);
    assertTrue(currentRectangle.getStroke()==null);
    testButton = lookup("#outlineButton").query();
    clickOn(testButton);
    assertTrue(thisGridView.getGridOutlined());
    assertTrue(currentRectangle.getStroke()==Color.BLACK);
  }

  @Test
  void testUserControlView_testRemoveOutlineGridButton(){
    GridView thisGridView = userControlViewTestController.getGridView();
    assertFalse(thisGridView.getGridOutlined());
    Rectangle currentRectangle = thisGridView.getRectangle(0,0);
    assertTrue(currentRectangle.getStroke()==null);
    testButton = lookup("#outlineButton").query();
    clickOn(testButton);
    assertTrue(thisGridView.getGridOutlined());
    assertTrue(currentRectangle.getStroke()==Color.BLACK);
    testButton = lookup("#outlineButton").query();
    clickOn(testButton);
    assertFalse(thisGridView.getGridOutlined());
    assertTrue(currentRectangle.getStroke()==Color.TRANSPARENT);
  }

  @Test
  void testUserControlView_testLoadSimulationButton_DialogPopUp(){
    testButton = lookup("#loadSimulationButton").query();
    clickOn(testButton);
    Dialog testDialog = currentUserControlView.getLoadSimulationFileDialog();
    testDialog.isShowing();
  }

  @Test
  void testUserControlView_testThemeSelectorButton(){
    testComboBox = lookup("#themeComboBox").query();
    clickOn(testComboBox);
    testButton = lookup("#pauseButton").query();
    clickOn(testButton);
    assertEquals("default.css",currentScene.getStylesheets().get(0));
  }

  @Test
  void testUserControlView_testLanguageSelectorButton(){
    testComboBox = lookup("#languageComboBox").query();
    clickOn(testComboBox);
    testButton = lookup("#pauseButton").query();
    assertEquals(testButton.getText(), "Pause");
  }



  @Test
  void testUserControlView_testColorButton_DialogShows(){
    testButton = lookup("#changeColorButton").query();
    clickOn(testButton);
    Dialog testDialog = currentUserControlView.getColorBoxDialog();
    testDialog.isShowing();
  }

  @Test
  void testUserControlView_testSaveFileButton_DialogShows(){
    testButton = lookup("#saveFileButton").query();
    clickOn(testButton);
    Dialog testDialog = currentUserControlView.getSaveFileDialog();
    testDialog.isShowing();
  }



}