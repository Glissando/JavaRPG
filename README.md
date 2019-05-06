# JavaRPG

Final Project for Java Spring 2019.

## Table of Contents

> References to explanations of the sourcecode

- [Architecture](#architecture)
- [AI](#ai)
- [Actor Positions](#positionhandling)
- [Graphics](#graphics)


## Architecture

- The following image displays the depedencies and structure of the project.
- An arrow going up to a node visualizes inheritance and an arrow going to the side means it is a member or field of that class.

<a href=""><img src="https://github.com/Glissando/JavaRPG/blob/master/src/images/architecture.png" title="Architecture" alt="Architecture"></a>

## AI

The AI was built upon from the BrainAI base class which supplies an interface for selecting actions.

## Position Handling

Positioning of abilities/skills is done using bitmasks.

## Code snippet

```java
//Ranks that can be selected
byte targetableRanks;

boolean isRankValid(int index)
{
    return ((1 << (7 - index)) & targetableRanks) != 0;
}
```

## Graphics

All graphics are drawn onto a JPanel. The color of the background changes depending on the amount of danger the player is in, shifting from blue to red. Character images are instances of JLabel and stared as components under the main JPanel instance which acts a manager class.