package com.bitshifting.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Sam on 4/18/2015.
 * Represents a wall object. These will be (randomly?) placed at the beginning of the game throughout the arena.
 * Not really much to this class. Walls are simple creatures with very few goals in life.
 */
public class WallObject extends GameObject{

    public WallObject(Vector2 position, Vector2 boundingBox){
        super(position, boundingBox);
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void handleInput() {

    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(this.texture, this.position.x, this.position.y);
    }

    @Override
    public void dispose() {

    }
}
