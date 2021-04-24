package cs5004.animator.view;

import java.util.List;
import javax.swing.JFrame;

/**
 * This will be used to call GraphicsView through.
 */
public class MyFrame extends JFrame {
  private GraphicsView graphicsDemo;
  private GraphicsView graphicsView = new GraphicsView();

  /**
   * Constructor of the frame to animate in.
   * @param width canvas width
   * @param height canvas height
   * @param eventLog model event log
   * @param speed speed of play
   */
  public MyFrame(int width, int height, List<String> eventLog, int speed) {
    this.setSize(width,height);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    graphicsDemo = new GraphicsView(eventLog, speed);
    this.add(graphicsDemo);
    this.setVisible(true);

  }

  /**
   * Default constructor.
   */
  public MyFrame() {
    this.setSize(840,840);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.add(graphicsView);
    this.setVisible(true);
  }

}
