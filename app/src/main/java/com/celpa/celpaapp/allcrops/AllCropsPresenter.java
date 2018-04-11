package com.celpa.celpaapp.allcrops;


import android.util.Log;

import com.celpa.celpaapp.data.Crop;
import com.celpa.celpaapp.data.source.remote.CropRemoteDataSource;
import com.celpa.celpaapp.utils.scheduler.BaseSchedulerProvider;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class AllCropsPresenter implements AllCropsContract.Presenter {

    private static final String TAG = AllCropsPresenter.class.getSimpleName();

    private AllCropsContract.View allCropsView;
    private CropRemoteDataSource remoteDataSource;
    private BaseSchedulerProvider schedulerProvider;

    private CompositeDisposable compositeDisposable;

    public AllCropsPresenter(AllCropsContract.View view,
                             CropRemoteDataSource source,
                             BaseSchedulerProvider provider) {
        allCropsView = view;
        remoteDataSource = source;
        schedulerProvider = provider;
        compositeDisposable = new CompositeDisposable();
        allCropsView.setPresenter(this);
    }

    @Override
    public void onCreate() {}

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void getCrops() {
        compositeDisposable.clear();

        // allCropsView.showLoadingDialog();
        Disposable disposable = remoteDataSource.getCropsInJson(allCropsView.getFarmerId())
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(jsonArr -> {
                            unsubscribe();
                            List<Crop> crops = new ArrayList<>(0);
                            JsonArray array = jsonArr.getAsJsonArray();
                            Log.d(TAG, String.valueOf(array.size()));
                            for(JsonElement element: array) {
                                crops.add(Crop.getCrop(element.getAsJsonObject()));
                            }
                            // allCropsView.hideLoadingDialog();
                            allCropsView.displayCrops(crops);
                        },
                        throwable -> {
                            unsubscribe();
                            allCropsView.showOkDialog(throwable.getMessage());
                        }
                );
        compositeDisposable.add(disposable);
    }
}
