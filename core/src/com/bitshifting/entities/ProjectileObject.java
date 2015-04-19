package com.bitshifting.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Sam on 4/18/2015.
 * A projectile object. These will be spawned from the player objects when the fire button is hit
 * Has a specific type that is set when fired. This type shouldn't change
 */
public class ProjectileObject extends GameObject {
    public ProjectileType type; // type of projectile it is (rock, paper, scissors)
    public int damageAmount; // base damage amount before modifiers
    public int playerID; // the ID of the player that fired it (just in case we need it)

    public ProjectileObject(Vector2 position, Vector2 boundingBox, ProjectileType type, int playerID) {
        super(position, boundingBox);
        this.type = type;
        this.damageAmount = 100;
        this.playerID = playerID;
    }

    @Override
    public void update(float dt) {
        this.position.x = this.velocity.x * dt;
        this.position.y = this.velocity.y * dt;
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
