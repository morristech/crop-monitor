package com.celpa.celpaapp.addcropdetails;

import com.celpa.celpaapp.data.Crop;
import com.celpa.celpaapp.data.source.local.CropLocalDataSource;
import com.celpa.celpaapp.data.source.remote.CropRemoteDataSource;
import com.celpa.celpaapp.utils.scheduler.BaseSchedulerProvider;

import java.util.Optional;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class AddCropDetailsPresenter implements AddCropDetailsContract.Presenter {

    private AddCropDetailsContract.View addCropDetailsView;

    private CropLocalDataSource localDataSource;
    private CropRemoteDataSource remoteDataSource;

    private BaseSchedulerProvider schedulerProvider;

    private CompositeDisposable compositeDisposable;

    public AddCropDetailsPresenter(AddCropDetailsContract.View view,
                                   BaseSchedulerProvider provider,
                                   CropLocalDataSource local,
                                   CropRemoteDataSource remote) {
        addCropDetailsView = view;
        schedulerProvider = provider;
        localDataSource = local;
        remoteDataSource = remote;
        compositeDisposable = new CompositeDisposable();
        addCropDetailsView.setPresenter(this);
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
    public void saveCropDetails(Crop crop) {

        addCropDetailsView.showLoadingDialog();

        compositeDisposable.clear();
        Flowable flowables = Flowable.concat(saveCropToLocal(crop), saveCropToRemote(crop))
                .filter(obj -> !obj.isPresent())
                .firstOrError()
                .toFlowable();

        Disposable disposable = flowables
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(result -> {
                            addCropDetailsView.hideLoadingDialog();
                        },
                        throwable -> addCropDetailsView.hideLoadingDialog()
                );

        compositeDisposable.add(disposable);
    }

    private Flowable<Optional<Crop>> saveCropToRemote(Crop crop) {
        return remoteDataSource.saveCrop(crop);
    }

    private Flowable<Optional<Crop>> saveCropToLocal(Crop crop) {
        return localDataSource.saveCrop(crop);
    }

    @Override
    public boolean isValidInput(String input) {
        return !input.isEmpty();
    }
}
