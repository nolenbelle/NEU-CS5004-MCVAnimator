package cs5004.animator.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of an AnimationModel. Includes all methods relevant in creating an animated movie
 * (ie. Stream of Frames).
 */
public class AnimationModelImpl implements AnimationModel {
  private List<Frame> frames;
  private List<String> eventLog;
  // ~added after assignment 6 to be able to hold the 'canvas x x x x' data as it gets read in~
  private int x;
  private int y;
  private int width;
  private int height;
  // ~added post A06 to make it easier to deal with the no parameter shape constructors~
  private List<Shape> shapes;

  /**
   * Construct an AnimationModelImpl. Creates an empty log of events (Shapes and Events),
   * an empty list of frames, and an empty list of shapes.
   */
  public AnimationModelImpl() {
    this.eventLog = new ArrayList<String>();
    this.eventLog.add("Shapes:\n");
    this.eventLog.add("Events:\n");
    this.frames = new ArrayList<Frame>();
    this.shapes = new ArrayList<Shape>();
  }

  /**
   * Gets the animation data for a specified moment in time.
   *
   * @param time the 'tick' mark for when you want the details
   * @return Frame a Frame object containing the shapes at that time.
   */
  @Override
  public Frame getFrameAtTick(int time) {
    return this.frames.get(time);
  }

  @Override
  public void addShapeToLog(Shape shape) {
    // method created post A06. Makes sense for this to be it's own thing and not be be reliant
    // on addShape() (which became addShapeToFrame reactively)
    int found = -1;
    for (Shape s : this.shapes) { // check if shape already in the shape list
      if (s.getDescriptor().equals(shape.getDescriptor())) {
        found = this.shapes.indexOf(s);
      }
    }
    if (found == -1) { // if it is not, add it
      this.shapes.add(shape);
    } else { // if it is, update it with the newly added shape
      this.shapes.remove(found);
      this.shapes.add(found,shape);
    }
  }

  /**
   * Adds a new shape object to all the frames within the given appear and disappear times.
   *
   * @param shape the object to be added
   * @param fromFrame the first frame to add the shape
   * @param toFrame the last frame to add the shape
   * @throws IllegalArgumentException if the given frame numbers are invalid
   */
  @Override
  public void addShapeToFrames(Shape shape, int fromFrame, int toFrame) {
    // check that the shape was added in the builder first
    // this couples this implementation to the AnimationReader & builder
    // but it would be simple to make a more generic implementation for a different input
    int check = 1;
    for (Shape s : this.shapes) {
      if (s.getDescriptor().equals(shape.getDescriptor())) {
        check = 0;
      }
    }
    if (check == 1) {
      this.addShapeToLog(shape);
    }

    // add the new shape to the shape section of the event log
    // change from assignment 6:
    // changed from creating a default amount of frames to always just adding
    // the frames as we need them for the exact amount
    if (this.frames.size() <= toFrame) {
      for (int i = this.frames.size(); i <= toFrame; i++) {
        this.frames.add(new FrameImpl());
      }
    }

    // go through every frame given and have that frame add a copy of the shape to itself
    for (int i = fromFrame; i <= toFrame; i++) {
      this.frames.get(i).addShape(shape.copy());
      // we need to copy them so they can be mutated independently
    }
  }

  /**
   * Moves a shape in a line from it's current location to the given location in the amount of
   * frames specified.
   *
   * @param shape the shape to be moved
   * @param fromX the ending x coordinate
   * @param fromY the ending y coordinate
   * @param toX the ending x coordinate
   * @param toY the ending y coordinate
   * @param fromFrame the frame at which to start the movement
   * @param toFrame the frame at which to end the movement
   * @throws IllegalArgumentException if a shape is attempted to be moved in frames that it was not
   *     added to, if the given frame numbers are invalid, or if the X & Y to move to are negative
   */
  @Override
  public void shapeMove(Shape shape, int fromX, int fromY, int toX, int toY,
                        int fromFrame, int toFrame)
          throws IllegalArgumentException {
    // adjust for the offset
    int minX = this.x;
    int minY = this.y;
    int maxX = minX + this.width;
    int maxY = minY + height;
    // ~canvas must have been initialized with setCanvas or else an exception will be thrown here
    // post A06, now handling the canvas parameters from the read files~

    // I think we technically dont need to check that we are within the canvas
    // because the view can decide to just not show it?
    /*if (toX > maxX) {
      throw new IllegalArgumentException(String.format("toX: %d is larger than width", toX));
    }
    if (toY > maxY) {
      throw new IllegalArgumentException(String.format("toY: %d is larger than height", toY));
    }
    if (fromX > maxX) {
      throw new IllegalArgumentException(String.format("FromX: %d is larger than width", fromX));
    }
    if (fromY > maxY) {
      throw new IllegalArgumentException(String.format("FromY: %d is larger than width", fromY));
    }
    if (toX < minX || toY < minY || fromX < minX || fromY < minY) {
      throw new IllegalArgumentException("Coordinates cannot lower than minimum values of canvas");
    }*/
    if (fromFrame >= toFrame) {
      throw new IllegalArgumentException(
              "toFrame must be larger than fromFrame");
    }
    if (fromFrame < 0 || toFrame >= this.frames.size()) {
      throw new IllegalArgumentException(
              "toFrame or fromFrame is out of bounds with the frame array."
                      + " Array should have been extended by addShape method.");
    }

    int newX = fromX;
    int newY = fromY;

    // calculate the total distance to move the shape
    int xDistance = toX - newX;
    int yDistance = toY - newY;

    // calculate the distance to move per frame
    int xDistancePerFrame = xDistance / (toFrame - fromFrame);
    int yDistancePerFrame = yDistance / (toFrame - fromFrame);

    // go through every frame that the shape changes state in and change that frame's copy
    // of the shape by the delta per frame to achieve gradual change
    // throws an IllegalArgumentException if the shape is not in the frame
    for (int i = fromFrame; i <= toFrame; i++) {
      this.frames.get(i).getShape(shape.getDescriptor()).setCoordinates(newX, newY);

      newX += xDistancePerFrame;
      newY += yDistancePerFrame;
    }

    // add the move event to the animation to the event section of the event log
    this.eventLog.add(
            String.format(
                    "%s is moved from (%d,%d) to (%d,%d) from frame=%d to " + "frame=%d\n",
                    shape.getDescriptor(), fromX, fromY, toX, toY, fromFrame, toFrame));
  }

  @Override
  public void changeColor(Shape shape, int r1, int g1, int b1, int r2, int g2, int b2,
                          int fromFrame, int toFrame)
          throws IllegalArgumentException {
    if (r1 < 0 || g1 < 0 || b1 < 0 || r1 > 255 || g1 > 255 || b1 > 255
            || r2 < 0 || g2 < 0 || b2 < 0 || r2 > 255 || g2 > 255 || b2 > 255) {
      throw new IllegalArgumentException("All r,g,b values must be at least 0 and at most 255.");
    }
    if (fromFrame >= toFrame || fromFrame < 0 ) {
      throw new IllegalArgumentException(
              "Frames must be at least 0. toFrame" + " must be larger than fromFrame.");
    }

    // starting values
    // now a parameter, used to be whatever the shape values were in the starting frame
    int newR = r1;
    int newG = g1;
    int newB = b1;

    // calculate the total value change of each color
    int rChange = r2 - newR;
    int gChange = g2 - newG;
    int bChange = b2 - newB;

    // calculate the distance to move per frame
    int rChangePerFrame = rChange / (toFrame - fromFrame);
    int gChangePerFrame = gChange / (toFrame - fromFrame);
    int bChangePerFrame = bChange / (toFrame - fromFrame);

    // go through every frame that the shape changes state in and change that frame's copy
    // of the shape by the delta per frame to achieve gradual change
    // throws an IllegalArgumentException if the shape is not in the frame
    for (int i = fromFrame; i <= toFrame; i++) {
      this.frames.get(i).getShape(shape.getDescriptor()).setColor(newR, newG, newB);

      newR += rChangePerFrame;
      newG += gChangePerFrame;
      newB += bChangePerFrame;
    }

    // add the change of color to the event section of our event log
    this.eventLog.add(
            String.format(
                    "%s changes color from [%d,%d,%d] to [%d,%d,%d] from " +
                            "frame=%d to frame=%d\n",
                    shape.getDescriptor(), r1, g1, b1, r2, g2, b2, fromFrame, toFrame));
  }

  /**
   * Gradually changes the width of the given shape from its current width to the provided width
   * over the course of the given frames.
   *
   * @param shape the given shape to be changed
   * @param newWidth the ending width
   * @param fromFrame the frame at which to start the change
   * @param toFrame the frame at which to end the change
   * @throws IllegalArgumentException if a shape is attempted to be moved in frames that it was now
   *     added to, if the given frame numbers are invalid, or if the new width is less than 0
   */
  @Override
  public void changeWidth(Shape shape, int oldWidth, int newWidth, int fromFrame, int toFrame)
          throws IllegalArgumentException {
    if (newWidth <= 0 || newWidth <= 0) {
      throw new IllegalArgumentException("Width values must be at least 1.");
    }
    if (fromFrame >= toFrame || fromFrame < 0) {
      throw new IllegalArgumentException(
              "Frames must be at least 0. toFrame" + " must be larger than fromFrame.");
    }
    int startWidth = oldWidth;

    // calculate the total width to change the shape
    int widthChange = newWidth - startWidth;

    // calculate the distance to move per frame
    int widthChangePerFrame = widthChange / (toFrame - fromFrame);

    // go through every frame that the shape changes state in and change that frame's copy
    // of the shape by the delta per frame to achieve gradual change
    // throws an IllegalArgumentException if the shape is not in the frame
    int deltaWidth = startWidth;
    for (int i = fromFrame; i <= toFrame; i++) {
      this.frames.get(i).getShape(shape.getDescriptor()).setWidth(deltaWidth);

      deltaWidth += widthChangePerFrame;
    }
    // add the change of width to the event section of our event log
    this.eventLog.add(
            String.format(
                    "%s changes width from %d to %d from frame=%d to frame=%d\n",
                    shape.getDescriptor(), startWidth, newWidth, fromFrame, toFrame));
  }

  /**
   * Gradually changes the height of the given shape from its current width to the provided height
   * over the course of the given frames.
   *
   * @param shape the given shape to be changed
   * @param newHeight the ending width
   * @param fromFrame the frame at which to start the change
   * @param toFrame the frame at which to end the change
   * @throws IllegalArgumentException if a shape is attempted to be moved in frames that it was now
   *     added to, if the given frame numbers are invalid, or if the new height is negative
   */
  @Override
  public void changeHeight(Shape shape, int oldHeight, int newHeight, int fromFrame, int toFrame)
          throws IllegalArgumentException {
    if (newHeight <= 0 || oldHeight <= 0) {
      throw new IllegalArgumentException("Height values must be at least 1.");
    }
    if (fromFrame >= toFrame || fromFrame < 0) {
      throw new IllegalArgumentException(
              "Frames must be at least 0. toFrame" + " must be larger than fromFrame.");
    }
    int startHeight = oldHeight;

    // calculate the total width to change the shape
    int heightChange = newHeight - startHeight;

    // calculate the distance to move per frame
    int heightChangePerFrame = heightChange / (toFrame - fromFrame);

    // go through every frame that the shape changes state in and change that frame's copy
    // of the shape by the delta per frame to achieve gradual change
    // throws an IllegalArgumentException if the shape is not in the frame
    int deltaHeight = startHeight;
    for (int i = fromFrame; i <= toFrame; i++) {
      this.frames.get(i).getShape(shape.getDescriptor()).setHeight(deltaHeight);

      deltaHeight += heightChangePerFrame;
    }
    // add the change of width to the event section of our event log
    this.eventLog.add(
            String.format(
                    "%s changes height from %d to %d from frame=%d to frame=%d\n",
                    shape.getDescriptor(), startHeight, newHeight, fromFrame, toFrame));
  }

  /**
   * Textual description of all the animations that take place.
   *
   * @return a list of animation events
   */
  @Override
  public List<String> getEventLog() {
    // add all our shapes to our event log
    this.clearShapes(); // preserves the added order of the shapes with no duplicates
    int afterShapes = eventLog.indexOf("Shapes:\n") + 1;
    for (int i = this.shapes.size() - 1; i >= 0; i--) {
      this.eventLog.add(
              afterShapes,
              String.format(
                      "Name: %s\n\n", this.shapes.get(i).toString()));
    }
    return this.eventLog;
  }

  /**
   * Gets all the frames of the animation.
   *
   * @return a list of frames
   */
  @Override
  public List<Frame> getFrames() {
    return this.frames;
  }

  @Override
  public void setCanvas(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.eventLog.add(0, String.format("Canvas: %d %d %d %d\n", x, y, width, height));
  }

  @Override
  public int[] getCanvas() {
    return new int[]{this.x, this.y, this.width, this.height};
  }

  @Override
  public List<Shape> getShapes() {
    return this.shapes;
  }

  /**
   * Return a human readable version of the AnimationModel. Goes through each event in the
   * AnimationModel and adds its toString() result to the result.
   *
   * @return String representation of the model data
   */
  @Override
  public String toString() {
    this.eventLog = this.getEventLog(); // updates the event log with any added shapes
    // puts all the event log strings into one string
    StringBuilder result = new StringBuilder();
    for (String event : this.eventLog) {
      if (!event.startsWith("Canvas:")) {
        result.append(event);
      }
    }
    //System.out.println(result.toString());
    return result.toString();
  }

  /**
   * To be called by the toString. This redoes the shape list in between multiple calls of
   * toString on the same model.
   */
  private void clearShapes() {
    for (int i = 0; i < eventLog.size(); i ++) {
      if (eventLog.get(i).equals("Events:\n")) {
        // eventLog(0) is always "Shapes".
        for (int j = i - 1; j > 0; j--) {
          if (!(eventLog.get(j).startsWith("Canvas") || eventLog.get(j).startsWith("Shapes"))) {
            eventLog.remove(j);
          }
        }
        break;
      }
    }
  }
}