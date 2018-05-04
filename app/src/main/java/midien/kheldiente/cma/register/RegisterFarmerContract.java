package midien.kheldiente.cma.register;


import midien.kheldiente.cma.BasePresenter;
import midien.kheldiente.cma.BaseView;
import midien.kheldiente.cma.common.OkDialog;
import midien.kheldiente.cma.data.Farmer;

public interface RegisterFarmerContract {

    interface View extends BaseView<Presenter> {

        void showLoadingDialog(String msg);

        void hideLoadingDialog();

        void showOkDialog(String msg);

        void showOkDialog(String msg, OkDialog.EventListener listener);

        String getRegisterFarmerTxt();

        String getPleaseFillUpAllFieldsTxt();

        String getRegistrationSuccessfulTxt();

        void goToTakePhoto();

        void closeMe();

    }

    interface Presenter extends BasePresenter {

        boolean isValid(Farmer farmer);

        boolean isValidEmail(String email);

        void registerFarmer(Farmer farmer);

        boolean isSamePassword(String password, String confirmPassword);
    }
}
