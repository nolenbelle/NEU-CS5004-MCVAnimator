package cs5004.animator.view;

import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;

import cs5004.animator.model.Frame;

/**
 * This will be used to call GraphicsView through.
 */
public class MyFrame extends JFrame implements AnimationView{
  private GraphicsView visual;

  /**
   * Constructor of the frame to animate in.
   * @param frames model event log
   * @param speed speed of play
   */
  public MyFrame(List<Frame> frames, int speed) {
    visual = new GraphicsView(frames, speed);
  }

  public MyFrame(){

  }

  /**
   * Gets the canvas size from the given event log
   * @param eventLog the model event log
   * @return array of width and height
   */
  private List<Integer> getCanvasSize(List<String> eventLog) {
    // if canvas size wasn't set by the time it reaches the view, that should be an error
    String size = eventLog.get(0);
    List<Integer> sizeData = new ArrayList<Integer>();
    Scanner scanner = new Scanner(size);
    while (scanner.hasNext()) {
      if (scanner.hasNextInt()) {
        sizeData.add(scanner.nextInt());
      } else {
        scanner.next();
      }
    }
    return sizeData;
  }

  @Override
  public void paintComponents(List<String> eventLog, int time, String outPut) throws IOException {
    List<Integer> sizes = this.getCanvasSize(eventLog);
    int width = sizes.get(2) + sizes.get(0);
    int height = sizes.get(3) + sizes.get(1);
    //this.setSize(new Dimension(width,height));
    this.setSize(new Dimension(1000,1000));

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.add(visual);
    this.setVisible(true);

  }
}
