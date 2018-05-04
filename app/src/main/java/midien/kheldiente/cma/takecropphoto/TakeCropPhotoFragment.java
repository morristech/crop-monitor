package midien.kheldiente.cma.takecropphoto;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import midien.kheldiente.cma.R;
import midien.kheldiente.cma.addcropdetails.AddCropDetailsActivity;
import midien.kheldiente.cma.allcrops.AllCropsActivity;
import midien.kheldiente.cma.common.LoadingDialog;
import midien.kheldiente.cma.common.OkDialog;
import midien.kheldiente.cma.data.Crop;
import midien.kheldiente.cma.login.LoginActivity;
import midien.kheldiente.cma.utils.ActivityUtils;
import midien.kheldiente.cma.utils.BitmapUtils;
import com.livinglifetechway.quickpermissions.annotations.WithPermissions;
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

    private static final String TAG = TakeCropPhotoFragment.class.getSimpleName();
    private static final String EXTRA_CROP = "crop";

    private TakeCropPhotoContract.Presenter presenter;

    private CameraView cameraView;
    private Button takePhotoBtn;
    private LoadingDialog loadingDialog;
    private OkDialog okDialog;

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
    public String saveBitmapToStorage(byte[] photoByte) {
        String filePath = BitmapUtils.saveBitmapToStorage(getContext(), photoByte);
        return filePath;
    }

    @Override
    public void showLoadingDialog(String msg) {
        loadingDialog = LoadingDialog.newInstance(msg);
        loadingDialog.show(getFragmentManager(), TAG);
    }

    @Override
    public void updateLoadingDialog(String msg) {
        if(loadingDialog == null)
            return;
        loadingDialog.updateTxtMsg(msg);
    }

    @Override
    public void hideLoadingDialog() {
        if(loadingDialog == null)
            return;
        loadingDialog.dismiss();
    }

    @Override
    public void showOkDialog(String msg) {
        okDialog = OkDialog.newInstance(msg);
        okDialog.show(getFragmentManager(), TAG);
    }

    @Override
    public String getGettingLocationText() {
        return getString(R.string.getting_location);
    }

    @Override
    public String getGettingWeatherText() {
        return getString(R.string.getting_weather);
    }

    @Override
    public String getParsingImageText() {
        return getString(R.string.parsing_image);
    }

    @Override
    public String getLoggingOutText() {
        return getString(R.string.logging_out);
    }

    @Override
    public void closeMe() {
        getActivity().finish();
    }

    @Override
    public void goToLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void goToList() {
        Intent intent = new Intent(getActivity(), AllCropsActivity.class);
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
    public void onError(CameraKitError cameraKitError) {}

    @WithPermissions(permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION})
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_list:
                goToList();
                break;
            case R.id.item_logout:
                presenter.clearCacheAndLogout();
                break;
            default:
                break;
        }
        return true;
    }
}
