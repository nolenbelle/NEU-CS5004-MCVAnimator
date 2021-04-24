package cs5004.animator.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.Timer;
import javax.swing.JPanel;

import cs5004.animator.model.Circle;
import cs5004.animator.model.Ellipse;
import cs5004.animator.model.Rectangle;
import cs5004.animator.model.Shape;

/**
 * Representation of an animation using java Graphics. Implements the AnimationView. Extends JPanel.
 */
public class GraphicsView extends JPanel implements AnimationView {
  private List<String> shapes = new ArrayList<>();
  private List<String> events = new ArrayList<>();
  // private List<Shape> shapeList = new ArrayList<>();
  private Map<String, Shape> nameToShape = new HashMap<>();
  Timer timer;// = new Timer(1000, this);
  private int time = 0;

  /**
   * Construct a GraphicsView object using a log of events. The log will be parsed into each of the
   * components that make up the animation.
   *
   * @param eventLog List of Strings that represents the events in an animation. In the constructor
   *     the eventLog will be parsed into the components necessary to draw the animation.
   */
  public GraphicsView(List<String> eventLog, int speed) {
    timer = new Timer(speed, this);
    this.parseEventLog(eventLog);
    System.out.println(eventLog.toString());

    this.setLayout(null);
    timer.start();
  }

  /** Empty constructor of a GraphicsView object. */
  public GraphicsView() {
    timer = new Timer(1, this);
    this.setLayout(null);
    timer.start();
  }

  /**
   * Shapes that are part of the animation will be drawn. A list of shapes is traversed, and each
   * will be drawn based on its specifications. The Graphics object given as a parameter is
   * converted to a 2-dimension graphics object.
   *
   * @param g Graphics object that allows for drawing on components.
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    this.setBackground(new Color(50, 255, 255));

    Graphics2D g2d = (Graphics2D) g;

    for (Shape shape : nameToShape.values()) {
      if (shape.getClass().equals(Rectangle.class)) {
        g2d.setColor(new Color(shape.getColor()[0], shape.getColor()[1], shape.getColor()[2]));
        g2d.fillRect(
            shape.getXCoordinate(), shape.getYCoordinate(), shape.getWidth(), shape.getHeight());
      }
      if (shape.getClass().equals(Circle.class)) {
        g2d.setColor(new Color(shape.getColor()[0], shape.getColor()[1], shape.getColor()[2]));
        g2d.fillOval(
            shape.getXCoordinate(), shape.getYCoordinate(), shape.getWidth(), shape.getHeight());
      }
    }
  }

  @Override
  public void paintComponents(List<String> eventLog, int time, String outPut) throws IOException {
    throw new UnsupportedOperationException("This method is not meant for this implementation.");
  }

  /**
   * Method that performed each time an ActionEvent is heard by the program.
   *
   * @param e ActionEvent heard by the program.
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    // System.out.println("Action - " + e.toString());
    time++;
    // System.out.println("Time: " + time);
    for (int i = 0; i < events.size(); i++) {
      String ev = events.get(i);
      int end = getEventEndTime(ev);
      if (time <= end) {
        // System.out.println("DO THIS - " + ev);
        for (Shape shape : nameToShape.values()) {
          if (shape.getDescriptor().equals(getEventShapeName(ev))) {
            // System.out.println(shape);
            if (ev.contains("moved")) {
              int oldX = shape.getXCoordinate();
              int oldY = shape.getYCoordinate();
              int newX = getEventX(ev);
              int newY = getEventY(ev);
              if (shape.getClass().equals(Rectangle.class)
                      ||shape.getClass().equals(Ellipse.class)) {
                // if (shape.getClass().equals(Rectangle.class)) {
                moveRectangle(
                    shape,
                    oldX,
                    oldY,
                    newX,
                    newY,
                    getEventStartTime(ev),
                    getEventEndTime(ev),
                    time);
              } else if (shape.getClass().equals(Circle.class)) {
                moveCircle(
                    shape,
                    oldX,
                    oldY,
                    newX,
                    newY,
                    getEventStartTime(ev),
                    getEventEndTime(ev),
                    time);
              }
            }
            if (ev.contains("changes color")) {
              int oldR = shape.getColor()[0];
              int oldG = shape.getColor()[1];
              int oldB = shape.getColor()[2];
              int newR = getEventR(ev);
              int newG = getEventG(ev);
              int newB = getEventB(ev);
              if (shape.getClass().equals(Rectangle.class)
              || shape.getClass().equals(Ellipse.class)) {
                // if (shape.getClass().equals(Rectangle.class)) {
                changeColorRectangle(
                    shape,
                    oldR,
                    oldG,
                    oldB,
                    newR,
                    newG,
                    newB,
                    getEventStartTime(ev),
                    getEventEndTime(ev),
                    time);
              } else if (shape.getClass().equals(Circle.class)) {
                changeColorCircle(
                    shape,
                    oldR,
                    oldG,
                    oldB,
                    newR,
                    newG,
                    newB,
                    getEventStartTime(ev),
                    getEventEndTime(ev),
                    time);
              }
            }

            if (ev.contains("width")) {
              int oldW = shape.getWidth();
              int newW = getEventWidth(ev);
              if (shape.getClass().equals(Rectangle.class)
                      || shape.getClass().equals(Ellipse.class)) {
                changeWidthRectangle(
                    shape, oldW, newW, getEventStartTime(ev), getEventEndTime(ev), time);
              } else if (shape.getClass().equals(Circle.class)) {
                changeWidthCircle(
                    shape, oldW, newW, getEventStartTime(ev), getEventEndTime(ev), time);
              }
            }

            if (ev.contains("height")) {
              int oldH = shape.getHeight();
              int newH = getEventHeight(ev);
              if (shape.getClass().equals(Rectangle.class)
                      || shape.getClass().equals(Ellipse.class)) {
                changeHeightRectangle(
                    shape, oldH, newH, getEventStartTime(ev), getEventEndTime(ev), time);
              } else if (shape.getClass().equals(Circle.class)) {
                changeHeightCircle(
                    shape, oldH, newH, getEventStartTime(ev), getEventEndTime(ev), time);
              }
            }
          }
        }
      }
    }
  }

  /**
   * Move a Rectangle from one point to an other during the animation. Rectangle is moved using
   * Tweening, * and repainted every time the time changes in the animation.
   *
   * @param shape Rectangle to be moved.
   * @param oldX X-Coordinate of the original point on the Rectangle. (Where the Rectangle came
   *     from).
   * @param oldY Y-Coordinate of the original point on the Rectangle. (Where the Rectangle came
   *     from).
   * @param newX X-Coordinate of the new point on the Rectangle. (Where the Rectangle is going).
   * @param newY Y-Coordinate of the new point on the Rectangle. (Where the Rectangle is going).
   * @param start Time at which the Rectangle starts to move.
   * @param end Time at which the Rectangle reaches its destination.
   * @param time Current time of the animation.
   */
  private void moveRectangle(
      Shape shape, int oldX, int oldY, int newX, int newY, int start, int end, int time) {
    double nextX = tweening((double) oldX, (double) newX, start, end, time);
    double nextY = tweening((double) oldY, (double) newY, start, end, time);
    if (shape.getClass().equals(Rectangle.class)) {
      Rectangle r =
              new Rectangle(
                      (int) nextX,
                      (int) nextY,
                      shape.getWidth(),
                      shape.getHeight(),
                      shape.getColor()[0],
                      shape.getColor()[1],
                      shape.getColor()[2],
                      shape.getDescriptor());
      nameToShape.put(shape.getDescriptor(), r);
    }
    else {
      Ellipse e =
              new Ellipse(
                      (int) nextX,
                      (int) nextY,
                      shape.getWidth(),
                      shape.getHeight(),
                      shape.getColor()[0],
                      shape.getColor()[1],
                      shape.getColor()[2],
                      shape.getDescriptor());
      nameToShape.put(shape.getDescriptor(), e);
    }
    // System.out.println("\t" + nameToShape.get(shape.getDescriptor()));
    repaint();
  }

  /**
   * Move a Circle from one point to an other during the animation. Circle is moved using tweening,
   * and repainted every time the time changes in the animation.
   *
   * @param shape Circle to be moved.
   * @param oldX X-Coordinate of the original point on the Circle. (Where the Circle came from).
   * @param oldY Y-Coordinate of the original point on the Circle. (Where the Circle came from).
   * @param newX X-Coordinate of the new point on the Circle. (Where the Circle is going).
   * @param newY Y-Coordinate of the new point on the Circle. (Where the Circle is going).
   * @param start Time at which the Circle starts to move.
   * @param end Time at which the Circle reaches its destination.
   * @param time Current time of the animation.
   */
  private void moveCircle(
      Shape shape, int oldX, int oldY, int newX, int newY, int start, int end, int time) {
    double nextX = tweening((double) oldX, (double) newX, start, end, time);
    double nextY = tweening((double) oldY, (double) newY, start, end, time);

    Circle c =
        new Circle(
            (int) nextX,
            (int) nextY,
            shape.getWidth(),
            shape.getColor()[0],
            shape.getColor()[1],
            shape.getColor()[2],
            shape.getDescriptor());
    nameToShape.put(shape.getDescriptor(), c);
    repaint();
  }

  /**
   * Color of a Rectangle is changed. Color is changed using tweening, and repainted every time the
   * time changes in the animation.
   *
   * @param shape Rectangle whose color is to be changed.
   * @param oldR Original red value of the Rectangle.
   * @param oldG Original green value of the Rectangle.
   * @param oldB Original blue value of the Rectangle.
   * @param newR New red value of the Rectangle.
   * @param newG New green value of the Rectangle.
   * @param newB New blue value of the Rectangle.
   * @param start Time at which the Rectangle starts to change color.
   * @param end Time at which the Rectangle gets to its new color.
   * @param time Current time of the animation.
   */
  private void changeColorRectangle(
      Shape shape,
      int oldR,
      int oldG,
      int oldB,
      int newR,
      int newG,
      int newB,
      int start,
      int end,
      int time) {
    double nextR = tweening((double) oldR, (double) newR, start, end, time);
    double nextG = tweening((double) oldG, (double) newG, start, end, time);
    double nextB = tweening((double) oldB, (double) newB, start, end, time);
    if (nextR > 255) {
      nextR = 255;
    }
    if (nextG > 255) {
      nextG = 255;
    }
    if (nextB > 255) {
      nextB = 255;
    }
    if (shape.getClass().equals(Rectangle.class)) {
      Rectangle r =
              new Rectangle(
                      shape.getXCoordinate(),
                      shape.getYCoordinate(),
                      shape.getWidth(),
                      shape.getHeight(),
                      (int) nextR,
                      (int) nextG,
                      (int) nextB,
                      shape.getDescriptor());
      nameToShape.put(shape.getDescriptor(), r);
    } else if (shape.getClass().equals(Ellipse.class)) {
      Ellipse e =
              new Ellipse(
                      shape.getXCoordinate(),
                      shape.getYCoordinate(),
                      shape.getWidth(),
                      shape.getHeight(),
                      (int) nextR,
                      (int) nextG,
                      (int) nextB,
                      shape.getDescriptor());
      nameToShape.put(shape.getDescriptor(), e);
    }
    repaint();
  }

  /**
   * Color of the Circle is changed. Color is changed using tweening, and repainted every time the
   * time changes in the animation.
   *
   * @param shape Circle whose color is to be changed.
   * @param oldR Original red value of the Circle.
   * @param oldG Original green value of the Circle.
   * @param oldB Original blue value of the Circle.
   * @param newR New red value of the Circle.
   * @param newG New green value of the Circle.
   * @param newB New blue value of the Circle.
   * @param start Time at which the Circle starts to change color.
   * @param end Time at which the Circle gets to its new color.
   * @param time Current time of the animation.
   */
  private void changeColorCircle(
      Shape shape,
      int oldR,
      int oldG,
      int oldB,
      int newR,
      int newG,
      int newB,
      int start,
      int end,
      int time) {
    double nextR = tweening((double) oldR, (double) newR, start, end, time);
    double nextG = tweening((double) oldG, (double) newG, start, end, time);
    double nextB = tweening((double) oldB, (double) newB, start, end, time);
    if (nextR > 255) {
      nextR = 255;
    }
    if (nextG > 255) {
      nextG = 255;
    }
    if (nextB > 255) {
      nextB = 255;
    }
    Circle c =
        new Circle(
            shape.getXCoordinate(),
            shape.getYCoordinate(),
            shape.getWidth(),
            (int) nextR,
            (int) nextG,
            (int) nextB,
            shape.getDescriptor());
    nameToShape.put(shape.getDescriptor(), c);
    repaint();
  }

  /**
   * Change the width of a Rectangle. Width is changed using tweening, and repainted every time the
   * time changes in the animation.
   *
   * @param shape Rectangle whose width is to be changed.
   * @param oldW Original width of the Rectangle.
   * @param newW New width of the Rectangle.
   * @param start Time at which the Rectangle starts to change width.
   * @param end Time at which the Rectangle reaches its desired width.
   * @param time Current time of the animation.
   */
  private void changeWidthRectangle(Shape shape, int oldW, int newW, int start, int end, int time) {
    double nextW = tweening((double) oldW, (double) newW, start, end, time);
    if (shape.getClass().equals(Rectangle.class)) {
      Rectangle r =
              new Rectangle(
                      shape.getXCoordinate(),
                      shape.getYCoordinate(),
                      (int) nextW,
                      shape.getHeight(),
                      shape.getColor()[0],
                      shape.getColor()[1],
                      shape.getColor()[2],
                      shape.getDescriptor());
      nameToShape.put(shape.getDescriptor(), r);
    } else if (shape.getClass().equals(Ellipse.class)) {
      Ellipse e =
              new Ellipse(
                      shape.getXCoordinate(),
                      shape.getYCoordinate(),
                      (int) nextW,
                      shape.getHeight(),
                      shape.getColor()[0],
                      shape.getColor()[1],
                      shape.getColor()[2],
                      shape.getDescriptor());
      nameToShape.put(shape.getDescriptor(), e);
    }
    repaint();
  }

  /**
   * Change the height of a Rectangle. Height is changed using tweening, and repainted every time
   * the time changes in the animation.
   *
   * @param shape Rectangle whose height is to be changed.
   * @param oldH Original height of the Rectangle.
   * @param newH New height of the Rectangle.
   * @param start Time at which the Rectangle starts to change height.
   * @param end Time at which the Rectangle reaches its desired height.
   * @param time Current time of the animation.
   */
  private void changeHeightRectangle(
      Shape shape, int oldH, int newH, int start, int end, int time) {
    double nextH = tweening((double) oldH, (double) newH, start, end, time);
    if (shape.getClass().equals(Rectangle.class)) {
      Rectangle r =
              new Rectangle(
                      shape.getXCoordinate(),
                      shape.getYCoordinate(),
                      (int) nextH,
                      shape.getHeight(),
                      shape.getColor()[0],
                      shape.getColor()[1],
                      shape.getColor()[2],
                      shape.getDescriptor());
      nameToShape.put(shape.getDescriptor(), r);
    } else if (shape.getClass().equals(Ellipse.class)) {
      Ellipse e =
              new Ellipse(
                      shape.getXCoordinate(),
                      shape.getYCoordinate(),
                      (int) nextH,
                      shape.getHeight(),
                      shape.getColor()[0],
                      shape.getColor()[1],
                      shape.getColor()[2],
                      shape.getDescriptor());
      nameToShape.put(shape.getDescriptor(), e);
    }
    repaint();
  }

  /**
   * Change the width of a Circle. The width of a Circle corresponds to its diameter. Width is
   * changed using tweening, and repainted every time the time changes in the animation.
   *
   * @param shape Circle whose width/diameter is to be changed.
   * @param oldD Original width/diameter of the Circle.
   * @param newD New width/diameter of the Circle.
   * @param start Time at which the Circle starts to change width/diameter.
   * @param end Time at which the Circle reaches its desired width/diameter.
   * @param time Current time of the animation.
   */
  private void changeWidthCircle(Shape shape, int oldD, int newD, int start, int end, int time) {
    double nextD = tweening((double) oldD, (double) newD, start, end, time);
    Circle r =
        new Circle(
            shape.getXCoordinate(),
            shape.getYCoordinate(),
            (int) nextD,
            shape.getColor()[0],
            shape.getColor()[1],
            shape.getColor()[2],
            shape.getDescriptor());
    nameToShape.put(shape.getDescriptor(), r);
    repaint();
  }

  /**
   * Change the height of a Circle. The height of a Circle corresponds to its diameter. Height is
   * changed using tweening, and repainted every time the time changes in the animation.
   *
   * @param shape Cicrlce whose height/diameter is to be changed.
   * @param oldD Original height/diameter of the Circle.
   * @param newD New height/diameter of the Circle
   * @param start Time at which the Circle starts to change height/diameter.
   * @param end Time at which the Circle reaches its desired height/diamter.
   * @param time Current time of the animation.
   */
  private void changeHeightCircle(Shape shape, int oldD, int newD, int start, int end, int time) {
    double nextD = tweening((double) oldD, (double) newD, start, end, time);
    Circle r =
        new Circle(
            shape.getXCoordinate(),
            shape.getYCoordinate(),
            (int) nextD,
            shape.getColor()[0],
            shape.getColor()[1],
            shape.getColor()[2],
            shape.getDescriptor());
    nameToShape.put(shape.getDescriptor(), r);
    repaint();
  }

  /**
   * Get the time at which an event begins.
   *
   * @param event String representation of an event.
   * @return Integer value of the time at which the event begins.
   */
  private int getEventStartTime(String event) {
    String timeStrLong = event.substring(event.indexOf('=') + 1);
    String timeStr = timeStrLong.substring(0, timeStrLong.indexOf(' '));
    // System.out.println(timeStr);
    int time = Integer.parseInt(timeStr);
    return time;
  }

  /**
   * Get the time at which an event ends.
   *
   * @param event String representation of an event.
   * @return Integer value of the time at which the even ends.
   */
  private int getEventEndTime(String event) {
    String timeStr = event.substring(event.lastIndexOf('=') + 1, event.length() - 1);
    int time = Integer.parseInt(timeStr);
    return time;
  }

  /**
   * Get the name of the shape that is involved in an event.
   *
   * @param event String representation of an event.
   * @return Name of the shape the is part of the event.
   */
  private String getEventShapeName(String event) {
    String name = event.substring(0, event.indexOf(' '));
    return name;
  }

  /**
   * If the event corresponds to a shape moving position, get the x-coordinate that the shape is
   * moving to.
   *
   * @param event String representation of an event.
   * @return X-Coordinate of the point that the shape is moving to.
   */
  private int getEventX(String event) {
    String xStr = event.substring(event.indexOf("to"));
    xStr = xStr.substring(xStr.indexOf(' ') + 2, xStr.indexOf(','));
    int x = Integer.parseInt(xStr);
    return x;
  }

  /**
   * If the event corresponds to a shape moving position, get the y-coordinate that the shape is
   * moving to.
   *
   * @param event String representation of an event.
   * @return Y-Coordinate of the point that the shape is moving to.
   */
  private int getEventY(String event) {
    String yStr = event.substring(event.indexOf("to"));
    yStr = yStr.substring(yStr.indexOf(',') + 1, yStr.indexOf(')'));
    int y = Integer.parseInt(yStr);
    return y;
  }

  /**
   * If the event corresponds to a color change, get the red value of the new color.
   *
   * @param event String representation of an event.
   * @return Red value of the new color.
   */
  private int getEventR(String event) {
    String rStr = event.substring(event.indexOf("to"));
    rStr = rStr.substring(rStr.indexOf('[') + 1, rStr.indexOf(','));
    // System.out.println("rStr: " + rStr);
    int r = Integer.parseInt(rStr);
    return r;
  }

  /**
   * If the event corresponds to a color change, get the green value of the new color.
   *
   * @param event String representation of an event.
   * @return Green value of the new color.
   */
  private int getEventG(String event) {
    String gStr = event.substring(event.indexOf("to"));
    gStr = gStr.substring(gStr.indexOf(',') + 1, gStr.lastIndexOf(','));
    // System.out.println("gStr: " + gStr);
    int g = Integer.parseInt(gStr);
    return g;
  }

  /**
   * If the event corresponds to a color change, get the blue value of the new color.
   *
   * @param event String representation of an event.
   * @return Blue value of the new color.
   */
  private int getEventB(String event) {
    String bStr = event.substring(event.indexOf("to"));
    bStr = bStr.substring(bStr.lastIndexOf(',') + 1, bStr.indexOf(']'));
    // System.out.println("bStr: " + bStr);
    int g = Integer.parseInt(bStr);
    return g;
  }

  /**
   * If the event corresponds to a change in width, get the new width of the shape.
   *
   * @param event String representation of an event.
   * @return New width of the shape.
   */
  private int getEventWidth(String event) {
    String wStr = event.substring(event.indexOf("to") + 3);
    wStr = wStr.substring(0, wStr.indexOf("from") - 1);
    int w = Integer.parseInt(wStr);
    // System.out.println(wStr);
    return w;
  }

  /**
   * If the event corresponds to a change in height, get the new height of the shape.
   *
   * @param event String representation of an event.
   * @return New height of the shape.
   */
  private int getEventHeight(String event) {
    String hStr = event.substring(event.indexOf("to") + 3);
    hStr = hStr.substring(0, hStr.indexOf("from") - 1);
    System.out.println(hStr);
    int h = Integer.parseInt(hStr);
    return h;
  }

  /**
   * When a String representation of shape enters the program, get the name of the shape.
   *
   * @param shape String representation of a shape.
   * @return Name of the shape.
   */
  private String getShapeName(String shape) {
    Scanner scanner = new Scanner(shape);
    scanner.next();
    String name = scanner.next();
    return name;
  }

  /**
   * When a String representation of shape enters the program, get the type of the shape.
   *
   * @param shape String representation of a shape.
   * @return Type of the shape.
   */
  private String getShapeType(String shape) {
    Scanner scanner = new Scanner(shape);
    scanner.nextLine(); // move past Name Line
    scanner.next(); // move past 'Type:'
    String type = scanner.next();
    return type;
  }

  /**
   * When a String representation of a shape enters the program, get the reference point on the
   * shape.
   *
   * @param shape String representation of a shape.
   * @return Reference point of the shape as an array with size of two. The first element is the
   *     x-coordinate. The second element is the y-coordinate.
   */
  private int[] getShapePoint(String shape) {
    int[] point = new int[2];
    Scanner scanner = new Scanner(shape);
    scanner.nextLine(); // move past Name line
    scanner.nextLine(); // move past Type line
    scanner.next(); // move past 'Point:'
    String pointStr = scanner.next();
    // System.out.println("pointStr - " + pointStr);
    String xStr = pointStr.substring(1, pointStr.indexOf(','));
    int x = Integer.parseInt(xStr);
    String yStr = pointStr.substring(pointStr.indexOf(',') + 1, pointStr.length() - 1);
    int y = Integer.parseInt(yStr);
    point[0] = x;
    point[1] = y;
    return point;
  }

  /**
   * When a String representation of a shape (Rectangle) enters the programs, get the width of the
   * shape.
   *
   * @param shape String representation of a shape.
   * @return Width of the shape.
   */
  private int getShapeWidth(String shape) {
    Scanner scanner = new Scanner(shape);
    scanner.nextLine(); // move past 'Name:' line
    scanner.nextLine(); // move past 'Type:' line
    scanner.nextLine(); // move past 'Point:' line
    scanner.next(); // move past 'Width:'
    int width = scanner.nextInt();
    return width;
  }

  /**
   * When a String representation of a shape (Rectangle) enters the programs, get the height of the
   * shape.
   *
   * @param shape String representation of a shape.
   * @return Height of the shape.
   */
  private int getShapeHeight(String shape) {
    Scanner scanner = new Scanner(shape);
    scanner.nextLine(); // move past 'Name:' line
    scanner.nextLine(); // move past 'Type:' line
    scanner.nextLine(); // move past 'Point:' line
    scanner.nextLine(); // move past 'Width:' line
    scanner.next(); // move past 'Height:'
    int height = scanner.nextInt();
    return height;
  }

  /**
   * When a String representation of a shape (Circle) enters the program, get the diameter of the
   * shape.
   *
   * @param shape String representation of a shape.
   * @return Diameter of the shape.
   */
  private int getShapeDiameter(String shape) {
    Scanner scanner = new Scanner(shape);
    scanner.nextLine(); // move past 'Name:' line
    scanner.nextLine(); // move past 'Type:' line
    scanner.nextLine(); // move past 'Point:' line
    scanner.next(); // move past 'Diameter:'
    int diameter = scanner.nextInt();
    return diameter;
  }

  /**
   * When a String representation of a Rectangle enters the program, get the RGB color of the
   * Rectangle.
   *
   * @param shape String representation of a Rectangle.
   * @return RGB color of the Rectangle as an array of size three. The first element is the red
   *     value. The second element is the green value. The third element is the blue value.
   */
  private int[] getShapeColorRectangle(String shape) {
    int[] color = new int[3];
    Scanner scanner = new Scanner(shape);
    scanner.nextLine(); // move past 'Name:'
    scanner.nextLine(); // move past 'Type:'
    scanner.nextLine(); // move past 'Point:'
    scanner.nextLine(); // move past 'Width:'
    scanner.nextLine(); // move past 'Height:'
    scanner.next(); // move past 'Color:'
    String[] colorStr = scanner.next().split(",");
    int r = Integer.parseInt(colorStr[0].substring(1));
    int g = Integer.parseInt(colorStr[1]);
    int b = Integer.parseInt(colorStr[2].substring(0, colorStr[2].length() - 1));
    color[0] = r;
    color[1] = g;
    color[2] = b;
    return color;
  }

  /**
   * When a String representation of a Circle enters the program, get the RGB color of the Circle.
   *
   * @param shape String representation of a Circle.
   * @return RGB color of the Circle as an array of size three. The first element is the red value.
   *     The second element is the green value. The third element is the blue value.
   */
  private int[] getShapeColorCircle(String shape) {
    int[] color = new int[3];
    // System.out.println(shape);
    Scanner scanner = new Scanner(shape);
    scanner.nextLine(); // move past 'Name:'
    scanner.nextLine(); // move past 'Type:'
    scanner.nextLine(); // move past 'Point:'
    scanner.nextLine(); // move past 'Diameter:'
    scanner.next(); // move past 'Color:'
    String[] colorStr = scanner.next().split(",");
    // System.out.println("Color - " + scanner.next());
    int r = Integer.parseInt(colorStr[0].substring(1));
    int g = Integer.parseInt(colorStr[1]);
    int b = Integer.parseInt(colorStr[2].substring(0, colorStr[2].length() - 1));
    color[0] = r;
    color[1] = g;
    color[2] = b;

    return color;
  }

  /**
   * Get all relavent information about an event from a String representation of an event. Runs
   * through a list of Strings that represent events from the log. The event is the creation of a
   * shape, then it is added to the list of shapes. If the event refers to an action (motion, color
   * change, etc.), the event is added to a list of events.
   *
   * @param eventLog List of Strings that represent events in an animation.
   */
  private void parseEventLog(List<String> eventLog) {
    // List<Integer> offset = this.getCanvasSize(eventLog);
    // int xOff = offset.get(0);
    // int yOff = offset.get(1);

    // System.out.println("eventLog:\n");
    for (String item : eventLog) {
      // System.out.println(item);
      if (item.startsWith("Name")) {
        // this.addShape(item, xOff, yOff);
        shapes.add(item);
        if (getShapeType(item).equals("Rectangle")) {
          nameToShape.put(
              getShapeName(item),
              new Rectangle(
                  getShapePoint(item)[0],
                  getShapePoint(item)[1],
                  getShapeWidth(item),
                  getShapeHeight(item),
                  getShapeColorRectangle(item)[0],
                  getShapeColorRectangle(item)[1],
                  getShapeColorRectangle(item)[2],
                  getShapeName(item)));
        }
        if (getShapeType(item).equals("Ellipse")) {
          nameToShape.put(
                  getShapeName(item),
                  new Ellipse(
                          getShapePoint(item)[0],
                          getShapePoint(item)[1],
                          getShapeWidth(item),
                          getShapeHeight(item),
                          getShapeColorRectangle(item)[0],
                          getShapeColorRectangle(item)[1],
                          getShapeColorRectangle(item)[2],
                          getShapeName(item)));

        }
        if (getShapeType(item).equals("Circle")) {
          nameToShape.put(
              getShapeName(item),
              new Circle(
                  getShapePoint(item)[0],
                  getShapePoint(item)[1],
                  getShapeDiameter(item),
                  getShapeColorCircle(item)[0],
                  getShapeColorCircle(item)[1],
                  getShapeColorCircle(item)[2],
                  getShapeName(item)));
        }
      }

      if (!(item.startsWith("Shapes")
          || item.startsWith("Events")
          || item.startsWith("Canvas")
          || (item.startsWith("Name")))) {

        events.add(item);
      }
    }
  }

  /**
   * Perform the tweening equation on a value. Allows a value to progress from a start value to an
   * end value in a consistent manor.
   *
   * @param from Start value.
   * @param to End value.
   * @param start Time at which the tweening begins.
   * @param end Time at which the tweening ends.
   * @param t Current time in the animation.
   * @return Current value in the tweening progression.
   */
  private double tweening(double from, double to, double start, double end, double t) {
    /*if (from == 0) {
      from = 1;
    }
    if (to == 0) {
      to = 1;
    }*/
    double tween = (from * ((end - t) / (end - start))) + (to * ((t - start) / (end - start)));

    if (tween < 0) {
      return 0;
    }
    return tween;
  }
}
