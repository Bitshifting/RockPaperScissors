package com.bitshifting.entities;

/**
 * Created by Sam on 4/18/2015.
 */
public enum ProjectileType {
    SCISSOR {
        @Override
        public String assetDir() {
            return "weapons/scissor.png";
        }
    },
    PAPER {
        @Override
        public String assetDir() {
            return "weapons/paper.png";
        }
    },
    ROCK {
        @Override
        public String assetDir() {
            return "weapons/rock.png";
        }
    };

    public abstract String assetDir();
}
