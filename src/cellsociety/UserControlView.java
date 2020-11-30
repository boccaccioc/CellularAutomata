package cellsociety;


import cellsociety.Views.GridView;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


/**
 * Creates all the user controls (play, step, pause) buttons/drop downs that are displayed on the view.
 * Specifically, handles the front-end actions buttons
 * All buttons/Drop-Downs affect the Grid View or the Scene display (such as colors of Control panels)
 *
 * Assumptions: the Controller and Grid Model has been initialized
 * @author Isabella Knox
 */
public class UserControlView {

  private static final String PROPERTIES_EXTENSION = ".properties" ;
  private String propertiesFile = "English";
  private int columnIndexGrid = 0;
  private int rowZeroIndex = 0;
  private int rowFirstIndex = 1;
  private int rowSecondIndex = 2;
  private int rowThirdIndex = 3;

  private int textInputLetterMax = 23;
  private static double SLIDER_MAX = 5;
  private static double SLIDER_MIN = 0;
  private static double SLIDER_INCREMENT = .5;
  private static double SLIDER_DEFAULT = 1;
  private Slider speedSlider = new Slider();
  private double simulationSpeed;

  private UserControlModel myModel;
  private GridView myGridView;
  private Properties myProperties;
  private Timeline myAnimation;

  private Dialog saveFileDialog = new TextInputDialog();
  private Optional<String> fileNameResult;
  private TextField fileNameInput = new TextField();
  private TextField authorNameInput = new TextField();
  private TextField titleNameInput = new TextField();
  private TextField descriptionInput = new TextField();

  private Dialog colorBoxDialog = new TextInputDialog();
  private TextField colorInput = new TextField();
  private TextField stateInput = new TextField();
  private Optional<String> colorStateResult;

  private Dialog imageStateBoxDialog = new TextInputDialog();
  private TextField imagePathInput = new TextField();
  private TextField stateForImageInput = new TextField();
  private Optional<String> imageStateResult;


  private TextInputDialog loadSimulationBoxDialog = new TextInputDialog();
  private TextField loadSimulationInput;
  private Optional<String> simulationResult;
  private ComboBox themeComboBox = new ComboBox();
  private String themeComboBoxText = "default";
  private ComboBox languageComboBox = new ComboBox();
  private String languageComboBoxText = "English";

  private Button playButton = new Button();
  private Button resetButton = new Button();
  private Button pauseButton = new Button();
  private Button stepButton = new Button();
  private Button outlineButton = new Button();
  private Button graphButton = new Button();
  private Button changeStateColorButton = new Button();
  private Button changeColorsToImagesButton = new Button();
  private Button saveFileButton = new Button();
  private Button loadSimulationFileButton = new Button();
  private Button randomizeConfigurationButton = new Button();
  private Button incrementConfigurationButton = new Button();
  private Button changeStateImageButton = new Button();



  /**
   * Constructor for UserControl view
   *
   * Assumes the GridModel, root, and animation for simulation has been initialized
   */
  public UserControlView(UserControlModel model, GridView gridView, BorderPane root,
      Timeline animation) {
    myModel = model;
    myAnimation = animation;
    myGridView = gridView;
    HBox myButtonsBox = createButtonBox();
    root.setBottom(myButtonsBox);
    VBox mySideButtonsBox = createSideButtonBox();
    root.setRight(mySideButtonsBox);
  }

  /**
   * Setter for the language properties file (used for setting Button language display)
   * @param newLanguageFile
   */
  public void changeLanguagePropertiesFile(String newLanguageFile){
    propertiesFile = newLanguageFile;
  }

  /**
   * Gets the information from the reading the properties file
   */
  private void readApplicationProperties() {
    myProperties = readFileFromClasspath(propertiesFile+PROPERTIES_EXTENSION);
  }

  /**
   * Reads the given properties file
   *
   * @param fileName
   * @return
   */
  private Properties readFileFromClasspath(String fileName) {
    Properties props = new Properties();
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
    if (inputStream == null) {
      throw new RuntimeException(
          "Could not find property file: [" + fileName + "] in the classpath.");
    }
    try {
      props.load(inputStream);
    } catch (IOException e) {
      throw new RuntimeException("Could not read properties from file: [" + fileName + "].", e);
    }
    return props;
  }


  /**
   * Steps the GridModel and GridView when the step button is pressed
   */
  private void stepSimulation() {
    myModel.stepAction();
    myGridView.update();
    myGridView.fillWithImage();
  }

  /**
   * Gets the value given by the Language drop down box
   * @return
   */
  public String languageSelector(){
    languageComboBox.setOnAction(e -> {
      languageComboBoxText = languageComboBox.getValue().toString();
    });
    return languageComboBoxText;
  }


  /**
   * Gets the value given by the Theme drop down box
   * @return
   */
  public String themeSelector(){
    themeComboBox.setOnAction(e -> {
      themeComboBoxText = themeComboBox.getValue().toString();
    });
    return themeComboBoxText;
  }

  /**
   * Plays the Grid animation and plays the GridModel simulation
   */
  private void playSimulation() {
    myAnimation.play();
  }


  /**
   * Pauses the Grid animation and pauses the GridModel simulation
   */
  private void pauseSimulation() {
    myAnimation.pause();
  }

  /**
   * Changes the color of a specific state when the "change color" button is pressed and filled out
   */
  private void colorSimulation(){
    setColorGridAndShow();
    if (stateInput != null && !(stateInput.getText().equals("")) && colorInput != null) {
      String color = colorInput.getText();
      if (checkColorInput(new Rectangle(), color) && checkStateIsInteger(stateInput) ){
        int state = Integer.valueOf(stateInput.getText());
        myModel.changeStateColor(state, color);
        if (myModel.wrongInputBoolean()){
          throwAlertInView("Ooops, there was an error! The state does not work for this simulation");
        }
      }
    }
  }

  /**
   *Action for when the State Image button is pressed
   *If able, if will set all the rectangles to images and change the current image used for the state given
   */
  private void changeStateImageSimulation(){
    setStateImageGridAndShow();
    myGridView.setUseImageFiles(true);
    if (imagePathInput != null && !(stateForImageInput.getText().equals("")) && stateForImageInput != null){
      String imagePath = imagePathInput.getText();
      if (checkStateIsInteger(stateForImageInput) ){
        int state = Integer.valueOf(stateForImageInput.getText());
        myModel.changeImageState(state, imagePath);
      }
    }
    if (myModel.wrongInputBoolean()){
      throwAlertInView("Ooops, there was an error! The state does not work for this simulation");
    }
    myGridView.fillWithImage();
  }



  /**
   * Sets all the cells to images when the image to colors button is pressed
   */
  private void imageSimulation(){
    myGridView.setUseImageFiles(true);
    myGridView.fillWithImage();
  }

  /**
   * Will load the new properties file for the new simulation once the "load simulation" button is pressed and filled out
   *
   * Assumes the file is in the correct folder with the ".properties" extension
   * @throws IOException
   */
  private void loadSimulation() throws IOException {
    textSimulationSettingsAndShow();
    if (loadSimulationInput.getText() != null && loadSimulationInput.getText().toString().length() != 0) {
      Controller myController = new Controller();
      myController.restartSimulationWithNewProp(loadSimulationInput.getText());
    }

  }

  /**
   * Writes a CSV and properties file once the "save file" button is pressed for the current model
   *
   * @throws IOException
   */
  private void saveFileSimulation() throws IOException {
    setGridInDialog();
    myModel.writeToCSVFile(fileNameInput.getText());
    myModel.writeToPropertiesFile(fileNameInput.getText(),titleNameInput.getText(), authorNameInput.getText(), descriptionInput.getText());
  }


  /**
   * Increments the configuration of the states on the grid view and in the grid model
   */
  private void incrementConfigurationSimulation() {
    myModel.incrementConfigurationAction();
  }


  /**
   * Randomizes the configuration of states on the grid view and in the grid model
   */
  private void randomizeConfigurationSimulation() {
    myModel.randomConfigurationAction();
  }



  /**
   * Creates the new stage/graph view of the simulation running when the "show graph" button is pressed
   */
  private void graphSimulation() {
    GraphView myGraphView = new GraphView(myModel.getMyGridModel());
  }



  /**
   * Creates the HBox with set of buttons that will be placed on the bottom of the display
   *
   * @return HBox with the bottom buttons
   */
  private HBox createButtonBox() {
    HBox buttonsBox = new HBox();
    buttonsBox.getStyleClass().add("box");
    buttonsBox.getChildren()
        .add(setSliderParameters(SLIDER_MIN, SLIDER_MAX, SLIDER_INCREMENT, SLIDER_DEFAULT));
    buttonsBox.getChildren()
        .add(giveButtonLabelAndAction(playButton,"playButton", event -> playSimulation()));
    buttonsBox.getChildren()
        .add(giveButtonLabelAndAction(pauseButton, "pauseButton", event -> pauseSimulation()));
    buttonsBox.getChildren()
        .add(giveButtonLabelAndAction(stepButton, "stepButton", event -> stepSimulation()));
    buttonsBox.getChildren()
        .add(giveButtonLabelAndAction(resetButton, "restartButton", event -> {
          try {
            restartSimulation();
          } catch (IOException e) {
            return;
          }
        }));
    return buttonsBox;
  }

  /**
   * Resets the simulation to the original configuration of the current properties file
   * @throws IOException
   */
  private void restartSimulation() throws IOException {
    Controller myController = new Controller();
    myController.restartSimulation();
  }

  /**
   * Outlines or removes outline of the grid (alternates to what is not currently showing)
   */
  private void outlineSimulation() {
    if (!myGridView.getGridOutlined()){
      myGridView.outlineGrid(Color.BLACK);
    }
    else{
      myGridView.outlineGrid(Color.TRANSPARENT);
      myGridView.setGridOutlined(false);
    }
  }

  /**
   * Creates the VBox with set of buttons that will be placed on the side of the display
   * @return the VBox for the side buttons and drop down boxes
   */
  private VBox createSideButtonBox(){
    VBox sideButtonsBox = new VBox();
    sideButtonsBox.getChildren().add(createThemeComboBox());
    sideButtonsBox.getChildren().add(createLanguageComboBox());
    sideButtonsBox.getChildren().add(giveButtonLabelAndAction(graphButton,"graphButton", event -> graphSimulation()));
    sideButtonsBox.getChildren().add(giveButtonLabelAndAction(outlineButton, "outlineButton", event -> outlineSimulation()));
    sideButtonsBox.getChildren().add(giveButtonLabelAndAction(changeStateColorButton, "changeColorButton", event -> colorSimulation()));
    sideButtonsBox.getChildren().add(giveButtonLabelAndAction(changeColorsToImagesButton, "changeToImagesButton", event->imageSimulation()));
    sideButtonsBox.getChildren().add(giveButtonLabelAndAction(changeStateImageButton, "changeStateImageButton", event ->changeStateImageSimulation()));
    sideButtonsBox.getChildren().add(giveButtonLabelAndAction(saveFileButton, "saveFileButton", event -> {
      try {
        saveFileSimulation();
      } catch (IOException e) {
        return;
      }
    }));
    sideButtonsBox.getChildren().add(giveButtonLabelAndAction(loadSimulationFileButton, "loadSimulationButton", event -> {
      try {
        loadSimulation();
      } catch (IOException e) {
        return;
      }
    }));

    sideButtonsBox.getChildren().add(giveButtonLabelAndAction(randomizeConfigurationButton, "randomizeConfigurationButton", event -> randomizeConfigurationSimulation()));
    sideButtonsBox.getChildren().add(giveButtonLabelAndAction(incrementConfigurationButton, "incrementConfigurationButton", event -> incrementConfigurationSimulation()));

    sideButtonsBox.getStyleClass().add("box");
    return sideButtonsBox;
  }


  /**
   * Helper method to set the default button labels and give buttons action
   * @param currentButton
   * @param property property with correct label in the properties file
   * @param handler action to be done by the button
   * @return current button with action and appropriate label
   */
  private Button giveButtonLabelAndAction(Button currentButton, String property, EventHandler<ActionEvent> handler){
    readApplicationProperties();
    setButtonText(currentButton, property);
    currentButton.setOnAction(handler);
    currentButton.setId(property);
    return currentButton;
  }

  /**
   * Will replace the label on the buttons from the current properties file chosen (chosen by the drop down box)
   */
  public void changeLanguageOnButtons(){
    readApplicationProperties();
    setButtonText(playButton, "playButton");
    setButtonText(pauseButton, "pauseButton");
    setButtonText(resetButton, "restartButton");
    setButtonText(stepButton, "stepButton");
    setButtonText(graphButton, "graphButton");
    setButtonText(outlineButton, "outlineButton");
    setButtonText(saveFileButton, "saveFileButton");
    setButtonText(loadSimulationFileButton, "loadSimulationButton");
    setButtonText(changeColorsToImagesButton, "changeToImagesButton");
    setButtonText(changeStateColorButton, "changeColorButton");
    setButtonText(randomizeConfigurationButton, "randomizeConfigurationButton");
    setButtonText(incrementConfigurationButton, "incrementConfigurationButton");
    setButtonText(changeStateImageButton, "changeStateImageButton");
  }

  /**
   * Sets the text that will be displayed on a button in the view
   * @param currentButton
   * @param property
   */
  private void setButtonText(Button currentButton, String property){
    currentButton.setText(myProperties.getProperty(property));
  }


  /**
   * Sets all the parameters for the speed slider
   *
   * @param min small value for speed
   * @param max greatest value for speed
   * @param increment how the speed slider tick marks increment
   * @param defaultValue the value the speed slider is set at when the view is initialized
   * @return the speed slider with values set
   */
  private Slider setSliderParameters(double min, double max, double increment,
      double defaultValue) {
    speedSlider.setMin(min);
    speedSlider.setMax(max);
    speedSlider.setMajorTickUnit(1);
    speedSlider.setMinorTickCount(1);
    speedSlider.setValue(defaultValue);
    speedSlider.setBlockIncrement(increment);
    speedSlider.setShowTickLabels(true);
    speedSlider.setShowTickMarks(true);
    speedSlider.setId("speedSlider");

    speedSlider.valueProperty().addListener(new ChangeListener<Number>() {
      public void changed(ObservableValue<? extends Number> observable, Number oldValue,
          Number newValue) {
        simulationSpeed = newValue.doubleValue();
        myAnimation.setRate(simulationSpeed);
      }
    });
    return speedSlider;
  }


  /**
   * Sets the State Image Grid Pane in the State Image File dialog and styles the State Image Dialog
   */
  private void setStateImageGridAndShow(){
    imageStateBoxDialog.getDialogPane().setContent(makeImageStateGridPane());
    styleDialog(imageStateBoxDialog, "Change Single Image for State", "Single Image Changer", imageStateResult);
  }

  /**
   * Sets the Save File Grid Pane in the Save File dialog and styles the SaveFile Dialog
   */
  private void setGridInDialog(){
    saveFileDialog.getDialogPane().setContent(makeGridPaneForDialog());
    styleDialog(saveFileDialog, "Enter File Name and properties", "Save File", fileNameResult);
  }

  /**
   * Styles the Simulation Dialog and places a optional to wait for text input
   *
   */
  private void textSimulationSettingsAndShow(){
    styleDialog(loadSimulationBoxDialog, "Enter Simulation Properties File Name with .properties extension", "Simulation Properties Name Input", simulationResult );
    loadSimulationInput = loadSimulationBoxDialog.getEditor();
  }

  /**
   * Sets the State Color Grid Pane in the Color State dialog and styles the State Color Dialog
   */
  private void setColorGridAndShow(){
    colorBoxDialog.getDialogPane().setContent(makeColorGridPane());
    styleDialog(colorBoxDialog, "Enter new color and associated state", "State Color Changer", colorStateResult);
  }

  /**
   * Styles a dialog box with a title, header, makes the dialogbox show and wait for input
   *
   * @param dialogBox
   * @param headerText
   * @param boxTitle
   * @param optionalResult
   */
  private void styleDialog(Dialog dialogBox, String headerText, String boxTitle, Optional optionalResult){
    dialogBox.setHeaderText(headerText);
    dialogBox.setTitle(boxTitle);
    optionalResult = dialogBox.showAndWait();
  }

  /**
   *Adds an input text field into a GridPane
   *
   * @param grid
   * @param textField
   */
  private void addToGridPane(GridPane grid, TextField textField, int columnIndex, int rowIndex){
    GridPane.setConstraints(textField, columnIndex, rowIndex);
    grid.getChildren().add(textField);
  }

  /**
   * Creates a grid pane for the Color State dialog and creates the four input fields
   *
   * @return grid pane with input fields
   */
  private GridPane makeColorGridPane(){
    GridPane grid = new GridPane();
    setTextFieldProperties(stateInput, "Enter one state (e.g. 0, 1, etc.)", textInputLetterMax);
    addToGridPane(grid, stateInput, columnIndexGrid, rowZeroIndex);
    setTextFieldProperties(colorInput, "Enter one color name (e.g. BLUE, RED, etc.)", textInputLetterMax);
    addToGridPane(grid, colorInput, columnIndexGrid, rowFirstIndex);
    return grid;
  }

  /**
   * Creates a grid pane for the single Image to State dialog and creates the two input fields
   * @return grid pane
   */
  private GridPane makeImageStateGridPane(){
    GridPane grid = new GridPane();
    setTextFieldProperties(stateForImageInput, "Enter one state (e.g. 0, 1, etc.)", textInputLetterMax);
    addToGridPane(grid, stateForImageInput, columnIndexGrid, rowZeroIndex);
    setTextFieldProperties(imagePathInput, "Enter one image path name (e.g. /images/happy.png)", textInputLetterMax);
    addToGridPane(grid, imagePathInput, columnIndexGrid, rowFirstIndex);
    return grid;
  }

  /**
   * Creates a grid pane for the Save File dialog and creates the four input fields
   *
   * @return grid pane with input fields
   */
  private GridPane makeGridPaneForDialog(){
    GridPane grid = new GridPane();
    setTextFieldProperties(fileNameInput, "Enter shared file name", textInputLetterMax);
    addToGridPane(grid, fileNameInput, columnIndexGrid, rowZeroIndex);
    setTextFieldProperties(titleNameInput, "Enter title name", textInputLetterMax);
    addToGridPane(grid, titleNameInput, columnIndexGrid, rowFirstIndex);
    setTextFieldProperties(authorNameInput, "Enter author name", textInputLetterMax);
    addToGridPane(grid, authorNameInput, columnIndexGrid, rowSecondIndex);
    setTextFieldProperties(descriptionInput, "Enter your description", textInputLetterMax);
    addToGridPane(grid, descriptionInput, columnIndexGrid, rowThirdIndex);
    return grid;
  }


  /**
   * Sets the TextField prompt text and the preferred text letter input length
   *
   * @param textField
   * @param promptText String of message for the input prompt
   * @param columnCount length of the preferred input
   */
  private void setTextFieldProperties(TextField textField, String promptText, int columnCount){
    textField.setPromptText(promptText);
    textField.setPrefColumnCount(columnCount);
  }


  /**
   * Sets the text options and sets default value for the language drop down box
   * @return
   */
  private ComboBox createLanguageComboBox(){
    languageComboBox.setId("languageComboBox");
    addToComboBox(languageComboBox, "English", "Spanish", "German");
    return languageComboBox;
  }

  /**
   * Sets text options and sets default value for the theme drop down box
   * @return
   */
  private ComboBox createThemeComboBox(){
    themeComboBox.setId("themeComboBox");
    addToComboBox(themeComboBox, "default", "duke", "dark");
    return themeComboBox;
  }

  /**
   * Adds text options and sets default value for a ComboBox
   * @param specificComboBox
   * @param option1
   * @param option2
   * @param option3
   * @return
   */
  private ComboBox addToComboBox(ComboBox specificComboBox, String option1, String option2, String option3){
    specificComboBox.getItems().addAll(
        option1,
        option2,
        option3
    );
    specificComboBox.setValue(option1);
    return specificComboBox;
  }

  /**
   * Throw an alert when incorrect data is entered
   * @param contextText
   */
  private void throwAlertInView(String contextText){
    Alert inputAlert = new Alert(AlertType.ERROR);
    inputAlert.setTitle("Input Error");
    inputAlert.setHeaderText("Input Error");
    inputAlert.setContentText(contextText);
    inputAlert.showAndWait();
  }

  /**
   * Checks to see if color input is a valid JavaFx color
   * @param shape
   * @param colorName
   * @return
   */
  private Boolean checkColorInput(Shape shape, String colorName){
      try {
        shape.setFill(Color.web(colorName));
        return true;
      } catch (IllegalArgumentException ex) {
        throwAlertInView("Unrecognized color: " + colorName);
        return false;
      }
  }

  /**
   * Helper method to check if the stateInput is
   * @return true if an integer, false if not only integers
   */
  private Boolean checkStateIsInteger(TextField stateInput){
    if (stateInput.getText().matches("-?\\d+")){
      return true;
    }
    throwAlertInView("State given is not integer");
    return false;
  }

  /**
   * Getter for the save file button dialog
   * @return
   */
  public Dialog getSaveFileDialog(){
    return saveFileDialog;
  }

  /**
   * Getter for the change one state's color dialog
   * @return
   */
  public Dialog getColorBoxDialog(){
    return colorBoxDialog;
  }

  /**
   * Getter for change one image's file dialog
   * @return
   */
  public Dialog getImageStateBoxDialog(){
    return imageStateBoxDialog;
  }

  /**
   * Getter for the load simualtion dialog
   * @return
   */
  public Dialog getLoadSimulationFileDialog(){
    return loadSimulationBoxDialog;
  }
}

