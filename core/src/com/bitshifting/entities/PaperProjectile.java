package com.bitshifting.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by ricardolopez on 4/18/15.
 */
public class PaperProjectile extends ProjectileObject {
    public PaperProjectile(Vector2 position, ProjectileType type, int playerID) {
        super(position, ProjectileType.PAPER, playerID, "weapons/paper.png");
    }
}
