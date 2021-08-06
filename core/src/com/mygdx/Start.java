package com.mygdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.mods.TestMode;

public class Start extends Game {

    @Override
    public void create() {
        setScreen(new TestMode());
    }
}
