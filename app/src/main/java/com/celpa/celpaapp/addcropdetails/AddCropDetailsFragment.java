package com.celpa.celpaapp.addcropdetails;


import android.Manifest;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.celpa.celpaapp.R;
import com.celpa.celpaapp.common.LoadingDialog;
import com.celpa.celpaapp.data.Crop;
import com.celpa.celpaapp.utils.DateUtils;
import com.livinglifetechway.quickpermissions.annotations.WithPermissions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddCropDetailsFragment extends Fragment
        implements AddCropDetailsContract.View,
        View.OnClickListener,
        DatePickerDialog.OnDateSetListener {

    private static final String TAG = AddCropDetailsFragment.class.getSimpleName();

    private static final String EXTRA_CROP = "crop";

    private Crop crop;

    private AddCropDetailsContract.Presenter presenter;

    private DatePickerDialog datePickerDialog;
    private LoadingDialog loadingDialog;
    private EditText nameEdittxt;
    private EditText fertsUsedEdittxt;
    private EditText waterAppliedEdittxt;
    private TextView approxDateHarvestTxt;
    private Button changeApproxDateBtn;
    private ImageView cropImgView;

    public static AddCropDetailsFragment newInstance() {
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
        nameEdittxt = root.findViewById(R.id.edittxt_crop_name);
        fertsUsedEdittxt = root.findViewById(R.id.edittxt_fert_used);
        approxDateHarvestTxt = root.findViewById(R.id.txt_approx_date_harvest);
        waterAppliedEdittxt = root.findViewById(R.id.edittxt_water_applied);
        changeApproxDateBtn = root.findViewById(R.id.btn_change_approx_date);

        init();

        return root;
    }

    private void init() {

        Bitmap photoTaken = BitmapFactory.decodeByteArray(crop.img.get(0).img, 0, crop.img.get(0).img.length);
        cropImgView.setImageBitmap(photoTaken);

        changeApproxDateBtn.setOnClickListener(this);

        // Init approx. date of harvest
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        String formattedDate = DateUtils.getFormattedString(year, month, day);
        setApproxDateOfHarvest(formattedDate);
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
        loadingDialog = LoadingDialog.newInstance(getString(R.string.please_wait));
        loadingDialog.show(getFragmentManager(), TAG);
    }

    @Override
    public void hideLoadingDialog() {
        loadingDialog.dismiss();
    }

    @Override
    public void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
        datePickerDialog.show();
    }

    @WithPermissions(permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    public void saveToLocalAndRemoteSources() {
        crop.name = nameEdittxt.getText().toString();
        crop.noOfFertilizersUsed = Long.parseLong(fertsUsedEdittxt.getText().toString());
        crop.noOfWaterAppliedPerDay = Long.parseLong(waterAppliedEdittxt.getText().toString());
        crop.weather = "";

        presenter.saveCropDetails(crop);
    }

    @Override
    public void setApproxDateOfHarvest(String dateOfHarvest) {
        approxDateHarvestTxt.setText(dateOfHarvest);
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
                saveToLocalAndRemoteSources();
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_change_approx_date:
                showDatePickerDialog();
                break;
            default:
                break;

        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String formattedDate = DateUtils.getFormattedString(year, month, dayOfMonth);
        setApproxDateOfHarvest(formattedDate);
    }
}
