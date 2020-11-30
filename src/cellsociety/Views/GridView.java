package cellsociety.Views;


import cellsociety.Models.GridModels.GridModel;
import cellsociety.Models.SimulationRules.Rules;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Creates the GridView of the simulation. Specifics of the View can be changed by the User Control
 * buttons (such as using images or which colors for states).
 *
 * Assumptions: the GridModel has been initialized
 *
 * @author Isabella Knox
 */
public class GridView {

  private int RECT_WIDTH;
  private int RECT_HEIGHT;
  private GridModel myGridModel;
  private Rules myRules;
  private int sideBoxPadding = 270;
  private int bottomBoxPadding = 70;


  private Rectangle[][] rectangleView;
  private int numberOfRows;
  private int numberOfColumns;
  private BorderPane root;
  private boolean useImageFiles = false;
  private boolean gridOutlined = false;


  /**
   * Constructor for the Grid View class
   */
  public GridView(GridModel newGridModel, BorderPane controllerRoot, int sceneWidth,
      int sceneHeight) {
    root = controllerRoot;
    myGridModel = newGridModel;
    myRules = myGridModel.getRules();
    numberOfColumns = myGridModel.getMyGridColumnSize();
    numberOfRows = myGridModel.getMyGridRowSize();
    RECT_WIDTH = ((sceneWidth - sideBoxPadding) / numberOfRows);
    RECT_HEIGHT = ((sceneHeight - bottomBoxPadding) / numberOfColumns);
    rectangleView = new Rectangle[numberOfRows][numberOfColumns];
    getGridGroup(root, myGridModel);
  }

  /**
   * Gets the rectangle for a specific row and column location
   *
   * @param row
   * @param col
   * @return desired rectangle
   */
  public Rectangle getRectangle(int row, int col) {
    return rectangleView[row][col];
  }

  public BorderPane getGridGroup(BorderPane root, GridModel cellMatrix) {
    createGridDisplay(root, cellMatrix, RECT_WIDTH, RECT_HEIGHT);
    return root;
  }

  /**
   * Creates the initial grid view of the simulation (using rectangles for the grid)
   *
   * @param groupRoot
   * @param cellMatrix
   * @param horizontal_width
   * @param vertical_height
   */
  private void createGridDisplay(BorderPane groupRoot, GridModel cellMatrix, int horizontal_width,
      int vertical_height) {
    for (int i = 0; i < numberOfColumns; i++) {
      for (int j = 0; j < numberOfRows; j++) {

        Rectangle rect = new Rectangle(horizontal_width * j, vertical_height * i, horizontal_width,
            vertical_height);
        rect.setFill(Paint.valueOf(myRules.getColor(cellMatrix.getCell(i, j).getState())));

        rectangleView[i][j] = rect;
        groupRoot.getChildren().add(rect);
      }
    }
    addEventToCells();
  }


  /**
   * Adds a listener to each rectangle of the grid to watch if it is clicked by the user
   */
  public void addEventToCells() {
    for (int i = 0; i < numberOfRows; i++) {
      for (int j = 0; j < numberOfColumns; j++) {
        int finalI = i;
        int finalJ = j;
        rectangleView[i][j].setOnMouseClicked(event -> handleMouseInput(event, finalI, finalJ));
      }
    }
  }


  /**
   * If a rectangle has been clicked, then the grid Model will increment it's state (or go back to
   * state 0 if max state is reached). The gridview will also update the colors or images (if using
   * images) of the grid.
   *
   * @param event
   * @param row
   * @param col
   */
  public void handleMouseInput(Event event, int row, int col) {
    myGridModel.incrementState(myGridModel.getMyGridCell(row, col));
    update();
    fillWithImage();
  }


  /**
   * Updates the GridView colors depending on the state color map of the Grid Model
   */
  public void update() {
    for (int row = 0; row < numberOfRows; row++) {
      for (int col = 0; col < numberOfColumns; col++) {
        rectangleView[row][col]
            .setFill(Paint.valueOf(myRules.getColor(myGridModel.getCell(row, col).getState())));
      }
    }
  }


  /**
   * Sets the flag to true that images should be used for rectangles and fills each rectangle with
   * their correct image
   *
   * @param value
   */
  public void setUseImageFiles(boolean value) {
    useImageFiles = value;
    fillWithImage();
  }

  /**
   * If we wish to use images for our grid, the grid is filled with the appropriate image from the
   * image map
   */
  public void fillWithImage() {
    if (useImageFiles) {
      for (int row = 0; row < numberOfRows; row++) {
        for (int col = 0; col < numberOfColumns; col++) {
            rectangleView[row][col].setFill(new ImagePattern(
                new Image(myRules.getImage(myGridModel.getCell(row, col).getState()))));
        }
      }
    } else {
      return;
    }
  }


  /**
   * Outlines each rectangle in the grid with given color and sets the flag that the grid is
   * outlined to true
   *
   * @param outlineColor
   */
  public void outlineGrid(Color outlineColor) {
    for (int row = 0; row < numberOfRows; row++) {
      for (int col = 0; col < numberOfColumns; col++) {
        rectangleView[row][col].setStroke(outlineColor);
        gridOutlined = true;
      }
    }
  }

  /**
   * Setter for the boolean gridOutlined (flag which states if current grid is outlined)
   *
   * @param value
   */
  public void setGridOutlined(Boolean value) {
    gridOutlined = value;
  }

  /**
   * Getter for the boolean gridOutlined (flag which states if current grid is outlined)
   *
   * @return gridOutlined (if the grid is outlined or not)
   */
  public Boolean getGridOutlined() {
    return gridOutlined;
  }

}
