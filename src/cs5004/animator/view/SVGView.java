package cs5004.animator.view;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * View implementation which creates an svg file.
 */
public class SVGView implements AnimationView {
  private List<String> shapeID = new ArrayList<String>();
  private List<List<String>> events = new ArrayList<>();
  private int time = 1;

  @Override
  public void paintComponents(List<String> eventLog, int time, String output) throws IOException {
    this.time = time;
    this.parseEventLog(eventLog);
    this.writeSVGHeader(eventLog);
    this.writeSVGCLoser();

    // when no output give, just write it to system out.
    if (output.equals("System.out")) {
      this.writeToSystem();
    } else { // else write it to an svg file with the given name
      this.writeToFile(output);
    }
  }

  /**
   * Writes the animation details to a given file.
   * @param file the file to write
   * @throws IOException if things go wrong
   */
  private void writeToFile(String file) throws IOException {
    FileWriter f = new FileWriter(file);
    for (List<String> events : this.events) {
      f.write(events.get(0) + "\n");
      int size = events.size();
      int count = 0;
      if (size > 1) {
        for (String event : events) {
          count++;
          if (count !=  size && count != 1) { // doesn't tab the </shape>
            f.write("    " + event + "\n");
          } else if (count != 1) {
            f.write(event + "\n");
          }
        }
      }
    }
    f.close();
  }

  /**
   * Writes the animation details to the system.
   */
  private void writeToSystem() {
    for (List<String> events : this.events) {
      System.out.println(events.get(0));
      int size = events.size();
      int count = 0;
      if (size > 1) {
        for (String event : events) {
          count++;
          if (count !=  size && count != 1) { // doesn't tab the </shape>
            System.out.println("    " + event);
          } else if (count != 1) {
            System.out.println(event);
          }
        }
      }
    }
  }

  /**
   * Sets the svg canvas size.
   * @param eventLog the model event log
   * @return List of integers containing size data
   */
  private List<Integer> getCanvasSize(List<String> eventLog) {
    // if canvas size wasn't set by the time it reaches the view, that should be an error
    String size = eventLog.get(0);
    List<Integer> sizeData = new ArrayList<Integer>();
    Scanner scanner = new Scanner(size);
    while (scanner.hasNext()) {
      if (scanner.hasNextInt()) {
        sizeData.add(scanner.nextInt());
      } else {
        scanner.next();
      }
    }
    return sizeData;
  }

  /**
   * Writes the SVG file to the internal list of events.
   * @param eventLog the event log from the model
   */
  private void writeSVGHeader(List<String> eventLog) {
    List<Integer> sizes = this.getCanvasSize(eventLog);
    int width = sizes.get(2) + sizes.get(0);
    int height = sizes.get(3) + sizes.get(1);
    List<String> header = new ArrayList<String>();
    header.add(String.format("<svg width=\"%d\" height=\"%d\" version=\"1.1\"\n"
            + "     xmlns=\"http://www.w3.org/2000/svg\">\n", width,height));
    this.events.add(0,header);
  }

  /**
   * Writes the SVG close to the internal list of events.
   */
  private void writeSVGCLoser() {
    List<String> tail = new ArrayList<String>();
    tail.add("</svg>\n");
    this.events.add(this.events.size(), tail);
  }

  /**
   * Transforms the model data into SVG data.
   * @param eventLog the model data event list
   */
  private void parseEventLog(List<String> eventLog) {
    for (String item : eventLog) {
      // add all the shapes
      if (item.startsWith("Name")) {
        this.addShape(item);
      }

      // add all the events
      if (!(item.startsWith("Shapes") || item.startsWith("Events") || item.startsWith("Canvas")
              || (item.startsWith("Name")))) {
        this.addEvent(item);
      }
    }
  }

  /**
   * Add a shape to the animation.
   * @param shape the string shape to add
   */
  public void addShape(String shape) {
    // strip the needed starting data from the string
    Scanner scanner = new Scanner(shape);

    scanner.next(); // move past 'Name:'
    String id = scanner.next();
    //System.out.println(String.format("Found id: {%s}", id));

    scanner.next(); // move past 'Type:'
    String type = scanner.next();
    //System.out.println(String.format("Found type: {%s}", type));

    scanner.next(); // move past 'Point:'
    String point = scanner.next();
    // now we have to get the x & y int values from the string
    Scanner pointS = new Scanner(point).useDelimiter(",");
    String xDirty = pointS.next();
    String yDirty = pointS.next();
    String xClean = xDirty.substring(1);
    String yClean = yDirty.substring(0,yDirty.length() - 1);
    int x = Integer.parseInt(xClean);
    int y = Integer.parseInt(yClean);

    scanner.next(); // move past 'Width:'
    int width = scanner.nextInt();

    scanner.next(); // move past 'Height:'
    int height = scanner.nextInt();

    scanner.next(); // move past 'Color:'
    String color = scanner.next();

    // now we have to get the rgb int values from the string in form [r,g,b]
    Scanner colorS = new Scanner(color).useDelimiter(",");
    String rDirty = colorS.next();
    String gClean = colorS.next();
    String bDirty = colorS.next();
    String rClean = rDirty.substring(1);
    String bClean = bDirty.substring(0,bDirty.length() - 1);
    int r = Integer.parseInt(rClean);
    int g = Integer.parseInt(gClean);
    int b = Integer.parseInt(bClean);

    List<String> shapeBlock = new ArrayList<String>();
    if (type.equals("Rectangle")) {
      // hold the ids of our shapes as index look up for
      shapeBlock.add(String.format(
              "<rect id=\"%s\" x=\"%d\" y=\"%d\" width=\"%d\" "
                      + "height=\"%d\" fill=\"rgb(%d,%d,%d)\" visibility=\"visible\" >", id,
              x, y, width, height, r, g, b));
      shapeBlock.add("</rect>\n");
    }
    if (type.equals("Ellipse")) {
      shapeBlock.add(String.format("<ellipse id=\"%s\" cx=\"%d\" cy=\"%d\" rx=\"%d\" ry=\"%d\" "
              + "fill=\"rgb(%d,%d,%d)\" visibility=\"visible\" >", id,
              x, y, width, height, r, g, b));
      shapeBlock.add("</ellipse>\n");
    }
    // add the shape details to the first index of it's list
    this.shapeID.add(id);
    this.events.add(shapeBlock);
  }

  /**
   * Add an event to the animation.
   * @param item the string event to be added
   */
  public void addEvent(String item) {
    Scanner event = new Scanner(item);
    String id = event.next();
    int idIndex = this.shapeID.indexOf(id);

    // everything we need to parse from our event string
    String attribute = null;
    String fromState = null;
    String toState = null;
    int t1 = 0;
    int duration = 0;

    // get the strings of all the needed data
    Scanner scanner = new Scanner(item);
    scanner.next(); // id
    String isOrChanges = scanner.next(); // is/changes
    String animationType = scanner.next(); // moved/'attribute'
    scanner.next(); // from
    String fromData = scanner.next(); // (x,y), x, (r,g,b)
    scanner.next(); // to
    String toData = scanner.next(); // (x,y), x, (r,g,b)
    scanner.next(); // from
    String time1 = scanner.next(); // frame=t1
    scanner.next();
    String time2 = scanner.next(); // frame=t2

    // get the starting time and duration
    Scanner timeS = new Scanner(time1).useDelimiter("=");
    timeS.next(); //frame
    t1 = timeS.nextInt() * 1000 / this.time; // to get it in adjusted ms

    timeS = new Scanner(time2).useDelimiter("=");
    timeS.next(); //frame
    int t2 = timeS.nextInt()  * 1000 / this.time; // to get it in adjusted ms
    duration = (t2 - t1);

    // now we create the different events for different attributes
    if (isOrChanges.equals("changes")) {
      if (animationType.equals("color")) {
        //this.changeColor(item, idIndex);
        attribute = "fill";

        // now we format the rgb data
        Scanner colorS = new Scanner(fromData).useDelimiter(",");
        String rDirty = colorS.next();
        String gClean = colorS.next();
        String bDirty = colorS.next();
        String rClean = rDirty.substring(1);
        String bClean = bDirty.substring(0,bDirty.length() - 1);
        int r1 = Integer.parseInt(rClean);
        int g1 = Integer.parseInt(gClean);
        int b1 = Integer.parseInt(bClean);

        colorS = new Scanner(toData).useDelimiter(",");
        rDirty = colorS.next();
        gClean = colorS.next();
        bDirty = colorS.next();
        rClean = rDirty.substring(1);
        bClean = bDirty.substring(0,bDirty.length() - 1);
        int r2 = Integer.parseInt(rClean);
        int g2 = Integer.parseInt(gClean);
        int b2 = Integer.parseInt(bClean);

        fromState = String.format("rbg(%d,%d,%d)", r1, g1, b1);
        toState = String.format("rbg(%d,%d,%d)", r2, g2, b2);

      } else if (animationType.equals("height")) {
        //this.changeHeight(item, idIndex);
        attribute = "height";
        fromState = fromData;
        toState = toData;
      } else if (animationType.equals("width")) {
        //this.changeWidth(item, idIndex);
        attribute = "width";
        fromState = fromData;
        toState = toData;
      }
      int size = this.events.get(idIndex).size();
      this.events.get(idIndex).add(size - 1,
              this.addAnimation(t1,duration,attribute,fromState,toState));
    } else {
      this.addMove(idIndex, t1, duration, fromData,toData);
    }
  }

  /**
   * Add a specific type of event, a move.
   * @param idIndex the index of the shape ID in the internal list
   * @param t1 the starting time
   * @param duration the moving time
   * @param fromData from coordinates
   * @param toData to coordinates
   */
  public void addMove(int idIndex, int t1, int duration, String fromData, String toData) {
    // now get the values in integer forms
    Scanner pointS = new Scanner(fromData).useDelimiter(",");
    String x1 = pointS.next().substring(1);
    String y1 = pointS.next();
    y1 = y1.substring(0,y1.length() - 1);

    pointS = new Scanner(toData).useDelimiter(",");
    String x2 = pointS.next().substring(1);
    String y2 = pointS.next();
    y2 = y2.substring(0,y2.length() - 1);

    int size = this.events.get(idIndex).size();
    this.events.get(idIndex).add(size - 1,
            this.addAnimation(t1,duration,"x",x1,x2));
    this.events.get(idIndex).add(size - 1,
            this.addAnimation(t1,duration,"y",y1,y2));
  }

  /**
   * Creates the SVG format line for an animation.
   * @param t1 start time
   * @param duration duration of animation
   * @param attribute attribute to change
   * @param from starting value
   * @param to ending value
   * @return String of the animation
   */
  public String addAnimation(int t1, int duration, String attribute, String from, String to) {
    return String.format("<animate attributeType=\"xml\" "
            + "begin=\"%dms\" dur=\"%dms\" attributeName=\"%s\" "
            + "from=\"%s\" to=\"%s\" fill=\"freeze\" />", t1, duration,
            attribute, from, to);
  }
}
