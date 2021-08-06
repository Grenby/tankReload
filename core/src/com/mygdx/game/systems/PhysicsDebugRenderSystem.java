package com.mygdx.game.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public final class PhysicsDebugRenderSystem extends EntitySystem {

    private final World world;
    private final Box2DDebugRenderer renderer = new Box2DDebugRenderer();
    private Matrix4 combined;

    public PhysicsDebugRenderSystem(World world, Matrix4 combined) {
        if (world == null) throw new NullPointerException("World is null");
        if (combined == null) throw new NullPointerException("Matrix is null");
        this.world = world;
        this.combined = combined;
    }

    public void setCombined(Matrix4 combined) {
        this.combined = combined;
    }

    @Override
    public void update(float deltaTime) {
        renderer.render(world, combined);
    }
}
