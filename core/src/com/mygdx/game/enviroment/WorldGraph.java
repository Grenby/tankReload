package com.mygdx.game.enviroment;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FlushablePool;
import com.badlogic.gdx.utils.ObjectSet;

public class WorldGraph implements Heuristic<IndexedGridPoint2>, IndexedGraph<IndexedGridPoint2> {

    public static final int[] iteratePoint = {1,0,0,1,-1,1,-1,0,0,-1,1,-1};

    ObjectSet<IndexedGridPoint2> point2s = new ObjectSet<>(42);
    FlushablePool<ConnectionPoints> connectionPool = new FlushablePool<ConnectionPoints>() {
        @Override
        protected ConnectionPoints newObject() {
            return new ConnectionPoints(null,null);
        }
    };

    private final IndexedGridPoint2 TMP_POINT2 = new IndexedGridPoint2(0,0,0);
    private final Array<Connection<IndexedGridPoint2>> connections = new Array<>(6);

    @Override
    public float estimate(IndexedGridPoint2 node, IndexedGridPoint2 endNode) {
        //todo need tests
        return (Math.abs(node.x - endNode.x) + Math.abs(node.x+node.y - endNode.x - Math.abs(node.y - endNode.y)))/2f;
    }

    @Override
    public int getIndex(IndexedGridPoint2 node) {
        return node.getIndex();
    }

    @Override
    public int getNodeCount() {
        return point2s.size;
    }

    @Override
    public Array<Connection<IndexedGridPoint2>> getConnections(IndexedGridPoint2 fromNode) {
        connections.clear();
        for (int i = 0; i < 6; i++) {
            TMP_POINT2.set(fromNode);
            TMP_POINT2.add(iteratePoint[2*i],iteratePoint[2*i+1]);
            IndexedGridPoint2 point = point2s.get(TMP_POINT2);
            if (point!=null && fromNode.hasConnect(IndexedGridPoint2.Connections.CONNECTIONS[i]))
                connections.add(connectionPool.obtain().setNodes(fromNode,point));
        }
        return connections;
    }

    public void update(){
        resetAllConnections();
    }

    public void resetAllConnections(){
        connectionPool.flush();
    }

    public ObjectSet<IndexedGridPoint2> getPoint2s() {
        return point2s;
    }

    public boolean addNode(IndexedGridPoint2 key) {
        return point2s.add(key);
    }

    public void addAllNodes(Array<? extends IndexedGridPoint2> array) {
        point2s.addAll(array);
    }

    public IndexedGridPoint2 getIndexedNode(int x,int y){
        return point2s.get(TMP_POINT2.set(x,y));
    }

    public IndexedGridPoint2 getIndexedNode(IndexedGridPoint2 point){
        return point2s.get(point);
    }

    public IndexedGridPoint2 getIndexedNode(GridPoint2 point){
        return point2s.get(TMP_POINT2.set(point));
    }
}
