package com.mygdx.game.enviroment;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;

public class HexagonCoords {

    private final Vector2 gX = new Vector2();
    private final Vector2 gY = new Vector2();

    private final Matrix3 toCartesian = new Matrix3();
    private final Matrix3 toHexagon = new Matrix3();

    private final Vector2 tmp = new Vector2();

    private final float[] oX = new float[7];
    private final float[] oY = new float[7];

    public HexagonCoords(Vector2 axis1,Vector2 axis2){
        set(axis1,axis2);
        setArray();
    }

    /**
     * @param x - x component in cartesian coordinates
     * @param y - y component in cartesian coordinates
     * @return coordinate in hexagon grid
     */
    public GridPoint2 getHexagon(float x, float y){
        return getHexagon(x,y,new GridPoint2());
    }

    /**
     * @param v - coordinate in cartesian system
     * @return coordinate in hexagon grid
     */
    public GridPoint2 getHexagon(Vector2 v){
        return getHexagon(v,new GridPoint2());
    }

    /**
     * @param out - GridPoint for to record the result
     */
    public GridPoint2 getHexagon(float x,float y,GridPoint2 out){
        tmp.set(x,y).mul(toHexagon);
        int xx = (int)(tmp.x + 0.5f * Math.signum(tmp.x));
        int yy = (int)(tmp.y + 0.5f* Math.signum(tmp.y));
        return out.set(xx,yy);
    }

    public GridPoint2 getHexagon(Vector2 v,GridPoint2 out){
        tmp.set(v).mul(toHexagon);
        int xx = (int)(tmp.x + 0.5f * Math.signum(tmp.x));
        int yy = (int)(tmp.y + 0.5f* Math.signum(tmp.y));
        return out.set(xx,yy);
    }

    /**
     * transform old coordinate(cartesian) to new coordinate(hexagon coordinate system)
     * @param out - vector with old coordinate, in this vector will be set result
     * @return out
     */
    public Vector2 inNewCoordinate(Vector2 out){
        return out.mul(toHexagon);
    }

    /**
     * translate from hexagon coordinate system to cartesian sys
     * @param out vector with hexagon coords
     * @return out
     */
    public Vector2 inOldCoordinate(Vector2 out){
        return out.mul(toCartesian);
    }

    /**
     * @param point -x,y component of hexagon grid
     * @return cartesian coords of center this hexagon
     */
    public Vector2 getCenter(GridPoint2 point){
        return getCenter(point,new Vector2());
    }

    /**
     * @param out - to set in this vector coords of center
     * @return out
     */
    public Vector2 getCenter(GridPoint2 point,Vector2 out){
        return out.set(gX).scl(point.x).mulAdd(gY,point.y);
    }

    public Vector2 getCenter(int x,int y){
        return getCenter(x,y,new Vector2());
    }

    public Vector2 getCenter(int x,int y, Vector2 out){
        return out.set(gX).scl(x).mulAdd(gY,y);
    }

    public Vector2 getTranslate(Vector2 out){
        return out.set(toCartesian.val[Matrix3.M02],toCartesian.val[Matrix3.M12]);
    }

    public Vector2 getX() {
        return gX;
    }

    public Vector2 getY() {
        return gY;
    }

    public Matrix3 getToCartesian() {
        return toCartesian;
    }

    public Matrix3 getToHexagon() {
        return toHexagon;
    }

    /**
     * @return array of X coords of a hexagon centered at (0,0), oX[0] = oX[7]
     */
    public float[] getOX() {
        return oX;
    }

    /**
     *
     * @return array of Y coords of a hexagon centered at (0,0). oY[0] = oY[7]
     */
    public float[] getOY() {
        return oY;
    }

    public void setOX(float[] oX) {
        System.arraycopy(oX,0,this.oX,0,7);
    }

    public void setOY(float[] oY) {
        System.arraycopy(oY,0,this.oY,0,7);
    }

    private void setArray(){
        ///todo
        float h = (float)(1d / Math.sqrt(3d));
        tmp.set(gX).scl(h).rotateRad(MathUtils.PI/6f);
        float angle = MathUtils.PI/6f;
        for (int i = 0; i < 7; i++) {
            oX[i] = tmp.x;
            oY[i] = tmp.y;
            angle += MathUtils.PI/3f;
            tmp.set(gX).scl(h).rotateRad(angle);
        }
    }

    /**
     * suppose, that (0,0) - in cartesian coords is center of first hexagon
     * @param axis1 - coords of center hexagon,that bordered with first hexagon
     * @param axis2 - coords of other hexagon
     */
    public void set(Vector2 axis1, Vector2 axis2){
        if (axis1.isCollinear(axis2))
            return;
        gX.set(axis1);
        gY.set(axis2);
        float [] val =toCartesian.val;
        val[Matrix3.M00] = gX.x;
        val[Matrix3.M01] = gY.x;
        val[Matrix3.M02] = 0;
        val[Matrix3.M10] = gX.y;
        val[Matrix3.M11] = gY.y;
        val[Matrix3.M12] = 0;
        val[Matrix3.M20] = 0;
        val[Matrix3.M21] = 0;
        val[Matrix3.M22] = 1;
        toHexagon.set(toCartesian).inv();
        setArray();

    }

    public void set(Vector2 axis1, Vector2 axis2,Vector2 translate){
        if (axis1.isCollinear(axis2))
            return;
        gX.set(axis1);
        gY.set(axis2);
        float [] val =toCartesian.val;
        val[Matrix3.M00] = gX.x;
        val[Matrix3.M01] = gY.x;
        val[Matrix3.M02] = translate.x;
        val[Matrix3.M10] = gX.y;
        val[Matrix3.M11] = gY.y;
        val[Matrix3.M12] = translate.y;
        val[Matrix3.M20] = 0;
        val[Matrix3.M21] = 0;
        val[Matrix3.M22] = 1;
        toHexagon.set(toCartesian).inv();
        setArray();
    }


}
