package cs5004.animator.view;

import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

/**
 * Interface used for any type of animation.
 */
public interface AnimationView extends ActionListener {
  /**
   * Shapes that are part of the animation will be drawn. A list of shapes is traversed, and each
   * will be drawn based on its specifications. The Graphics object given as a parameter is
   * converted to a 2-dimension grphics object.
   *
   * @param g Graphics object that allows for drawing on components.
   */
  void paintComponent(Graphics g);

  void paintComponents(List<String> eventLog, int time, String outPut) throws IOException;
}
