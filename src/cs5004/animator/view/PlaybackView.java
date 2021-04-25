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

public class PlaybackView extends JPanel implements ActionListener {
  private Timer timer;
  private int time = 0;
  private final List<Frame> frames;
  private boolean loop;

  public PlaybackView(List<Frame> frames, int speed) {
    this.frames = frames;
    int delay = 1000 / speed; // so a speed of 2 has half the delay time between timer clicks
    timer = new Timer(delay, this);
    timer.setInitialDelay(0); // start playing right away.
    loop= false;
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
    System.out.println("in paintComp at time = " + time);

    Frame currentFrame = frames.get(time);
    java.util.List<cs5004.animator.model.Shape> shapes = currentFrame.getAllShapes();

    Graphics2D g2d = (Graphics2D) g;
    for (cs5004.animator.model.Shape shape : shapes) {
      g2d.setColor(new Color(shape.getColor()[0], shape.getColor()[1], shape.getColor()[2]));
      if (shape.getClass().equals(Rectangle.class)) {
        g2d.fillRect(
                shape.getXCoordinate(), shape.getYCoordinate(), shape.getWidth(), shape.getHeight());
      }
      if (shape.getClass().equals(Ellipse.class)) {
        g2d.fillOval(
                shape.getXCoordinate(), shape.getYCoordinate(), shape.getWidth(), shape.getHeight());
      }
      System.out.printf("Shape:%s X:%d Y:%d Color[%d,%d,%d] Width:%d Height:%d%n",
              shape.getDescriptor(), shape.getXCoordinate(), shape.getYCoordinate(),
              shape.getColor()[0],shape.getColor()[1],shape.getColor()[2],
              shape.getWidth(), shape.getHeight());
    }
    if (time == frames.size()-1 && loop) {
      this.rewind();
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

  /**
   * Cause the animation to go slower by a factor of 2.
   */
  public void slower() {
    timer.stop();
    timer.setDelay(timer.getDelay() * 2);
    timer.start();
  }

  /**
   * Cause the animation to occure faster by a factor of x2.
   */
  public void faster() {
    timer.stop();
    timer.setDelay(timer.getDelay() / 2);
    timer.start();
  }

  /**
   * Stop the animation at a certain point.
   */
  public void pause() {
    timer.stop();
  }

  /**
   * If the animation is paused, indicated for it to start again from the current point.
   */
  public void play() {
    timer.start();
  }

  /**
   * Restart the entire animation from the beginning.
   */
  public void rewind() {
    time = 0;
    timer.restart();
  }

  /**
   * Signify to the Controller that we want to loop through the animation when it is completed. The
   * time limit is 50. The method will also make the former frame close;
   *
   * @param frame CompositeFrame to be closed once the next loop starts.
   */
  public void loop() {
    if (loop) { // if loop is currently on, we must turn it off
      loop = false;
    } else {
      loop = true;
    }
  }
}
