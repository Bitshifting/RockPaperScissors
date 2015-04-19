package com.bitshifting.state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.bitshifting.CollisionEvent;
import com.bitshifting.entities.GameObject;
import com.bitshifting.entities.PlayerObject;
import com.bitshifting.entities.ProjectileObject;
import com.bitshifting.managers.InputManager;
import com.bitshifting.managers.StateManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sschwebach on 4/18/15.
 */
public class MainGame extends State{
    // References into the entities array!
    PlayerObject player1;
    PlayerObject player2;
    float velocityMod = 1.0f; //change this to correctly adjust the velocity to a reasonable level

    SpriteBatch batch; // Our sprite batch for rendering
    List<GameObject> entities; // the list of game entities

    public MainGame(StateManager sm){
        super(sm);
        entities = new ArrayList<GameObject>(); // Initialize our game object list

    }

    @Override
    public void handleInput() {
        InputManager manager = InputManager.getInstance();
        // We want to only handle inputs that would alter the game state (pause, fire, movement keys)
        // See if the pause key is pressed
        if (manager.getPlayerStart(1) || manager.getPlayerStart(2)){
            //Pause the game
        }
        // See if either fire key is pressed and handle accordingly
        // First get player 1's joystick input
        Vector2 player1Fire = manager.player1RightStick;
        Vector2 player2Fire = manager.player2RightStick;
        // Now see if these are over a threshold (.5 or something)
        // TODO add some timer so that we don't shoot too fast probably
        if (player1Fire.len() > 0.5f){
            ProjectileObject newBullet = player1.fire(player1Fire);
            entities.add(newBullet);
        }
        if (player2Fire.len() > 0.5f){
            ProjectileObject newBullet = player2.fire(player2Fire);
            entities.add(newBullet);
        }

        // Now look at the left stick and assign player velocities
        // Get the input values for the joysticks
        Vector2 player1Vel = manager.player1LeftStick;
        Vector2 player2Vel = manager.player2LeftStick;
        // TODO might need to flip magnitudes of stick inputs (weird)
        player1.velocity = player1Vel;
        player2.velocity = player2Vel;
        // I think that's it
    }

    @Override
    public void update(float dt) {
        ArrayList<CollisionEvent> collisionEvents = new ArrayList<CollisionEvent>();

        // Move all entities to their next position based on their velocity
        for (GameObject o : entities) {
            o.lastPosition = o.position;
            o.position = new Vector2(o.position.x + o.velocity.x*dt*velocityMod, o.position.y + o.velocity.y*dt*velocityMod);
            o.bouncing = false;
        }


        // Now that everything is updated, check new positions for conflicts in the bounding boxes, and if there are
        // conflicts, move back the entities to their previous position.
        for (GameObject o : entities) {
            // Check this entity against every other entity (o(n^2) heck yeah) and see if it collides. if it does, mark
            // both entities as bouncing.
            for (GameObject p : entities) {
                if (o.collidesWith(p)) {
                    o.bouncing = true;
                    p.bouncing = true;
                    collisionEvents.add(new CollisionEvent(o, p));
                }
            }
        }

        // Check for bounces and restore their previous positions.
        for (GameObject o : entities) {
            if (o.bouncing) {
                o.position = o.lastPosition;
            }
        }

        /*
        Things we want to do:
        We want to detect collisions between players/bullets/walls
        Update player health, despan bullets that collide. Update positions in general
        Randomly spawn powerups
         */
        // First see if any player movement has been performed. If so, give the player a velocity and update their positions
        // Now update the positions of all the bullets based on their velocities
        // Collision detection. We now have the updated positions of all the objects
        // Iterate through the moving objects (players, bullets) and determine if they collide
        /*
        Collision rules:
        A player hitting a wall: Take the position and add a negative velocity to it (this will effectively undo movement)
        A player hitting a player: Take the position and add a negative veolcity to both players (undoing both their movements)
        A bullet hitting a player: Assume the bullet was fired from the other player, so don't check.
            Despawn the bullet and decrease the player's health by some amount
        A bullet hitting a wall: Despawn the bullet
        A bullet hitting another bullet: It can do one of three things depending on the types of the two bullets
            Bullet inferior: Despawn the bullet (other bullet is unaffected)
            Bullets equal: Despawn both bullets
            Bullet greater: Despawn the other bullet (this bullet is unaffected)
        Walls cannot (hopefully) collide with other walls, so we'll leave them be
         */


        Iterator<GameObject> objectIterator = entities.iterator();

        // Determine if a player has died in the last update, if so, end the game or something
    }

    @Override
    public void render() {
        batch.begin();

        for (GameObject entity : entities){
            batch.draw(entity.texture, entity.position.x, entity.position.y);
        }

        batch.end();
    }

    @Override
    public void dispose() {
        // dispose of our textures if we want
    }
}
