package com.bitshifting.state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.bitshifting.CollisionEvent;
import com.bitshifting.entities.GameObject;
import com.bitshifting.entities.ProjectileObject;
import com.bitshifting.managers.InputManager;
import com.bitshifting.managers.StateManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sschwebach on 4/18/15.
 */
public class MainGame extends State{
    // References into the entities array!
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
        ArrayList<CollisionEvent> collisionEvents = new ArrayList<CollisionEvent>();

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

        for (CollisionEvent c : collisionEvents) {

            if (c.collider1 instanceof ProjectileObject && c.collider2 instanceof ProjectileObject) {
                // Resolve which one is better and kill the inferior object; in a tie, kill both
                ProjectileObject p1 = (ProjectileObject)c.collider1;
                ProjectileObject p2 = (ProjectileObject)c.collider2;

                int winner = 0; // if 1, it's p1, if 2, it's p2, 0 = tie

                // Switch on the other projectile
                switch (p1.type) {
                    case PAPER:
                        switch (p2.type) {
                            case PAPER:
                                winner = 0;
                                break;
                            case ROCK:
                                winner = 1;
                                break;
                            case SCISSOR:
                                winner = 2;
                                break;
                        }
                        break;
                    case ROCK:
                        switch (p2.type) {
                            case PAPER:
                                winner = 2;
                                break;
                            case ROCK:
                                winner = 0;
                                break;
                            case SCISSOR:
                                winner = 1;
                                break;
                        }
                        break;
                    case SCISSOR:
                        switch (p2.type) {
                            case PAPER:
                                winner = 1;
                                break;
                            case ROCK:
                                winner = 2;
                                break;
                            case SCISSOR:
                                winner = 0;
                                break;
                        }
                        break;
                }

                switch (winner) {
                    case 0:
                        entities.remove(p1);
                        entities.remove(p2);
                        break;
                    case 1:
                        entities.remove(p2);
                        break;
                    case 2:
                        entities.remove(p1);
                        break;
                }
            }
        }

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
