package com.celpa.celpaapp.common;

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

import com.celpa.celpaapp.R;


public class OkDialog extends DialogFragment implements View.OnClickListener {

    private EventListener listener;

    private TextView msgTxt;
    private Button okBtn;
    private String message = "";

    public static OkDialog newInstance(String text) {
        OkDialog l = new OkDialog();
        l.message = text;
        return l;
    }

    public static OkDialog newInstance(String text, EventListener listener) {
        OkDialog l = new OkDialog();
        l.message = text;
        l.listener = listener;
        return l;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_ok, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        msgTxt = view.findViewById(R.id.txt_msg);
        okBtn = view.findViewById(R.id.btn_ok);
        msgTxt.setText(message);
        okBtn.setOnClickListener(this);

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

    private void handleOk() {
        if(listener == null)
            dismiss();
        else {
            listener.onOk();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                handleOk();
                break;
            default:
                break;
        }
    }

    public interface EventListener {

        void onOk();

    }
}
