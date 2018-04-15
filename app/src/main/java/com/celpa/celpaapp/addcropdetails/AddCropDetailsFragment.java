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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.celpa.celpaapp.R;
import com.celpa.celpaapp.common.LoadingDialog;
import com.celpa.celpaapp.common.OkDialog;
import com.celpa.celpaapp.data.Crop;
import com.celpa.celpaapp.utils.AppSettings;
import com.celpa.celpaapp.utils.BitmapUtils;
import com.celpa.celpaapp.utils.DateUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.livinglifetechway.quickpermissions.annotations.WithPermissions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddCropDetailsFragment extends Fragment
        implements AddCropDetailsContract.View,
        View.OnClickListener,
        DatePickerDialog.OnDateSetListener,
        AdapterView.OnItemSelectedListener {

    private static final String TAG = AddCropDetailsFragment.class.getSimpleName();

    private static final String EXTRA_CROP = "crop";

    private static final int MAX_DEFAULT_CROP_NAMES = 4;

    private Crop crop;

    private AddCropDetailsContract.Presenter presenter;

    private DatePickerDialog datePickerDialog;
    private LoadingDialog loadingDialog;
    private OkDialog okDialog;
    private Spinner defaulNamesSpinner;
    private EditText nameEdittxt;
    private EditText fertsUsedEdittxt;
    private EditText waterAppliedEdittxt;
    private TextView plantedStartDateTxt;
    private TextView locationTxt;
    private TextView weatherTxt;
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
        defaulNamesSpinner = root.findViewById(R.id.sp_crop_names);
        nameEdittxt = root.findViewById(R.id.edittxt_crop_name);
        fertsUsedEdittxt = root.findViewById(R.id.edittxt_fert_used);
        plantedStartDateTxt = root.findViewById(R.id.txt_planted_start_date);
        waterAppliedEdittxt = root.findViewById(R.id.edittxt_water_applied);
        changeApproxDateBtn = root.findViewById(R.id.btn_change_approx_date);
        locationTxt = root.findViewById(R.id.txt_location);
        weatherTxt = root.findViewById(R.id.txt_weather);

        init();

        return root;
    }

    private void init() {
        changeApproxDateBtn.setOnClickListener(this);
        showCapturedPhoto();
        showLocation();
        showWeather();

        defaulNamesSpinner.setOnItemSelectedListener(this);

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);

        // Init approx. date of harvest
        String formattedDate = DateUtils.getFormattedString(year, month, day);
        setPlantedStartDate(formattedDate);
    }


    @Override
    public void setPresenter(AddCropDetailsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showCapturedPhoto() {
        Bitmap photoTaken = BitmapUtils.getBitmapFromStorage(getContext(), crop.img.get(0).imgPath);
        cropImgView.setImageBitmap(photoTaken);
    }

    @Override
    public void showLocation() {
        try {
            JsonObject addressObj = new JsonParser().parse(crop.location).getAsJsonObject();
            locationTxt.setText(addressObj.getAsJsonPrimitive("address").getAsString());
        } catch (JsonParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void showWeather() {
        weatherTxt.setText(crop.weather);
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
        crop.farmerId = AppSettings.getInstance(getContext()).getFarmerLoggedIn();

        long selected = defaulNamesSpinner.getSelectedItemId();
        String cropName = "";
        if(selected < MAX_DEFAULT_CROP_NAMES - 3) {
            cropName = getResources().getStringArray(R.array.crop_name_defaults)[0];
        } else {
            cropName = nameEdittxt.getText().toString();
        }

        crop.name = cropName;
        crop.noOfFertilizersUsed = Double.parseDouble(fertsUsedEdittxt.getText().toString());
        crop.noOfWaterAppliedPerDay = Double.parseDouble(waterAppliedEdittxt.getText().toString());

        int year = datePickerDialog.getDatePicker().getYear();
        int month = datePickerDialog.getDatePicker().getMonth();
        int dayOfMonth = datePickerDialog.getDatePicker().getDayOfMonth();

        crop.plantedStartDate = DateUtils.getDate(year, month, dayOfMonth).getTime() / 1000;
        crop.timeStamp = System.currentTimeMillis() / 1000;

        presenter.saveCropDetails(crop);
    }

    @Override
    public void setPlantedStartDate(String dateOfHarvest) {
        plantedStartDateTxt.setText(dateOfHarvest);
    }

    @Override
    public void showOkDialog(String msg) {
        okDialog = OkDialog.newInstance(msg);
        okDialog.show(getFragmentManager(), TAG);
    }

    @Override
    public void showOkDialog(String msg, OkDialog.EventListener listener) {
        okDialog = OkDialog.newInstance(msg, listener);
        okDialog.show(getFragmentManager(), TAG);
    }

    @Override
    public String getCropSavedText() {
        return getString(R.string.saved_crop);
    }

    @Override
    public void closeEditor() {
        getActivity().finish();
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
        setPlantedStartDate(formattedDate);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position == MAX_DEFAULT_CROP_NAMES - 1) {
            nameEdittxt.setVisibility(View.VISIBLE);
        } else {
            nameEdittxt.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        defaulNamesSpinner.setSelection(defaulNamesSpinner.getSelectedItemPosition());
    }
}
