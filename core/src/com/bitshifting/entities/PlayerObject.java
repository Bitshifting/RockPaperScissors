package com.bitshifting.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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

    public static final int PLAYER_1 = 1;
    public static final int PLAYER_2 = 2;

    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int SIDE = 2;

    public static final int STANDING = 1;
    private int cycle;
    private int lastDirection;

    private Texture[] bodyDown;
    private Texture[] bodySide;
    private Texture[] bodyUp;

    boolean flipped = false;

    public PlayerObject(Vector2 position, int id, ProjectileType currentType) {
        super(position, "tito.png");
        this.playerID = id;
        this.currentType = currentType;
        this.health = 1000;

        bodyDown = new Texture[3];
        bodySide = new Texture[3];
        bodyUp = new Texture[3];

        if (PLAYER_1 == id) {
            // set up for player 1
            bodyDown[0] = new Texture("player 1/p1_down_run1.png");
            bodyDown[1] = new Texture("player 1/p1_down_stand.png");
            bodyDown[2] = new Texture("player 1/p1_down_run2.png");

            bodySide[0] = new Texture("player 1/p1_side_run1.png");
            bodySide[1] = new Texture("player 1/p1_side_stand.png");
            bodySide[2] = new Texture("player 1/p1_side_run2.png");

            bodyUp[0] = new Texture("player 1/p1_up_run1.png");
            bodyUp[1] = new Texture("player 1/p1_up_stand.png");
            bodyUp[2] = new Texture("player 1/p1_up_run2.png");
        }
        else if (PLAYER_2 == id) {
            // set up for player 1
            bodyDown[0] = new Texture("player 2/p2_down_run1.png");
            bodyDown[1] = new Texture("player 2/p2_down_stand.png");
            bodyDown[2] = new Texture("player 2/p2_down_run2.png");

            bodySide[0] = new Texture("player 2/p2_side_run1.png");
            bodySide[1] = new Texture("player 2/p2_side_stand.png");
            bodySide[2] = new Texture("player 2/p2_side_run2.png");

            bodyUp[0] = new Texture("player 2/p2_up_run1.png");
            bodyUp[1] = new Texture("player 2/p2_up_stand.png");
            bodyUp[2] = new Texture("player 2/p2_up_run2.png");
        }

        cycle = STANDING;
        sideStand(id == PLAYER_2);

        sprite.setSize(sprite.getWidth() * 0.5f, sprite.getHeight() * 0.5f);
        sprite.setPosition(position.x, position.y);
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
        this.lastPosition = new Vector2(this.position);
        this.position.x += dt * this.velocity.x * MainGame.VELOCITY_MOD;
        this.position.y += dt * this.velocity.y * MainGame.VELOCITY_MOD;

        float thresholdVelocity = 0.2f;

        if (Math.abs(this.velocity.x) < 0.001f && Math.abs(this.velocity.y) < 0.001f) {
            callStand();
        }

        //use x direction
        else if(Math.abs(this.velocity.x) > Math.abs(this.velocity.y)) {
            if(this.velocity.x < -thresholdVelocity) {
                sideRun(true);
            } else if(this.velocity.x > thresholdVelocity) {
                sideRun(false);
            } else {
                callStand();
            }
        }

        //use y direction
        else if(Math.abs(this.velocity.y) > Math.abs(this.velocity.x)) {
            if(this.velocity.y < -thresholdVelocity) {
                downRun();
            } else if(this.velocity.y > thresholdVelocity) {
                upRun();
            } else {
                callStand();
            }
        } else {
            if(this.velocity.y < -thresholdVelocity) {
                downRun();
            } else if(this.velocity.y > thresholdVelocity) {
                upRun();
            } else {
                callStand();
            }
        }

        sprite.setPosition(this.position.x, this.position.y);

        if (this.position.x < 0)
            this.position.x = 0;
        if (this.position.y < 0)
            this.position.y = 0;
        if (this.position.y + this.sprite.getHeight() > Gdx.graphics.getHeight())
            this.position.y = Gdx.graphics.getHeight() - this.sprite.getHeight();
        if (this.position.x + this.sprite.getWidth() > Gdx.graphics.getWidth())
            this.position.x = Gdx.graphics.getWidth() - this.sprite.getWidth();

        this.bouncing = false;
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    @Override
    public void dispose() {

    }

    private void callStand() {
        switch (lastDirection) {
            case UP:
                upStand();
                break;
            case DOWN:
                downStand();
                break;
            case SIDE:
                sideStand(flipped);
                break;
        }
    }

    private void downStand() {
        sprite.setTexture(bodyDown[STANDING]);
        cycle = STANDING;
        lastDirection = DOWN;
    }

    private void downRun() {
        sprite.setTexture(bodyDown[cycle]);
        cycle = (cycle + 1) % 3;
        lastDirection = DOWN;
    }

    private void upStand() {
        sprite.setTexture(bodyUp[STANDING]);
        cycle = STANDING;
        lastDirection = UP;
    }

    private void upRun() {
        sprite.setTexture(bodyUp[cycle]);
        cycle = (cycle + 1) % 3;
        lastDirection = UP;
    }

    private void sideStand(boolean flip) {
        sprite.setFlip(flip, false);
        sprite.setTexture(bodySide[STANDING]);
        cycle = STANDING;
        lastDirection = SIDE;
    }

    private void sideRun(boolean flip) {
        sprite.setFlip(flip, false);
        sprite.setTexture(bodySide[cycle]);
        cycle = (cycle + 1) % 3;
        lastDirection = SIDE;
    }
}
