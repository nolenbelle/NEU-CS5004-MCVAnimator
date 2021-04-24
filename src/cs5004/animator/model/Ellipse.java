package cs5004.animator.model;

/** Represent an Ellipse. A specific form of an AbstractShape. */
public class Ellipse extends AbstractShape {
  private String type = "ellipse";

  /**
   * Construct a Ellipse using an x/y point on the Rectangle, the width of the Rectangle, the
   * height of the Rectangle, the RGB color of the Rectangle, and a unique descriptor of the
   * Rectangle.
   *
   * @param x X-Coordinate of the given point on the Rectangle.
   * @param y Y-Coordinate of the given point on the Rectangle.
   * @param width Width of the Ellipse.
   * @param height height of the Ellipse.
   * @param r Red value of the color of the Rectangle.
   * @param g Green value of the color of the Rectangle.
   * @param b Blue value of the color of the Rectangle.
   * @param descriptor Unique descriptive name of the Rectangle.
   */
  public Ellipse(int x, int y, int width, int height, int r, int g, int b, String descriptor) {
    super(x, y, width, height, r, g, b, descriptor);
  }

  /**
   * Constructor which only takes the descriptor and
   * sets all values to defaults. Needs have initializeShape()
   * called on it before use.
   */
  public Ellipse( String descriptor) {
    super(descriptor);
  }

  /**
   * Create a copy of a Shape.
   *
   * @return Copy of this Shape.
   */
  @Override
  public Shape copy() {
    Shape copy =
        new Ellipse(
            super.getXCoordinate(),
            super.getYCoordinate(),
            super.getWidth(),
            super.getHeight(),
            super.getColor()[0],
            super.getColor()[1],
            super.getColor()[2],
            super.getDescriptor());
    return copy;
  }

  public String getType(){
    return this.type;
  }

  @Override
  public String toString() {
    return String.format(
            "%s\n   Type: Ellipse\n   Point: (%d,%d)\n   Width: %d\n   Height: %d"
                    + "\n   Color: [%d,%d,%d]",
            this.getDescriptor(),
            this.getXCoordinate(),
            this.getYCoordinate(),
            this.getWidth(),
            this.getHeight(),
            this.getColor()[0],
            this.getColor()[1],
            this.getColor()[2]);
  }
}
