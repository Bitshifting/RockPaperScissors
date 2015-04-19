package com.bitshifting.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Sam on 4/19/2015.
 */
public class HealthBar extends GameObject {
    public int maxHealth;
    public int currentHealth;
    public Vector2 position;
    public int playerID;
    public float width, height;
    ShapeRenderer renderer;

    public HealthBar(Vector2 position, String img, int maxHealth, int playerID) {
        super(position, img);
        this.maxHealth = maxHealth;
        this.currentHealth = this.maxHealth;
        this.playerID = playerID;
        renderer = new ShapeRenderer();
        this.position = position;
        this.width = 300.f;
        this.width = Gdx.graphics.getWidth() * 3.f / 7.f;
        this.height = 25.f;
        // get the position
    }

    public void decrementHealth(int loss) {
        this.currentHealth -= loss;
    }

    public void setHealth(int newHealth) {
        this.currentHealth = newHealth;
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void handleInput() {

    }

    @Override
    public void render(SpriteBatch batch) {
        batch.end();
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.identity();
        renderer.translate(this.position.x, this.position.y, 0.f);
        if (this.playerID == 1) {
            renderer.setColor(Color.GREEN);
            renderer.rect(0, 0, width * (float) currentHealth / maxHealth, height);
            renderer.setColor(Color.RED);
            renderer.rect(width * (float) currentHealth / maxHealth, 0, width * (1.f - ((float) currentHealth / maxHealth)), height);
        } else {
            renderer.setColor(Color.RED);
            renderer.rect(0, 0, width * (1.f - ((float) currentHealth / maxHealth)), height);
            renderer.setColor(Color.GREEN);
            renderer.rect(width * (1.f - ((float) currentHealth / maxHealth)), 0, width * (float) currentHealth / maxHealth, height);
        }
        renderer.end();
        batch.begin();
    }

    @Override
    public void dispose() {

    }
}
