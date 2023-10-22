# Module Sovereign
Sovereign is an autonomous-centric FTC utility library created by FIRST FTC team 19922. It's goal is
to make the prototyping and development of autonomous (and teleop!) modes easier and faster for
advanced and rookie teams alike.

Sovereign is lead by
[Teo Welton](https://github.com/DragonDev07),
[Milo Banks](https://github.com/IsaccBarker), and
[Jack Revoy](https://github.com/blazeboy75).

# Package org.ironlions.sovereign
Latin words!

# Package org.ironlions.sovereign.geometry
Basic geometry functionality.

These are not typically used by themselves, and are instead  meant as a delivery system for things
like [pathfinding](org.ironlions.sovereign.pathfinding).

# Package org.ironlions.sovereign.math
Basic math related utilities.

# Package org.ironlions.sovereign.pathfinding
Computes a path from point A to point B, considering obstacles and the future.

Using this package, we can compute the most optimal path from the a position (usually the current
one) to a destination. It can avoid obstacles, do basic dynamic entity prediction, and more. It
works on a pipelined architecture; the pathfinding pipeline plays out as follows, assuming we
already have a populated [Environment](org.ironlions.sovereign.pathfinding.field.Environment).

1. Spacial Data Fitting
2. Cell Weighting
3. Pathfinding
4. Curve Interpolation
5. Execution

# Package org.ironlions.sovereign.pathfinding.environment.entities
A collection of stock field entities for use in [pathfinding](org.ironlions.sovereign.pathfinding).

#  Package org.ironlions.sovereign.pathfinding.environment
Classes and utilities to describe an environment/field.

# Package org.ironlions.sovereign.pathfinding.fitting
Fits the ontological environment description into a form ready for pathfinding.

# Package org.ironlions.sovereign.smc
A generic, abstracted state machine controller.
