package com.celpa.celpaapp.allcrops;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.celpa.celpaapp.R;
import com.celpa.celpaapp.allcrops.adapter.CropAdapter;
import com.celpa.celpaapp.common.LoadingDialog;
import com.celpa.celpaapp.common.OkDialog;
import com.celpa.celpaapp.data.Crop;
import com.celpa.celpaapp.utils.AppSettings;

import java.util.List;

public class AllCropsFragment extends Fragment
        implements AllCropsContract.View {

    private static final String TAG = AllCropsFragment.class.getSimpleName();

    private static AllCropsFragment INSTANCE;

    private AllCropsContract.Presenter presenter;

    private LoadingDialog loadingDialog;
    private OkDialog okDialog;
    private RecyclerView cropRecyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private CropAdapter cropAdapter;

    public static AllCropsFragment newInstance() {
        if(INSTANCE == null) {
            INSTANCE = new AllCropsFragment();
        }
        return INSTANCE;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_allcrops, container, false);
        cropRecyclerView = root.findViewById(R.id.rv_crops);

        init();
        return root;
    }

    private void init() {
        presenter.getCrops();
    }

    @Override
    public void setPresenter(AllCropsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void displayCrops(List<Crop> crops) {
        cropAdapter = new CropAdapter(crops);
        layoutManager = new LinearLayoutManager(getContext());
        cropRecyclerView.setLayoutManager(layoutManager);
        cropRecyclerView.setItemAnimator(new DefaultItemAnimator());
        cropRecyclerView.setAdapter(cropAdapter);
    }

    @Override
    public void showLoadingDialog() {
        loadingDialog = LoadingDialog.newInstance(getString(R.string.getting_crops));
        loadingDialog.show(getFragmentManager(), TAG);
    }

    @Override
    public void hideLoadingDialog() {
        if(loadingDialog == null)
            return;
        loadingDialog.dismiss();
    }

    @Override
    public void showOkDialog(String msg) {
        okDialog = OkDialog.newInstance(msg);
        okDialog.show(getFragmentManager(), TAG);
    }

    @Override
    public void showOkDialog(String msg, OkDialog.EventListener listener) {
        okDialog = OkDialog.newInstance(msg, listener);
        okDialog.show(getFragmentManager(), TAG);
    }

    @Override
    public long getFarmerId() {
        return AppSettings.getInstance(getContext()).getFarmerLoggedIn();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_allcrops, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_refresh:
                presenter.getCrops();
                break;
            default:
                break;
        }
        return true;
    }
}
