package cs5004.animator.controller;

/**
 * The animation controller. So this is an MVC project, right? Therefore we need a controller.
 * Our controller however is really just a listener. When the button is clicked on the view,
 * our controller is the listener for that button. It then tells the button UI and the PlaybackView
 * what it needs to do in response to the button (via their own method calls). So there are no
 * methods other than action performed. So we have this interface because it feels like we have to
 * have it... but clearly we don't need it. We left it in here however because if this was ever to
 * be extended, you would likely need to have an interface.
 */
public interface AnimationController {
}
