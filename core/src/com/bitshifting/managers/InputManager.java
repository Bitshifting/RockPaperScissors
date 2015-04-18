package com.bitshifting.managers;

/**
 * Created by sschwebach on 4/18/15.
 * This class interprets all inputs based on which mode we're doing and what input devices we have.
 * For example, when there are two controllers connected it routes each to one player.
 * When there is one controller, it uses the keyboard for player 1 and the controller for player 2
 * Online mode is a bit more complicated. First, we need to know if we've been assigned player 1 or 2
 * Once we know, that player uses local input and the other player uses input from a web caller (if we get to it)
 */
public class InputManager {
}
