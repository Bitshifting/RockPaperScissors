package com.bitshifting.entities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.bitshifting.state.MainGame;

/**
 * Created by Sam on 4/18/2015.
 */
public class PlayerObject extends GameObject {
    public int playerID; // either player 1 or 2
    public int health; // health value (starts at 1000 currently)
    public ProjectileType currentType; // the current projectile type that the player has

    public PlayerObject(Vector2 position, int id, ProjectileType currentType) {
        super(position, "tito.png");
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

        Vector2 newDirection = new Vector2(0, 0);

        float thresholdForShooting = 0.5f;

        //set the velocity direction

        if(Math.abs(direction.x) > thresholdForShooting) {
            if(direction.x > thresholdForShooting) {
                newDirection.x = 1.f;
            } else if( direction.x < thresholdForShooting) {
                newDirection.x = -1.f;
            }
        }

        if(Math.abs(direction.y) > thresholdForShooting) {
            if(direction.y > thresholdForShooting) {
                newDirection.y = 1.f;
            } else if (direction.y < thresholdForShooting) {
                newDirection.y = -1.f;
            }
        }

        //normalize the velocity
        newDirection.nor();

        if(newDirection.len() < 0.5f) {
            return null;
        }

        ProjectileObject projectile;

        if(this.currentType == ProjectileType.ROCK) {
            projectile = new RockProjectile(new Vector2(this.position), ProjectileType.ROCK, this.playerID);

        } else if(this.currentType == ProjectileType.PAPER) {
            projectile = new PaperProjectile(new Vector2(this.position), ProjectileType.ROCK, this.playerID);
        } else {
            projectile = new ScissorProjectile(new Vector2(this.position), ProjectileType.ROCK, this.playerID);
        }

        projectile.velocity = newDirection;

        return projectile;
    }

    @Override
    public void update(float dt) {
        this.lastPosition = this.position;

        this.position = new Vector2(this.position.x + dt * this.velocity.x * MainGame.VELOCITY_MOD, this.position.y + dt * this.velocity.y * MainGame.VELOCITY_MOD);


        this.bouncing = false;
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
