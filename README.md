Model Overview:

The central aspect of our design is that the model can be told to modify and create shape/image data which is then stored in a list of 'frames'. The controller can then instuct the view how to display the shape data and how quickly to flip through the frames which will create an animation.

Design:
We have 3 interfaces: AnimationModel, Frame, & Shape.
- AnimationModel of course holds all of the methods that the controller will use to dictate the events in the animation
- Frames holds the data of the animation at one 'tick' mark in the sequence
- Shapes are the objects in the animation.

AnimationModel & Frame are implemented in AnimationModelImpl and FrameImpl. Shapes is implemented by AbstractShape which is then extended by Circle, Square, & Rectangle.

The AnimationModelImpl has a List of Frames as it's field. This is basically like a movie reel, with each Frame representing one moment in the film. The AnimationModelImple implements most all of it's methods by getting the Frames which are being modified and having each frame call on it's internal shape data to mutate. So AnimationModel has Frames, and Frames each have their respective shapes.

Output:
The AnimationModelImpl also has a field 'eventLog'. As any shape is added or mutated, that action is recorded in the eventLog and the textual representation of the animation is the result of printing that log.
The list of frames and the list of Strings are the two ways that the model format data for views.


View Over'view':

Changes after implementing the View:
- the individual changes are well documented within the code
- on a structural level, no sweeping changes were made
- multiple methods were decomposed into two pieces once we realized that they were doing two things which the 'controller' would need to use separatly
- initially, animation changes were being made on whatever the shape's stating state was. We changed it to were you now give the 
 change method both the starting and ending values. This made it easier to match the given files to animate and is also a logical way to go about it.

View Design:
Our view is very generic, the interface promises that every view will paintComponents ie it will visually display the given data from the model. The implementation of 
that visual is left entirely up to the implementaion of each specific view. This turned into a slight hinderence in HW8 but was overall a good design choice, we think.

Controller Overview:
Changes made after HW7:
- no changes were made other than the creation of the new files for the specific new view (meaning the ComponantFrame, PlaybackUI, PlaybackView, and the controller classes).
- arguably, we should have created a new view interface for the new view because it doesn't actully fit all that well with the others, but we liked
  the organizational structure of the super interface and it had enough benefits that we were willing to make the trade off.
  
Design Aspects:
- the controller is set as the event listener for the buttons in the view. When a button is clicked, the controller then tells the view what to do. This is our main
  strong point for loose coupling because you could easily modify what the buttons do without going into the view which is as it should be since the action as a result of
  a button should be the purview of the controller. So the model data is given to the view, the view displays buttons and the animation of the data, when a button is
  called, the controller tells the view how to change the display and tada.
  
  
  Known Bugs:
  - SVG initializes the values of the shape with their final values. This issues comes from somewhere in the model where the data is being stored 
    in the field List<Shapes> shape. This leads so some animation issues where it just isn't quite right.
  - The two visual views both have some  sizing issues where ideally it would set the frame sizes better
  - We didn't account for the offset values of the positioning.

