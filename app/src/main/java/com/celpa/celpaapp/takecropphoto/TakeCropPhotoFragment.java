package com.celpa.celpaapp.takecropphoto;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.celpa.celpaapp.R;
import com.wonderkiln.camerakit.CameraKit;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

public class TakeCropPhotoFragment extends Fragment
        implements TakeCropPhotoContract.View,
        View.OnClickListener,
        CameraKitEventListener {

    private TakeCropPhotoContract.Presenter presenter;

    private CameraView cameraView;
    private Button takePhotoBtn;

    public static TakeCropPhotoFragment newInstance() {
        return new TakeCropPhotoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_takecropphoto, container, false);
        cameraView = root.findViewById(R.id.view_camera);
        takePhotoBtn = root.findViewById(R.id.btn_takephoto);

        takePhotoBtn.setOnClickListener(this);

        setCamera();
        return root;
    }

    private void setCamera() {
        cameraView.setPermissions(CameraKit.Constants.PERMISSIONS_PICTURE);
        cameraView.addCameraKitListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    public void onPause() {
        cameraView.stop();
        super.onPause();
    }

    @Override
    public void setPresenter(TakeCropPhotoContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public boolean takePhoto() {
        cameraView.captureImage();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_takephoto:
                takePhoto();
                break;
            default:
                break;
        }
    }

    @Override
    public void onEvent(CameraKitEvent cameraKitEvent) {}

    @Override
    public void onError(CameraKitError cameraKitError) {

    }

    @Override
    public void onImage(CameraKitImage cameraKitImage) {
        Bitmap photo = cameraKitImage.getBitmap();
    }

    @Override
    public void onVideo(CameraKitVideo cameraKitVideo) {}
}