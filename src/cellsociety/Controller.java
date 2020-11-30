package cellsociety;

import cellsociety.FileReader.ReadCSVFile;
import cellsociety.Models.CellModel;
import cellsociety.Models.GridModels.GridData;
import cellsociety.Models.GridModels.GridModel;
import cellsociety.Models.SimulationRules.Rules;
import cellsociety.Views.GridView;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.annotation.processing.FilerException;

public class Controller extends Application {

  /**
   * this is the main class in our MVC pattern that connects the functionality
   * between the view and the model
   * @autho Malvika Jain, Colin Boccaccio, Isabella Knox
   */

  private static final double SECOND_DELAY = 1;
  private GridModel myGridModel;
  private GridView myGridView;
  private UserControlView myUserControlView;
  public String PROPERTIES_FILE = "GameOfLife.properties";
  private String PROPERTIES_ERROR_FILE = "errors.properties";
  private InputStream PROPERTIES_INPUT_STREAM = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE);
  private final InputStream PROPERTIES_ERROR_INPUT_STREAM = getClass().getClassLoader().getResourceAsStream(PROPERTIES_ERROR_FILE);
  private UserControlModel myUserControlModel;
  private static Timeline animation;
  private BorderPane root;
  private static final int SCENE_HEIGHT = 600;
  private static final int SCENE_WIDTH = 800;
  private Properties prop_errors = new Properties();
  private Properties prop = new Properties();
  private static final String RESOURCES = "resources/";
  private Scene mainScene;
  private static Stage thisStage;
  private static final String THRESHOLD_FINDER = "Threshold";
  private static final String SIMULATION_FINDER = "Simulation";
  private static final String NEIGHBORHOOD_POLICY_FINDER = "Neighborhood_Policy";
  private static final String EDGE_POLICY_FINDER = "Edge_Policy";
  private static final String MAX_STATE_FINDER = "Max_States";
  private static final String STATE_FINDER = "State_";
  private static final String IMAGE_FINDER = "Image_State";
  private static final int STATE_FINDER_INDEX = 6;
  private static final int IMAGE_FINDER_INDEX = 12;
  private static final String CSV_FINDER = "CSVFile";
  private static final String TITLE_FINDER = "Title";


  public Controller(){}

  public static void main(String[] args){
    launch(args);
  }

  /**
   * changes the properties file used to make the simulation
   * @param propFile
   */
  public void changePropertiesFile(String propFile){
    PROPERTIES_FILE = propFile;
    PROPERTIES_INPUT_STREAM = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE);
  }

  /**
   * resarts the simualtion with the new properties file that is passed in
   * @param propFile
   * @throws IOException
   */
  public void restartSimulationWithNewProp(String propFile) throws IOException {
    if (checkIfSimulationExists(propFile)) {
      return;
    }
    if (checkIfPropValsArePresent()) {
      return;
    }
    start(thisStage);
  }

  private boolean checkIfPropValsArePresent() throws IOException {
    prop.load(PROPERTIES_INPUT_STREAM);
    if(prop.get(SIMULATION_FINDER)==null
        || prop.get(NEIGHBORHOOD_POLICY_FINDER)==null || prop.get(EDGE_POLICY_FINDER)==null||
        prop.get(MAX_STATE_FINDER)==null){
      throwAlertInView();
      return true;
    }
    return false;
  }

  private boolean checkIfSimulationExists(String propFile) {
    changePropertiesFile(propFile);
    if(PROPERTIES_INPUT_STREAM==null){
      throwAlertInView();
      return true;
    }
    return false;
  }

  /**
   * restarts the simulation
   * @throws IOException
   */
  public void restartSimulation() throws IOException {
    start(thisStage);
  }

  /**
   * starts the original stage with a scene based on the properties file
   * @param stage
   * @throws IOException
   */
  @Override
  public void start (Stage stage) throws IOException {
    thisStage=stage;
    prop_errors.load(PROPERTIES_ERROR_INPUT_STREAM);
    try {
      prop.load(PROPERTIES_INPUT_STREAM);
     }catch (Exception e){
    throw new FileExceptions((String)prop_errors.get("SimulationError"));
    }
    Rules ruleSet = createRules();
    String file = prop.getProperty(CSV_FINDER);
    root = new BorderPane();
    KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step());
    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    stage.setScene(setUpScene(file, ruleSet));
    stage.setTitle(prop.getProperty(TITLE_FINDER));
    stage.show();
  }

  private Rules createRules(){
    double threshHold = 0;
    if(prop.keySet().contains(THRESHOLD_FINDER)){
      threshHold = Double.parseDouble((String)prop.get(THRESHOLD_FINDER));
    }
    Rules ruleSet = getRuleSet((String)prop.get(SIMULATION_FINDER), (String)prop.get(NEIGHBORHOOD_POLICY_FINDER), (String)prop.get(EDGE_POLICY_FINDER), threshHold,
        Integer.parseInt((String)prop.get(MAX_STATE_FINDER)));

    for(Entry e : prop.entrySet()){
      if(e.getKey().toString().startsWith(STATE_FINDER)){
        ruleSet.addColor(Integer.parseInt(e.getKey().toString().substring(STATE_FINDER_INDEX,STATE_FINDER_INDEX+1)), (String)e.getValue());
      }
      if(e.getKey().toString().startsWith(IMAGE_FINDER)){
        ruleSet.addImage(Integer.parseInt(e.getKey().toString().substring(IMAGE_FINDER_INDEX,IMAGE_FINDER_INDEX+1)), (String)e.getValue());
      }
    }
    return ruleSet;
  }

  /**
   * gets the values in the properties file
   * @param key
   * @return
   * @throws IOException
   */
  public String getPropValue(String key) throws IOException {
    prop = new Properties();
    prop.load(PROPERTIES_INPUT_STREAM);
    return prop.getProperty(key);
  }

  /**
   * gets the Rules obejct that is created from teh properties file for this simulation
   * @param simulation
   * @param neighborPolicy
   * @param edgePolicy
   * @param threshHold
   * @param maxStates
   * @return a Rules object that is intialized using the values int he properties file
   */
  private Rules getRuleSet(String simulation, String neighborPolicy, String edgePolicy, Double threshHold, int maxStates) {
    try{
      Class<?> cl = Class.forName("cellsociety.Models.SimulationRules."+simulation+"Rules");
      Constructor<?> constructor = cl.getConstructors()[0];
      return (Rules) constructor.newInstance(neighborPolicy, edgePolicy, threshHold, maxStates);
    } catch (Exception e){
      throwAlertInView();
      throw new FileExceptions((String)prop_errors.get("SimulationError"));  // catches
    }
  }

  /**
   * throws an alert in teh view
   */
  public void throwAlertInView(){
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Error Dialog");
    alert.setHeaderText("File Error");
    alert.setContentText("Ooops, there was an error! Simulation properties file/required info missing");
    alert.showAndWait();
  }

  /**
   * sets up a scene to be shown on the stage
   * @param data
   * @param ruleSet
   * @return
   * @throws IOException
   */
  public Scene setUpScene(String data, Rules ruleSet) throws IOException {
    ReadCSVFile simulationReader = new ReadCSVFile(data);
    if(simulationReader.checkTheContentsOfString()){
      throwAlertInView();
    }
    simulationReader.checkTheContentsOfString();
    myGridModel = new GridModel(simulationReader.getGrid(), ruleSet);
    myGridView = new GridView(myGridModel, root, SCENE_WIDTH, SCENE_HEIGHT);

    myUserControlModel = new UserControlModel(myGridModel);
    myUserControlView = new UserControlView(myUserControlModel, myGridView, root, animation);
    mainScene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
    updateCSS(mainScene);
    return mainScene;
  }


  /**
   * calls all the necessary methods for everytime
   * the simulation updates
   *
   */
  public void step(){
   myGridModel.update();
   myGridView.update();
   myGridView.fillWithImage();
   myUserControlView.themeSelector();
   updateCSS(mainScene);
   myUserControlView.changeLanguagePropertiesFile(myUserControlView.languageSelector());
   myUserControlView.changeLanguageOnButtons();
  }


  /**
   * updated the css based on the scene
   * @param scene
   */
  public void updateCSS(Scene scene){
    scene.getStylesheets().clear();
    scene.getStylesheets().add(myUserControlView.themeSelector() +".css");
  }


  /**
   * Getter for the animation of the simulation for the GridView simulation
   * @return animation
   */
  public Timeline getMainAnimation(){
    return animation;
  }

  /**
   * returns the gridModel that is associated with the simulation
   * @return Grid Model for the simulation
   */
  public GridModel getGridOfCells(){
    return myGridModel;
  }

  /**
   * gets the grid view object associated with this simulation
   * @return The gridView object that is associated with the simulation
   */
  public GridView getGridView(){
    return myGridView;
  }

  /**
   * gets the usercontrolview object asscoiated with this simulation
   * @return the usercontrol view object that is for this simulation
   */
  public UserControlView getMyUserControlView(){return myUserControlView;}

  /**
   * gets the current scene
   * @return the current scene
   */
  public Scene getScene(){return mainScene;}


}
