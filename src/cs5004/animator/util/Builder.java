package cs5004.animator.util;

import cs5004.animator.model.AnimationModel;
import cs5004.animator.model.AnimationModelImpl;
import cs5004.animator.model.Ellipse;
import cs5004.animator.model.Rectangle;
import cs5004.animator.model.Shape;

/**
 * Implementation of AnimationBuilder. This acts as an adaptor for the AnimationReader so that
 * it can parse the files and transform it into proper code for our AnimationModel.
 */
public class Builder implements AnimationBuilder<AnimationModel> {
  private AnimationModel model = new AnimationModelImpl();

  /**
   * Constructs a final document.
   * @return the newly constructed document
   */
  @Override
  public AnimationModel build() {
    return model;
  }

  /**
   * Specify the bounding box to be used for the animation.
   * @param x The leftmost x value
   * @param y The topmost y value
   * @param width The width of the bounding box
   * @param height The height of the bounding box
   * @return This {@link AnimationBuilder}
   */
  @Override
  public AnimationBuilder<AnimationModel> setBounds(int x, int y, int width, int height) {
    model.setCanvas(x,y,width,height);
    return this;
  }

  /**
   * Adds a new shape to the growing document.
   *
   * @param name The unique name of the shape to be added.
   *             No shape with this name should already exist.
   * @param type The type of shape (e.g. "ellipse", "rectangle") to be added.
   *             The set of supported shapes is unspecified, but should
   *             include "ellipse" and "rectangle" as a minimum.
   * @return This {@link AnimationBuilder}
   */
  @Override
  public AnimationBuilder<AnimationModel> declareShape(String name, String type) {
    Shape newShape;
    // only two shapes right now but more just go here with additional switches
    if (type.equals("ellipse")) {
      newShape = new Ellipse(name);
    }
    else {
      newShape = new Rectangle(name);
    }
    model.addShapeToLog(newShape);
    return this;
  }

  /**
   * Adds a transformation to the growing document.
   *
   * @param name The name of the shape (added with {@link AnimationBuilder#declareShape})
   * @param t1   The start time of this transformation
   * @param x1   The initial x-position of the shape
   * @param y1   The initial y-position of the shape
   * @param w1   The initial width of the shape
   * @param h1   The initial height of the shape
   * @param r1   The initial red color-value of the shape
   * @param g1   The initial green color-value of the shape
   * @param b1   The initial blue color-value of the shape
   * @param t2   The end time of this transformation
   * @param x2   The final x-position of the shape
   * @param y2   The final y-position of the shape
   * @param w2   The final width of the shape
   * @param h2   The final height of the shape
   * @param r2   The final red color-value of the shape
   * @param g2   The final green color-value of the shape
   * @param b2   The final blue color-value of the shape
   * @return This {@link AnimationBuilder}
   */
  @Override
  public AnimationBuilder<AnimationModel> addMotion(String name, int t1, int x1, int y1,
                                                    int w1, int h1, int r1, int g1, int b1,
                                                    int t2, int x2, int y2, int w2, int h2,
                                                    int r2, int g2, int b2) {
    //first check and see if this shape has been initialized
    //check all the shapes added to the model so far. Should be there, alias will stay null if not
    //and throw a null pointer error at some point
    Shape alias = null;
    for (int i = 0; i < this.model.getShapes().size(); i++) {
      if (this.model.getShapes().get(i).getDescriptor().equals(name)) {
        alias = this.model.getShapes().get(i);
        // if the shape data is not yet initialized aka we haven't done any motions with it yet,
        // then we initialize it with it's first set of data values
        if (alias.getWidth() == -1 && alias.getHeight() == -1) {
          this.model.getShapes().get(i).initializeShape(x1,y1,r1,g1,b1,h1,w1);
        }
        alias = this.model.getShapes().get(i); // update our alias
        model.addShapeToFrames(alias, t1, t2); // add it to the frames
      }
    }
    // perform the moves on the model data for the data that changed
    if ((x1 != x2 || y1 != y2)) {
      model.shapeMove(alias, x1, y1, x2, y2, t1, t2);
    }
    if (r1 != r2 || g1 != g2 || b1 != b2) {
      model.changeColor(alias, r1, g1, b1, r2, g2, b2, t1, t2);
    }
    if (w1 != w2) {
      model.changeWidth(alias, w1, w2, t1, t2);
    }
    if (h1 != h2) {
      model.changeHeight(alias, h1, h2, t1, t2);
    }
    return this;
  }
}
