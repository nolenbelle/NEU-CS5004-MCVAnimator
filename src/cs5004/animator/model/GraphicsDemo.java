package cs5004.animator.model;

import cs5004.animator.view.MyFrame;
import java.io.IOException;

/**
 * A demo of the graphics class.
 */
public class GraphicsDemo {

  /**
   * Used for helping us implement the model graphics view.
   * @param args the user args from the command line
   * @throws IOException if things went wrong with the file
   */
  public static void main(String[] args) throws IOException {
    AnimationModel movie = new AnimationModelImpl();
    movie.setCanvas(0,0,1000,1000);
    Rectangle rect = new Rectangle(1,1,100,100,0,0,0,"rect");
    movie.addShapeToFrames(rect,1,100);
    movie.addShapeToLog(rect);
    Circle circ = new Circle(1,1,100,0,0,0,"circ");
    movie.addShapeToFrames(circ,1,100);
    movie.addShapeToLog(circ);
    movie.shapeMove(rect, rect.getXCoordinate(), rect.getYCoordinate(), 200,200,
            10,30);
    movie.changeColor(circ, circ.getColor()[0], circ.getColor()[1], circ.getColor()[2], 255,
            255,255, 31, 50);
    movie.changeHeight(circ, circ.getHeight(),300,1,5);
    movie.changeWidth(circ,circ.getWidth(),300,1,5);

    MyFrame myFrame = new MyFrame(1000,1000, movie.getEventLog(), 1);
  }
}
