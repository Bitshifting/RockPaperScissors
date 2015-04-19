package com.bitshifting;

import com.bitshifting.entities.GameObject;

/**
 * An event between two gameobjects
 *
 * Created by akersten on 4/18/15.
 */
public class CollisionEvent {
    public GameObject collider1, collider2;

    public CollisionEvent(GameObject collider1, GameObject collider2) {
        this.collider1 = collider1;
        this.collider2 = collider2;
    }
}
