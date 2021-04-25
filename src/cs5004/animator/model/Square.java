package cs5004.animator.model;

/** Represent a Rectangle. A specific form of an AbstractShape. */
public class Square extends Rectangle {
  private String descriptor;
  private int[] color = new int[3]; // [RED , GREEN , BLUE])
  private int x; // x-coordinate of the center point of shape
  private int y; // y-coordinate of the center point of shape
  private int width;
  private int height;
  //private final String type = "rectangle";

  /**
   * Construct a Square using an x/y point on the Square, the length of the Square, the RGB color of
   * the Square, and a unique descriptor of the Square.
   *
   * @param x X-Coordinate of the given point on the Square.
   * @param y Y-Coordinate of the given point on the Square.
   * @param length Length of the Square.
   * @param r Red value of the color of the Square.
   * @param g Green value of the color of the Square.
   * @param b Blue value of the color of the Square.
   * @param descriptor Unique descriptive name given to the Square.
   */
  public Square(int x, int y, int length, int r, int g, int b, String descriptor) {
    super(x, y, length, length, r, g, b, descriptor);
  }

  /**
   * Return a human readable version of a Square that contains all of the Square's information.
   *
   * @return String representing the Square.
   */
  @Override
  public String toString() {
    return String.format(
        "%s\n   Type: Square\n   Point: (%d,%d)\n   Length: %d" + "\n   Color: [%d,%d,%d]",
        this.getDescriptor(),
        this.getXCoordinate(),
        this.getYCoordinate(),
        this.getHeight(),
        this.getColor()[0],
        this.getColor()[1],
        this.getColor()[2]);
  }
}
