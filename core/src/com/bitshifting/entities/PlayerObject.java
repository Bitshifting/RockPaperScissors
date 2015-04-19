package com.bitshifting.entities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Sam on 4/18/2015.
 */
public class PlayerObject extends GameObject {
    public int playerID; // either player 1 or 2
    public int health; // health value (starts at 1000 currently)
    public ProjectileType currentType; // the current projectile type that the player has

    public PlayerObject(Vector2 position, Vector2 boundingBox, int id, ProjectileType currentType) {
        super(position, boundingBox);
        this.playerID = id;
        this.currentType = currentType;
        this.health = 1000;
    }

    public void changeProjectile(ProjectileType newType) {
        this.currentType = newType;
    }

    public ProjectileType getProjectileType() {
        return this.currentType;
    }

    /**
     * Creates a new projectile from the player based on position and the input vector which represents the
     * direction the control stick was pointed in
     *
     * @return
     */
    public ProjectileObject fire(Vector2 direction) {
        //TODO
        return null;
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void handleInput() {

    }

    @Override
    public void render() {

    }

    @Override
    public void dispose() {

    }
}
