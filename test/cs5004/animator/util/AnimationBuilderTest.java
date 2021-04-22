package cs5004.animator.util;

import org.junit.Before;
import org.junit.Test;

import cs5004.animator.model.AnimationModel;
import static org.junit.Assert.assertEquals;

/**
 * Testing class for the implementation of the AnimationBuilder adaptor.
 */
public class AnimationBuilderTest {
  AnimationBuilder builder;

  @Before
  public void setUp() {
    builder = new Builder();
  }

  @Test
  public void build() {
    //check that this returns an empty model
    assertEquals("Shapes:\n" +
            "Events:\n", builder.build().toString());

    builder.setBounds(0,0,500,500);

    AnimationModel model = (AnimationModel) builder.build();
    int[] canvas = model.getCanvas();
    assertEquals(0,canvas[0]);
    assertEquals(0,canvas[1]);
    assertEquals(500,canvas[2]);
    assertEquals(500,canvas[3]);

    builder.declareShape("rectangle", "rectangle");

    // check that a default shape (not initialized so all values should be -1) was added
    assertEquals("Shapes:\n" +
            "Name: rectangle\n" +
            "   Type: Rectangle\n" +
            "   Point: (-1,-1)\n" +
            "   Width: -1\n" +
            "   Height: -1\n" +
            "   Color: [-1,-1,-1]\n" +
            "\n" +
            "Events:\n", builder.build().toString());

    builder.addMotion("rectangle",0,0,0,10,15,255,255,255,
            0,0,0,10,15,255,255,255);

    //check that initializing the data works
    assertEquals("Shapes:\n" +
            "Name: rectangle\n" +
            "   Type: Rectangle\n" +
            "   Point: (0,0)\n" +
            "   Width: 10\n" +
            "   Height: 15\n" +
            "   Color: [255,255,255]\n" +
            "\n" +
            "Events:\n", builder.build().toString());

    builder.addMotion("rectangle",0,0,0,10,15,255,255,255,
            10,5,5,20,30,0,0,0);
    //check that each type of move gets added to the events
    assertEquals("Shapes:\n" +
            "Name: rectangle\n" +
            "   Type: Rectangle\n" +
            "   Point: (0,0)\n" +
            "   Width: 10\n" +
            "   Height: 15\n" +
            "   Color: [255,255,255]\n" +
            "\n" +
            "Events:\n" +
            "rectangle is moved from (0,0) to (5,5) from frame=0 to frame=10\n"
            + "rectangle changes color from [255,255,255] to [0,0,0] from frame=0 to frame=10\n"
            + "rectangle changes width from 10 to 20 from frame=0 to frame=10\n"
            + "rectangle changes height from 15 to 30 from frame=0 to frame=10\n",
            builder.build().toString());

    //check adding a shape later
    builder.declareShape("ellipse", "ellipse");
    assertEquals("Shapes:\n"
                    + "Name: rectangle\n"
                    + "   Type: Rectangle\n"
                    + "   Point: (0,0)\n"
                    + "   Width: 10\n"
                    + "   Height: 15\n"
                    + "   Color: [255,255,255]\n\n"
                    + "Name: ellipse\n"
                    + "   Type: Ellipse\n"
                    + "   Point: (-1,-1)\n"
                    + "   Width: -1\n"
                    + "   Height: -1\n"
                    + "   Color: [-1,-1,-1]\n\n"
                    + "Events:\n"
                    + "rectangle is moved from (0,0) to (5,5) from frame=0 to frame=10\n"
              + "rectangle changes color from [255,255,255] to [0,0,0] from frame=0 to frame=10\n"
                    + "rectangle changes width from 10 to 20 from frame=0 to frame=10\n"
                    + "rectangle changes height from 15 to 30 from frame=0 to frame=10\n",
            builder.build().toString());

    //check initializing the later shape
    builder.addMotion("ellipse",0,10,10,10,15,255,255,255,
            0,10,10,10,15,255,255,255);
    assertEquals("Shapes:\n"
                    + "Name: rectangle\n"
                    + "   Type: Rectangle\n"
                    + "   Point: (0,0)\n"
                    + "   Width: 10\n"
                    + "   Height: 15\n"
                    + "   Color: [255,255,255]\n\n"
                    + "Name: ellipse\n"
                    + "   Type: Ellipse\n"
                    + "   Point: (10,10)\n"
                    + "   Width: 10\n"
                    + "   Height: 15\n"
                    + "   Color: [255,255,255]\n\n"
                    + "Events:\n"
                    + "rectangle is moved from (0,0) to (5,5) from frame=0 to frame=10\n"
                    + "rectangle changes color from [255,255,255] to [0,0,0] from frame=0" +
                    " to frame=10\n"
                    + "rectangle changes width from 10 to 20 from frame=0 to frame=10\n"
                    + "rectangle changes height from 15 to 30 from frame=0 to frame=10\n",
            builder.build().toString());

    // test every move for ellipse
    builder.addMotion("ellipse",0,10,10,10,15,255,255,255,
            20,30,30,15,10,0,25,25);
    assertEquals("Shapes:\n"
                    + "Name: rectangle\n"
                    + "   Type: Rectangle\n"
                    + "   Point: (0,0)\n"
                    + "   Width: 10\n"
                    + "   Height: 15\n"
                    + "   Color: [255,255,255]\n\n"
                    + "Name: ellipse\n"
                    + "   Type: Ellipse\n"
                    + "   Point: (10,10)\n"
                    + "   Width: 10\n"
                    + "   Height: 15\n"
                    + "   Color: [255,255,255]\n\n"
                    + "Events:\n"
                    + "rectangle is moved from (0,0) to (5,5) from frame=0 to frame=10\n"
                    + "rectangle changes color from [255,255,255] to [0,0,0] from frame=0 " +
                    "to frame=10\n"
                    + "rectangle changes width from 10 to 20 from frame=0 to frame=10\n"
                    + "rectangle changes height from 15 to 30 from frame=0 to frame=10\n"
            + "ellipse is moved from (10,10) to (30,30) from frame=0 to frame=20\n"
              + "ellipse changes color from [255,255,255] to [0,25,25] from frame=0 to frame=20\n"
                    + "ellipse changes width from 10 to 15 from frame=0 to frame=20\n"
                    + "ellipse changes height from 15 to 10 from frame=0 to frame=20\n",
            builder.build().toString());
  }
}