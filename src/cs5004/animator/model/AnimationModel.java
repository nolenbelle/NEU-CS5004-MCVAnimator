package cs5004.animator.model;

import java.io.InvalidClassException;
import java.util.List;

/**
 * This model is designed to create animations based on simple images within a frame much like a
 * movie reel or a flip book. The model will create and store the frames which contain various shape
 * data. All of these methods belong in the model because it is dealing with the storage and
 * mutation of the internal data which creates the animation. It does not do any of the deciding
 * when to mutate or create the animations, and it does not do any of the visual representation of
 * the animation. It does produce a textual representation of the events that were called on the
 * model as per instructed.
 */
public interface AnimationModel {

  /**
   * Gets the animation data for a specified moment in time.
   *
   * @param time the 'tick' mark for when you want the details
   * @return Frame a Frame object containing the shapes at that time.
   */
  Frame getFrameAtTick(int time) throws InvalidClassException;

  /**
   * Adds a new shape object to the event log. Intended to be called once on every new
   * shape after it has been initialized with it's starting data.
   *
   * @param shape the object to be added
   */
  void addShapeToLog(Shape shape);

  /**
   * Adds a shape object to all the frames within the given appear and disappear times.
   *
   * @param shape the object to be added
   * @param fromFrame the first frame to add the shape
   * @param toFrame the last frame to add the shape
   * @throws IllegalArgumentException if the given frame numbers are invalid
   */
  void addShapeToFrames(Shape shape, int fromFrame, int toFrame) throws IllegalArgumentException;

  /**
   * Moves a shape in a line from the starting location to the ending location in the amount of
   * frames specified.
   *
   * @param shape the shape to be moved
   * @param fromX the starting x coordinate
   * @param fromY the starting y coordinate
   * @param toX the ending x coordinate
   * @param toY the ending y coordinate
   * @param fromFrame the frame at which to start the movement
   * @param toFrame the frame at which to end the movement
   * @throws IllegalArgumentException if a shape is attempted to be moved in frames that it was now
   *     added to, if the given frame numbers are invalid, or if the X & Y to move to are negative
   */
  void shapeMove(Shape shape, int fromX, int fromY, int toX, int toY, int fromFrame, int toFrame)
      throws IllegalArgumentException;

  /**
   * Gradually changes the color of the given shape from the starting color to the ending color
   * over the course of the given frames.
   *
   * @param shape the shape to be modified
   * @param r1 the starting red value
   * @param g1 the starting green value
   * @param b1 the starting blue value
   * @param r2 the ending red value
   * @param g2 the ending green value
   * @param b2 the ending blue value
   * @param fromFrame the frame at which to start the color change
   * @param toFrame the frame at which to end the color change
   * @throws IllegalArgumentException if a shape is attempted to be moved in frames that it was now
   *     added to, if the given frame numbers are invalid, or if the r,g,b values or less than 0 or
   *     greater than 255
   */
  void changeColor(Shape shape, int r1, int g1, int b1, int r2, int g2, int b2,
                   int fromFrame, int toFrame)
      throws IllegalArgumentException;

  /**
   * Gradually changes the width of the given shape from its current width to the provided width
   * over the course of the given frames.
   *
   * @param shape the given shape to be changed
   * @param oldWidth the starting width
   * @param newWidth the ending width
   * @param fromFrame the frame at which to start the change
   * @param toFrame the frame at which to end the change
   * @throws IllegalArgumentException if a shape is attempted to be moved in frames that it was now
   *     added to, if the given frame numbers are invalid, or if the new width is less than 0
   */
  void changeWidth(Shape shape, int oldWidth, int newWidth, int fromFrame, int toFrame)
      throws IllegalArgumentException;

  /**
   * Gradually changes the height of the given shape from its current width to the provided height
   * over the course of the given frames.
   *
   * @param shape the given shape to be changed
   * @param oldHeight the starting height
   * @param newHeight the ending height
   * @param fromFrame the frame at which to start the change
   * @param toFrame the frame at which to end the change
   * @throws IllegalArgumentException if a shape is attempted to be moved in frames that it was now
   *     added to, if the given frame numbers are invalid, or if the new height is negative
   */
  void changeHeight(Shape shape, int oldHeight, int newHeight, int fromFrame, int toFrame)
      throws IllegalArgumentException;

  /**
   * Textual description of all the animations that take place.
   *
   * @return a list of animation events
   */
  List<String> getEventLog();

  /**
   * Gets all the frames of the animation.
   *
   * @return a list of frames
   */
  List<Frame> getFrames();

  /**
   * Sets the size of the canvas so that the model can verify that any x,y moves are
   * valid within the canvas area. This was added after assignment 6 in reaction to the
   * 'canvas x x x x' line at the start of the files to be read in.
   */
  void setCanvas(int x, int y, int width, int height);

  /**
   * Gets the min x, min y, width & height of the canvas as passed to the model.
   * @return the canvas parameters
   */
  int[] getCanvas();

  /**
   * Gets the list of shapes added to the model.
   * @return the list of shapes added to the model
   */
  List<Shape> getShapes();
}
