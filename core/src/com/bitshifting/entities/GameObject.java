package com.bitshifting.entities;

import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {
    public Vector2 position;

    public GameObject(Vector2 position)
    {
        this.position = position;
    }

    public abstract void update(float dt);
    public abstract void handleInput();
    public abstract void render();
    public abstract void dispose();
}