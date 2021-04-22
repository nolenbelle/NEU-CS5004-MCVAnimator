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
    // Okay so as a button is clicked on from the UI, eventAction will hold the enum of the
    // button which was most recently just clicked. I may change the button type. JList will
    // work but it wont be pretty. But in any case, the controller will have some listener
    // which will receive the num of the button pushed
    if (e.getActionCommand() == "play") {
      System.out.println("play");
    }
    if (e.getActionCommand() == "pause") {
      System.out.println("pause");
    }
    if (e.getActionCommand() == "rewind") {
      System.out.println("play");
    }
    if (e.getActionCommand() == "faster") {
      System.out.println("faster");
    }
    if (e.getActionCommand() == "slower") {
      System.out.println("slower");
    }
     else if (e.getActionCommand() == "loop") {
      System.out.println("loop");
    }
    // here we can do calls for different methods in the viewFrame or in the Playback view
    // depending on what eventAction just happened.


  }
}
