package com.celpa.celpaapp.common;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.celpa.celpaapp.R;


public class LoadingDialog extends DialogFragment {

    private TextView text;
    private String message = "";

    public static LoadingDialog newInstance(String text) {
        LoadingDialog l = new LoadingDialog();
        l.message = text;
        return l;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_loading, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        text = view.findViewById(R.id.txt_progress_msg);
        updateTxtMsg(message);
        return view;
    }

    public void updateTxtMsg(String msg) {
        message = msg;
        text.setText(message);
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
}
