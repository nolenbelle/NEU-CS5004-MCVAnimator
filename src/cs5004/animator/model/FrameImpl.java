package cs5004.animator.model;

import java.util.ArrayList;
import java.util.List;

/** Implementation of the Frame interface. */
public class FrameImpl implements Frame {

  private List<Shape> shapes = new ArrayList<Shape>();

  /**
   * Add a shape to the frame. Removes the current shape with the same descriptor (which allows
   * for edits).
   *
   * @param s Shape to be added to the frame.
   */
  @Override
  public void addShape(Shape s) throws IllegalArgumentException {
    for (int i = 0; i < this.shapes.size(); i++) {
      if (shapes.get(i).getDescriptor().equals(s.getDescriptor())) {
        shapes.remove(i);
      }
    }
    this.shapes.add(s);
  }

  /**
   * Remove a shape to the frame.
   *
   * @param descriptor the descriptor of the shape to be removed
   */
  @Override
  public void removeShape(String descriptor) {
    for (Shape shape : this.shapes) {
      if (shape.getDescriptor().equals(descriptor)) {
        this.shapes.remove(this.shapes.indexOf(shape));
      }
    }
  }

  /**
   * Obtain a list all the Shapes that are currently present in the Frame.
   *
   * @return List of Shapes in the Frame.
   */
  @Override
  public List<Shape> getAllShapes() {
    return this.shapes;
  }

  /**
   * Obtain the Shape with the given descriptor. Cycles through all the Shapes in the Frame.
   *
   * @param descriptor String representing the Shape that we want to obtain.
   * @return Shape with the matching descriptor.
   */
  @Override
  public Shape getShape(String descriptor) throws IllegalArgumentException {
    for (Shape s : this.shapes) {
      if (s.getDescriptor().equals(descriptor)) {
        return s;
      }
    }
    throw new IllegalArgumentException(
        "Could not find a shape with '" + descriptor + "' as its descriptor.");
  }

  @Override
  public String toString() {
    String str = "";
    for (Shape s : this.shapes) {
      str = str + s.toString() + "\n";
    }
    return str;
  }
}
