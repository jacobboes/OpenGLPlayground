package com.example.jacobboes.myapplication.augmentedreality;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.DisplayMetrics;
import com.example.jacobboes.myapplication.ARFragment;
import com.example.jacobboes.myapplication.R;
import com.example.jacobboes.myapplication.camera2.Camera2BasicFragment;

public class ARActivity extends Activity {
    /**
     * Hold a reference to our GLSurfaceView
     */
    private ARSurfaceView mGLSurfaceView;
    private ARRenderer mRenderer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null == savedInstanceState) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.ar_container, Camera2BasicFragment.newInstance())
                    .commit();
        }
        setContentView(R.layout.ar_activity);

        mGLSurfaceView = (ARSurfaceView) findViewById(R.id.gl_surface_view);

        // Check if the system supports OpenGL ES 2.0.
        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;

        if (supportsEs2) {
            // Request an OpenGL ES 2.0 compatible context.
//            mGLSurfaceView.setEGLContextClientVersion(2);

            final DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);//TODO Needed?

            // Set the renderer to our demo renderer, defined below.
            mGLSurfaceView.setEGLContextClientVersion(2);

            mGLSurfaceView.setZOrderOnTop(true);
            mGLSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
            mGLSurfaceView.getHolder().setFormat(PixelFormat.RGBA_8888);

            mRenderer = new ARRenderer(this);
            mGLSurfaceView.setRenderer(mRenderer);

        } else {
            // This is where you could create an OpenGL ES 1.x compatible
            // renderer if you wanted to support both ES 1 and ES 2.
            return;
        }
    }

    @Override
    protected void onResume() {
        // The activity must call the GL surface view's onResume() on activity
        // onResume().
        super.onResume();
        mGLSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        // The activity must call the GL surface view's onPause() on activity
        // onPause().
        super.onPause();
        mGLSurfaceView.onPause();
    }
}
