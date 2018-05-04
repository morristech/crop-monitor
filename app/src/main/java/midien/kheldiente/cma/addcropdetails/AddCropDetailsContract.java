package midien.kheldiente.cma.addcropdetails;


import midien.kheldiente.cma.BasePresenter;
import midien.kheldiente.cma.BaseView;
import midien.kheldiente.cma.common.OkDialog;
import midien.kheldiente.cma.common.YesNoDialog;
import midien.kheldiente.cma.data.Crop;

public interface AddCropDetailsContract {

    interface View extends BaseView<Presenter> {

        void showCapturedPhoto();

        void showLocation();

        void showWeather();

        void showLoadingDialog();

        void hideLoadingDialog();

        void showDatePickerDialog();

        void showPostToMarketDialog(String msg, YesNoDialog.EventListener listener);

        void hidePostToMarketDialog();

        void setPlantedStartDate(String dateOfHarvest);

        void showOkDialog(String msg);

        void showOkDialog(String msg, OkDialog.EventListener listener);

        String getCropSavedText();

        void closeEditor();

    }

    interface Presenter extends BasePresenter {

        void saveCropDetails(Crop crop);

        boolean isValidInput(String input);

    }

}
