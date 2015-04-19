package com.bitshifting.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.bitshifting.entities.GameObject;
import com.bitshifting.managers.InputManager;
import com.bitshifting.managers.StateManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sschwebach on 4/18/15.
 */
public class MainGame extends State{
    GameObject player1;
    GameObject player2;

    SpriteBatch batch; // Our sprite batch for rendering
    List<GameObject> entities; // the list of game entities
    InputManager inputs; // The manager for the inputs

    public MainGame(StateManager sm){
        super(sm);
        entities = new ArrayList<GameObject>(); // Initialize our game object list

    }

    @Override
    public void handleInput() {
        // We want to only handle inputs that would alter the game state (pause, fire, movement keys)
        // See if the pause key is pressed

        // See if either fire key is pressed and handle accordingly

        // See what movement keys are pressed
        // MAKE SURE TO NOT ACTUALLY MOVE/MAKE AN OBJECT BUT RATHER JUST SET A BOOLEAN THAT WILL BE HANDLED IN update
    }

    @Override
    public void update(float dt) {
        // Move all entities to their next position based on their velocity
        for (GameObject o : entities) {
            o.lastPosition = o.position;
            o.position = new Vector2(o.position.x + o.velocity.x, o.position.y + o.velocity.y);
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

        // First iterate through the players and detect the first two

        // Now iterate through the bullets and detect the next three


        Iterator<GameObject> objectIterator = entities.iterator();

        // Determine if a player has died in the last update, if so, end the game or something
    }

    @Override
    public void render() {
        batch.begin();

        for (GameObject entity : entities){
            batch.draw(entity.texture, entity.position.x, entity.position.y);
        }

        // Draw the players on top of other entities
        batch.draw(player1.texture, player1.position.x, player1.position.y);
        batch.draw(player2.texture, player2.position.x, player2.position.y);

        batch.end();
    }

    @Override
    public void dispose() {
        // dispose of our textures if we want
    }
}
