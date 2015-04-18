package com.bitshifting.managers;

import com.bitshifting.Main;
import com.bitshifting.state.MainMenu;
import com.bitshifting.state.State;

import java.util.Stack;
/**
 * Created by jdallman2570 on 2/23/2015.
 */
public class StateManager {

    //state imnts
    public static final int MAIN_MENU = 0;

    private Main game;
    private Stack<State> stateStack;

    //constructor
    public StateManager(Main main)
    {
        this.game = main;
        stateStack= new Stack<State>();
        pushState(MAIN_MENU);
    }

    public Main getGame() { return game; }

    //update only the top state in the stack
    public void update(float dt) {
        stateStack.peek().handleInput();
        stateStack.peek().update(dt);
    }

    //render all states in the stack
    public void render() {
        //in java, this will go from bottom to the top
        for(State state : stateStack) {
            state.render();
        }
    }

    public void setState(int state) {
        popState();
        pushState(state);
    }

    private State getState(int state) {

        //find the state to get
        if(state == MAIN_MENU) {
            return new MainMenu(this);
        }

        return null;
    }

    public void pushState(int state) {
        stateStack.push(getState(state));
    }

    public void popState() {
        State state = stateStack.pop();
        state.dispose();
    }
}
