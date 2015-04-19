package com.bitshifting.state;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.bitshifting.managers.InputManager;
import com.bitshifting.managers.StateManager;

/**
 * Created by ricardolopez on 4/19/15.
 */
public class WinnerState extends State {
    Stage stage;
    TextureAtlas buttonAtlas;
    Skin skin;

    ImageButton backButton;
    Image winner;
    InputManager inputs;

    public int loser = -1;
    public boolean loserSet;

    public WinnerState(StateManager sm) {
        super(sm);

        inputs = InputManager.getInstance();
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        float heightOfButton = (9.f / 50.f) * height;
        float widthOfButton = (9.f / 50.f) * height;

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // Back Button
        Texture textureUp   = new Texture(Gdx.files.internal("rules/return.png"));
        Texture textureDown = new Texture(Gdx.files.internal("rules/return.png"));
        backButton = new ImageButton(new SpriteDrawable(new Sprite(textureUp)),
                new SpriteDrawable(new Sprite(textureDown)));

        backButton.setPosition(width / 14.0f, 0.0f);
        backButton.setSize(widthOfButton, heightOfButton);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Back button Clicked.");
                //push on rules state
                leave();
            }
        });

        Image background = new Image(new Texture("winscreen/winscreen.png"));
        background.setSize(width, height);

        stage.addActor(background);
        stage.addActor(backButton);
    }

    private void leave() {
        Gdx.input.setInputProcessor(null);
        sm.setState(StateManager.MAIN_MENU);
    }

    @Override
    public void handleInput() {
        if (inputs.getPlayerB(1) || inputs.getPlayerB(2)){
            leave();
        }
    }

    @Override
    public void update(float dt) {
        // add winner
        if (!loserSet && loser != -1) {
            float width = Gdx.graphics.getWidth();
            float height = Gdx.graphics.getHeight();

            if (loser == 0) {
                winner = new Image(new SpriteDrawable(new Sprite(new Texture("winscreen/player1.png"))));
                System.out.println("Player 1 one");
            }
            else {
                winner = new Image(new SpriteDrawable(new Sprite(new Texture("winscreen/player2.png"))));
                System.out.println("Player 2 one");
            }


            winner.setOriginX((width / 6.0f));
            winner.setY((height / 2.0f));
//            winner.setX((width / 7.5f));
            loserSet = true;
            stage.addActor(winner);
        }
    }

    @Override
    public void render() {
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().setScreenHeight(height);
        stage.getViewport().setScreenWidth(width);
    }
}
