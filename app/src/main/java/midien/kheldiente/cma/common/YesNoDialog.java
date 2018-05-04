package midien.kheldiente.cma.common;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import midien.kheldiente.cma.R;


public class YesNoDialog extends DialogFragment implements View.OnClickListener {

    private EventListener listener;

    private TextView msgTxt;
    private Button yesBtn;
    private Button noBtn;
    private String message = "";

    public static YesNoDialog newInstance(String text) {
        YesNoDialog l = new YesNoDialog();
        l.message = text;
        return l;
    }

    public static YesNoDialog newInstance(String text, EventListener listener) {
        YesNoDialog l = new YesNoDialog();
        l.message = text;
        l.listener = listener;
        return l;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_yesno, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        msgTxt = view.findViewById(R.id.txt_msg);
        yesBtn = view.findViewById(R.id.btn_yes);
        noBtn = view.findViewById(R.id.btn_no);
        msgTxt.setText(message);
        yesBtn.setOnClickListener(this);
        noBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Window window = getDialog().getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(this, tag);
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private void handleYes() {
        if(listener == null)
            dismiss();
        else {
            listener.yes();
        }
    }

    private void handleNo() {
        if(listener == null)
            dismiss();
        else {
            listener.no();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                handleYes();
                break;
            case R.id.btn_no:
                handleNo();
                break;
            default:
                break;
        }
    }

    public interface EventListener {

        void yes();

        void no();

    }
}
