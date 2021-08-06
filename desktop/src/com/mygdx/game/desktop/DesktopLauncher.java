package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.Start;

public class DesktopLauncher {
	public static void main (String[] arg) {
//		int mask = 0x0000FFFF;
//		short a = -32768;
//		System.out.println("A="+(short)(a&mask)+" "+Integer.toBinaryString(a & mask));
//		short b = 32767;
//		byte shift = 16;
//		int c = (b << shift) | (a & mask);
//		System.out.println(Integer.toBinaryString(c));
//		System.out.println(c);
//		a = (short)(c & mask);
//		System.out.println(a);
//		b = (short)((c>>>shift)&mask);
//		System.out.println(b);
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new Start(), config);
	}
}
