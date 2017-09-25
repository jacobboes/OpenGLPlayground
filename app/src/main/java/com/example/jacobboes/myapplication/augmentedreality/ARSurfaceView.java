package com.example.jacobboes.myapplication.augmentedreality;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ARSurfaceView extends GLSurfaceView {
    private ARRenderer mRenderer;

    public ARSurfaceView(Context context) {
        super(context);
    }

    public ARSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event != null) {

            return true;
        } else {
            return super.onTouchEvent(event);
        }
    }

    // Hides superclass method.
    public void setRenderer(ARRenderer renderer) {
        mRenderer = renderer;
        super.setRenderer(renderer);
    }
}