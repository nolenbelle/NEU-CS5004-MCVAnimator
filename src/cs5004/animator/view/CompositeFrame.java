package cs5004.animator.view;

import java.awt.*;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;

import cs5004.animator.controller.AnimationController;
import cs5004.animator.model.Frame;

/**
 * JFrame class to contain the composite view PlaybackView & PlaybackUI.
 */
public class CompositeFrame extends JFrame implements AnimationView {
  private PlaybackView view; // can't be AnimationView interface type bc we need it to be a JPanel
  private PlaybackUI ui; // the options for the user
  private int speed;

  /**
   * Constructor of the frame to animate in.
   * @param frames model event log
   * @param speed speed of play
   */
  public CompositeFrame(List<Frame> frames, int speed) {
    this.speed = 1000;
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel container = new JPanel();
    container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS)); // stack  our panels
    view = new PlaybackView(frames, speed); // call however we need to call the new view
    ui = new PlaybackUI();


    container.add(view); // top
    container.add(ui); // bottom

    this.add(container);
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

  }
}
