package com.bitshifting.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by ricardolopez on 4/18/15.
 */
public class ScissorProjectile extends ProjectileObject {
    public ScissorProjectile(Vector2 position, Vector2 boundingBox, ProjectileType type, int playerID) {
        super(position, boundingBox, ProjectileType.SCISSOR, playerID);
        this.texture = new Texture("weapons/scissor.png");
    }
}
