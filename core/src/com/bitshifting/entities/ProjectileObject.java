package com.bitshifting.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.bitshifting.state.MainGame;

/**
 * Created by Sam on 4/18/2015.
 * A projectile object. These will be spawned from the player objects when the fire button is hit
 * Has a specific type that is set when fired. This type shouldn't change
 */
public class ProjectileObject extends GameObject {
    public ProjectileType type; // type of projectile it is (rock, paper, scissors)
    public int damageAmount; // base damage amount before modifiers
    public int playerID; // the ID of the player that fired it (just in case we need it)

    public ProjectileObject(Vector2 position, ProjectileType type, int playerID, String img) {
        super(position, img);
        this.type = type;
        this.damageAmount = 100;
        this.playerID = playerID;
        this.rotation = 0.f;
    }

    @Override
    public void update(float dt) {
        this.position.x += dt * this.velocity.x * MainGame.VELOCITY_MOD;
        this.position.y += dt * this.velocity.y * MainGame.VELOCITY_MOD;
        this.rotation += dt * 360.f;

        while(this.rotation > 360.f) {
            this.rotation -= 360.f;
        }

        this.sprite.setPosition(this.position.x, this.position.y);
        this.sprite.setRotation(this.rotation);
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void render(SpriteBatch batch) {
        this.sprite.draw(batch);
    }

    @Override
    public void dispose() {

    }
}
