package com.bitshifting.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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

    Texture readyBox;
    Texture notReadyBox;

    // position of player 1 ready box
    // position of player 2 ready box
    Vector2 p1RdyBoxPos;
    Vector2 p2RdyBoxPos;

    Vector2 instructionSheetPos;


    public ChooseState(StateManager sm) {
        super(sm);
        batch = new SpriteBatch();

        player1Pressed = false;
        player2Pressed = false;

        player1StartingPos = new Vector2(0, 0);
        player2StartingPos = new Vector2(0, 0);
        playerBoundingBox = new Vector2(50, 50);

        readyBox = new Texture("tito.png");
        notReadyBox = new Texture("tito.png");
        instructionSheet = new Texture("tito.png");

        float widthOfReadyBox = (float)readyBox.getWidth();
        float centeredHeight = ((float)Gdx.graphics.getHeight() / 2.f) - (widthOfReadyBox / 2.f);
        float marginOfReadyBox = (float)Gdx.graphics.getWidth() / 10.f;

        p1RdyBoxPos = new Vector2(marginOfReadyBox, centeredHeight);
        p2RdyBoxPos = new Vector2((float)Gdx.graphics.getWidth() - marginOfReadyBox - widthOfReadyBox, centeredHeight);

        instructionSheetPos = new Vector2(0, 0);

    }

    @Override
    public void handleInput() {
        //let's the user choose the input
        for(int i = 1; i <= 2; i++) {
            if (InputManager.getInstance().getPlayerDLeft(i)) {
                if (i == 1) {
                    game.player1 = new PlayerObject(player1StartingPos, playerBoundingBox, i, ProjectileType.ROCK);
                } else {
                    game.player2 = new PlayerObject(player2StartingPos, playerBoundingBox, i, ProjectileType.ROCK);
                }

                System.out.println("Player " + i + " chose Rock!");
            }

            if (InputManager.getInstance().getPlayerDRight(i)) {
                if (i == 1) {
                    game.player1 = new PlayerObject(player1StartingPos, playerBoundingBox, i, ProjectileType.SCISSOR);
                } else {
                    game.player2 = new PlayerObject(player2StartingPos, playerBoundingBox, i, ProjectileType.SCISSOR);
                }

                System.out.println("Player " + i + " chose Scissors!");
            }

            if(InputManager.getInstance().getPlayerDDown(i)) {
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
        batch.draw(instructionSheet, instructionSheetPos.x, instructionSheetPos.y);

        if(game.player1 != null) {
            batch.draw(readyBox, p1RdyBoxPos.x, p1RdyBoxPos.y);
        } else {
            batch.draw(notReadyBox, p1RdyBoxPos.x, p1RdyBoxPos.y);
        }

        if(game.player2 != null) {
            batch.draw(readyBox, p2RdyBoxPos.x, p2RdyBoxPos.y);
        } else {
            batch.draw(notReadyBox, p2RdyBoxPos.x, p2RdyBoxPos.y);
        }

        batch.end();
    }

    @Override
    public void dispose() {

    }
}
