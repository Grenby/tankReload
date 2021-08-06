package com.mygdx.game.enviroment;

import com.badlogic.gdx.math.GridPoint2;

public class IndexedGridPoint2 extends GridPoint2 {
    public static class Connections {
        public static final byte CONNECT_1 = 1;
        public static final byte CONNECT_2 = 2;
        public static final byte CONNECT_3 = 4;
        public static final byte CONNECT_4 = 8;
        public static final byte CONNECT_5 = 16;
        public static final byte CONNECT_6 = 32;
        public static final byte CONNECT_7 = 64;
        public static final byte[] CONNECTIONS = {1,2,4,8,16,32,64};
    }

    private int index = -1;
    private byte connection = -1;

    public IndexedGridPoint2(int index) {
        this.index = index;
    }

    public IndexedGridPoint2(int x, int y, int index) {
        super(x, y);
        this.index = index;
    }

    public IndexedGridPoint2(GridPoint2 point, int index) {
        super(point);
        this.index = index;
    }

    public IndexedGridPoint2() {
    }

    public IndexedGridPoint2(int x, int y) {
        super(x, y);
    }

    public IndexedGridPoint2(GridPoint2 point) {
        super(point);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void addConnect(final byte connect){
        this.connection |= connect;
    }

    public void removeConnect(final byte connect){
        this.connection&=(~connect);
    }

    public byte getConnection() {
        return connection;
    }

    public boolean hasConnect(final byte c){
        return (connection & c) == c;
    }

    @Override
    public IndexedGridPoint2 set(GridPoint2 point) {
        this.x = point.x;
        this.y = point.y;
        return this;
    }

    @Override
    public IndexedGridPoint2 set(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }
//
//    public IndexedGridPoint2 set(IndexedGridPoint2 point){
//        this.x = point.x;
//        this.y = point.y;
//        return this;
//    }
//
}
