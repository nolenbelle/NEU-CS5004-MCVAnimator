package cs5004.animator.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.*;

/**
 * The composite view which allows for the user to select various play back button while
 * watching the animation from GraphicsView & FrameView. When the controller gets an event from
 * the UI, it will then call on methods here to create those changes. I'm thinking we
 * may need this to have it's own interface
 */
public class PlaybackView extends JPanel implements AnimationView {
  @Override
  public void paintComponent(Graphics g) {

  }

  @Override
  public void paintComponents(List<String> eventLog, int time, String outPut) throws IOException {

  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }
}
