package com.celpa.celpaapp.takecropphoto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.celpa.celpaapp.R;
import com.celpa.celpaapp.addcropdetails.AddCropDetailsActivity;
import com.celpa.celpaapp.data.Crop;
import com.celpa.celpaapp.utils.ActivityUtils;
import com.wonderkiln.camerakit.CameraKit;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class TakeCropPhotoFragment extends Fragment
        implements TakeCropPhotoContract.View,
        View.OnClickListener,
        CameraKitEventListener {

    private static final String EXTRA_CROP = "crop";

    private TakeCropPhotoContract.Presenter presenter;

    private CameraView cameraView;
    private Button takePhotoBtn;

    private Bitmap capturedBitmap;

    public static TakeCropPhotoFragment newInstance() {
        return new TakeCropPhotoFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
    public void goToAddCropDetails() {
        ActivityUtils.goToActivity(getActivity(), AddCropDetailsActivity.class);
    }

    @Override
    public void goToAddCropDetails(Crop crop) {
        Intent intent = new Intent(getActivity(), AddCropDetailsActivity.class);
        intent.putExtra(EXTRA_CROP, crop);
        startActivity(intent);
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
        byte[] photoByte = cameraKitImage.getJpeg();
        presenter.processPhoto(photoByte);
    }

    @Override
    public void onVideo(CameraKitVideo cameraKitVideo) {}

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_takecropphoto, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
