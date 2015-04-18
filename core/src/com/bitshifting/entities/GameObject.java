package com.bitshifting.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {
    public Vector2 position;
    public Vector2 velocity;

    public Texture texture;
    public float rotation;


    public GameObject(Vector2 position)   {
        this.position = position;
        this.texture = new Texture(Gdx.files.internal("assets/tito.png"));
        this.rotation = 0.0f;

    }

    public abstract void update(float dt);
    public abstract void handleInput();
    public abstract void render();
    public abstract void dispose();
}