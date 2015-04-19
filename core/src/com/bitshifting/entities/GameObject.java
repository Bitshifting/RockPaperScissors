package com.bitshifting.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {
    public Vector2 position;
    public Vector2 lastPosition;
    public boolean bouncing = false;

    public Vector2 boundingBox;

    public Vector2 velocity;

    public Texture texture;
    public float rotation;


    public GameObject(Vector2 position, Vector2 boundingBox)   {
        this.position = position;
        this.lastPosition = new Vector2(position.x, position.y);
        this.boundingBox = boundingBox;
        this.texture = new Texture(Gdx.files.internal("assets/tito.png"));
        this.rotation = 0.0f;
    }

    public abstract void update(float dt);
    public abstract void handleInput();
    public abstract void render(SpriteBatch batch);
    public abstract void dispose();


    public boolean collidesWith(GameObject p) {
        Vector2 a = this.position;
        Vector2 b = p.position;

        return (Math.abs(a.x - b.x) * 2 < (this.boundingBox.x + p.boundingBox.x)) && (Math.abs(a.y - b.y) * 2 < (this.boundingBox.y + p.boundingBox.y));
    }
}