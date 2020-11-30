package cellsociety;


import cellsociety.Models.GridModels.GridModel;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Class to control the back end of the UserControl panel and buttons. This controls how the buttons
 * that don't use simply change the view / don't use JavaFx work.
 *
 * Assumptions: The controller class is already running, the property file for the current GridModel is correct and contains needed information, and the GridModel simulation has been initialized
 * @author Isabella Knox
 */
public class UserControlModel {

  private GridModel myGridModel;
  private int imageNameStartingIndex = 8;
  private int imageNameEndingIndex = 4;
  private Boolean myWrongInputBoolean = false;

  /**
   * Constructor for UserControlModel
   *
   * @param gridModel
   */
  public UserControlModel(GridModel gridModel) {
    myGridModel = gridModel;
  }

  /**
   *
   */

  public void incrementConfigurationAction(){
    myGridModel.setIncrementConfigurationFlag(true);
  }

  /**
   *
   */
  public void randomConfigurationAction(){
    myGridModel.setRandomConfigurationFlag(true);
  }

  /**
   * Action done when step button is pressed. GridModel is updated.
   */
  public void stepAction() {
    myGridModel.update();
  }

  /**
   * Returns the current GridModel to the UserControlView. Currently, only used to create an instance of the GraphView class when the "show grid" button is pressed.
   * @return
   */
  public GridModel getMyGridModel(){
    return myGridModel;
  }

  /**
   * Changes the color associated with a given state in the GridModel.
   * Used when the "change state color" button is pressed.
   *
   * @param state state whose color is wished to be changed
   * @param color new color to associated with state
   */
  public void changeStateColor(int state, String color) {
    if(state >= myGridModel.getMyMaxStates()){
      myWrongInputBoolean= true;
    }
    else{
      myWrongInputBoolean=false;
      myGridModel.changeStateColor(state, color);
    }
  }

  /**
   * Changes the image associated with a given state in the GridModel.
   * Used when the "change one state image" button is pressed.
   */
  public void changeImageState(int state, String imagePath){
    if(state >= myGridModel.getMyMaxStates()){
      myWrongInputBoolean= true;
    }
    else{
      myWrongInputBoolean=false;
      myGridModel.changeStateImage(state, imagePath);
    }
  }


  public Boolean wrongInputBoolean(){
    return myWrongInputBoolean;
  }

  /**
   * Writes the CSV file with the grid's current state grid after the "save file" button is pressed and window is closed.
   *
   * @param filename name for the CSV to be written
   * @throws IOException
   */
  public void writeToCSVFile(String filename) throws IOException {
    BufferedWriter br = null;
    try {
      br = new BufferedWriter(new FileWriter(filename + ".csv"));
    } catch (IOException e) {
      return;
    }
    br.write(getDimensionsRow());
    br.newLine();
    br.write(String.valueOf(buildGrid()));
    br.close();
  }

  /**
   * Creates String that contains the first line in the CSV file that contains the grid dimensions
   *
   * @return String value of the first row of written CSV file (with grid dimensions)
   */
  private String getDimensionsRow() {
    String a = String.valueOf(myGridModel.getMyGridRowSize());
    String b = String.valueOf(myGridModel.getMyGridColumnSize());
    String firstRow = a + ',' + b;
    return firstRow;
  }

  /**
   * Creates a row of the current grid (all column values for that row)
   * @param row  the specific row number
   * @return String of all the states for that row in the grid
   */
  private StringBuilder getRow(int row) {
    StringBuilder rowValues = new StringBuilder();
    for (int col = 0; col < myGridModel.getMyGridColumnSize(); col++) {
      if (col < myGridModel.getMyGridColumnSize() - 1) {
        rowValues.append(myGridModel.getMyGridValue(row, col));
        rowValues.append(",");
      } else {
        rowValues.append(myGridModel.getMyGridValue(row, col));
      }
    }
    rowValues.append("\n");
    return rowValues;
  }


  /**
   * Concatenates each row of the grid states to build the entire current grid (without the dimensions row)
   *
   * @return the entire states grid
   */
  private StringBuilder buildGrid() {
    StringBuilder grid = new StringBuilder();
    for (int row = 0; row < myGridModel.getMyGridRowSize(); row++) {
      grid.append(getRow(row));
    }
    return grid;
  }


  /**
   * Concatenates all the information that will be added into the properties file
   *
   * @param filename given filename for the new properties file
   * @param title title of the current grid simulation
   * @param author author of the current grid simulation
   * @param description small description for the grid simulation's purpose or attributes
   * @return all the content that will be included in the properties file
   * @throws IOException
   */
  public String buildPropertiesFileContent(String filename, String title, String author,
      String description)
      throws IOException {
    StringBuilder properties = new StringBuilder();
    properties.append(makeLine("Simulation"));
    properties.append("CSVFile=" + filename + ".csv");
    properties.append("Title=" + title + "\n");
    properties.append("Author=" + author + "\n");
    properties.append("Description=" + description + "\n");
    properties.append(makeColorLines());
    properties.append(makeImageLines());
    properties.append("NeighborhoodPolicy=" + myGridModel.getMyNeighborPolicy() + "\n");
    properties.append("Threshold=" + myGridModel.getMyThreshHold() + "\n");
    properties.append("EdgePolicy=" + myGridModel.getMyEdgePolicy() + "\n");
    return String.valueOf(properties);
  }

  /**
   * Gets the current simulation type from the properties file that is being used to run the current GridModel
   *
   * @return name of the current simulation
   * @throws IOException
   */
  private String getPropertyValue() throws IOException {
    Controller myController = new Controller();
    return myController.getPropValue("Simulation");
  }

  /**
   * Helper method for concatenating a property and the found value
   *
   * @param property
   * @return specific string that contains the key and correct associated value
   * @throws IOException
   */
  private StringBuilder makeLine(String property) throws IOException {
    StringBuilder properties = new StringBuilder();
    properties.append(property + "=" + getPropertyValue() + "\n");
    return properties;
  }

  /**
   * Creates the String lines that make the state's color properties
   * @return
   */
  private StringBuilder makeColorLines() {
    Map currentColorMap = myGridModel.getColorMap();
    StringBuilder colorProperties = new StringBuilder();
    for (int i = 0; i < currentColorMap.size(); i++) {
      colorProperties.append("State_" + i + "Color=" + currentColorMap.get(i) + "\n");
    }
    return colorProperties;
  }

  /**
   * Builds the String lines for image properties
   *
   * @return
   */
  private StringBuilder makeImageLines() {
    Map currentImageMap = myGridModel.getImageMap();
    StringBuilder imageProperties = new StringBuilder();
    for (int i = 0; i < currentImageMap.size(); i++) {
      String imageName = (String) currentImageMap.get(i);
      imageProperties.append("Image_State_" + i + "=" + imageName
          .substring(imageNameStartingIndex, imageName.length() - imageNameEndingIndex) + "\n");
    }
    return imageProperties;
  }


  /**
   * Writes the properties file for the current grid simulation running after the "save file" button is pressed and the window has closed.
   *
   * Assumes the GridModel is running and the properties file that was originally used contains all necessary information
   * @param filename
   * @param title
   * @param author
   * @param description
   * @throws IOException
   */
  public void writeToPropertiesFile(String filename, String title, String author,
      String description) throws IOException {
    BufferedWriter br = null;
    try {
      br = new BufferedWriter(new FileWriter(filename + ".properties"));
    } catch (IOException e) {
      return;
    }
    br.write(buildPropertiesFileContent(filename, title, author, description));
    br.newLine();
    br.close();
  }

}
