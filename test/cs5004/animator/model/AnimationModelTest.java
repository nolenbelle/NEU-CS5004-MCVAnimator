package cs5004.animator.model;

import org.junit.Before;
import org.junit.Test;

import java.io.InvalidClassException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/** Testing class for the AnimationModel implementation. */
public class AnimationModelTest {
  Shape r;
  Shape c;
  Shape s;
  AnimationModel model;

  /** Implement Shapes and a Model to be used while testing. */
  @Before
  public void setUp() {
    r = new Rectangle(20, 20, 10, 15, 5, 10, 5, "rectange1");
    c = new Circle(50, 50, 11, 255, 255, 255, "circle1");
    s = new Square(100, 100, 25, 60, 70, 0, "square1");
    model = new AnimationModelImpl();
    model.setCanvas(0,0,500,500);
  }

  /** Test the fortitude of the event log under multiple changes to elements in the frames. */
  @Test
  public void eventLog() {
    model.addShapeToFrames(r, 50, 70);
    model.addShapeToFrames(c, 0, 50);

    // first checking that  added shapes go in the Shapes block
    assertEquals(
            "Shapes:\n"
                    + "Name: rectange1\n"
                    + "   Type: Rectangle\n"
                    + "   Point: (20,20)\n"
                    + "   Width: 10\n"
                    + "   Height: 15\n"
                    + "   Color: [5,10,5]\n\n"
                    + "Name: circle1\n"
                    + "   Type: Circle\n"
                    + "   Point: (50,50)\n"
                    + "   Diameter: 11\n"
                    + "   Color: [255,255,255]\n"
                    + "\n"
            + "Events:\n",
        model.toString());

    // next check that events go in the event block.
    model.shapeMove(r, 20,20,70, 70, 50, 70);
    assertEquals(
            "Shapes:\n"
                    + "Name: rectange1\n"
                    + "   Type: Rectangle\n"
                    + "   Point: (20,20)\n"
                    + "   Width: 10\n"
                    + "   Height: 15\n"
                    + "   Color: [5,10,5]\n\n"
                    + "Name: circle1\n"
                    + "   Type: Circle\n"
                    + "   Point: (50,50)\n"
                    + "   Diameter: 11\n"
                    + "   Color: [255,255,255]\n"
                    + "\n"
            + "Events:\n"
            + "rectange1 is moved from (20,20) to (70,70) from frame=50 to frame=70\n",
        model.toString());

    // check that an added shape later still goes in the shape block
    model.addShapeToFrames(s, 0, 99); // frames index from 0
    assertEquals(
        "Shapes:\n"
                    + "Name: rectange1\n"
                    + "   Type: Rectangle\n"
                    + "   Point: (20,20)\n"
                    + "   Width: 10\n"
                    + "   Height: 15\n"
                    + "   Color: [5,10,5]\n\n"
                    + "Name: circle1\n"
                    + "   Type: Circle\n"
                    + "   Point: (50,50)\n"
                    + "   Diameter: 11\n"
                    + "   Color: [255,255,255]\n\n"
            + "Name: square1\n"
            + "   Type: Square\n"
            + "   Point: (100,100)\n"
            + "   Length: 25\n"
            + "   Color: [60,70,0]\n"
                + "\n"
            + "Events:\n"
            + "rectange1 is moved from (20,20) to (70,70) from frame=50 to frame=70\n",
        model.toString());

    // check the rest of the events
    model.changeColor(s, 60,70,0,0, 1, 2, 0, 99);
    assertEquals(
        "Shapes:\n"
                + "Name: rectange1\n"
                + "   Type: Rectangle\n"
                + "   Point: (20,20)\n"
                + "   Width: 10\n"
                + "   Height: 15\n"
                + "   Color: [5,10,5]\n\n"
                + "Name: circle1\n"
                + "   Type: Circle\n"
                + "   Point: (50,50)\n"
                + "   Diameter: 11\n"
                + "   Color: [255,255,255]\n\n"
                + "Name: square1\n"
                + "   Type: Square\n"
                + "   Point: (100,100)\n"
                + "   Length: 25\n"
                + "   Color: [60,70,0]\n"
                + "\n"
            + "Events:\n"
            + "rectange1 is moved from (20,20) to (70,70) from frame=50 to frame=70\n"
            + "square1 changes color from [60,70,0] to [0,1,2] from frame=0 to frame=99\n",
        model.toString());

    model.changeHeight(c, 11,1, 49, 50);
    assertEquals(
        "Shapes:\n"
                + "Name: rectange1\n"
                + "   Type: Rectangle\n"
                + "   Point: (20,20)\n"
                + "   Width: 10\n"
                + "   Height: 15\n"
                + "   Color: [5,10,5]\n\n"
                + "Name: circle1\n"
                + "   Type: Circle\n"
                + "   Point: (50,50)\n"
                + "   Diameter: 11\n"
                + "   Color: [255,255,255]\n\n"
                + "Name: square1\n"
                + "   Type: Square\n"
                + "   Point: (100,100)\n"
                + "   Length: 25\n"
                + "   Color: [60,70,0]\n"
                + "\n"
            + "Events:\n"
            + "rectange1 is moved from (20,20) to (70,70) from frame=50 to frame=70\n"
            + "square1 changes color from [60,70,0] to [0,1,2] from frame=0 to frame=99\n"
            + "circle1 changes height from 11 to 1 from frame=49 to frame=50\n",
        model.toString());

    model.changeWidth(r, 10,30, 60, 70);
    assertEquals(
        "Shapes:\n"
                + "Name: rectange1\n"
                + "   Type: Rectangle\n"
                + "   Point: (20,20)\n"
                + "   Width: 10\n"
                + "   Height: 15\n"
                + "   Color: [5,10,5]\n\n"
                + "Name: circle1\n"
                + "   Type: Circle\n"
                + "   Point: (50,50)\n"
                + "   Diameter: 11\n"
                + "   Color: [255,255,255]\n\n"
                + "Name: square1\n"
                + "   Type: Square\n"
                + "   Point: (100,100)\n"
                + "   Length: 25\n"
                + "   Color: [60,70,0]\n"
                + "\n"
            + "Events:\n"
            + "rectange1 is moved from (20,20) to (70,70) from frame=50 to frame=70\n"
            + "square1 changes color from [60,70,0] to [0,1,2] from frame=0 to frame=99\n"
            + "circle1 changes height from 11 to 1 from frame=49 to frame=50\n"
            + "rectange1 changes width from 10 to 30 from frame=60 to frame=70\n",
        model.toString());

    // test that an illegal move throws an error and is not added to the log
    try {
      model.changeHeight(new Square(1, 1, 1, 1, 1, 1, "square2"), 1,2, 1, 2);
      fail("Un-added shape allowed to have it's height changed.");
    } catch (IllegalArgumentException ignored) {
    }
    // nothing should have changed
    assertEquals(
        "Shapes:\n"
                + "Name: rectange1\n"
                + "   Type: Rectangle\n"
                + "   Point: (20,20)\n"
                + "   Width: 10\n"
                + "   Height: 15\n"
                + "   Color: [5,10,5]\n\n"
                + "Name: circle1\n"
                + "   Type: Circle\n"
                + "   Point: (50,50)\n"
                + "   Diameter: 11\n"
                + "   Color: [255,255,255]\n\n"
                + "Name: square1\n"
                + "   Type: Square\n"
                + "   Point: (100,100)\n"
                + "   Length: 25\n"
                + "   Color: [60,70,0]\n"
                + "\n"
            + "Events:\n"
            + "rectange1 is moved from (20,20) to (70,70) from frame=50 to frame=70\n"
            + "square1 changes color from [60,70,0] to [0,1,2] from frame=0 to frame=99\n"
            + "circle1 changes height from 11 to 1 from frame=49 to frame=50\n"
            + "rectange1 changes width from 10 to 30 from frame=60 to frame=70\n",
        model.toString());
  }

  /** Test the addShape() method for an AnimationModelImpl. */
  @Test
  public void addShape() {
    model.addShapeToFrames(r, 0, 99);
    for (int i = 0; i < 100; i++) {
      assertEquals("rectange1", model.getFrames().get(i).getAllShapes().get(0).getDescriptor());
    }
    model.addShapeToFrames(c, 0, 49);
    for (int i = 0; i < 50; i++) {
      assertEquals("circle1", model.getFrames().get(i).getAllShapes().get(1).getDescriptor());
    }
    model.addShapeToFrames(s, 40, 60);
    for (int i = 40; i < 49; i++) {
      assertEquals("square1", model.getFrames().get(i).getAllShapes().get(2).getDescriptor());
    }
    for (int i = 50; i < 61; i++) {
      assertEquals("square1", model.getFrames().get(i).getAllShapes().get(1).getDescriptor());
    }
  }

  /** Test the addShape() method for an AnimationModelImpl for improper Shape inputs. */
  @Test
  public void badAddShape() {
    // cant add a shape with a non unique descriptor
    model.addShapeToFrames(r, 0, 99);
    try {
      Shape badS = new Square(1, 1, 1, 1, 1, 1, "rectange1");
      model.addShapeToFrames(badS, 0, 1);
      fail("duplicate descriptor allowed to pass");
    } catch (IllegalArgumentException ignored) {
      System.out.println("ignored");
    }

    // can't add a shape out of bounds of the frames which default 0-99
    Shape outOfBounds = new Circle(1, 1, 1, 1, 1, 1, "out");
    try {
      model.addShapeToFrames(outOfBounds, -1, 1);
    } catch (IndexOutOfBoundsException ignore) {
      System.out.println("ignored");
    }
  }

  /** Test the shapeMove() method for an AnimationModelImpl. */
  @Test
  public void shapeMove() {
    model.addShapeToLog(r);
    model.addShapeToFrames(r, 0, 99);
    model.addShapeToLog(c);
    model.addShapeToFrames(c, 0, 49);

    // check one time step aka instant instead of gradual move
    model.shapeMove(r, 20,20,30, 40, 0, 1);
    // check that the r in the ending frame is moved
    assertEquals(30, model.getFrames().get(1).getShape("rectange1").getXCoordinate());
    assertEquals(40, model.getFrames().get(1).getShape("rectange1").getYCoordinate());
    // check that the initial frame should not have moved
    assertEquals(20, model.getFrames().get(0).getShape("rectange1").getXCoordinate());
    assertEquals(20, model.getFrames().get(0).getShape("rectange1").getYCoordinate());

    // check two time steps aka gradual move
    model.shapeMove(c, 50,50,100, 150, 10, 12);
    // check that the circle copy of each frame is appropriately moved
    assertEquals(75, model.getFrames().get(11).getShape("circle1").getXCoordinate());
    assertEquals(100, model.getFrames().get(11).getShape("circle1").getYCoordinate());
    assertEquals(100, model.getFrames().get(12).getShape("circle1").getXCoordinate());
    assertEquals(150, model.getFrames().get(12).getShape("circle1").getYCoordinate());
    // check that the first frame's object wasn't changed
    assertEquals(50, model.getFrames().get(10).getShape("circle1").getXCoordinate());
    assertEquals(50, model.getFrames().get(10).getShape("circle1").getYCoordinate());

    // now lets test some bad moves
    // first try and move a shape into frames that is is not in
    try {
      model.shapeMove(c, 50,50,100, 150, 45, 55);
      fail(
          "Error should have been thrown when no matching descriptor was found in"
              + " the frame that the shape isn't in.");
    } catch (IllegalArgumentException ignored) {
    }

    // try moving to a toFrame which is before the fromFrame
    try {
      model.shapeMove(c, 50,50,100, 150, 45, 35);
      fail("IllegalArgument error should have been thrown due to invalid frames");
    } catch (IllegalArgumentException ignored) {
    }

    // try moving to negative frame value
    try {
      model.shapeMove(c, 50,50,100, 150, -1, 35);
      fail("IllegalArgument error should have been thrown due to invalid frames");
    } catch (IllegalArgumentException ignored) {
    }

    // try moving to values outside of the canvas
    try {
      model.shapeMove(c, 50,50,-1, 150, 45, 55);
      fail("IllegalArgument error should have been thrown due to negative x");
    } catch (IllegalArgumentException ignored) {
    }
    try {
      model.shapeMove(c,50,50, 100, 600, 45, 55);
      fail("IllegalArgument error should have been thrown due y too big");
    } catch (IllegalArgumentException ignored) {
    }
  }

  /** Test the changeColor() method for an AnimationModelImpl. */
  @Test
  public void changeColor() {
    /* c = new Circle(50,50,11,255,255,255,"circle1") */
    model.addShapeToLog(c);
    model.addShapeToFrames(c, 0, 49);


    // first check just one time step
    model.changeColor(c, 255,255,255,200, 100, 0, 48, 49);
    assertEquals(200, model.getFrames().get(49).getShape("circle1").getColor()[0]);
    assertEquals(100, model.getFrames().get(49).getShape("circle1").getColor()[1]);
    assertEquals(0, model.getFrames().get(49).getShape("circle1").getColor()[2]);
    // check that the values in the first frame weren't mutated
    assertEquals(255, model.getFrames().get(48).getShape("circle1").getColor()[0]);
    assertEquals(255, model.getFrames().get(48).getShape("circle1").getColor()[1]);
    assertEquals(255, model.getFrames().get(48).getShape("circle1").getColor()[2]);

    // check multi step, gradual change
    model.changeColor(c, 255,255,255,205, 155, 255, 0, 2);
    assertEquals(230, model.getFrames().get(1).getShape("circle1").getColor()[0]);
    assertEquals(205, model.getFrames().get(1).getShape("circle1").getColor()[1]);
    assertEquals(255, model.getFrames().get(1).getShape("circle1").getColor()[2]);
    assertEquals(205, model.getFrames().get(2).getShape("circle1").getColor()[0]);
    assertEquals(155, model.getFrames().get(2).getShape("circle1").getColor()[1]);
    assertEquals(255, model.getFrames().get(2).getShape("circle1").getColor()[2]);
    // check that the values in the first frame weren't mutated
    assertEquals(255, model.getFrames().get(0).getShape("circle1").getColor()[0]);
    assertEquals(255, model.getFrames().get(0).getShape("circle1").getColor()[1]);
    assertEquals(255, model.getFrames().get(0).getShape("circle1").getColor()[2]);

    // now lets test some bad moves
    // first try and change the color in frames that is is not in
    try {
      model.changeColor(c,255,255,255, 100, 150, 0, 0, 99);
      fail(
          "Error should have been thrown when no matching descriptor was found in"
              + " the frame that the shape isn't in.");
    } catch (IndexOutOfBoundsException ignored) {
    }

    // try moving to a toFrame which is before the fromFrame
    try {
      model.changeColor(c, 255,255,255,100, 150, 45, 35, 25);
      fail("IllegalArgument error should have been thrown due to invalid frames");
    } catch (IllegalArgumentException ignored) {
    }

    // try moving to negative frame value
    try {
      model.changeColor(c,255,255,255, 100, 150, 0, 35, -1);
      fail("IllegalArgument error should have been thrown due to invalid frames");
    } catch (IllegalArgumentException ignored) {
    }

    // try to pass illegal r,g,b values
    try {
      model.changeColor(c, 255,255,255,-1, 150, 0, 0, 10);
      fail("RGB values can't be negative.");
    } catch (IllegalArgumentException ignored) {
    }
    try {
      model.changeColor(c, 255,255,255,0, 150, 256, 0, 10);
      fail("RGB values can't be greater than 255.");
    } catch (IllegalArgumentException ignored) {
    }
  }

  /** Test the changeWidth() method for an AnimationModelImpl. */
  @Test
  public void changeWidth() {
    /* r = new Rectangle(20,20,10,15,5,10, 5, "rectange1") */
    model.addShapeToLog(r);
    model.addShapeToFrames(r, 0, 50);


    // check one time step aka instant instead of gradual move
    model.changeWidth(r, 10, 5, 0, 1);
    // check that the r in the ending frame is
    assertEquals(5, model.getFrames().get(1).getShape("rectange1").getWidth());
    // check that the initial frame should not have moved
    assertEquals(10, model.getFrames().get(0).getShape("rectange1").getWidth());

    // check two time steps aka gradual move
    model.changeWidth(r, 10,4, 3, 5);
    // check that the circle copy of each frame is appropriately moved
    assertEquals(7, model.getFrames().get(4).getShape("rectange1").getWidth());
    assertEquals(4, model.getFrames().get(5).getShape("rectange1").getWidth());
    // check that the first frame's object wasn't changed
    assertEquals(10, model.getFrames().get(3).getShape("rectange1").getWidth());

    // now lets test some bad moves
    // first try and change a shape into frames that is is not in
    try {
      model.changeWidth(r, 50,100, 45, 55);
      fail(
          "IllegalArgument error should have been thrown when no matching descriptor was found in"
              + " the frame that the shape isn't in.");
    } catch (IndexOutOfBoundsException ignored) {
    }

    // try changing with a toFrame which is before the fromFrame
    try {
      model.changeWidth(r, 50,100, 45, 35);
      fail("IllegalArgument error should have been thrown due to invalid frames");
    } catch (IllegalArgumentException ignored) {
    }

    // try changing to negative frame value
    try {
      model.changeWidth(r, 50,100, 45, -7);
      fail("IllegalArgument error should have been thrown due to invalid frames");
    } catch (IllegalArgumentException ignored) {
    }

    // try changing to negative value
    try {
      model.changeWidth(r, 50,-100, 45, 100);
      fail("IllegalArgument error should have been thrown due to negative x");
    } catch (IllegalArgumentException ignored) {
    }
  }

  /** Test the changeHeight() method for an AnimationModelImpl. */
  @Test
  public void changeHeight() {
    /* r = new Rectangle(20,20,10,15,5,10, 5, "rectange1") */
    model.addShapeToLog(r);
    model.addShapeToFrames(r, 0, 50);

    // check one time step aka instant instead of gradual move
    model.changeHeight(r, 10,5, 0, 1);
    // check that the r in the ending frame is
    assertEquals(5, model.getFrames().get(1).getShape("rectange1").getHeight());
    // check that the initial frame should not have moved
    assertEquals(10, model.getFrames().get(0).getShape("rectange1").getHeight());

    // check two time steps aka gradual move
    model.changeHeight(r, 10,4, 3, 5);
    // check that the circle copy of each frame is appropriately moved
    assertEquals(7, model.getFrames().get(4).getShape("rectange1").getHeight());
    assertEquals(4, model.getFrames().get(5).getShape("rectange1").getHeight());
    // check that the first frame's object wasn't changed
    assertEquals(10, model.getFrames().get(3).getShape("rectange1").getHeight());

    // now lets test some bad moves
    // first try and change a shape into frames that is is not in
    try {
      model.changeHeight(r, 10,100, 45, 55);
      fail(
          "IllegalArgument error should have been thrown when no matching descriptor was found in"
              + " the frame that the shape isn't in.");
    } catch (IndexOutOfBoundsException ignored) {
    }

    // try changing with a toFrame which is before the fromFrame
    try {
      model.changeHeight(r, 10, 100, 45, 35);
      fail("IllegalArgument error should have been thrown due to invalid frames");
    } catch (IllegalArgumentException ignored) {
    }

    // try changing to negative frame value
    try {
      model.changeHeight(r, 10,100, 45, -7);
      fail("IllegalArgument error should have been thrown due to invalid frames");
    } catch (IllegalArgumentException ignored) {
    }

    // try changing to negative value
    try {
      model.changeHeight(c, 10, -100, 45, 100);
      fail("IllegalArgument error should have been thrown due to negative x");
    } catch (IllegalArgumentException ignored) {
    }
  }

  /** Test the getFrameAtTick() method for an AnimationModelImpl. */
  @Test
  public void getFrameAtTick() throws InvalidClassException {
    model.addShapeToLog(r);
    model.addShapeToFrames(r, 0, 99);
    model.addShapeToLog(c);
    model.addShapeToFrames(c, 0, 49);
    model.addShapeToLog(s);
    model.addShapeToFrames(s, 40, 60);


    assertEquals(2, model.getFrameAtTick(0).getAllShapes().size());
    assertEquals(3, model.getFrameAtTick(45).getAllShapes().size());
    assertEquals(1, model.getFrameAtTick(90).getAllShapes().size());

    assertEquals("rectange1", model.getFrameAtTick(45).getAllShapes().get(0).getDescriptor());
    assertEquals("circle1", model.getFrameAtTick(45).getAllShapes().get(1).getDescriptor());
    assertEquals("square1", model.getFrameAtTick(45).getAllShapes().get(2).getDescriptor());
  }
}
