package com.mygdx.game.utils;

import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utils.box2d.B2DSteeringUtils;

public final class DefaultLocation implements Location<Vector2> {

    private final Vector2 pos = new Vector2();
    private float orientation = 0f;

    @Override
    public Vector2 getPosition() {
        return pos;
    }

    @Override
    public float getOrientation() {
        return orientation;
    }

    @Override
    public void setOrientation(float orientation) {
        this.orientation = orientation;
    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return B2DSteeringUtils.vectorToAngle(vector);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        return B2DSteeringUtils.angleToVector(outVector, angle);
    }

    @Override
    public Location<Vector2> newLocation() {
        return new DefaultLocation();
    }
}
