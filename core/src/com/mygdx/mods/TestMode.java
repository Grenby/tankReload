package com.mygdx.mods;

import com.badlogic.gdx.*;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.ObjectSet;
import com.mygdx.game.enviroment.HexagonCoords;
import com.mygdx.game.enviroment.IndexedGridPoint2;
import com.mygdx.game.enviroment.WorldGraph;
import com.mygdx.game.utils.MyInput;

public class TestMode extends InputAdapter implements Screen {

    ShapeRenderer renderer = new ShapeRenderer();
    PerspectiveCamera camera = new PerspectiveCamera(67,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    FirstPersonCameraController controller = new FirstPersonCameraController(camera);
    Vector2 tmp2d_1 = new Vector2();
    Vector3 tmp3d_1 = new Vector3();
    float size = 1;
    MyInput input = new MyInput();
    HexagonCoords hexagonCoords;

    ObjectSet<IndexedGridPoint2> points;;
    WorldGraph graph = new WorldGraph();

    IndexedGridPoint2 startPoint = new IndexedGridPoint2(0,0);
    IndexedGridPoint2 endPoint = new IndexedGridPoint2(2,2);
    boolean selectStart = false;
    boolean selectEnd = false;

    IndexedGridPoint2 selectPoint = new IndexedGridPoint2();
    boolean selected = false;

    IndexedGridPoint2 TMP_POINT2 = new IndexedGridPoint2();
    IndexedAStarPathFinder<IndexedGridPoint2> finder;
    GraphPath<IndexedGridPoint2> path = new DefaultGraphPath<>();

    ObjectSet<IndexedGridPoint2> closePoints = new ObjectSet<>(21);

    float [] oX;
    float [] oY;

    private boolean setVector(Ray ray, Vector3 v) {
        if(MathUtils.isEqual(ray.direction.y,0,0.1f))return false;
        float t = -ray.origin.y / ray.direction.y;
        if (t<0)return false;

        v.set(ray.direction).scl(t).add(ray.origin);
        return !(v.dst2(ray.origin) > 10000);
    }

    private void addClosePoint(int screenX, int screenY,boolean canRemove){
            selectEnd = false;
            if (setVector(camera.getPickRay(screenX, screenY), tmp3d_1)){
                hexagonCoords.getHexagon(tmp3d_1.z, tmp3d_1.x, TMP_POINT2);
                if (TMP_POINT2.equals(startPoint))
                    selectStart = false;
                if (points.contains(TMP_POINT2)) {
                    IndexedGridPoint2 pp = points.get(TMP_POINT2);
                    int[] iterate = WorldGraph.iteratePoint;
                    if (closePoints.contains(pp)) {
                        if (!canRemove)
                            return;
                        closePoints.remove(pp);
                        for (int i=0;i<iterate.length / 2;i++){
                            TMP_POINT2.add(iterate[2*i],iterate[2*i+1]);
                            if (closePoints.contains(TMP_POINT2))
                                pp.removeConnect(IndexedGridPoint2.Connections.CONNECTIONS[i]);
                            else if (points.contains(TMP_POINT2)){
                                points.get(TMP_POINT2).addConnect(IndexedGridPoint2.Connections.CONNECTIONS[(i+3) % 6]);
                            }
                            TMP_POINT2.sub(iterate[2*i],iterate[2*i+1]);
                        }
                    }else {
                        //0 - 3
                        //1 - 4
                        //2 - 5
                        //3 - 0
                        //4 - 1
                        //5 - 2
                        //(n + 3) mod 6
                        pp.addConnect((byte)-1);
                        for (int i=0;i<iterate.length / 2;i++){
                            TMP_POINT2.add(iterate[2*i],iterate[2*i+1]);
                            if (points.contains(TMP_POINT2)){
                                points.get(TMP_POINT2).removeConnect(IndexedGridPoint2.Connections.CONNECTIONS[(i+3) % 6]);
                            }
                            TMP_POINT2.sub(iterate[2*i],iterate[2*i+1]);
                        }
                        closePoints.add(pp);
                    }
                }
            }
    }

    void initInput(){
        input.addCallback(Input.Keys.ESCAPE,()->Gdx.app.exit());
        input.setTouchDown((screenX, screenY, pointer, button) -> {
            if (button == 1){
                addClosePoint(screenX,screenY,true);
                return;
            }
            if (!setVector(camera.getPickRay(screenX, screenY), tmp3d_1)){
                selectEnd = false;
                selectStart = false;
                return;
            }
            if (selectStart) {
                hexagonCoords.getHexagon(tmp3d_1.z, tmp3d_1.x, endPoint);
                if (closePoints.contains(endPoint))
                    return;
                selectEnd = points.contains(endPoint);
                if (!selectEnd){
                    selectStart = false;
                }else{
                    path.clear();
                    finder.searchNodePath(graph.getIndexedNode(startPoint),graph.getIndexedNode(endPoint),graph,path);
                    graph.resetAllConnections();
                }
            }else{
                hexagonCoords.getHexagon(tmp3d_1.z, tmp3d_1.x, startPoint);
                if (closePoints.contains(selectPoint))
                    return;
                selectStart = points.contains(startPoint);
                if (!selectStart){
                    selectEnd = false;
                }
            }
        });
        input.setMouseMoved((screenX, screenY) -> {
            if (!setVector(camera.getPickRay(screenX, screenY), tmp3d_1)){
                selected = false;
                return;
            }
            hexagonCoords.getHexagon(tmp3d_1.z, tmp3d_1.x, selectPoint);
            selected = points.contains(selectPoint);
        });
        input.setTouchDragged((screenX, screenY, pointer) -> {
            if (input.getPressedButton().contains(1)){
                addClosePoint(screenX,screenY,false);
                return true;
            }
            return false;
        });
    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(new InputMultiplexer(input,controller));

        initInput();

        camera.position.set(-1,1,-1);
        camera.lookAt(0,1,0);
        camera.far = 100f;
        camera.near = .1f;
        camera.update();

        float h = (float)Math.sqrt(3) * size;
        Vector2 gX = new Vector2(h,0).rotateRad(MathUtils.PI/6f);
        Vector2 gY = new Vector2(0,h);
        hexagonCoords = new HexagonCoords(gX,gY);

        renderer.getTransformMatrix()
                .rotateRad(1,0,0, -MathUtils.PI/2f)
                .rotateRad(0,0,1,-MathUtils.PI/2f);
        points = graph.getPoint2s();
        int index = 0;
        for (int i = -10; i < 10 ; i++) {
            for (int j = -10; j < 10; j++) {
                points.add(new IndexedGridPoint2(i,j + Math.min(-i,5),index++));
            }
        }
        finder  = new IndexedAStarPathFinder<>(graph);
        oX = hexagonCoords.getOX();
        oY = hexagonCoords.getOY();
    }

    void drawHexagonLine(IndexedGridPoint2 p) {
        tmp2d_1.set(hexagonCoords.getX()).scl(p.x).mulAdd(hexagonCoords.getY(),p.y);
        for (int k = 0; k < 6; k++) {
            renderer.line(tmp2d_1.x + oX[k], tmp2d_1.y+oY[k], tmp2d_1.x + oX[k+1], tmp2d_1.y+oY[k+1]);
        }
    }

    void drawHexagonFilled(IndexedGridPoint2 p) {
        tmp2d_1.set(hexagonCoords.getX()).scl(p.x).mulAdd(hexagonCoords.getY(), p.y);
        for (int k = 0; k < 6; k++) {
            renderer.triangle(tmp2d_1.x, tmp2d_1.y, tmp2d_1.x + oX[k], tmp2d_1.y + oY[k], tmp2d_1.x + oX[k + 1], tmp2d_1.y + oY[k + 1]);
        }
    }

    @Override
    public void render(float delta) {

        controller.update(delta);

        Gdx.gl.glClearColor(0.4f, 0.4f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        {
            renderer.setColor(Color.WHITE);
            for (IndexedGridPoint2 p:points) {
                drawHexagonLine(p);
            }
        }
        renderer.setColor(Color.RED);
        tmp2d_1.set(hexagonCoords.getX()).scl(10);
        renderer.line(0,0,tmp2d_1.x,tmp2d_1.y);

        renderer.setColor(Color.GREEN);
        tmp2d_1.set(hexagonCoords.getY()).scl(10);
        renderer.line(0,0,tmp2d_1.x,tmp2d_1.y);

        renderer.end();

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        if (selected || selectStart || selectEnd) {

            if (selectStart && selectEnd) {
                renderer.setColor(Color.GREEN);
                for (IndexedGridPoint2 p : path) {
                    drawHexagonFilled(p);
                }
            }else if (selectStart) {
                renderer.setColor(Color.GREEN);
                drawHexagonFilled(startPoint);
            }
        }
        renderer.setColor(Color.BLACK);
        for (IndexedGridPoint2 p: closePoints) {
            drawHexagonFilled(p);
        }
        renderer.end();

        if (selected) {
            renderer.begin(ShapeRenderer.ShapeType.Line);
            renderer.setColor(Color.GOLD);
            drawHexagonLine(selectPoint);
            renderer.end();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

}
