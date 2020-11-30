package cellsociety;

import cellsociety.Models.GridModels.GridModel;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Class manages the new stage/ window for the Graph View and the Graph View updates GraphView will
 * use the current GridModel being shown by the GridView. However, once the GraphView is
 * initialized, if the a cell in the GridView is clicked (changing the state in the model of the
 * GridView), this change will not be displayed on the GraphView. After a grid's cell is clicked,
 * the models of Graph and Grid will diverge. This can be useful to see how a cell change in the
 * Grid can change how the model would have changed over time(with the GraphView showing the
 * original).
 * <p>
 * If you wish to have the GraphView change when cells are clicked on the GridView, change the
 * step() method in this class to contain only updateGraph(). By deleting myGridModel.update, the
 * GraphView is updating based on the steps made by the Controller class. However, if the Controller
 * window is deleted, steps are no longer being made by the Controller, and the GraphView will no
 * longer update the GridModel over time. This is why the default is to have our GraphView updating
 * separate from our Controller.
 * <p>
 * Buttons (such as play, pause, reset, etc.) on the User Control panel (of the main window that has
 * the GridView) were coded to only affect the GridView of the simulation (not the GraphView).
 * <p>
 * However, changing state color on the user panel changes both the gridView cell state and the line
 * on the Graph view for that given state
 * <p>
 * Assumptions: Controller (along with GridModel, UserControl Model/View) have been initialized
 *
 * @author Isabella Knox
 */
public class GraphView {
  private static final double SECOND_DELAY = 1;
  private GridModel myGridModel;
  private Map<Integer, Series> seriesMap = new HashMap<Integer, Series>();
  private Map<Integer, Integer> stateToCountMap = new HashMap<Integer, Integer>();
  private int stepCounter = 0;


  private final NumberAxis xAxis = new NumberAxis();
  private final NumberAxis yAxis = new NumberAxis();
  private final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
  private Timeline animation;

  /**
   * Constructor of the GraphView
   *
   * @param gridModel
   */
  public GraphView(GridModel gridModel) {
    myGridModel = gridModel;
    createNewStage();
    createSeriesForEachState();
    updateGraph();
  }

  /**
   * Creates a new stage, scene with chart, and timeline (after the "Show Grid" button is pressed)
   */
  private void createNewStage() {
    xAxis.setLabel("Time/s");
    xAxis.setAnimated(false);
    yAxis.setLabel("Population Number");
    yAxis.setAnimated(false);

    lineChart.setTitle("Simulation Population");
    lineChart.setAnimated(false);

    KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step());
    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
    Stage secondStage = new Stage();
    Scene scene = new Scene(lineChart, 800, 600);
    secondStage.setScene(scene);
    secondStage.show();
  }

  /**
   * Updates the GridModel and updates the Graph view
   */
  private void step() {
    myGridModel.update();
    updateGraph();
    styleAllSeriesLines();
  }


  /**
   * Initializes the correct number of series for the simulations
   */
  private void createSeriesForEachState() {
    for (int i = 0; i < myGridModel.getMyMaxStates(); i++) {
      XYChart.Series currentStateSeries = new Series();
      currentStateSeries.setName("State " + i);
      seriesMap.put(i, currentStateSeries);
      lineChart.getData().add(currentStateSeries);
    }
  }


  /**
   * Finds the current population of the given state (number of cells with that current state)
   *
   * @param state
   * @return count of how many cells have that current state in the GridModel
   */
  private int findPopulationOfState(int state) {
    int statePopulationCounter = 0;
    for (int i = 0; i < myGridModel.getMyGridRowSize(); i++) {
      for (int j = 0; j < myGridModel.getMyGridColumnSize(); j++) {
        if (myGridModel.getMyGridValue(i, j) == state) {
          statePopulationCounter++;
        }
      }
    }
    return statePopulationCounter;
  }

  /**
   * Updates the stepCounter and the updates the number of cells for each given state
   */
  private void updateStateToCountMap() {
    stepCounter++;
    for (int i = 0; i < myGridModel.getMyMaxStates(); i++) {
      stateToCountMap.put(i, findPopulationOfState(i));
    }
  }

  /**
   * Updates the Graph and adds new data point of each state's population count for the current
   * step
   */
  private void updateGraph() {
    updateStateToCountMap();
    for (int i = 0; i < seriesMap.size(); i++) {
      XYChart.Series currentSeries = seriesMap.get(i);
      XYChart.Data dataPoint = new XYChart.Data(stepCounter, stateToCountMap.get(i));
      currentSeries.getData().add(dataPoint);
    }
  }

  /**
   * Color data point correctly
   */
  private void styleSeriesPoint(int state, Node dataPoint) {
    Map myColorMap = myGridModel.getColorMap();
    StringBuilder desiredColor = new StringBuilder();
    desiredColor.append("-fx-stroke: " + myColorMap.get(state).toString() + "; ");
    desiredColor.append("-fx-background-color: " + myColorMap.get(state) + ", white");
    dataPoint.setStyle(desiredColor.toString());
  }

  /**
   * Color one data series line correctly according to color map
   */
  private void styleSeriesLine(int state) {
    Set<Node> line = lineChart.lookupAll(".series" + state);
    for (Node dataPoint : line) {
      styleSeriesPoint(state, dataPoint);
    }
  }

  /**
   * Color each series in the graph according to the color map
   */
  private void styleAllSeriesLines() {
    for (int i = 0; i < seriesMap.size(); i++) {
      styleSeriesLine(i);
    }
  }

  /**
   * Getter for series Map
   * @return
   */
  public Map getSeriesMap(){return seriesMap;}


  /**
   * Getter for state to count Map
   * @return
   */
  public Map getStateToCountMap(){return stateToCountMap;}

}

