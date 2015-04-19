package com.bitshifting.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Sam on 4/18/2015.
 * Represents a powerup object. These will be periodically spawned throughout the arena as a random type
 */
public class PowerUpObject extends GameObject {
    ProjectileType type; // The type that the player can change to
    public int timeToLive; // The amount of time this power up exists before it must be deleted
    boolean valid; // Set to false when it should be deleted

    public PowerUpObject(Vector2 position, ProjectileType type){
        super(position, "tito.png");
        this.type = type;
        this.valid = true;
    }

    public void setValid(boolean valid){
        this.valid = valid;
    }

    public boolean isValid(){
        return this.valid;
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
