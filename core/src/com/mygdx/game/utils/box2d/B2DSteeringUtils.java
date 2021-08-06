package com.mygdx.game.utils.box2d;

import com.badlogic.gdx.math.Vector2;

public final class B2DSteeringUtils {

    private B2DSteeringUtils() {
    }

    public static float vectorToAngle(Vector2 vector) {
        return (float) Math.atan2(vector.y, vector.x);
    }

    public static Vector2 angleToVector(Vector2 outVector, float angle) {
        outVector.x = (float) Math.sin(angle);
        outVector.y = (float) Math.cos(angle);
        return outVector;
    }
}