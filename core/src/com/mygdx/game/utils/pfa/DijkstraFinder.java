package com.mygdx.game.utils.pfa;

import com.badlogic.gdx.ai.pfa.*;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.FlushablePool;
import com.badlogic.gdx.utils.Pool;

public class DijkstraFinder<T> implements PathFinder<T> {

    private final FlushablePool<Connection<T>> connectionPool;
    private final IndexedGraph<T> graph;

    public DijkstraFinder(IndexedGraph<T> graph, FlushablePool<Connection<T>> connectionPool){
        this.graph = graph;
        this.connectionPool = connectionPool;
    }

    @Override
    public boolean searchConnectionPath(T startNode, T endNode, Heuristic<T> heuristic, GraphPath<Connection<T>> outPath) {

        return false;
    }

    @Override
    public boolean searchNodePath(T startNode, T endNode, Heuristic<T> heuristic, GraphPath<T> outPath) {
        //boolean search = search();
        connectionPool.flush();
        return false;
    }

    @Override
    public boolean search(PathFinderRequest<T> request, long timeToRun) {

        return false;
    }

    protected boolean search (T startNode, T endNode, Heuristic<T> heuristic) {
        ///todo
        //initSearch(startNode, endNode, heuristic);

        // Iterate through processing each node
//        do {
//            // Retrieve the node with smallest estimated total cost from the open list
//            current = openList.pop();
//            current.category = CLOSED;
//
//            // Terminate if we reached the goal node
//            if (current.node == endNode) return true;
//
//            visitChildren(endNode, heuristic);
//
//        } while (openList.size > 0);
//
        // We've run out of nodes without finding the goal, so there's no solution
        return false;
    }

}
