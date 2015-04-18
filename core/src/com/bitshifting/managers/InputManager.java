package com.bitshifting.managers;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import javax.sound.midi.ControllerEventListener;

/**
 * Created by sschwebach on 4/18/15.
 * This class interprets all inputs based on which mode we're doing and what input devices we have.
 * For example, when there are two controllers connected it routes each to one player.
 * When there is one controller, it uses the keyboard for player 1 and the controller for player 2
 * Online mode is a bit more complicated. First, we need to know if we've been assigned player 1 or 2
 * Once we know, that player uses local input and the other player uses input from a web caller (if we get to it)
 */
public class InputManager implements ControllerListener {
    double player1X, player1Y, player2X, player2Y; // The various player directoins
    boolean player1Fire, player2Fire, pause; // The various player button presses
    Controller player1, player2;

    public InputManager(){
        Array<Controller> controllers = Controllers.getControllers();
        player1 = controllers.get(0);
        //player2 = controllers.removeIndex(0);
    }

    @Override
    public void connected(Controller controller) {
        // I hope we don't have to mess with this
    }

    @Override
    public void disconnected(Controller controller) {
        // I hope we don't have to mess with this
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        System.out.println("Got Controller code " + buttonCode);
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        return false;
    }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {
        return false;
    }

    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        return false;
    }
}
