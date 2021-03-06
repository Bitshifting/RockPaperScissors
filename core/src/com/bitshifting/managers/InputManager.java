package com.bitshifting.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.bitshifting.Input.ControllerButtons;

import javax.naming.ldap.Control;

/**
 * Created by sschwebach on 4/18/15.
 * This class interprets all inputs based on which mode we're doing and what input devices we have.
 * For example, when there are two controllers connected it routes each to one player.
 * When there is one controller, it uses the keyboard for player 1 and the controller for player 2
 * Online mode is a bit more complicated. First, we need to know if we've been assigned player 1 or 2
 * Once we know, that player uses local input and the other player uses input from a web caller (if we get to it)
 */
public class InputManager implements InputProcessor {
    public static InputManager instance = new InputManager();

    boolean player1Keyboard = false; // true if player 1 is using a keyboard
    Controller player1, player2;
    // Current button states and stuff
    boolean player1Start, player1DLeft, player1DRight, player1DUp, player1DDown, player1A, player1B, player1X;
    boolean player2Start, player2DLeft, player2DRight, player2DUp, player2DDown, player2A, player2B, player2X;
    public Vector2 player1LeftStick, player1RightStick, player2LeftStick, player2RightStick;

    private InputManager() {
        Array<Controller> controllers = Controllers.getControllers();

        System.out.println("Found " + controllers.size + " controllers");
        // See how many controllers we have
        if (controllers.size == 0) {
            player1Keyboard = true;
            Gdx.input.setInputProcessor(this);
            // Online mode only
        } else if (controllers.size == 1) {
            player1Keyboard = true;
            // Local or online, player 1 is on keyboard for local
            player2 = controllers.get(0);
            if (player2.getName().equals(ControllerButtons.XboxName)) {
                System.out.println("Controller name or something " + controllers.get(0).getName());
            }
            player2.addListener(new XboxListener());
            Gdx.input.setInputProcessor(this);
        } else {
            // Local or online, player 1 and player 2 are on controllers
            player1 = controllers.get(0);
            player2 = controllers.get(1);
            player1.addListener(new XboxListener());
            player2.addListener(new XboxListener());
        }
        // initialize shit
        player1LeftStick = new Vector2();
        player1RightStick = new Vector2();
        player2LeftStick = new Vector2();
        player2RightStick = new Vector2();
    }

    public static InputManager getInstance() {
        return instance;
    }


    @Override
    public boolean keyDown(int keycode) {
        if (player1Keyboard) {
            switch (keycode) {
                case Input.Keys.ESCAPE:
                    // Escape sets start to true
                    player1Start = true;
                    break;
                case Input.Keys.W:
                    // W sets Y to -1.0
                    player1LeftStick.y += 1.0f;
                    break;
                case Input.Keys.S:
                    // S sets Y to 1.0
                    player1LeftStick.y += -1.0f;
                    break;
                case Input.Keys.A:
                    // A sets X to -1.0
                    player1LeftStick.x += -1.0f;
                    break;
                case Input.Keys.D:
                    // D sets X to 1.0
                    player1LeftStick.x += 1.0f;
                    break;
                case Input.Keys.UP:
                    // Arrow up sets Y to -1.0
                    player1RightStick.y += 1.0f;
                    player1DUp = true;
                    break;
                case Input.Keys.DOWN:
                    // Arrow down sets Y to 1.0
                    player1RightStick.y += -1.0f;
                    player1DDown = true;
                    break;
                case Input.Keys.LEFT:
                    // Arrow left sets X to -1.0, left to true
                    player1RightStick.x += -1.0f;
                    player1DLeft = true;
                    break;
                case Input.Keys.RIGHT:
                    // Arrow right sets X to 1.0, right to true
                    player1RightStick.x += 1.0f;
                    player1DRight = true;
                    break;
                case Input.Keys.ENTER:
                    // Enter sets a to true
                    player1A = true;
                    break;
                case Input.Keys.BACKSPACE:
                    // Backspace sets b to true
                    player1B = true;
                    break;
                case Input.Keys.BACKSLASH:
                    // Backslash set x to true
                    player1X = true;
                    break;
            }

        }

        checkForFloatingPointError(player1LeftStick);
        checkForFloatingPointError(player1RightStick);
        checkForFloatingPointError(player2LeftStick);
        checkForFloatingPointError(player2RightStick);

        return false;
    }

    public void checkForFloatingPointError(Vector2 stick) {
        float error = 0.15f;

        if(Math.abs(stick.x) < error) {
            stick.x = 0.0f;
        }

        if(Math.abs(stick.y) < error) {
            stick.y = 0.0f;
        }
    }

    @Override
    public boolean keyUp(int keycode) {
        if (player1Keyboard) {
            switch (keycode) {
                case Input.Keys.ESCAPE:
                    // Escape sets start to true
                    player1Start = false;
                    break;
                case Input.Keys.W:
                    // W sets Y to -1.0
                    player1LeftStick.y -= 1.0f;
                    break;
                case Input.Keys.S:
                    // S sets Y to 1.0
                    player1LeftStick.y -= -1.0f;
                    break;
                case Input.Keys.A:
                    // A sets X to -1.0
                    player1LeftStick.x -= -1.0f;
                    break;
                case Input.Keys.D:
                    // D sets X to 1.0
                    player1LeftStick.x -= 1.0f;
                    break;
                case Input.Keys.UP:
                    // Arrow up sets Y to -1.0
                    player1RightStick.y -= 1.0f;
                    player1DUp = false;
                    break;
                case Input.Keys.DOWN:
                    // Arrow down sets Y to 1.0
                    player1RightStick.y -= -1.0f;
                    player1DDown = false;
                    break;
                case Input.Keys.LEFT:
                    // Arrow left sets X to -1.0, left to true
                    player1RightStick.x -= -1.0f;
                    player1DLeft = false;
                    break;
                case Input.Keys.RIGHT:
                    // Arrow right sets X to 1.0, right to true
                    player1RightStick.x -= 1.0f;
                    player1DRight = false;
                    break;
                case Input.Keys.ENTER:
                    // Enter sets a to true
                    player1A = false;
                    break;
                case Input.Keys.BACKSPACE:
                    // Backspace sets b to true
                    player1B = false;
                    break;
                case Input.Keys.BACKSLASH:
                    // Backslash set x to true
                    player1X = false;
                    break;
            }

        }

        checkForFloatingPointError(player1LeftStick);
        checkForFloatingPointError(player1RightStick);
        checkForFloatingPointError(player2LeftStick);
        checkForFloatingPointError(player2RightStick);

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private class XboxListener implements ControllerListener {
        @Override
        public void connected(Controller controller) {

        }

        @Override
        public void disconnected(Controller controller) {

        }

        @Override
        public boolean buttonDown(Controller controller, int buttonCode) {
            if (!player1Keyboard) {
                if (controller == player1) {
                    if (buttonCode == ControllerButtons.getAButton(controller.getName())) {
                        // A button
                        player1A = true;
                    } else if (buttonCode == ControllerButtons.getBButton(controller.getName())) {
                        // B button
                        player1B = true;
                    } else if (buttonCode == ControllerButtons.getXButton(controller.getName())) {
                        // X button
                        player1X = true;
                    } else if (buttonCode == ControllerButtons.getStart(controller.getName())) {
                        // Start button
                        player1Start = true;
                    }
                }
            }
            if (controller == player2) {
                if (buttonCode == ControllerButtons.getAButton(controller.getName())) {
                    // A button
                    player2A = true;
                } else if (buttonCode == ControllerButtons.getBButton(controller.getName())) {
                    // B button
                    player2B = true;
                } else if (buttonCode == ControllerButtons.getXButton(controller.getName())) {
                    // X button
                    player2X = true;
                } else if (buttonCode == ControllerButtons.getStart(controller.getName())) {
                    // Start button
                    player2Start = true;
                }
            }
            return false;
        }

        @Override
        public boolean buttonUp(Controller controller, int buttonCode) {
            return false;
        }

        @Override
        public boolean axisMoved(Controller controller, int axisCode, float value) {
            if (!player1Keyboard) {
                if (controller == player1) {
                    if (axisCode == ControllerButtons.getLeftY(controller.getName())) {
                        // Left stick Y
                        player1LeftStick.y = -value;
                    } else if (axisCode == ControllerButtons.getLeftX(controller.getName())) {
                        // Left stick X
                        player1LeftStick.x = value;
                    } else if (axisCode == ControllerButtons.getRightY(controller.getName())) {
                        // Right stick Y
                        player1RightStick.y = -value;
                    } else if (axisCode == ControllerButtons.getRightX(controller.getName())) {
                        // Right stick X
                        player1RightStick.x = value;
                    }
                }
            }
            if (controller == player2) {
                if (axisCode == ControllerButtons.getLeftY(controller.getName())) {
                    // Left stick Y
                    player2LeftStick.y = -value;
                } else if (axisCode == ControllerButtons.getLeftX(controller.getName())) {
                    // Left stick X
                    player2LeftStick.x = value;
                } else if (axisCode == ControllerButtons.getRightY(controller.getName())) {
                    // Right stick Y
                    player2RightStick.y = -value;
                } else if (axisCode == ControllerButtons.getRightX(controller.getName())) {
                    // Right stick X
                    player2RightStick.x = value;
                }
            }

            checkForFloatingPointError(player1LeftStick);
            checkForFloatingPointError(player1RightStick);
            checkForFloatingPointError(player2LeftStick);
            checkForFloatingPointError(player2RightStick);
            return false;
        }

        @Override
        public boolean povMoved(Controller controller, int povCode, PovDirection value) {
            if (!player1Keyboard) {
                if (controller == player1) {
                    if (value == PovDirection.north) {
                        // Dpad up
                        player1DUp = true;
                    } else if (value == PovDirection.south) {
                        // Dpad down
                        player1DDown = true;
                    } else if (value == PovDirection.west) {
                        // Dpad left
                        player1DLeft = true;
                    } else if (value == PovDirection.east) {
                        // Dpad right
                        player1DRight = true;
                    }
                }
            }
            if (controller == player2) {
                if (value == PovDirection.north) {
                    // Dpad up
                    player2DUp = true;
                } else if (value == PovDirection.south) {
                    // Dpad down
                    player2DDown = true;
                } else if (value == PovDirection.west) {
                    // Dpad left
                    player2DLeft = true;
                } else if (value == PovDirection.east) {
                    // Dpad right
                    player2DRight = true;
                }
            }
            return false;
        }

        @Override
        public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
            return false;
        }

        @Override
        public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
            return false;
        }

        @Override
        public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
            return false;
        }
    }

    // Methods to get the button flags
    // THESE METHODS ARE DESTRUCTIVE (the flag is set to zero afterwards)

    /**
     * Returns the specfied button, and sets that flag to zero (access then without the methods to not destroy them)
     *
     * @param player player number (1 or 2 currently)
     * @return Returns if that button was pressed since the last check
     */
    public boolean getPlayerStart(int player) {
        boolean toReturn = false;
        if (player == 1) {
            toReturn = player1Start;
            player1Start = false;
        } else if (player == 2) {
            toReturn = player2Start;
            player2Start = false;
        }
        return toReturn;
    }

    /**
     * Returns the specfied button, and sets that flag to zero (access then without the methods to not destroy them)
     *
     * @param player player number (1 or 2 currently)
     * @return Returns if that button was pressed since the last check
     */
    public boolean getPlayerA(int player) {
        boolean toReturn = false;
        if (player == 1) {
            toReturn = player1A;
            player1A = false;
        } else if (player == 2) {
            toReturn = player2A;
            player2A = false;
        }
        return toReturn;
    }

    /**
     * Returns the specfied button, and sets that flag to zero (access then without the methods to not destroy them)
     *
     * @param player player number (1 or 2 currently)
     * @return Returns if that button was pressed since the last check
     */
    public boolean getPlayerB(int player) {
        boolean toReturn = false;
        if (player == 1) {
            toReturn = player1B;
            player1B = false;
        } else if (player == 2) {
            toReturn = player2B;
            player2B = false;
        }
        return toReturn;
    }

    /**
     * Returns the specfied button, and sets that flag to zero (access then without the methods to not destroy them)
     *
     * @param player player number (1 or 2 currently)
     * @return Returns if that button was pressed since the last check
     */
    public boolean getPlayerX(int player) {
        boolean toReturn = false;
        if (player == 1) {
            toReturn = player1X;
            player1X = false;
        } else if (player == 2) {
            toReturn = player2X;
            player2X = false;
        }
        return toReturn;
    }

    /**
     * Returns the specfied button, and sets that flag to zero (access then without the methods to not destroy them)
     *
     * @param player player number (1 or 2 currently)
     * @return Returns if that button was pressed since the last check
     */
    public boolean getPlayerDLeft(int player) {
        boolean toReturn = false;
        if (player == 1) {
            toReturn = player1DLeft;
            player1DLeft = false;
        } else if (player == 2) {
            toReturn = player2DLeft;
            player2DLeft = false;
        }
        return toReturn;
    }

    /**
     * Returns the specfied button, and sets that flag to zero (access then without the methods to not destroy them)
     *
     * @param player player number (1 or 2 currently)
     * @return Returns if that button was pressed since the last check
     */
    public boolean getPlayerDRight(int player) {
        boolean toReturn = false;
        if (player == 1) {
            toReturn = player1DRight;
            player1DRight = false;
        } else if (player == 2) {
            toReturn = player2DRight;
            player2DRight = false;
        }
        return toReturn;
    }

    /**
     * Returns the specfied button, and sets that flag to zero (access then without the methods to not destroy them)
     *
     * @param player player number (1 or 2 currently)
     * @return Returns if that button was pressed since the last check
     */
    public boolean getPlayerDUp(int player) {
        boolean toReturn = false;
        if (player == 1) {
            toReturn = player1DUp;
            player1DUp = false;
        } else if (player == 2) {
            toReturn = player2DUp;
            player2DUp = false;
        }
        return toReturn;
    }

    /**
     * Returns the specfied button, and sets that flag to zero (access then without the methods to not destroy them)
     *
     * @param player player number (1 or 2 currently)
     * @return Returns if that button was pressed since the last check
     */
    public boolean getPlayerDDown(int player) {
        boolean toReturn = false;
        if (player == 1) {
            toReturn = player1DDown;
            player1DDown = false;
        } else if (player == 2) {
            toReturn = player2DDown;
            player2DDown = false;
        }
        return toReturn;
    }


}
