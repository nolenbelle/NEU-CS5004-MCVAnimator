package cs5004.animator.view;

import cs5004.animator.model.Frame;

import java.awt.*;
import java.util.List;

import javax.swing.*;

/**
 * Representation of an animation using java Graphics. Implements the AnimationView. Extends
 * JPanel.
 */
public class PlaybackViewBeta extends JPanel{
  private GraphicsView view;
  private Timer timer;
  private int time;
  private boolean loop;

  /**
   * Construct a PlaybackView object using a log of events. The log will be parsed into each of the
   * components that make up the animation.
   *
   * @param frames frames
   */
  public PlaybackViewBeta(List<Frame> frames, int speed) {
    view = new GraphicsView(frames,speed);
    timer = view.timer;
    time = view.time;
    this.add(view);
    this.setPreferredSize(new Dimension(1000,1000));
    loop = false;
  }

  /**
   * Empty constructor of a PlaybackView object.
   */
  public PlaybackViewBeta() {
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
    view.time = 0;
  }

  /**
   * Signify to the Controller that we want to loop through the animation when it is completed. The
   * time limit is 50. The method will also make the former frame close;
   *
   * @param frame CompositeFrame to be closed once the next loop starts.
   */
  public void loop(CompositeFrame frame) {
    if (loop) { // if loop is currently on, we must turn it off
      loop = false;
    }
  }

}