package cellsociety.FileReader;

import cellsociety.FileExceptions;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * The role of this class is to turn information in a file into a
 * 2d int array of information with the same ints in the file
 * this class makes no assumptions about what the values of the digits in the file mean
 * or what they do
 * the 2d int array is a temp data strucutre to hold the information that can be
 * modified and changed later in the grid model class
 * @author Malvika Jain, Colin Boccaccio
 */
public class ReadCSVFile {
  private int numberOfRows;
  private int numberOfColums;
  private int[][] grid;
  private String data;
  private final String DEFAULT_FILE = "Examples/ErrorTests/default.csv";
  private  final String PROPERTIES_LANGUAGE_FILE = "errors.properties";
  private final InputStream PROPERTIES_INPUT_STREAM = getClass().getClassLoader().getResourceAsStream(PROPERTIES_LANGUAGE_FILE);
  private Properties prop;

  /**
   *initalizes all the information needed for
   * reading the CSV file
   * if an incorrect file is passed in a delfault file is used
   * @param newData
   * @throws IOException
   */
  public ReadCSVFile(String newData) throws IOException {
    data = newData;
    prop = new Properties();
    prop.load(PROPERTIES_INPUT_STREAM);
    if(!checkTheContentsOfString()) {
      formGrid(newData);
    }else{
      formGrid(DEFAULT_FILE);
    }

  }

  /**
   * gets the 2D array of states
   * @return a 2D array of integers of the states in the file
   */
  public int[][] getGrid(){
    return grid;
  }


  /**
   * reads the input stream to be able to
   * make it a usable data structure
   * @return a list of list of strings that have all the information in the csv file
   * @param resourceAsStream
   */

  private List<List<String>> readAll(InputStream resourceAsStream) {
    if(resourceAsStream == null){
      throw new FileExceptions((String)prop.get("FileError"));
    }
    try (CSVReader csvReader = new CSVReader(new InputStreamReader(resourceAsStream))) {
      List<List<String>> myGrid = new ArrayList<>();
      csvReader.readAll().forEach(item -> myGrid.add(Arrays.asList(item)));
      return myGrid;
    }
    catch (IOException | CsvException e) {
      throw new FileExceptions((String)prop.get("FileError"));  // custom exception
    }
  }

  /**
   * makes sure that the contents in the file are just numbers
   * and commas and that the size is correct
   * @return true if the connects are not correct and false is they are
   */
  public boolean checkTheContentsOfString(){
    List<List<String>> infoRead = readAll(
        ReadCSVFile.class.getClassLoader().getResourceAsStream(data));
    if(infoRead.size()==0){
      return true;
    }
    if (checkCorrectContentFormat(infoRead)) {
      return true;
    }
    if (checkCorrectValsForDimension(infoRead)) {
        return true;
      }
      int row = Integer.parseInt(infoRead.get(0).get(0));
      int col = Integer.parseInt(infoRead.get(0).get(0));
      if (checkRowDimensions(infoRead, row)) {
        return true;
      }
    if (checkColDimensions(infoRead, row, col)) {
      return true;
    }

    return false;
  }

  private boolean checkCorrectContentFormat(List<List<String>> infoRead) {
    String regex = "[0-9, /,]+";
    for(List<String> line: infoRead) {
      for (String num : line) {
        if (!num.matches(regex)) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean checkColDimensions(List<List<String>> infoRead, int row, int col) {
    for(int i=1; i< row +1;i++){
      if(infoRead.get(i).size()!= col){
        return true;
      }
    }
    return false;
  }

  private boolean checkRowDimensions(List<List<String>> infoRead, int row) {
    if(infoRead.size()-1!=row){
      return true;
    }
    return false;
  }

  private boolean checkCorrectValsForDimension(List<List<String>> infoRead) {
    if(infoRead.get(0).size()!=2){
      return true;
    }
    return false;
  }

  private int[][] formGrid(String data){
    checkTheContentsOfString();
    List<List<String>> infoRead = readAll(
        ReadCSVFile.class.getClassLoader().getResourceAsStream(data));
    if(infoRead.size()==0){
      throw new FileExceptions((String)prop.get("FileEmpty")); //custom exception
    }
    numberOfColums = getNumberOfColumns(infoRead);
    numberOfRows = getNumberOfRows(infoRead);
    grid = new int[numberOfRows][numberOfColums];
    putElementsInTheGrid(infoRead);
    return grid;
  }

  private void putElementsInTheGrid(List<List<String>> infoRead) {
    removeListElementAboutDimensions(infoRead);
    int i =0;
    int j =0;
    for(List<String> row : infoRead){
      for(String element: row){
        grid[i][j] = Integer.parseInt(element);
        j++;
      }
      i++;
      j=0;
    }
  }

  private void removeListElementAboutDimensions(List<List<String>> infoRead) {
    infoRead.remove(0);
  }

  /**
   * assumes that the second value in a file is the number of columns
   * @param infoRead
   * @return
   */
  private int getNumberOfColumns(List<List<String>> infoRead){
    return Integer.parseInt(infoRead.get(0).get(0));
    //assume that the dimensions provided in the file are not negative

  }
  /**
   * assumes that the second value in a file is the number of columns
   * @param infoRead
   * @return
   */
  private int getNumberOfRows(List<List<String>> infoRead){
    return Integer.parseInt(infoRead.get(0).get(0));
  }




}
