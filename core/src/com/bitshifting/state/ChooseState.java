package com.bitshifting.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.bitshifting.entities.PlayerObject;
import com.bitshifting.entities.ProjectileType;
import com.bitshifting.managers.InputManager;
import com.bitshifting.managers.StateManager;

public class ChooseState extends State {
    public SpriteBatch batch;

    //checks whether or not the player has pressed a button yet
    public boolean player1Pressed;
    public boolean player2Pressed;

    public MainGame game;

    Vector2 player1StartingPos;
    Vector2 player2StartingPos;

    Vector2 playerBoundingBox;

    Texture instructionSheet;
    Sprite instructionSheetSprite;

    Texture readyBox;
    Texture notReadyBox;

    // position of player 1 ready box
    // position of player 2 ready box
    Vector2 p1RdyBoxPos;
    Vector2 p2RdyBoxPos;
    Sprite p1RdySprite;
    Sprite p1NRdySprite;

    Sprite p2RdySprite;
    Sprite p2NRdySprite;

    Vector2 instructionSheetPos;


    public ChooseState(StateManager sm) {
        super(sm);
        batch = new SpriteBatch();

        player1Pressed = false;
        player2Pressed = false;

        player1StartingPos = new Vector2(0, 0);
        player2StartingPos = new Vector2(0, 0);
        playerBoundingBox = new Vector2(50, 50);

        readyBox = new Texture("selectmenu/ready.png");
        notReadyBox = new Texture("selectmenu/notready.png");
        instructionSheet = new Texture("selectmenu/select_menu.png");

        instructionSheetSprite = new Sprite(instructionSheet);
        instructionSheetSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        instructionSheetSprite.setPosition(0, 0);

        float widthOfReadyBox = (float)readyBox.getWidth();
        float centeredHeight = ((float)Gdx.graphics.getHeight() / 3.f) - (widthOfReadyBox / 2.f);
        float marginOfReadyBox = (float)Gdx.graphics.getWidth() / 40.f;
        Vector2 sizeOfBox = new Vector2((float)Gdx.graphics.getWidth() / 5.f, (float)Gdx.graphics.getWidth() / 5.f);
        float resize = 0.1f;

        p1RdyBoxPos = new Vector2(marginOfReadyBox, centeredHeight);
        p2RdyBoxPos = new Vector2((float)Gdx.graphics.getWidth() - marginOfReadyBox, centeredHeight);

        p1RdySprite = new Sprite(readyBox);
        p1RdySprite.setSize(sizeOfBox.x, sizeOfBox.y);
        p1RdySprite.setScale(resize);
        p1RdySprite.setPosition(p1RdyBoxPos.x, p1RdyBoxPos.y);

        p1NRdySprite = new Sprite(notReadyBox);
        p1NRdySprite.setSize(sizeOfBox.x, sizeOfBox.y);
        p1NRdySprite.setPosition(p1RdyBoxPos.x, p1RdyBoxPos.y);

        p2RdySprite = new Sprite(readyBox);
        p2RdySprite.setSize(sizeOfBox.x, sizeOfBox.y);
        p2RdySprite.setPosition(p2RdyBoxPos.x - p2RdySprite.getWidth(), p2RdyBoxPos.y);

        p2NRdySprite = new Sprite(notReadyBox);
        p2NRdySprite.setSize(sizeOfBox.x, sizeOfBox.y);
        p2NRdySprite.setPosition(p2RdyBoxPos.x - p2NRdySprite.getWidth(), p2RdyBoxPos.y);

        instructionSheetPos = new Vector2(0, 0);

    }

    @Override
    public void handleInput() {
        //let's the user choose the input
        for(int i = 1; i <= 2; i++) {
            if (InputManager.getInstance().getPlayerDLeft(i) || InputManager.getInstance().getPlayerA(i)) {
                if (i == 1) {
                    game.player1 = new PlayerObject(player1StartingPos, playerBoundingBox, i, ProjectileType.ROCK);
                } else {
                    game.player2 = new PlayerObject(player2StartingPos, playerBoundingBox, i, ProjectileType.ROCK);
                }

                System.out.println("Player " + i + " chose Rock!");
            }

            if (InputManager.getInstance().getPlayerDRight(i) || InputManager.getInstance().getPlayerB(i)) {
                if (i == 1) {
                    game.player1 = new PlayerObject(player1StartingPos, playerBoundingBox, i, ProjectileType.SCISSOR);
                } else {
                    game.player2 = new PlayerObject(player2StartingPos, playerBoundingBox, i, ProjectileType.SCISSOR);
                }

                System.out.println("Player " + i + " chose Scissors!");
            }

            if(InputManager.getInstance().getPlayerDDown(i) || InputManager.getInstance().getPlayerX(i)) {
                if (i == 1) {
                    game.player1 = new PlayerObject(player1StartingPos, playerBoundingBox, i, ProjectileType.PAPER);
                } else {
                    game.player2 = new PlayerObject(player2StartingPos, playerBoundingBox, i, ProjectileType.PAPER);
                }

                System.out.println("Player " + i + " chose Paper!");
            }
        }

    }

    @Override
    public void update(float dt) {
        //both not null, it is time to start
        if (game.player1 != null && game.player2 != null) {
            sm.popState();
        }
    }

    @Override
    public void render() {
        batch.begin();
        //draw instruction sheet
        instructionSheetSprite.draw(batch);

        if(game.player1 != null) {
            p1RdySprite.draw(batch);
        } else {
            p1NRdySprite.draw(batch);
        }

        if(game.player2 != null) {
            p2RdySprite.draw(batch);
        } else {
            p2NRdySprite.draw(batch);
        }

        batch.end();
    }

    @Override
    public void dispose() {

    }
}
