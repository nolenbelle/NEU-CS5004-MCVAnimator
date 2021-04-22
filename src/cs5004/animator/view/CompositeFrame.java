package cs5004.animator.view;

import java.util.List;

import javax.swing.*;

import cs5004.animator.controller.AnimationController;

/**
 * JFrame class to contain the composite view PlaybackView & PlaybackUI.
 *
 * We could also maybe add this all into myFrame?
 */
public class CompositeFrame extends JFrame {
  private PlaybackView view; // can't be AnimationView interface type bc we need it to be a JPanel
  private PlaybackUI ui; // the options for the user
  private JList<ButtonEvent> buttonList; // gotten from the UI -> these are the possible buttons
  private ButtonEvent[] buttons = {ButtonEvent.PLAY, ButtonEvent.PAUSE, ButtonEvent.FASTER,
  ButtonEvent.SLOWER, ButtonEvent.LOOP};

  /**
   * Constructor of the frame to animate in.
   * @param eventLog model event log
   * @param speed speed of play
   */
  public CompositeFrame(List<String> eventLog, int speed) {
    JPanel container = new JPanel();
    container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS)); // stack  our panels
    view = new PlaybackView(); // call however we need to call the new view
    ui = new PlaybackUI();

    container.add(view); // top
    container.add(ui); // bottom

    this.add(container);
  }

  public void setListener(AnimationController controller) {
    ui.setListener(controller);
  }
}
