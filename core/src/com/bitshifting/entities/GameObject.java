package com.bitshifting.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {
    public Vector2 position;
    public Vector2 lastPosition;
    public boolean bouncing = false;

    public Vector2 velocity;

    public Texture texture;
    public float rotation;
    public Sprite sprite;


    public GameObject(Vector2 position, String textureName)   {
        this.position = position;
        this.lastPosition = new Vector2(position.x, position.y);
        this.texture = new Texture(textureName);
        this.sprite = new Sprite(this.texture);
        this.rotation = 0.0f;
        this.sprite.setPosition(this.position.x, this.position.y);
    }

    public abstract void update(float dt);
    public abstract void handleInput();
    public abstract void render(SpriteBatch batch);
    public abstract void dispose();


    public boolean collidesWith(GameObject p) {
        return Intersector.overlaps(
                new Rectangle(position.x, position.y, sprite.getWidth(), sprite.getHeight()),
                new Rectangle(p.position.x, p.position.y, p.sprite.getWidth(), p.sprite.getHeight()));
    }
}