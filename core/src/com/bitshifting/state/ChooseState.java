package com.bitshifting.state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bitshifting.managers.InputManager;
import com.bitshifting.managers.StateManager;

public class ChooseState extends State {
    public SpriteBatch batch;

    //checks whether or not the player has pressed a button yet
    public boolean player1Pressed;
    public boolean player2Pressed;


    public ChooseState(StateManager sm) {
        super(sm);
        batch = new SpriteBatch();

        player1Pressed = false;
        player2Pressed = false;
    }

    @Override
    public void handleInput() {
        //let's the user choose the input
        
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render() {
        batch.begin();
        batch.end();
    }

    @Override
    public void dispose() {

    }
}
