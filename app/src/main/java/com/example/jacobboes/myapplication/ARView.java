package com.example.jacobboes.myapplication;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;
import com.example.jacobboes.myapplication.shapes.Color;
import com.example.jacobboes.myapplication.shapes.Square;
import com.example.jacobboes.myapplication.shapes.dto.DTOSquare;

import java.util.Random;
import java.util.concurrent.*;

public class ARView extends GLSurfaceView {

    private final ARRenderer renderer;
    private ScheduledExecutorService future;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                renderer.addSquare(new DTOSquare(0, 0, Color.BLUE));
                renderer.addSquare(new DTOSquare(0, 0.05f, Color.GREEN));
                renderer.addSquare(new DTOSquare(0, -0.05f, Color.GREEN));
                renderer.addSquare(new DTOSquare(0, 0.1f, Color.BLUE));
                renderer.addSquare(new DTOSquare(0, -0.1f, Color.BLUE));
                renderer.addSquare(new DTOSquare(0, 0.15f, Color.GREEN));
                renderer.addSquare(new DTOSquare(0, -0.15f, Color.GREEN));
                renderer.addSquare(new DTOSquare(0, 0.2f, Color.BLUE));
                renderer.addSquare(new DTOSquare(0, -0.2f, Color.BLUE));
                renderer.addSquare(new DTOSquare(0, 0.25f, Color.GREEN));
                renderer.addSquare(new DTOSquare(0, -0.25f, Color.GREEN));
                renderer.addSquare(new DTOSquare(0, 0.3f, Color.BLUE));
                renderer.addSquare(new DTOSquare(0, -0.3f, Color.BLUE));
                renderer.addSquare(new DTOSquare(0, 0.35f, Color.GREEN));
                renderer.addSquare(new DTOSquare(0, -0.35f, Color.GREEN));
                renderer.addSquare(new DTOSquare(0, 0.4f, Color.BLUE));
                renderer.addSquare(new DTOSquare(0, -0.4f, Color.BLUE));
                renderer.addSquare(new DTOSquare(0, 0.45f, Color.GREEN));
                renderer.addSquare(new DTOSquare(0, -0.45f, Color.GREEN));
                renderer.addSquare(new DTOSquare(0, 0.5f, Color.BLUE));
                renderer.addSquare(new DTOSquare(0, -0.5f, Color.BLUE));


                renderer.addSquare(new DTOSquare(0.05f, 0, Color.GREEN));
                renderer.addSquare(new DTOSquare(-0.05f, 0, Color.GREEN));
                renderer.addSquare(new DTOSquare(0.1f, 0, Color.BLUE));
                renderer.addSquare(new DTOSquare(-0.1f, 0, Color.BLUE));
                renderer.addSquare(new DTOSquare(0.15f, 0, Color.GREEN));
                renderer.addSquare(new DTOSquare(-0.15f, 0, Color.GREEN));
                renderer.addSquare(new DTOSquare(0.2f, 0, Color.BLUE));
                renderer.addSquare(new DTOSquare(-0.2f, 0, Color.BLUE));
                renderer.addSquare(new DTOSquare(0.25f, 0, Color.GREEN));
                renderer.addSquare(new DTOSquare(-0.25f, 0, Color.GREEN));
                renderer.addSquare(new DTOSquare(0.3f, 0, Color.BLUE));
                renderer.addSquare(new DTOSquare(-0.3f, 0, Color.BLUE));
                requestRender();

        }
        return true;
    }

    public ARView(Context context) {
        super(context);

        setEGLContextClientVersion(2);

        setZOrderOnTop(true);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        getHolder().setFormat(PixelFormat.RGBA_8888);
        renderer = new ARRenderer();
        setRenderer(renderer);
    }

    public void setup() {
        future = Executors.newScheduledThreadPool(1);
        future.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

//                renderer.addSquare(new DTOSquare(nextRandomFloat(), nextRandomFloat(), Color.BLUE));
//                requestRender();
            }
        }, 5, 1, TimeUnit.SECONDS);
    }

    private float nextRandomFloat() {
        Random random = new Random();
        return random.nextFloat() * (-0.2f * 0.2f) - 0.2f;
    }
}
