package com.example.jacobboes.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.FrameLayout;
import com.example.jacobboes.myapplication.camera.CameraPreview;

public class MainActivity extends Activity {

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1255;
    private GLSurfaceView glView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkCameraPermission();

        if (checkCameraHardware(this)) {
            FrameLayout layout = new FrameLayout(this);
            Camera camera = getCameraInstance();
            if (camera != null) {
                layout.addView(new CameraPreview(this, camera));
                glView = new ARView(this);
                layout.addView(glView);
                setContentView(layout);
            } else {
                glView = new ARView(this);
                setContentView(glView);
            }
        }
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)
                    this, Manifest.permission.CAMERA)) {
            } else {
                ActivityCompat.requestPermissions((Activity) this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);
            }

        }
    }

    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(0); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    /**
     * Check if this device has a camera
     */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }
}
