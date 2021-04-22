package cs5004.animator.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Testing class for the Frame object. I did not test getAllShapes since it is a getter with no
 * errors possible.
 */
public class FrameTest {
  Frame frame1;
  Shape rect;
  Shape circ1;
  Shape sq;

  /** Initialize any Frames and Shapes that will be used during testing. */
  @Before
  public void setUp() {
    rect = new Rectangle(10, 10, 10, 20, 100, 200, 255, "rect");
    circ1 = new Circle(50, 50, 10, 255, 255, 255, "circ");
    sq = new Square(40, 50, 300, 155, 0, 0, "sq");
    frame1 = new FrameImpl();
    frame1.addShape(rect);
    frame1.addShape(circ1);
    frame1.addShape(sq);
  }

  /** Test the addShape() method for a FrameImpl. */
  @Test
  public void addShape() {
    Frame frame = new FrameImpl();
    frame.addShape(new Square(1, 1, 10, 50, 0, 50, "square"));

    try {
      frame.addShape(new Square(1, 1, 10, 50, 0, 50, "differentSquare"));
    } catch (IllegalArgumentException e) {
      fail("Should have been allowed to add a different shape with all the same data.");
    }

    try {
      frame.addShape(new Circle(25, 25, 15, 5, 30, 50, "square"));
      fail("Should not have allowed a non unique unifier even from a different shape class");
    } catch (IllegalArgumentException ignored) {
    }

    // there should just be two squares in the list
    Assert.assertEquals(frame.getAllShapes().size(), 2);
    frame1.addShape(new Square(1, 1, 10, 50, 0, 50, "square1"));
    assertEquals(4, frame1.getAllShapes().size());
  }

  /**
   * Test the addShape() method for a FrameImpl for an improper input. Two Shapes in the same Frame
   * cannot have the same descriptor.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddShapeFail() {
    Shape rect2 = new Rectangle(1, 1, 20, 10, 0, 0, 0, "rect");
    frame1.addShape(rect2);
  }

  /** Test the removeShape() method for a FrameImpl. */
  @Test
  public void removeShape() {
    Frame frame = new FrameImpl();
    frame.addShape(new Circle(45, 45, 10, 1, 2, 3, "circ"));
    frame.addShape(new Rectangle(1, 1, 1, 1, 1, 1, 1, "rect"));
    Shape fake = new Circle(45, 45, 10, 1, 2, 3, "fakeCirc");

    // test that it won't remove the circ when the fake circle is called to remove
    frame.removeShape("fakeCirc");
    Assert.assertEquals(frame.getAllShapes().size(), 2);

    // test valid remove
    frame.removeShape("circ");
    Assert.assertEquals(frame.getAllShapes().size(), 1);
  }

  /** Test the getAllShapes() method for a FrameImpl. */
  @Test
  public void testGetAllShapes() {
    String[] shapeNames = {"rect", "circ", "sq"};
    List<Shape> shapes = frame1.getAllShapes();
    for (int i = 0; i < shapes.size(); i++) {
      assertEquals(shapes.get(i).getDescriptor(), shapeNames[i]);
    }
  }

  /** Test the getShape() method for a FrameImpl. */
  @Test
  public void getShape() {
    Frame frame = new FrameImpl();
    Shape circ = new Circle(45, 45, 10, 1, 2, 3, "circ");
    frame.addShape(circ);

    try {
      frame.getShape("bad");
      fail("Non existing descriptor allowed to pass.");
    } catch (IllegalArgumentException ignored) {
    }

    // test that we are getting the original aka creating an alias not a copy
    Shape newCirc = frame.getShape("circ");
    newCirc.setHeight(100);
    assertEquals(100, frame.getShape("circ").getHeight());
    assertEquals(frame1.getShape("rect"), rect);
    assertEquals(frame1.getShape("circ"), circ1);
    assertEquals(frame1.getShape("sq"), sq);
  }

  /** Test the toString() method for a FrameImpl. */
  @Test
  public void testToString() {
    String str =
        "rect\n"
            + "   Type: Rectangle\n"
            + "   Point: (10,10)\n"
            + "   Width: 10\n"
            + "   Height: 20\n"
            + "   Color: [100,200,255]\n"
            + "circ\n"
            + "   Type: Circle\n"
            + "   Point: (50,50)\n"
            + "   Diameter: 10\n"
            + "   Color: [255,255,255]\n"
            + "sq\n"
            + "   Type: Square\n"
            + "   Point: (40,50)\n"
            + "   Length: 300\n"
            + "   Color: [155,0,0]\n";
    assertEquals(str, frame1.toString());
  }
}
