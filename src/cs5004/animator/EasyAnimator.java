package cs5004.animator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

import cs5004.animator.controller.AnimationController;
import cs5004.animator.controller.AnimationControllerImpl;
import cs5004.animator.model.AnimationModel;
import cs5004.animator.util.Builder;
import cs5004.animator.view.AnimationView;
import cs5004.animator.view.CompositeFrame;
import cs5004.animator.view.GraphicsView;
import cs5004.animator.view.MyFrame;
import cs5004.animator.view.PlaybackView;
import cs5004.animator.view.SVGView;
import cs5004.animator.view.TextualView;

import static cs5004.animator.util.AnimationReader.parseFile;

/**
 * Effectively the controller, this is the driver for launching an animation.
 */
public final class EasyAnimator {

  /**
   * Given the view type from the user, provide the correct implementation of the view interface.
   *
   * @param type String with the type name
   * @return implementation of View interface
   */
  public static AnimationView viewFactory(String type) {
    // The assignment says to have this
    if (type.equals("visual")) {
      return new GraphicsView();
    }
    if (type.equals("playback")) {
      return new PlaybackView();
    }
    if (type.equals("svg")) {
      return new SVGView();
    } else {
      return new TextualView();
    }
  }

  /**
   * Creates the cs5004.model and populates it with data from the input file.
   *
   * @param input the input given by the user
   * @return a populated AnimationModel
   */
  public static AnimationModel readToModel(Readable input) {
    return parseFile(input, new Builder());
  }

  /**
   * The entry point for the program. Run this program with the desired command line arguments to
   * launch the animation view.
   *
   * @param args command line arguments from the user
   * @throws IOException from the view methods which write to files
   */
  public static void main(String[] args) throws IOException {
    // first I put all my command line args into a list just for convenience
    List<String> argString = new ArrayList<>(Arrays.asList(args));

    // check that the required inputs are present
    if (!(argString.contains("-in") || argString.contains("-view"))) {
      throw new IllegalArgumentException(
          "Must pass at least an -in and a -view through the command" + "line");
    }

    // user input must be converted to Readable type to be passed to the AnimationReader parse
    String input = argString.get(argString.indexOf("-in") + 1);
    Readable readInput = new BufferedReader(new FileReader(input));

    String viewType = argString.get(argString.indexOf("-view") + 1);

    // set default values & update if present in command line
    String output = "System.out"; // default
    if (argString.contains("-out")) {
      output = argString.get(argString.indexOf("-out") + 1);
    }
    int speed = 1; // default
    if (argString.contains("-speed")) {
      speed = Integer.parseInt(argString.get(argString.indexOf("-speed") + 1));
    }

    // populate the cs5004.model with the data that was read from the input files
    AnimationModel model = readToModel(readInput);
    // create the appropriate view type per requested
    AnimationView view = viewFactory(viewType);

    if (viewType.equals("playback")) {
      CompositeFrame viewFrame = new CompositeFrame(model.getEventLog(), speed);
      AnimationController controller = new AnimationControllerImpl(model, viewFrame);
      viewFrame.setListener(controller);

      //start up the animation here
      viewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      viewFrame.setSize(1000,1000);
      viewFrame.setVisible(true);
    }
    if (viewType.equals("visual")) {
      // play the JFrames visual
      new MyFrame(1000, 1000, model.getEventLog(), speed);
    } else { // that the SVG & textual frames
      view.paintComponents(model.getEventLog(), speed, output);
    }
  }
}
