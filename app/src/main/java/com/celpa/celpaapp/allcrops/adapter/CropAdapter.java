package com.celpa.celpaapp.allcrops.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.celpa.celpaapp.R;
import com.celpa.celpaapp.data.Crop;
import com.celpa.celpaapp.utils.DateUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class CropAdapter extends RecyclerView.Adapter<CropAdapter.ViewHolder> {

    private static String BASE_URL_IMAGE = "";
    private List<Crop> crops = new ArrayList<>(0);

    public CropAdapter(List<Crop> crops) {
        this.crops = crops;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_crop, parent, false);

        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindCrop(crops.get(position));
    }

    @Override
    public int getItemCount() {
        return crops.size();
    }

    public void setBaseUrlForImage(String url) {
        BASE_URL_IMAGE = url;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView cropImgView;
        TextView cropNameTxt;
        TextView locationTxt;
        TextView weatherTxt;
        TextView plantedStartDateTxt;
        TextView fertsUsedTxt;
        TextView waterAppliedTxt;

        public ViewHolder(View itemView) {
            super(itemView);
            cropImgView = itemView.findViewById(R.id.iv_crop);
            cropNameTxt = itemView.findViewById(R.id.txt_crop_name);
            locationTxt = itemView.findViewById(R.id.txt_location);
            weatherTxt = itemView.findViewById(R.id.txt_weather);
            plantedStartDateTxt = itemView.findViewById(R.id.txt_planted_start_date);
            fertsUsedTxt = itemView.findViewById(R.id.txt_ferts_used);
            waterAppliedTxt = itemView.findViewById(R.id.txt_water_applied);
        }

        public void bindCrop(Crop crop) {
            cropNameTxt.setText(crop.name);

            try {
                JsonObject locationObj = new JsonParser().parse(crop.location).getAsJsonObject();
                locationTxt.setText(locationObj.getAsJsonPrimitive("address").getAsString());
            } catch (JsonParseException e) {
                e.printStackTrace();
            }

            weatherTxt.setText(crop.weather);
            plantedStartDateTxt.setText(DateUtils.getFormattedString(crop.plantedStartDate));
            fertsUsedTxt.setText(String.valueOf(crop.noOfFertilizersUsed));
            waterAppliedTxt.setText(String.valueOf(crop.noOfWaterAppliedPerDay));

            // Trigger the download of the URL async into the image view
            Picasso.get()
                    .load(BASE_URL_IMAGE + crop.img.get(0).imgPath)
                    .resize(100, 100)
                    .centerInside()
                    .error(R.drawable.error_img)
                    .into(cropImgView);
        }
    }

}
