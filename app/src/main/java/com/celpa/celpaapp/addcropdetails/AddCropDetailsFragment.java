package com.celpa.celpaapp.addcropdetails;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.celpa.celpaapp.R;
import com.celpa.celpaapp.common.LoadingDialog;
import com.celpa.celpaapp.data.Crop;

public class AddCropDetailsFragment extends Fragment
        implements AddCropDetailsContract.View {

    private static final String EXTRA_CROP = "crop";

    private Crop crop;

    private AddCropDetailsContract.Presenter presenter;

    private LoadingDialog loadingDialog;
    private EditText fertsUsedEdittxt;
    private EditText waterAppliedEdittxt;
    private TextView approxDateHarvestTxt;
    private Button changeApproxDateBtn;
    private ImageView cropImgView;

    public static AddCropDetailsFragment newInstance(Crop crop) {
        return new AddCropDetailsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if(getActivity().getIntent() != null) {
            crop = getActivity().getIntent().getParcelableExtra(EXTRA_CROP);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_addcropdetails, container, false);
        cropImgView = root.findViewById(R.id.iv_crop);
        fertsUsedEdittxt = root.findViewById(R.id.edittxt_fert_used);
        approxDateHarvestTxt = root.findViewById(R.id.txt_approx_date_harvest);
        waterAppliedEdittxt = root.findViewById(R.id.edittxt_water_applied);
        changeApproxDateBtn = root.findViewById(R.id.btn_change_approx_date);

        init();

        return root;
    }

    private void init() {
        Bitmap photoTaken = BitmapFactory.decodeByteArray(crop.img, 0, crop.img.length);
        cropImgView.setImageBitmap(photoTaken);
    }


    @Override
    public void setPresenter(AddCropDetailsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showCapturedPhoto() {

    }

    @Override
    public void showLocation() {

    }

    @Override
    public void showWeather() {

    }

    @Override
    public void showLoadingDialog() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_addcropdetails, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_save_crop:
                presenter.saveCropDetails(crop);
                break;
            default:
                break;
        }

        return true;
    }
}
