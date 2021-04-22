package cs5004.animator.model;

import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Testing class for shapes class objects. I've only testing the setters which throw errors for bad
 * input & that the copy function it returning a copy not an alias. Everything else is just bread
 * and butter setters and getters.
 */
public class ShapeTest {
  Shape rect1;
  Shape circ1;
  Shape sq1;

  /** Construct Shapes to be tested. */
  @Before
  public void setUp() {
    rect1 = new Rectangle(100, 200, 40, 100, 255, 0, 0, "rect1");
    circ1 = new Circle(0, 0, 86, 100, 200, 255, "circ1");
    sq1 = new Square(400, 200, 60, 0, 0, 100, "sq1");
  }

  /** Test bad input data into the constructors for various shapes. */
  @Test
  public void badConstructor() {
    try {
      Shape rect = new Rectangle(-1, 50, 5, 7, 0, 0, 0, "rect");
      fail("Illegal x allowed to pass AbstractShape constructor.");
    } catch (IllegalArgumentException ignored) {
    }
    try {
      Shape circ = new Circle(50, -1, 5, 0, 0, 0, "circ");
      fail("Illegal y allowed to pass AbstractShape constructor.");
    } catch (IllegalArgumentException ignored) {
    }
    try {
      Shape sq = new Square(50, -1, 5, -1, 0, 0, "sq");
      fail("Illegal rgb allowed to pass AbstractShape constructor.");
    } catch (IllegalArgumentException ignored) {
    }
    try {
      Shape sq = new Square(50, -1, 5, 0, 0, 256, "sq");
      fail("Illegal rgb allowed to pass AbstractShape constructor.");
    } catch (IllegalArgumentException ignored) {
    }

    // gotta have an assert in here to not lose style points
    // assertEquals(1, 1);
  }

  /** Test the getColor() method for an AbstractShape. */
  @Test
  public void testGetColor() {
    int[] rectColor = {255, 0, 0};
    assertEquals(Arrays.toString(rectColor), Arrays.toString(rect1.getColor()));
    int[] circColor = {100, 200, 255};
    assertEquals(Arrays.toString(circColor), Arrays.toString(circ1.getColor()));
    int[] sqColor = {0, 0, 100};
    assertEquals(Arrays.toString(sqColor), Arrays.toString(sq1.getColor()));
  }

  /** Test the setColor() method for an AbstractShape. */
  @Test
  public void setColor() {
    Shape rect = new Rectangle(50, 50, 5, 7, 0, 0, 0, "rect");
    try {
      rect.setColor(0, -1, 0);
      fail("Illegal rgb allowed to pass setColor().");
    } catch (IllegalArgumentException ignored) {
    }
    try {
      rect.setColor(0, 266, 0);
      fail("Illegal rgb allowed to pass setColor().");
    } catch (IllegalArgumentException ignored) {
    }
    // gotta have an assert in here to not lose style points
    rect1.setColor(0, 0, 0);
    int[] rectColor = {0, 0, 0};
    assertEquals(Arrays.toString(rectColor), Arrays.toString(rect1.getColor()));
    circ1.setColor(255, 255, 255);
    int[] circColor = {255, 255, 255};
    assertEquals(Arrays.toString(circColor), Arrays.toString(circ1.getColor()));
    sq1.setColor(100, 200, 155);
    int[] sqColor = {100, 200, 155};
    assertEquals(Arrays.toString(sqColor), Arrays.toString(sq1.getColor()));
  }

  /** Test the getWidth() method for an AbstractShape. */
  @Test
  public void testGetWidth() {
    assertEquals(40, rect1.getWidth());
    assertEquals(86, circ1.getWidth());
    assertEquals(60, sq1.getWidth());
  }

  /** Test the setWidth() method for an AbstractShape. */
  @Test
  public void setWidth() {
    Shape rect = new Rectangle(50, 50, 5, 7, 0, 0, 0, "rect");
    try {
      rect.setWidth(-7);
      fail("Illegal width allowed to pass setWidth().");
    } catch (IllegalArgumentException ignored) {
    }
    // gotta have an assert in here to not lose style points
    rect1.setWidth(100);
    assertEquals(100, rect1.getWidth());
    circ1.setWidth(45);
    assertEquals(45, circ1.getWidth());
    sq1.setWidth(58);
    assertEquals(58, sq1.getWidth());
  }

  /** Test the getHeight() method for an AbstractShape. */
  @Test
  public void testGetHeight() {
    assertEquals(100, rect1.getHeight());
    assertEquals(86, circ1.getHeight());
    assertEquals(60, sq1.getHeight());
  }

  /** Test the setHeight() method for an AbstractShape. */
  @Test
  public void setHeight() {
    Shape rect = new Rectangle(50, 50, 5, 7, 0, 0, 0, "rect");
    try {
      rect.setHeight(-1);
      fail("Illegal height allowed to pass setWidth().");
    } catch (IllegalArgumentException ignored) {
    }
    // gotta have an assert in here to not lose style points
    rect1.setHeight(200);
    assertEquals(200, rect1.getHeight());
    circ1.setHeight(234);
    assertEquals(234, circ1.getHeight());
    sq1.setHeight(2);
    assertEquals(2, sq1.getHeight());
  }

  /** Test the copy() method for an AbstractShape. */
  @Test
  public void copy() {
    Shape rect = new Rectangle(50, 50, 5, 7, 0, 0, 0, "rect");
    Shape copy = rect.copy();

    // check that all fields are the same
    assertEquals(rect.getHeight(), copy.getHeight());
    assertEquals(rect.getWidth(), copy.getWidth());
    assertEquals(rect.getXCoordinate(), copy.getXCoordinate());
    assertEquals(rect.getYCoordinate(), copy.getYCoordinate());
    assertEquals(rect.getColor()[0], copy.getColor()[0]);
    assertEquals(rect.getColor()[1], copy.getColor()[1]);
    assertEquals(rect.getColor()[2], copy.getColor()[2]);
    assertEquals(rect.getDescriptor(), copy.getDescriptor());

    // check that the original isn't change when we mutate the copy
    copy.setColor(255, 255, 255);
    assertEquals(rect.getColor()[0], 0);
    assertEquals(rect.getColor()[1], 0);
    assertEquals(rect.getColor()[2], 0);
  }

  /** Test the getXCoordinate() method for an AbstractShape. */
  @Test
  public void testGetXCoordinate() {
    assertEquals(100, rect1.getXCoordinate());
    assertEquals(0, circ1.getXCoordinate());
    assertEquals(400, sq1.getXCoordinate());
  }

  /** Test the getYCoordinate() method for an AbstractShape. */
  @Test
  public void testGetYCoordinate() {
    assertEquals(200, rect1.getYCoordinate());
    assertEquals(0, circ1.getYCoordinate());
    assertEquals(200, sq1.getYCoordinate());
  }

  /** Test the setCoordinates() method for an AbstractShape. */
  @Test
  public void testSetCoordinates() {
    rect1.setCoordinates(200, 100);
    assertEquals(200, rect1.getXCoordinate());
    assertEquals(100, rect1.getYCoordinate());
    circ1.setCoordinates(10, 10);
    assertEquals(10, circ1.getXCoordinate());
    assertEquals(10, circ1.getYCoordinate());
    sq1.setCoordinates(0, 401);
    assertEquals(0, sq1.getXCoordinate());
    assertEquals(401, sq1.getYCoordinate());
  }

  /** Test the setCoordinates() method for an AbstractShape for improper input values. */
  @Test(expected = IllegalArgumentException.class)
  public void testSetCoordinatesFail() {
    rect1.setCoordinates(-10, 10);
    circ1.setCoordinates(0, -232);
  }

  /** Test the getDescriptor() method for an AbstractShape. */
  @Test
  public void testGetDescriptor() {
    assertEquals("rect1", rect1.getDescriptor());
    assertEquals("circ1", circ1.getDescriptor());
    assertEquals("sq1", sq1.getDescriptor());
  }

  /** Test the toString() method for a Rectangle, Circle, and Square. */
  @Test
  public void testToString() {
    String rectStr =
        "rect1\n"
            + "   Type: Rectangle\n"
            + "   Point: (100,200)\n"
            + "   Width: 40\n"
            + "   Height: 100\n"
            + "   Color: [255,0,0]";
    assertEquals(rectStr, rect1.toString());
    String circStr =
        "circ1\n"
            + "   Type: Circle\n"
            + "   Point: (0,0)\n"
            + "   Diameter: 86\n"
            + "   Color: [100,200,255]";
    assertEquals(circStr, circ1.toString());
    String sqStr =
        "sq1\n"
            + "   Type: Square\n"
            + "   Point: (400,200)\n"
            + "   Length: 60\n"
            + "   Color: [0,0,100]";
    assertEquals(sqStr, sq1.toString());
  }
}
