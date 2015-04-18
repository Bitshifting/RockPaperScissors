package com.bitshifting.state;

import com.bitshifting.Main;
import com.bitshifting.managers.StateManager;

/**
 * Created by jdallman2570 on 2/23/2015.
 */
public abstract class State {
    protected StateManager sm;
    protected Main game;



    public State(StateManager sm) {
        this.sm = sm;
        game = sm.getGame();

    }

    public abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render();
    public abstract void dispose();

}