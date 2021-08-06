package com.mygdx.game.enviroment;


import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.utils.Pool;

public class ConnectionPoints implements Connection<IndexedGridPoint2>, Pool.Poolable {

    IndexedGridPoint2 fromNode;
    IndexedGridPoint2 toNode;

    public ConnectionPoints (IndexedGridPoint2 fromNode, IndexedGridPoint2 toNode) {
        this.fromNode = fromNode;
        this.toNode = toNode;
    }

    public ConnectionPoints setNodes(IndexedGridPoint2 fromNode, IndexedGridPoint2 toNode){
        this.fromNode = fromNode;
        this.toNode = toNode;
        return this;
    }

    public ConnectionPoints setFromNode(IndexedGridPoint2 fromNode) {
        this.fromNode = fromNode;
        return this;
    }

    public ConnectionPoints setToNode(IndexedGridPoint2 toNode) {
        this.toNode = toNode;
        return this;
    }

    @Override
    public float getCost() {
        return 1;
    }

    @Override
    public IndexedGridPoint2 getFromNode() {
        return fromNode;
    }

    @Override
    public IndexedGridPoint2 getToNode() {
        return toNode;
    }

    @Override
    public void reset() {
        fromNode = null;
        toNode = null;
    }
}
