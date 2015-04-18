package com.bitshifting.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bitshifting.managers.StateManager;

public class MainMenu extends State {

    Stage stage = new Stage();
    TextButton button;
    TextButton.TextButtonStyle textButtonStyle;
    BitmapFont font;
    Skin skin;
    TextureAtlas buttonAtlas;

    public MainMenu(StateManager sm) {
        super(sm);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin();
        font = new BitmapFont();
        buttonAtlas = new TextureAtlas(Gdx.files.internal("Button/ButtonPack.atlas"));
        skin.addRegions(buttonAtlas);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("Play");
        textButtonStyle.over = skin.getDrawable("PlayHover");
        textButtonStyle.down = skin.getDrawable("PlayClick");

        button = new TextButton("", textButtonStyle);
        button.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                System.out.println("Button Clicked.");
            }
        });
        stage.addActor(button);
    }

    public void handleInput() {

    }

    public void update(float dt) {

    }

    public void render() {
        stage.draw();
    }

    public void dispose() {

    }
}
