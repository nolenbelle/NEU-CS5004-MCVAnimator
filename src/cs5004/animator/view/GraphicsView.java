package cs5004.animator.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Timer;
import javax.swing.JPanel;

import cs5004.animator.model.Ellipse;
import cs5004.animator.model.Frame;
import cs5004.animator.model.Rectangle;
import cs5004.animator.model.Shape;

/**
 * Representation of an animation using java Graphics. Implements the AnimationView. Extends JPanel.
 */
public class GraphicsView extends JPanel implements ActionListener {
  Timer timer;
  int time = 0;
  private final List<Frame> frames;

  /**
   * Construct a GraphicsView object using a log of events. The log will be parsed into each of the
   * components that make up the animation.
   *
   * @param frames List of Strings that represents the events in an animation. In the constructor
   *     the eventLog will be parsed into the components necessary to draw the animation.
   */
  public GraphicsView(List<Frame> frames, int speed) {
    this.frames = frames;
    int delay = 1000 / speed; // so a speed of 2 has half the delay time between timer clicks
    timer = new Timer(delay, this);
    timer.setInitialDelay(0); // start playing right away.

    timer.start();
    repaint();
  }

  /**
   * Shapes that are part of the animation will be drawn. A list of shapes is traversed, and each
   * will be drawn based on its specifications. The Graphics object given as a parameter is
   * converted to a 2-dimension graphics object.
   *
   * @param g Graphics object that allows for drawing on components.
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    //System.out.println("in paintComp at time = " + time);

    Frame currentFrame = frames.get(time);
    List<Shape> shapes = currentFrame.getAllShapes();

    Graphics2D g2d = (Graphics2D) g;
    for (Shape shape : shapes) {
      g2d.setColor(new Color(shape.getColor()[0], shape.getColor()[1], shape.getColor()[2]));
      if (shape.getClass().equals(Rectangle.class)) {
        g2d.fillRect(
                shape.getXCoordinate(), shape.getYCoordinate(), shape.getWidth(), shape.getHeight());
      }
      if (shape.getClass().equals(Ellipse.class)) {
        g2d.fillOval(
                shape.getXCoordinate(), shape.getYCoordinate(), shape.getWidth(), shape.getHeight());
      }
//      System.out.printf("Shape:%s X:%d Y:%d Color[%d,%d,%d] Width:%d Height:%d%n",
//              shape.getDescriptor(), shape.getXCoordinate(), shape.getYCoordinate(),
//              shape.getColor()[0],shape.getColor()[1],shape.getColor()[2],
//              shape.getWidth(), shape.getHeight());
    }
  }


  /**
   * Method that performed each time an ActionEvent is heard by the program.
   *
   * @param e ActionEvent heard by the program.
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    time++;
    if (time < frames.size()) {
      repaint();
    }
  }
}
