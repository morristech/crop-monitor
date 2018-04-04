package com.celpa.celpaapp.takephoto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.celpa.celpaapp.R;
import com.celpa.celpaapp.utils.BitmapUtils;
import com.flurgle.camerakit.CameraKit;
import com.flurgle.camerakit.CameraListener;
import com.flurgle.camerakit.CameraView;

public class TakePhotoFragment extends Fragment
        implements TakePhotoContract.View,
        View.OnClickListener {

    private TakePhotoContract.Presenter presenter;

    boolean hasTakenPicture;

    private CameraView cameraView;
    private Button takePhotoBtn;

    public static TakePhotoFragment newInstance() {
        return new TakePhotoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_takephoto, container, false);
        cameraView = root.findViewById(R.id.view_camera);
        takePhotoBtn = root.findViewById(R.id.btn_takephoto);

        takePhotoBtn.setOnClickListener(this);

        setCamera();
        return root;
    }

    private void setCamera() {
        cameraView.setPermissions(CameraKit.Constants.PERMISSIONS_PICTURE);
        cameraView.setCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(byte[] jpeg) {
                if(hasTakenPicture)
                    return;

                try {
                    hasTakenPicture = true;
                    super.onPictureTaken(jpeg);

                    // Create bitmap
                    Bitmap bitmap = BitmapUtils.createBitmap(getContext(), jpeg);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
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
    public void setPresenter(TakePhotoContract.Presenter presenter) {
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
        }
    }
}
