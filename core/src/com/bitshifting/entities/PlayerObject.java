package com.bitshifting.entities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.bitshifting.state.MainGame;

import java.util.List;

/**
 * Created by Sam on 4/18/2015.
 */
public class PlayerObject extends GameObject {
    public int playerID; // either player 1 or 2
    public int health; // health value (starts at 1000 currently)
    public ProjectileType currentType; // the current projectile type that the player has

    public static final int PLAYER_1 = 1;
    public static final int PLAYER_2 = 2;

    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int SIDE = 2;

    public static final int STANDING = 1;


    private Sprite body;
    private Sprite[] bodyDown;
    private Sprite[] bodySide;
    private Sprite[] bodyUp;

    private int cycle;

    public PlayerObject(Vector2 position, int id, ProjectileType currentType) {
        super(position, "tito.png");
        this.playerID = id;
        this.currentType = currentType;
        this.health = 1000;

        bodyDown = new Sprite[3];
        bodySide = new Sprite[3];
        bodyUp = new Sprite[3];

        if (PLAYER_1 == id) {
            // set up for player 1
            bodyDown[0] = new Sprite(new Texture("player 1/p1_down_run1.png"));
            bodyDown[1] = new Sprite(new Texture("player 1/p1_down_stand.png"));
            bodyDown[2] = new Sprite(new Texture("player 1/p1_down_run2.png"));

            bodySide[0] = new Sprite(new Texture("player 1/p1_side_run1.png"));
            bodySide[1] = new Sprite(new Texture("player 1/p1_side_stand.png"));
            bodySide[2] = new Sprite(new Texture("player 1/p1_side_run2.png"));

            bodyUp[0] = new Sprite(new Texture("player 1/p1_up_run1.png"));
            bodyUp[1] = new Sprite(new Texture("player 1/p1_up_stand.png"));
            bodyUp[2] = new Sprite(new Texture("player 1/p1_up_run2.png"));
        }
        else if (PLAYER_2 == id) {
            // set up for player 1
            bodyDown[0] = new Sprite(new Texture("player 2/p2_down_run1.png"));
            bodyDown[1] = new Sprite(new Texture("player 2/p2_down_stand.png"));
            bodyDown[2] = new Sprite(new Texture("player 2/p2_down_run2.png"));

            bodySide[0] = new Sprite(new Texture("player 2/p2_side_run1.png"));
            bodySide[1] = new Sprite(new Texture("player 2/p2_side_stand.png"));
            bodySide[2] = new Sprite(new Texture("player 2/p2_side_run2.png"));

            bodyUp[0] = new Sprite(new Texture("player 2/p2_up_run1.png"));
            bodyUp[1] = new Sprite(new Texture("player 2/p2_up_stand.png"));
            bodyUp[2] = new Sprite(new Texture("player 2/p2_up_run2.png"));
        }

        cycle = STANDING;
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
        this.position.x += dt * this.velocity.x * MainGame.VELOCITY_MOD;
        this.position.y += dt * this.velocity.y * MainGame.VELOCITY_MOD;

        this.lastPosition = this.position;
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

    private void downStand() {
        body = bodyDown[STANDING];
        cycle = STANDING;
    }

    private void downRun(int cycle) {
        body = bodyDown[cycle];
        cycle = (cycle + 1) % 3;
    }

    private void upStand() {
        body = bodyUp[STANDING];
        cycle = STANDING;
    }

    private void upRun(int cycle) {
        body = bodyUp[cycle];
        cycle = (cycle + 1) % 3;
    }

    private void sideStand() {
        body = bodySide[STANDING];
        cycle = STANDING;
    }

    private void sideRun(int cycle) {
        body = bodySide[cycle];
        cycle = (cycle + 1) % 3;
    }
}
