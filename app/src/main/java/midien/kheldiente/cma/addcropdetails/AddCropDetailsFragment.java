package midien.kheldiente.cma.addcropdetails;


import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Bitmap;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import midien.kheldiente.cma.R;
import midien.kheldiente.cma.common.LoadingDialog;
import midien.kheldiente.cma.common.OkDialog;
import midien.kheldiente.cma.common.YesNoDialog;
import midien.kheldiente.cma.data.Crop;
import midien.kheldiente.cma.utils.SquareMeterUtils;
import midien.kheldiente.cma.utils.AppSettings;
import midien.kheldiente.cma.utils.BitmapUtils;
import midien.kheldiente.cma.utils.DateUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.livinglifetechway.quickpermissions.annotations.WithPermissions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddCropDetailsFragment extends Fragment
        implements AddCropDetailsContract.View,
        View.OnClickListener,
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener,
        AdapterView.OnItemSelectedListener,
        CompoundButton.OnCheckedChangeListener {

    private static final String TAG = AddCropDetailsFragment.class.getSimpleName();

    private static final String EXTRA_CROP = "crop";

    private static final int MAX_DEFAULT_CROP_NAMES = 4;

    private List<String> sqMeters = new ArrayList<>(0);
    private Crop crop;
    private Date plantedDate;

    private AddCropDetailsContract.Presenter presenter;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private LoadingDialog loadingDialog;
    private OkDialog cropSavedOkDialog;
    private YesNoDialog postToMarkerDialog;
    private Spinner defaulNamesSpinner;
    private EditText nameEdittxt;
    private EditText fertsUsedEdittxt;
    private EditText waterAppliedEdittxt;
    private EditText quantityEdittxt;
    private EditText plantedDurEdittxt;
    private TextView plantedStartDateTxt;
    private TextView locationTxt;
    private TextView weatherTxt;
    private TextView hectaresTxt;
    private Button changeApproxDateBtn;
    private ImageView cropImgView;
    private Spinner fertsUnitSpinner;
    private Spinner waterUnitSpinner;
    private CheckBox setMinSqMeterCBox;
    private LinearLayout sqMeterLayout;
    private Spinner sqMeterSpinner;
    private ArrayAdapter<String> sqMeterAdapter;

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
        quantityEdittxt = root.findViewById(R.id.edittxt_quantity);
        plantedDurEdittxt = root.findViewById(R.id.edittxt_planted_duration);
        fertsUnitSpinner = root.findViewById(R.id.sp_ferts_unit);
        waterUnitSpinner = root.findViewById(R.id.sp_water_unit);
        setMinSqMeterCBox = root.findViewById(R.id.cb_min_sq_meter);
        sqMeterLayout = root.findViewById(R.id.ll_sqm);
        sqMeterSpinner = root.findViewById(R.id.sp_minimum_sq);
        hectaresTxt = root.findViewById(R.id.txt_hectares);

        init();

        return root;
    }

    private void init() {
        changeApproxDateBtn.setOnClickListener(this);
        showCapturedPhoto();
        showLocation();
        showWeather();

        defaulNamesSpinner.setOnItemSelectedListener(this);

        setMinSqMeterCBox.setOnCheckedChangeListener(this);

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);

        datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
        timePickerDialog  = new TimePickerDialog(getActivity(), this, hour, minute, false);

        plantedDate = DateUtils.getDate(year, month + 1, day, hour, minute);

        // Init approx. date of harvest
        // Need to +1 in month becasue January === 1
        String formattedDate = DateUtils.getFormattedString(plantedDate);
        setPlantedStartDate(formattedDate);

        sqMeters = SquareMeterUtils.generateSquareMeters(1000, 30000, 1000);
        sqMeterAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, sqMeters);
        sqMeterSpinner.setAdapter(sqMeterAdapter);
        sqMeterSpinner.setOnItemSelectedListener(this);
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
        if(datePickerDialog == null)
            return;

        datePickerDialog.show();
    }

    @Override
    public void showPostToMarketDialog(String msg, YesNoDialog.EventListener listener) {
        if(msg.isEmpty()) {
            msg = getString(R.string.post_to_market_msg);
        }
        postToMarkerDialog = YesNoDialog.newInstance(msg, listener);
        postToMarkerDialog.show(getFragmentManager(), TAG);
    }

    @Override
    public void hidePostToMarketDialog() {
        if(postToMarkerDialog == null) {
            return;
        }

        postToMarkerDialog.dismiss();
    }

    @WithPermissions(permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    public void saveToLocalAndRemoteSources() {
        try {
            crop.farmerId = AppSettings.getInstance(getContext()).getFarmerLoggedIn();

            long selected = defaulNamesSpinner.getSelectedItemPosition();
            String cropName = "";
            if (selected < MAX_DEFAULT_CROP_NAMES - 1) {
                cropName = defaulNamesSpinner.getSelectedItem().toString();
            } else {
                cropName = nameEdittxt.getText().toString();
            }

            crop.name = cropName;
            crop.noOfFertilizersUsed = fertsUsedEdittxt.getText().toString() + fertsUnitSpinner.getSelectedItem().toString();
            crop.noOfWaterAppliedPerDay = waterAppliedEdittxt.getText().toString() + waterUnitSpinner.getSelectedItem().toString();

            crop.plantedStartDate = plantedDate.getTime() / 1000;
            crop.timeStamp = System.currentTimeMillis() / 1000;

            if(quantityEdittxt.getText().toString().isEmpty()) {
                crop.quantity = 0;
            } else {
                crop.quantity = Double.parseDouble(quantityEdittxt.getText().toString());
            }

            if(plantedDurEdittxt.getText().toString().isEmpty()) {
                crop.plantedDuration = 0;
            } else {
                crop.plantedDuration = Integer.parseInt(plantedDurEdittxt.getText().toString());
            }

            if (setMinSqMeterCBox.isChecked()) {
                crop.squareMeter = Double.parseDouble(sqMeterSpinner.getSelectedItem().toString());
            } else {
                crop.squareMeter = 0;
            }

            showPostToMarketDialog("", new YesNoDialog.EventListener() {
                @Override
                public void yes() {
                    hidePostToMarketDialog();
                    crop.postToMarket = 1; // True
                    presenter.saveCropDetails(crop);
                }

                @Override
                public void no() {
                    hidePostToMarketDialog();
                    crop.postToMarket = 0; // False
                    presenter.saveCropDetails(crop);
                }
            });
        } catch (NumberFormatException e) {
            showOkDialog(e.getMessage());
        }

    }

    @Override
    public void setPlantedStartDate(String dateOfHarvest) {
        plantedStartDateTxt.setText(dateOfHarvest);
    }

    @Override
    public void showOkDialog(String msg) {
        cropSavedOkDialog = OkDialog.newInstance(msg);
        cropSavedOkDialog.show(getFragmentManager(), TAG);
    }

    @Override
    public void showOkDialog(String msg, OkDialog.EventListener listener) {
        cropSavedOkDialog = OkDialog.newInstance(msg, listener);
        cropSavedOkDialog.show(getFragmentManager(), TAG);
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
        timePickerDialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_crop_names:
                if(position == MAX_DEFAULT_CROP_NAMES - 1) {
                    nameEdittxt.setVisibility(View.VISIBLE);
                } else {
                    nameEdittxt.setVisibility(View.GONE);
                }
                break;
            case R.id.sp_minimum_sq:
                processSquareMeter();
                break;
            default:
                break;
        }

    }

    private void processSquareMeter() {
        long selected = defaulNamesSpinner.getSelectedItemPosition();
        String cropName = "";
        if (selected < MAX_DEFAULT_CROP_NAMES - 1) {
            cropName = defaulNamesSpinner.getSelectedItem().toString();
        } else {
            cropName = nameEdittxt.getText().toString();
        }
        quantityEdittxt.setText(
                String.valueOf(processQuantity(cropName, Double.parseDouble(sqMeterSpinner.getSelectedItem().toString())))
        );
        hectaresTxt.setText(
                String.valueOf(SquareMeterUtils.toHectares(Double.parseDouble(sqMeterSpinner.getSelectedItem().toString())))
        );
    }

    private double processQuantity(String cropName, double squareMeters) {
        String[] defaultCrops = getResources().getStringArray(R.array.crop_name_defaults);
        if(cropName.equals(defaultCrops[0])) { // Lettuce
            return squareMeters / 1.5;
        } else if(cropName.equals(defaultCrops[1])) { // Cucumber
            return squareMeters / 4;
        } else if(cropName.equals(defaultCrops[2])) { // Chili(Pangsigang)
            return squareMeters / 3.333;
        } else {
            return 0d;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        switch (parent.getId()) {
            case R.id.sp_crop_names:
                defaulNamesSpinner.setSelection(defaulNamesSpinner.getSelectedItemPosition());
                break;
            case R.id.sp_minimum_sq:
                sqMeterSpinner.setSelection(sqMeterSpinner.getSelectedItemPosition());
                hectaresTxt.setText(
                        String.valueOf(SquareMeterUtils.toHectares(Double.parseDouble(sqMeterSpinner.getSelectedItem().toString())))
                );
                break;
            default:
                break;
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        int year = datePickerDialog.getDatePicker().getYear();
        int month = datePickerDialog.getDatePicker().getMonth() + 1; // Need to +1 because January == 1
        int dayOfMonth = datePickerDialog.getDatePicker().getDayOfMonth();
        plantedDate = DateUtils.getDate(year, month, dayOfMonth, hourOfDay, minute);
        String formattedDate = DateUtils.getFormattedString(plantedDate);
        setPlantedStartDate(formattedDate);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked) {
            quantityEdittxt.setVisibility(View.GONE);
            sqMeterLayout.setVisibility(View.VISIBLE);
            quantityEdittxt.setText(String.valueOf(Double.parseDouble(sqMeterSpinner.getSelectedItem().toString()) / 10));
        } else {
            sqMeterLayout.setVisibility(View.GONE);
            quantityEdittxt.setVisibility(View.VISIBLE);
            quantityEdittxt.setText("0");
        }
    }
}
