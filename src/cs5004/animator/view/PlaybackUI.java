package cs5004.animator.view;

import java.awt.event.ActionListener;

import javax.swing.*;

import cs5004.animator.controller.AnimationController;

/**
 * Creates the JFrame GUI for the animation buttons during an animation.
 * Required user buttons:
 * - Start, pause, resume, and restart (i.e. rewind to beginning and start the animation again)
 *   the animation with explicit user input (e.g. key press, button click, etc).
 * - Enable/disable looping: enabling looping should cause the animation to automatically restart
 *   once it reaches the end.
 * - Increase or decrease the speed of the animation, as it is being played, and immediately
 *   see the results.
 */
public class PlaybackUI extends JPanel {
  private JButton play;
  private JButton pause;
  private JButton rewind;
  private JButton faster;
  private JButton slower;
  private JButton loop;

  public PlaybackUI(){
    this.createButtons();
    this.addButtons();
  }

  private void createButtons() {
    play = new JButton("play");
    play.setActionCommand("play");
    pause = new JButton("pause");
    pause.setActionCommand("pause");
    rewind = new JButton("rewind");
    rewind.setActionCommand("rewind");
    faster = new JButton("faster");
    faster.setActionCommand("faster");
    slower = new JButton("slower");
    slower.setActionCommand("slower");
    loop = new JButton("loop");
    loop.setActionCommand("loop");
  }

  public void setListener(AnimationController controller) {
    play.addActionListener((ActionListener) controller);
    pause.addActionListener((ActionListener) controller);
    rewind.addActionListener((ActionListener) controller);
    faster.addActionListener((ActionListener) controller);
    slower.addActionListener((ActionListener) controller);
    loop.addActionListener((ActionListener) controller);
  }

  private void addButtons() {
    this.add(play);
    this.add(pause);
    this.add(rewind);
    this.add(faster);
    this.add(slower);
    this.add(loop);
  }
}
