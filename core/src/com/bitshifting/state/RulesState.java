package com.bitshifting.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.bitshifting.managers.InputManager;
import com.bitshifting.managers.StateManager;

public class RulesState extends State {
    public SpriteBatch batch;

    Stage stage;
    Texture rulesSheet;
    TextureAtlas buttonAtlas;
    Sprite rulesSheetSprite;
    ImageButton backButton;
    Skin skin;
    InputManager inputs;

    public RulesState(StateManager sm) {
        super(sm);
        inputs = InputManager.getInstance();
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        float heightOfButton = (9.f / 50.f) * height;
        float widthOfButton = (9.f / 50.f) * height;

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        skin = new Skin();
        buttonAtlas = new TextureAtlas(Gdx.files.internal("menu/menu_buttons.atlas"));
        skin.addRegions(buttonAtlas);


        // Back Button
        Texture textureUp   = new Texture(Gdx.files.internal("rules/return.png"));
        Texture textureDown = new Texture(Gdx.files.internal("rules/return.png"));
        backButton = new ImageButton(new SpriteDrawable(new Sprite(textureUp)),
                new SpriteDrawable(new Sprite(textureDown)));

        backButton.setPosition(0.0f, 0.0f);
        backButton.setSize(widthOfButton, heightOfButton);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Back button Clicked.");
                //push on rules state
                leave();

            }
        });

        Image background = new Image(new Texture("rules/rules.png"));
        background.setSize(width, height);

        stage.addActor(background);
        stage.addActor(backButton);


    }

    @Override
    public void handleInput() {
        if (inputs.getPlayerB(1) || inputs.getPlayerB(2)){
            leave();
        }
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render() {
        stage.draw();
    }

    public void leave(){
        Gdx.input.setInputProcessor(null);
        sm.setState(StateManager.MAIN_MENU);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
