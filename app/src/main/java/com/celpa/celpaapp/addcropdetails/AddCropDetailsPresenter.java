package com.celpa.celpaapp.addcropdetails;


import com.celpa.celpaapp.data.Crop;

public class AddCropDetailsPresenter implements AddCropDetailsContract.Presenter {

    private AddCropDetailsContract.View addCropDetailsView;

    public AddCropDetailsPresenter(AddCropDetailsContract.View view) {
        addCropDetailsView = view;
    }

    @Override
    public void onCreate() {}

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void saveCropDetailsInLocal(Crop crop) {
        // Save in database
    }

    @Override
    public void saveCropDetailsInRemote(Crop crop) {
        // Save to remote server
    }

    @Override
    public void saveCropDetails(Crop crop) {
        saveCropDetailsInLocal(crop);
        saveCropDetailsInRemote(crop);
    }

    @Override
    public boolean isValidInput(String input) {
        return !input.isEmpty();
    }
}
