package cs5004.animator.view;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Generates an textual view describing the shapes and events of an animation.
 */
public class TextualView implements AnimationView {

  @Override
  public void paintComponents(List<String> eventLog, int time, String output) throws IOException {
    if (output.equals("System.out")) {
      this.writeToSystem(eventLog);
    }
    else {
      this.writeToFile(eventLog, output);
    }
  }

  /**
   * Writes the events of the eventLog to the system.
   * @param eventLog the events to be written
   */
  private void writeToSystem(List<String> eventLog) {
    StringBuilder result = new StringBuilder();
    for (String event : eventLog) {
      result.append(event);
    }
    System.out.println(result.toString());
  }

  /**
   * Writes the events of the eventLog to the given file.
   * @param eventLog the events to be written
   */
  private void writeToFile(List<String> eventLog, String file) throws IOException {
    FileWriter f = new FileWriter(file);
    for (String events : eventLog) {
      f.write(events);
    }
    f.close();
  }
}
