package com.bitshifting.managers;

import com.bitshifting.Main;
import com.bitshifting.state.*;

import java.util.Stack;

public class StateManager {

    //state imnts
    public static final int MAIN_MENU = 0;
    public static final int CHOOSE_STATE = 1;
    public static final int MAIN_GAME = 2;
    public static final int RULES_STATE = 3;
    public static final int WINNER_STATE = 4;

    private Main game;
    public Stack<State> stateStack;

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
        } else if(state == CHOOSE_STATE) {
            return new ChooseState(this);
        } else if(state == RULES_STATE) {
            return new RulesState(this);
        } else if(state == MAIN_GAME) {
            return new MainGame(this);
        } else if(state == WINNER_STATE) {
            return new WinnerState(this);
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

    public void resize(int width, int height) {
        for(State state : stateStack) {
            state.resize(width, height);
        }
    }
}
