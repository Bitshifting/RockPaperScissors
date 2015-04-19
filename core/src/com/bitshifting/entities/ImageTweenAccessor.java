package com.bitshifting.entities;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by ricardolopez on 4/18/15.
 */
public class ImageTweenAccessor implements TweenAccessor<Image> {

    public static final int POSITION_X = 1;
    public static final int POSITION_Y = 2;
    public static final int POSITION_XY = 3;

    @Override
    public int getValues(Image target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case POSITION_X:
                returnValues[0] = target.getImageX();
                return 1;
            case POSITION_Y:
                returnValues[0] = target.getImageY();
                return 1;
            case POSITION_XY:
                returnValues[0] = target.getImageX();
                returnValues[0] = target.getImageY();
                return 1;
            default:
                assert false;
                return -1;
        }
    }

    @Override
    public void setValues(Image target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case POSITION_X:
                target.setX(newValues[0]);
                break;
            case POSITION_Y:
                target.setY(newValues[0]);
                break;
            case POSITION_XY:
                target.setX(newValues[0]);
                target.setY(newValues[1]);
                break;
            default:
                assert false;
                break;
        }
    }
}
