package com.celpa.celpaapp.addcropdetails;


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
import android.widget.EditText;
import android.widget.ImageView;

import com.celpa.celpaapp.R;
import com.celpa.celpaapp.common.LoadingDialog;
import com.celpa.celpaapp.data.Crop;
import com.celpa.celpaapp.utils.ActivityUtils;

public class AddCropDetailsFragment extends Fragment
        implements AddCropDetailsContract.View {

    private Crop crop;

    private AddCropDetailsContract.Presenter presenter;

    private LoadingDialog loadingDialog;
    private EditText fertsUsedEdittxt;
    private EditText approxDateHarvestEdittxt;
    private EditText waterAppliedEdittxt;
    private ImageView cropImgView;

    public static AddCropDetailsFragment newInstance(Crop crop) {
        return new AddCropDetailsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_addcropdetails, container, false);
        cropImgView = root.findViewById(R.id.iv_crop);
        fertsUsedEdittxt = root.findViewById(R.id.edittxt_fert_used);
        approxDateHarvestEdittxt = root.findViewById(R.id.edittxt_approx_date);
        waterAppliedEdittxt = root.findViewById(R.id.edittxt_water_applied);

        return root;
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
