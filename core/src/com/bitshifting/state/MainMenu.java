package com.bitshifting.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bitshifting.managers.StateManager;

import javax.xml.soap.Text;

public class MainMenu extends State {

    Stage stage;
    TextureAtlas buttonAtlas;
    Skin skin;

    ImageButton rulesButton;
    ImageButton exitButton;
    ImageButton shootButton;
    ImageButton settingsButton;

    public MainMenu(StateManager sm) {
        super(sm);

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        float buttonWidthDivider = 1.f / 14.f;
        float buttonPositionDivider = 1.f / 14.f;
        float heightOfButton = (7.f / 50.f) * height;


        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        skin = new Skin();
        buttonAtlas = new TextureAtlas(Gdx.files.internal("menu/menu_buttons.atlas"));
        skin.addRegions(buttonAtlas);

        // Rules Button
        ImageButton.ImageButtonStyle ruleStyle = new ImageButton.ImageButtonStyle();
        ruleStyle.up = skin.getDrawable("rules");
        ruleStyle.over = skin.getDrawable("rules_hover");
        ruleStyle.down = skin.getDrawable("rules_pressed");

        rulesButton = new ImageButton(ruleStyle);
        rulesButton.setSize( 3.f * buttonWidthDivider * width, heightOfButton);
        rulesButton.setPosition(0, 0);

        rulesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Rules Clicked.");
            }
        });


        // Settings Button
        ImageButton.ImageButtonStyle settingsStyle = new ImageButton.ImageButtonStyle();
        settingsStyle.up = skin.getDrawable("settings");
        settingsStyle.over = skin.getDrawable("settings_hover");
        settingsStyle.down = skin.getDrawable("settings_pressed");

        settingsButton = new ImageButton(settingsStyle);
        settingsButton.setSize(4.f * buttonWidthDivider * width, heightOfButton);
//        settingsButton.setPosition(7.f * buttonPositionDivider * width, 0);
        settingsButton.setPosition(5f * buttonPositionDivider * width, 0);

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Settings Clicked.");
            }
        });

        // Shoot Button
        ImageButton.ImageButtonStyle shootStyle = new ImageButton.ImageButtonStyle();
        shootStyle.up = skin.getDrawable("shoot");
        shootStyle.over = skin.getDrawable("shoot_hover");
        shootStyle.down = skin.getDrawable("shoot_pressed");

        shootButton = new ImageButton(shootStyle);
        shootButton.setSize(3.f * buttonWidthDivider * width, heightOfButton);
        shootButton.setPosition(5.5f * buttonPositionDivider * width, heightOfButton + (heightOfButton / 4.0f));

        shootButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Shoot Clicked.");
            }
        });

        // Exit Button
        ImageButton.ImageButtonStyle exitStyle = new ImageButton.ImageButtonStyle();
        exitStyle.up = skin.getDrawable("exit");
        exitStyle.over = skin.getDrawable("exit_hover");
        exitStyle.down = skin.getDrawable("exit_pressed");

        exitButton = new ImageButton(exitStyle);
        exitButton.setSize(3.f * buttonWidthDivider * width, heightOfButton);
        exitButton.setPosition(11.f * buttonPositionDivider * width, 0);

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        stage.addActor(exitButton);
        stage.addActor(shootButton);
        stage.addActor(settingsButton);
        stage.addActor(rulesButton);
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
