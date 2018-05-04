package midien.kheldiente.cma.allcrops;


import midien.kheldiente.cma.BasePresenter;
import midien.kheldiente.cma.BaseView;
import midien.kheldiente.cma.common.OkDialog;
import midien.kheldiente.cma.data.Crop;

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
