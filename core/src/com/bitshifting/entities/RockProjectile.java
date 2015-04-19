package com.bitshifting.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by ricardolopez on 4/18/15.
 */
public class RockProjectile extends ProjectileObject {
    public RockProjectile(Vector2 position, ProjectileType type, int playerID) {
        super(position, ProjectileType.ROCK, playerID, "weapons/rock.png");
    }
}
