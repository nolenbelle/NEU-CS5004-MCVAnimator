package cs5004.animator.model;

/** Represent an Ellipse. A specific form of an AbstractShape. */
public class Ellipse extends AbstractShape {

  /**
   * Constructor which only takes the descriptor and
   * sets all values to defaults. Needs have initializeShape()
   * called on it before use.
   */
  public Ellipse( String descriptor) {
    super(descriptor);
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
