import cellsociety.FileReader.ReadCSVFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

import cellsociety.Models.CellModel;
import java.io.IOException;
import org.junit.Test;

public class cellModelTest {
  private CellModel testCell;
  public cellModelTest(){
     testCell = new CellModel(0);

  }

  /**
   * tests that the states of the cell can be correctly updated and retrived
   */
  @Test
  public void testChangeState(){
    assertEquals(0, testCell.getState());
    testCell.setState(1);
    assertEquals(1, testCell.getState());
  }

  @Test
  public void testChangeInfo(){
    assertEquals(0, testCell.getInfo());
    testCell.setInfo(5);
    assertEquals(5, testCell.getInfo());
  }




}
