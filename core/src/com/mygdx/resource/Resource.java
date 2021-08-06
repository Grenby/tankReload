package com.mygdx.resource;

import com.badlogic.gdx.utils.GdxRuntimeException;

public class Resource {

    private static Resource instance;
    private static boolean init = false;

    public static void init(){
        if (!init){
            instance = new Resource();
            init =true;
        }
    }

    public Resource getInstance(){
        if (!init){
            throw new GdxRuntimeException("Resource didn't init");
        }
        return instance;
    }



}
