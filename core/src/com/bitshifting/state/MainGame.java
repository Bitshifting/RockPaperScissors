package com.bitshifting.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.bitshifting.CollisionEvent;
import com.bitshifting.entities.*;
import com.bitshifting.managers.InputManager;
import com.bitshifting.managers.StateManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sschwebach on 4/18/15.
 */
public class MainGame extends State{
    public static final float WIDTH_OF_WALL = (float) Gdx.graphics.getWidth() / 10.f;
    // References into the entities array!
    PlayerObject player1;
    PlayerObject player2;
    public static final float VELOCITY_MOD = 100.0f; //change this to correctly adjust the velocity to a reasonable level

    public static final float BULLET_TIMER = 0.5f;

    SpriteBatch batch; // Our sprite batch for rendering
    List<GameObject> entities; // the list of game entities

    Sprite floor;
    Sprite walls;

    Texture w;
    Texture f;
    InputManager inputs; // The manager for the inputs

    //set a time for the first time
    private boolean isFirstTime = true;
    private float timerStart = 0;
    private Sprite timerSprite;

    private Texture threeTex;
    private Texture twoTex;
    private Texture oneTex;
    private Texture goTex;

    float p1Shoot = 0.f;
    boolean p1Bullet = false;
    float p2Shoot = 0.f;
    boolean p2Bullet = false;

    public MainGame(StateManager sm){
        super(sm);
        float height = Gdx.graphics.getHeight();
        float width = Gdx.graphics.getWidth();
        entities = new ArrayList<GameObject>(); // Initialize our game object list
        f = new Texture("arena/arena_floor.png");
        w = new Texture("arena/arena_walls.png");

        floor = new Sprite(f);
        walls = new Sprite(w);

        floor.setPosition(0, 0);
        walls.setPosition(0, 0);

        floor.setSize(width, height);
        walls.setSize(width, height);

        batch = new SpriteBatch();

        threeTex = new Texture("loadingScreen/three.png");
        twoTex = new Texture("loadingScreen/two.png");
        oneTex = new Texture("loadingScreen/one.png");
        goTex = new Texture("loadingScreen/go.png");

        timerSprite = new Sprite(threeTex);
        timerSprite.setSize(timerSprite.getWidth() * 0.4f, timerSprite.getHeight() * 0.4f);
        timerSprite.setCenter((float)Gdx.graphics.getWidth() / 2.f, (float) Gdx.graphics.getHeight() / 2.f);
    }

    @Override
    public void handleInput() {

        if(isFirstTime) {
            return;
        }

        InputManager manager = InputManager.getInstance();
        // We want to only handle inputs that would alter the game state (pause, fire, movement keys)
        // See if the pause key is pressed
        if (manager.getPlayerStart(1) || manager.getPlayerStart(2)){
            //Pause the game
        }

        // Now look at the left stick and assign player velocities
        // Get the input values for the joysticks
        Vector2 player1Vel = manager.player1LeftStick;
        Vector2 player2Vel = manager.player2LeftStick;
        // TODO might need to flip magnitudes of stick inputs (weird)
        player1.velocity = player1Vel;
        player2.velocity = player2Vel;
        // I think that's it
        // See what movement keys are pressed
        // MAKE SURE TO NOT ACTUALLY MOVE/MAKE AN OBJECT BUT RATHER JUST SET A BOOLEAN THAT WILL BE HANDLED IN update
    }

    @Override
    public void update(float dt) {

        if(isFirstTime) {
            timerStart += dt;

            if(timerStart > 4.f) {
                isFirstTime = false;
            } else if(timerStart > 3.f) {
                timerSprite.setTexture(goTex);
            } else if(timerStart > 2.f) {
                timerSprite.setTexture(oneTex);
            } else if (timerStart > 1.f) {
                timerSprite.setTexture(twoTex);
            }

            return;
        }

        if(p1Bullet) {
            p1Shoot += dt;

            if(p1Shoot > BULLET_TIMER) {
                p1Shoot = 0.f;
                p1Bullet = false;
            }
        }

        if(p2Bullet) {
            p2Shoot += dt;

            if(p2Shoot > BULLET_TIMER) {
                p2Shoot = 0.f;
                p2Bullet = false;
            }
        }

        // See if either fire key is pressed and handle accordingly
        // First get player 1's joystick input
        Vector2 player1Fire = InputManager.getInstance().player1RightStick;
        Vector2 player2Fire = InputManager.getInstance().player2RightStick;
        // Now see if these are over a threshold (.5 or something)

        if ((player1Fire.len() > 0.5f) && !p1Bullet) {
            ProjectileObject newBullet = player1.fire(player1Fire);
            if(newBullet != null) {
                entities.add(newBullet);
                p1Bullet = true;
            }
        }
        if ((player2Fire.len() > 0.5f) && !p2Bullet){
            ProjectileObject newBullet = player2.fire(player2Fire);
            if(newBullet != null) {
                entities.add(newBullet);
                p2Bullet = true;
            }
        }

        // Run update on all the entities
        for (GameObject o : entities) {
            o.update(dt);
        }

        ArrayList<CollisionEvent> collisionEvents = new ArrayList<CollisionEvent>();

        // Now that everything is updated, check new positions for conflicts in the bounding boxes, and if there are
        // conflicts, move back the entities to their previous position.
//        for (GameObject o : entities) {
//            // Check this entity against every other entity (o(n^2) heck yeah) and see if it collides. if it does, mark
//            // both entities as bouncing.
//            for (GameObject p : entities) {
//                if (o.collidesWith(p)) {
//                    o.bouncing = true;
//                    p.bouncing = true;
//                    collisionEvents.add(new CollisionEvent(o, p));
//                }
//            }
//        }
//
//        // Check for bounces and restore their previous positions.
//        for (GameObject o : entities) {
//            if (o.bouncing) {
//                o.position = o.lastPosition;
//            }
//        }
//
//        for (CollisionEvent c : collisionEvents) {
//
//            if (c.collider1 instanceof ProjectileObject && c.collider2 instanceof ProjectileObject) {
//                // Resolve which one is better and kill the inferior object; in a tie, kill both
//                ProjectileObject p1 = (ProjectileObject)c.collider1;
//                ProjectileObject p2 = (ProjectileObject)c.collider2;
//
//                int winner = 0; // if 1, it's p1, if 2, it's p2, 0 = tie
//
//                // Switch on the other projectile
//                switch (p1.type) {
//                    case PAPER:
//                        switch (p2.type) {
//                            case PAPER:
//                                winner = 0;
//                                break;
//                            case ROCK:
//                                winner = 1;
//                                break;
//                            case SCISSOR:
//                                winner = 2;
//                                break;
//                        }
//                        break;
//                    case ROCK:
//                        switch (p2.type) {
//                            case PAPER:
//                                winner = 2;
//                                break;
//                            case ROCK:
//                                winner = 0;
//                                break;
//                            case SCISSOR:
//                                winner = 1;
//                                break;
//                        }
//                        break;
//                    case SCISSOR:
//                        switch (p2.type) {
//                            case PAPER:
//                                winner = 1;
//                                break;
//                            case ROCK:
//                                winner = 2;
//                                break;
//                            case SCISSOR:
//                                winner = 0;
//                                break;
//                        }
//                        break;
//                }
//
//                switch (winner) {
//                    case 0:
//                        entities.remove(p1);
//                        entities.remove(p2);
//                        break;
//                    case 1:
//                        entities.remove(p2);
//                        break;
//                    case 2:
//                        entities.remove(p1);
//                        break;
//                }
//            }
//
//
//            // Player v projectile collision
//            if ((c.collider1 instanceof PlayerObject && c.collider2 instanceof ProjectileObject) ||
//                    (c.collider1 instanceof ProjectileObject && c.collider2 instanceof PlayerObject)) {
//                PlayerObject p = (PlayerObject) (c.collider1 instanceof PlayerObject ? c.collider1 : c.collider2);
//                ProjectileObject j = (ProjectileObject) (c.collider1 instanceof  ProjectileObject ? c.collider1 : c.collider2);
//
//                p.health -= 50;
//
//                // remove the projectile
//                entities.remove(j);
//            }
//
//            // projectile vs wall collision - remove the projectile
//            if ((c.collider1 instanceof ProjectileObject && c.collider2 instanceof WallObject )  ||
//                    (c.collider1 instanceof ProjectileObject && c.collider2 instanceof WallObject)) {
//                ProjectileObject p = (ProjectileObject) (c.collider1 instanceof  ProjectileObject ? c.collider1 : c.collider2);
//
//                entities.remove(p);
//            }
//
//
//            // Player vs powerup - pick up the powerup!
//            if ((c.collider1 instanceof PowerUpObject && c.collider2 instanceof PlayerObject) ||
//                    (c.collider1 instanceof PlayerObject && c.collider2 instanceof PowerUpObject)) {
//                // Change the type of the player to the powerup
//                PlayerObject p = (PlayerObject) (c.collider1 instanceof  PowerUpObject? c.collider1: c.collider2);
//                ProjectileObject j = (ProjectileObject)(c.collider1 instanceof  PowerUpObject? c.collider2: c.collider1);
//                p.currentType = j.type;
//                entities.remove(j);
//            }
//
//        }

        // Determine if a player has died in the last update, if so, end the game or something
    }

    @Override
    public void render() {
        batch.begin();

        floor.draw(batch);
        walls.draw(batch);

        for (GameObject entity : entities) {
            entity.render(batch);
        }

        if(isFirstTime) {
            timerSprite.draw(batch);
        }

        batch.end();
    }

    @Override
    public void dispose() {
        // dispose of our textures if we want
    }
}
