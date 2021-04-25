package cs5004.animator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cs5004.animator.model.AnimationModel;
import cs5004.animator.view.CompositeFrame;
import cs5004.animator.view.PlaybackUI;
import cs5004.animator.view.PlaybackView;

/**
 * The implementation of our AnimationController which is effectively a listener for the view.
 * The CompositeFrame will set the controller as it's action listener. The controller then responds
 * to which  button was clicked on the screen and tells the compositeFrame's pieces what to
 * do in response to each button.
 */
public class AnimationControllerImpl implements ActionListener, AnimationController {
  private CompositeFrame viewFrame;
  // the view is initiated with the model data which we justified in our HW7

  /**
   * Constructor for the controller which takes the model and the view.
   * @param model the model component
   * @param view the view component
   */
  public AnimationControllerImpl(AnimationModel model, CompositeFrame view) {
    viewFrame = view;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // listening to the buttons from PlaybackUI which is shown in CompositeFrame
    PlaybackUI ui = viewFrame.getUI();
    PlaybackView view = viewFrame.getPlayback();

    if (e.getActionCommand().equals("play")) {
      ui.clickPlay();
      view.play();
    }
    if (e.getActionCommand().equals("pause")) {
      ui.clickPause();
      view.pause();
    }
    if (e.getActionCommand().equals("loop")) {
      ui.clickLoop();
      view.loop();
    }
    if (e.getActionCommand().equals("faster")) {
      view.faster();
    }
    if (e.getActionCommand().equals("slower")) {
      view.slower();
    }
    if (e.getActionCommand().equals("rewind")) { // restart
      view.rewind();
    }
  }
}
