# UML
I just made it in google sheet. Here is the link incase you need to modify any of the classes:
https://docs.google.com/presentation/d/170qbRaJAqiKrxdKrBAxzHm-IPTm4KlmKPjiQ8XGB8m0/edit?usp=sharing

Overview:

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
There is a main method in the Demo class which will print out an example of the textual representation.

Changes after implementing the View:
- the individual changes are well documented within the code
- on a structural level, no sweeping changes were made
- multiple methods were decomposed into two pieces once we realized that they were doing two things which the 'controller' would need to use separatly
- initially, animation changes were being made on whatever the shape's stating state was. We changed it to were you now give the 
 change method both the starting and ending values. This made it easier to match the given files to animate and is also a logical way to go about it.
