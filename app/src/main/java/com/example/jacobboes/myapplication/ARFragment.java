package com.example.jacobboes.myapplication;

import android.app.Fragment;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ARFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ARView arView = new ARView(getActivity());
        arView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        arView.setup();
        return arView;

    }
}
