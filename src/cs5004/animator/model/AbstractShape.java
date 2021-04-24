package cs5004.animator.model;

/** Represents any Shape that may be present in a Frame. */
public class AbstractShape implements Shape {

  private String descriptor;
  private int[] color = new int[3]; // [RED , GREEN , BLUE])
  private int x; // x-coordinate of the center point of shape
  private int y; // y-coordinate of the center point of shape
  private int width;
  private int height;
  private String type;

  /**
   * Construct an AbstractShape using a given point located on the Shape, the width of the Shape,
   * the height of the Shape, the RGB color of the Shape, and a unique descriptor representing the
   * Shape.
   *
   * @param x X-Coordinate of the given point on the Shape.
   * @param y Y-Coordinate of the given point of the Shape.
   * @param width Width of the Shape.
   * @param height Height of the Shape.
   * @param r Red value for the color of the Shape.
   * @param g Green value for the color of the Shape.
   * @param b Blue value for the color of the Shape.
   * @param descriptor Unique String to represent the Shape.
   */
  public AbstractShape(
      int x, int y, int width, int height, int r, int g, int b, String descriptor) {
    if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
      throw new IllegalArgumentException("Color values must be between 0 & 255");
    }
    this.descriptor = descriptor;

    this.color[0] = r;
    this.color[1] = g;
    this.color[2] = b;

    this.x = x;
    this.y = y;

    this.width = width;
    this.height = height;
  }

  /**
   * No parameter constructor added to match the read file parsing style given after A06.
   * Sets every field to a default -1 or null so that it is clear that the initial values
   * have not been set yet. All the values can be set at the same time by calling
   * initializeShape();
   */
  public AbstractShape(String descriptor) {
    this.descriptor = descriptor;
    this.x = -1;
    this.y = -1;
    this.width = -1;
    this.height = -1;
    this.color = new int[]{-1, -1, -1};
  }

  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }

  /**
   * Set the location of the shape using x/y coordinates.
   *
   * @param x X-Value of the center point.
   * @param y Y-Value of the center point
   */
  @Override
  public void setCoordinates(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Set the color of the Shape using RGB values. The values must be between 0 and 255.
   *
   * @param r Red value for the Shape (0 - 255).
   * @param g Green value for the Shape (0 - 255).
   * @param b Blue value for the Shape (0 - 255).
   */
  @Override
  public void setColor(int r, int g, int b) {
    if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
      throw new IllegalArgumentException("Color values must be between 0 & 255");
    }
    this.color[0] = r;
    this.color[1] = g;
    this.color[2] = b;
  }

  /**
   * Get the x-value for the center of the Shape.
   *
   * @return x-value for the center of the Shape.
   */
  @Override
  public int getXCoordinate() {
    return this.x;
  }

  /**
   * Get the y-value for the center of the Shape.
   *
   * @return y-value for the center of the Shape.
   */
  @Override
  public int getYCoordinate() {
    return this.y;
  }

  /**
   * Get the RGB values representing the color of the Shape.
   *
   * @return Integer array representing the RGB color value of the Shape ([Red, Green, Blue]).
   */
  @Override
  public int[] getColor() {
    int[] color = {this.color[0], this.color[1], this.color[2]};
    return color;
  }

  /**
   * Set the width of a shape.
   *
   * @param w Width we want to set for the Shape.
   */
  @Override
  public void setWidth(int w) {
    if (w < 1) {
      throw new IllegalArgumentException("Width must be at least 1");
    }
    this.width = w;
  }

  /**
   * Set the height of a Shape.
   *
   * @param h Height we want to set for the Shape.
   */
  @Override
  public void setHeight(int h) {
    if (h < 1) {
      throw new IllegalArgumentException("Height must be at least 1");
    }
    this.height = h;
  }

  /**
   * Get the width of a Shape.
   *
   * @return Width of this Shape.
   */
  @Override
  public int getWidth() {
    return this.width;
  }

  /**
   * Get the height of a Shape.
   *
   * @return Height of this Shape.
   */
  @Override
  public int getHeight() {
    return this.height;
  }

  /**
   * Get the descriptive title for the Shape.
   *
   * @return Descriptor of this Shape.
   */
  @Override
  public String getDescriptor() {
    return this.descriptor;
  }

  /**
   * Create a copy of a Shape.
   *
   * @return Copy of this Shape.
   */
  @Override
  public Shape copy() {
    Shape copy =
        new AbstractShape(
            this.x,
            this.y,
            this.width,
            this.height,
            this.color[0],
            this.color[1],
            this.color[2],
            this.descriptor);
    return copy;
  }

  @Override
  public void initializeShape(int x, int y, int r, int g, int b, int height, int width) {
    if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
      throw new IllegalArgumentException("Color values must be between 0 & 255");
    }
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.color = new int[]{r,g,b};
  }
}
