package cs5004.animator.model;

/**
 * Interface representing all the methods that a Shape should implement. Shapes will be used to make
 * animations that will appear on Frames.
 */
public interface Shape {

  /**
   * Set the center point of the shape using x/y coordinates.
   *
   * @param x X-Value of the center point.
   * @param y Y-Value of the center point
   */
  void setCoordinates(int x, int y);

  /**
   * Set the color of the Shape using RGB values. The values must be between 0 and 255.
   *
   * @param r Red value for the Shape (0 - 255).
   * @param g Green value for the Shape (0 - 255).
   * @param b Blue value for the Shape (0 - 255).
   */
  void setColor(int r, int g, int b);

  /**
   * Get the x-value for the center of the Shape.
   *
   * @return x-value for the center of the Shape.
   */
  int getXCoordinate();

  /**
   * Get the y-value for the center of the Shape.
   *
   * @return y-value for the center of the Shape.
   */
  int getYCoordinate();

  /**
   * Get the RGB values representing the color of the Shape.
   *
   * @return Integer array representing the RGB color value of the Shape ([Red, Green, Blue]).
   */
  int[] getColor();

  /**
   * Set the width of a shape. Set the diameter of a Circle.
   *
   * @param w Width we want to set for the Shape.
   */
  void setWidth(int w);

  /**
   * Set the height of a Shape. Set the diameter of a Circle.
   *
   * @param h Height we want to set for the Shape.
   */
  void setHeight(int h);

  /**
   * Get the width of a Shape. Get the diameter of a Circle.
   *
   * @return Width of this Shape.
   */
  int getWidth();

  /**
   * Get the height of a Shape. Get the diameter of a Circle.
   *
   * @return Height of this Shape.
   */
  int getHeight();

  /**
   * Get the descriptive title for the Shape.
   *
   * @return Descriptor of this Shape.
   */
  String getDescriptor();

  /**
   * Create a copy of a Shape.
   *
   * @return Copy of this Shape.
   */
  Shape copy();

  /**
   * Create a human readable version of the Shape.
   *
   * @return String representing this Shape.
   */
  String toString();

  // added after A06 to bridge the fact that we declared our shapes with all their starting
  // data and the read files do not. Adding this helps to not have to go back and dramatically
  // change too much else in how we are creating the textual output of events.
  /**
   * Sets all the field parameters of the shape.
   * @param x the x values
   * @param y y value
   * @param r r value
   * @param g g value
   * @param b b value
   * @param height height value
   * @param width width value
   */
  void initializeShape(int x, int y, int r, int g, int b, int height, int width);
}
