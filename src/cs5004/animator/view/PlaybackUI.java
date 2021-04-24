package cs5004.animator.view;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;

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
  private int loopStatus;
  private int playStatus;

  /**
   * Constructor for the UI which creates and adds all the buttons.
   */
  public PlaybackUI() {
    loopStatus = 0;
    playStatus = 1;
    this.createButtons();
    this.addButtons();
  }

  /**
   * The visual response to be called when the loop is clicked.
   */
  public void clickLoop() {
    Icon loopON = new ImageIcon("resources/loopON.jpg");
    Icon loopOFF = new ImageIcon("resources/loopOFF.jpg");
    if (loopStatus == 0) { // loop is off
      loop.setIcon(loopON);
      loopStatus = 1;
    }
    else { // loop is on
      loop.setIcon(loopOFF);
      loopStatus = 0;
    }
  }

  /**
   * The visual response to be called when the play is clicked.
   */
  public void clickPlay() {
    Icon playON = new ImageIcon("resources/playON.jpg");
    Icon pauseOFF = new ImageIcon("resources/pauseOFF.jpg");
    if (playStatus == 0) { // play is off ie it is paused
      play.setIcon(playON);
      playStatus = 1;
      pause.setIcon(pauseOFF); //toggle
    } // do nothing if already playing.
  }

  /**
   * The visual response to be called when the pause is clicked.
   */
  public void clickPause() {
    Icon pauseON = new ImageIcon("resources/pauseON.jpg");
    Icon playOFF = new ImageIcon("resources/playOFF.jpg");
    if (playStatus == 1) { // play is on ie it is playing
      pause.setIcon(pauseON);
      playStatus = 0;
      play.setIcon(playOFF);
    } // do nothing if already paused
  }

  /**
   * Creates the buttons.
   */
  private void createButtons() {
    Icon rewindI = new ImageIcon("resources/restart.jpg");
    rewind = new JButton(rewindI);
    rewind.setPreferredSize(new Dimension(100,100));
    rewind.setActionCommand("rewind");

    Icon slowerI = new ImageIcon("resources/slower.jpg");
    slower = new JButton(slowerI);
    slower.setPreferredSize(new Dimension(100,100));
    slower.setActionCommand("slower");

    Icon pauseI = new ImageIcon("resources/pauseOFF.jpg");
    pause = new JButton(pauseI);
    pause.setPreferredSize(new Dimension(100,100));
    pause.setActionCommand("pause");

    Icon playI = new ImageIcon("resources/playON.jpg");
    play = new JButton(playI);
    play.setPreferredSize(new Dimension(100,100));
    play.setActionCommand("play");

    Icon fasterI = new ImageIcon("resources/faster.jpg");
    faster = new JButton(fasterI);
    faster.setPreferredSize(new Dimension(100,100));
    faster.setActionCommand("faster");

    Icon loopI = new ImageIcon("resources/loopOFF.jpg");
    loop = new JButton(loopI);
    loop.setPreferredSize(new Dimension(100,100));
    loop.setActionCommand("loop");
  }

  /**
   * Sets the controller as the listener for all of the buttons.
   * @param controller the controller of the project
   */
  public void setListener(AnimationController controller) {
    play.addActionListener((ActionListener) controller);
    pause.addActionListener((ActionListener) controller);
    rewind.addActionListener((ActionListener) controller);
    faster.addActionListener((ActionListener) controller);
    slower.addActionListener((ActionListener) controller);
    loop.addActionListener((ActionListener) controller);
  }

  /**
   * Adds the buttons.
   */
  private void addButtons() {
    this.add(rewind);
    this.add(slower);
    this.add(pause);
    this.add(play);
    this.add(faster);
    this.add(loop);
  }
}
