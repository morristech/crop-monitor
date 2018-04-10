package com.celpa.celpaapp.allcrops;


import com.celpa.celpaapp.BasePresenter;
import com.celpa.celpaapp.BaseView;
import com.celpa.celpaapp.common.OkDialog;
import com.celpa.celpaapp.data.Crop;

import java.util.List;

public interface AllCropsContract {

    interface View extends BaseView<Presenter> {

        void displayCrops(List<Crop> crops);

        void showLoadingDialog();

        void hideLoadingDialog();

        void showOkDialog(String msg);

        void showOkDialog(String msg, OkDialog.EventListener listener);

        long getFarmerId();

    }

    interface Presenter extends BasePresenter {

        void getCrops();

    }

}
