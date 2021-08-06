package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.components.B2DComponent;

public final class PhysicsSystem extends EntitySystem implements EntityListener {

    private final ComponentMapper<B2DComponent> b2DComponent = ComponentMapper.getFor(B2DComponent.class);

    private final World world;

    public PhysicsSystem(World world) {
        if (world == null) throw new NullPointerException("world is null");
        this.world = world;
    }

    @Override
    public void update(float deltaTime) {
        world.step(deltaTime, 4, 4);
    }

    @Override
    public void entityAdded(Entity entity) {
        //b2DComponent.has();
    }

    @Override
    public void entityRemoved(Entity entity) {
        if (!checkProcessing()){
            world.destroyBody(entity.getComponent(B2DComponent.class).body);
        }
    }
}
