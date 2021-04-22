package cs5004.animator.model;

import java.util.List;

/**
 * Interface representing all the methods that a single Frame in an AnimationModel should implement.
 * A Single Frame is like a cel of animation. An animated movie is constructed by stringing together
 * multiple Frames.
 */
public interface Frame {

  /**
   * Add a shape to the frame.
   *
   * @param s Shape to be added to the frame.
   */
  void addShape(Shape s);

  /**
   * Remove a shape to the frame.
   *
   * @param descriptor the descriptor of the shape to be removed
   */
  void removeShape(String descriptor);

  /**
   * Obtain a list all the Shapes that are currently present in the Frame.
   *
   * @return List of Shapes in the Frame.
   */
  List<Shape> getAllShapes();

  /**
   * Obtain the Shape with the given descriptor. Cycles through all the Shapes in the Frame.
   *
   * @param descriptor String representing the Shape that we want to obtain.
   * @return Shape with the matching descriptor.
   */
  Shape getShape(String descriptor);

  /**
   * Return a readable version of all the Shapes present in the Frame.
   *
   * @return String representing all the Shapes in the Frame.
   */
  String toString();
}
