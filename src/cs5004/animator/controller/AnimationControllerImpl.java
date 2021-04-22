package cs5004.animator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cs5004.animator.model.AnimationModel;
import cs5004.animator.view.CompositeFrame;

public class AnimationControllerImpl implements ActionListener, AnimationController {
  private CompositeFrame viewFrame; // can't be the interface type
  private AnimationModel model;

  public AnimationControllerImpl(AnimationModel model, CompositeFrame view) {
    viewFrame = view;
    this.model = model;
  }


  @Override
  public void actionPerformed(ActionEvent e) {
    // listening to the buttons from PlaybackUI which is shown in CompositeFrame
    if (e.getActionCommand() == "play") {
      //System.out.println("play");
    }
    if (e.getActionCommand() == "pause") {
      //System.out.println("pause");
    }
    if (e.getActionCommand() == "rewind") {
      //System.out.println("rewind");
    }
    if (e.getActionCommand() == "faster") {
      //System.out.println("faster");
    }
    if (e.getActionCommand() == "slower") {
      //System.out.println("slower");
    }
     else if (e.getActionCommand() == "loop") {
      //System.out.println("loop");
    }
  }
}
