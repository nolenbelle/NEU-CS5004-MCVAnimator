package cs5004.animator.view;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.*;

import cs5004.animator.controller.AnimationController;
import cs5004.animator.model.Frame;

/**
 * JFrame class to contain the composite view PlaybackView & PlaybackUI.
 */
public class CompositeFrame extends JFrame implements AnimationView {
  private PlaybackView view; // animation panel
  private PlaybackUI ui; // the options for the user

  /**
   * Constructor of the frame to animate in.
   * @param frames model event log
   * @param speed speed of play
   */
  public CompositeFrame(List<Frame> frames, int speed) {
    view = new PlaybackView(frames, speed);
    ui = new PlaybackUI();
  }

  public CompositeFrame() {
  }

  /**
   * Sets the controller as the listener for the button clicks.
   * @param controller the controller
   */
  public void setListener(AnimationController controller) {
    ui.setListener(controller);
  }

  /**
   * Gets the UI component so the controller can have it call it's own methods.
   * @return the buttons ui
   */
  public PlaybackUI getUI() {
    return ui;
  }

  /**
   * Gets the playback view specifically. Ideally it would just get whatever view the controller
   * was using but the controller is pretty coupled to this frame. If we wanted to change that,
   * we could have set the PlayBack view to it's own interface instead of forcing it to be under
   * the giant same AnimationView interface, but having it under the super interface was
   * beneficial for other reasons so that was our trade off.
   * @return the playback view
   */
  public PlaybackView getPlayback() { // this is a place of not great coupling
    return view;
  }

  @Override
  public void paintComponents(List<String> eventLog, int time, String outPut) throws IOException {
    List<Integer> sizes = this.getCanvasSize(eventLog);
    int width = sizes.get(2) + sizes.get(0);
    int height = sizes.get(3) + sizes.get(1);
    view.setSize(new Dimension(width,height));
    view.setPreferredSize(new Dimension(width,height));
    ui.setSize(new Dimension(600,150));
    ui.setPreferredSize(new Dimension(650,100));
    if (width < 650) {
      width = 650;
    }

    JPanel container = new JPanel();
    container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS)); // stack  our panels
    container.add(ui);
    container.add(view);

    container.setSize(new Dimension(width, 150 + height));
    container.setPreferredSize(new Dimension(width, 150 + height));
    this.setSize(new Dimension(width, 150 + height));
    this.setPreferredSize(new Dimension(width, 150 + height));

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.add(container);

//    Container contentPane = getContentPane();
//    contentPane.setLayout(new SpringLayout());
//    contentPane.setSize(new Dimension(width, 150 + height));
//    this.setSize(new Dimension(width, 150 + height));
//    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//    contentPane.add(ui); // bottom
//    contentPane.add(view); // top
//    Component[] components = contentPane.getComponents();
//    SpringLayout layout = (SpringLayout)contentPane.getLayout();;
//    Spring yPad = Spring.constant(5);
//    Spring XSpring = Spring.constant(5);
//    Spring YSpring = yPad;
//
//// Make every component 5 pixels away from the component to its left.
//    for (int i = 0; i < components.length; i++) {
//      SpringLayout.Constraints cons = layout.getConstraints(components[i]);
//      cons.setY(YSpring);
//      YSpring = Spring.sum(yPad, cons.getConstraint("North"));
//      cons.setX(XSpring);
//    }

    this.setVisible(true);
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
}
