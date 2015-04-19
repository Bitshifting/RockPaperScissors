package com.bitshifting.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.bitshifting.managers.InputManager;
import com.bitshifting.managers.StateManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by ricardolopez on 4/19/15.
 */
public class WinnerState extends State {
    Stage stage;
    TextureAtlas buttonAtlas;
    Skin skin;
    BitmapFont font;

    SpriteBatch batch;

    ImageButton backButton;
    Image winner;
    InputManager inputs;


    public int loser = -1;
    public boolean loserSet;

    int player1Wins;
    int player2Wins;
    boolean hasPosted = false;

    public WinnerState(StateManager sm) {
        super(sm);

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.setColor(Color.WHITE);

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

            Net.HttpRequest httpPost = new Net.HttpRequest(Net.HttpMethods.GET);
            httpPost.setUrl(" https://rockpaperscissorstopscore.azure-mobile.net/tables/TopScores");
            Gdx.net.sendHttpRequest(httpPost, new Net.HttpResponseListener() {
                @Override
                public void handleHttpResponse(Net.HttpResponse httpResponse) {
                    System.out.println("" + httpResponse.getStatus().getStatusCode());
                    String response = httpResponse.getResultAsString();
                    System.out.println(response);

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject object = jsonArray.getJSONObject(jsonArray.length() - 1);
                        player1Wins = Integer.parseInt((String)object.get("Player1"));
                        player2Wins = Integer.parseInt((String)object.get("Player2"));

                        if(loser == 1) {
                            player2Wins++;
                        } else {
                            player1Wins++;
                        }

                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("Player1", "" + player1Wins);
                        parameters.put("Player2", "" + player2Wins);
                        Net.HttpRequest httpPost = new Net.HttpRequest(Net.HttpMethods.POST);
                        httpPost.setUrl(" https://rockpaperscissorstopscore.azure-mobile.net/tables/TopScores");
                        httpPost.setContent(HttpParametersUtils.convertHttpParameters(parameters));
                        Gdx.net.sendHttpRequest(httpPost, new Net.HttpResponseListener() {
                            @Override
                            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                                System.out.println("" + httpResponse.getStatus().getStatusCode());
                                System.out.println(httpResponse.getResultAsString());
                            }

                            @Override
                            public void failed(Throwable t) {
                                Gdx.app.log("Failed ", t.getMessage());
                            }

                            @Override
                            public void cancelled() {

                            }
                        });

                        hasPosted = true;
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }

                }

                @Override
                public void failed(Throwable t) {
                    Gdx.app.log("Failed ", t.getMessage());
                }

                @Override
                public void cancelled() {

                }
            });

            float width = Gdx.graphics.getWidth();
            float height = Gdx.graphics.getHeight();

            if (loser == 2) {
                winner = new Image(new SpriteDrawable(new Sprite(new Texture("winscreen/pladyer1.png"))));
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

        if(hasPosted) {
            batch.begin();
            String fontText =  "Global Player 1 Wins: " + player1Wins + " | Global Player 2 Wins: " + player2Wins;
            font.draw(batch, fontText,
                    Gdx.graphics.getWidth() / 2.f - (fontText.length() * font.getSpaceWidth() / 2.f), Gdx.graphics.getHeight() / 10.f);
            batch.end();
        }


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
