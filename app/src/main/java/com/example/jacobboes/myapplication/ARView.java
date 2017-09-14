package com.example.jacobboes.myapplication;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;

public class ARView extends GLSurfaceView {
//    private final ARRenderer arRenderer;
    public ARView(Context context) {
        super(context);

        setEGLContextClientVersion(2);

//        arRenderer = new ARRenderer();
//        setRenderer(arRenderer);


        setZOrderOnTop(true);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        getHolder().setFormat(PixelFormat.RGBA_8888);
        setRenderer(new ARRenderer());
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

    }
}
