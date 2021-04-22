package cs5004.animator.model;

/** Represent a Circle. A specific form of an AbstractShape. */
public class Circle extends AbstractShape {

  /**
   * Construct a Circle using an x/y point on the Circle, the diameter of the Circle, the RGB color
   * of the Circle, and a unique descriptor of the Circle.
   *
   * @param x X-Coordinate of the given point on the Circle.
   * @param y Y-Coordinate of the given point on the Circle.
   * @param diameter Diameter of the Circle.
   * @param r Red component of the color of the Circle.
   * @param g Green component of the color of the Circle.
   * @param b Blue component of the color of the Circle.
   * @param descriptor Unique descriptive name give to the Circle.
   */
  public Circle(int x, int y, int diameter, int r, int g, int b, String descriptor) {
    super(x, y, diameter, diameter, r, g, b, descriptor);
  }

  /**
   * Return a human readable version of a Circle that contains all of the Circle's information.
   *
   * @return String representing the Circle.
   */
  @Override
  public String toString() {
    return String.format(
        "%s\n   Type: Circle\n   Point: (%d,%d)\n   Diameter: %d" + "\n   Color: [%d,%d,%d]",
        this.getDescriptor(),
        this.getXCoordinate(),
        this.getYCoordinate(),
        this.getHeight(),
        this.getColor()[0],
        this.getColor()[1],
        this.getColor()[2]);
  }
}
