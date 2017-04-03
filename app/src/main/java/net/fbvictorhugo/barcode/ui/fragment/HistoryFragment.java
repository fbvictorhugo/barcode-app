package net.fbvictorhugo.barcode.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.vision.barcode.Barcode;

import net.fbvictorhugo.barcode.R;
import net.fbvictorhugo.barcode.ui.BarcodeModelView;
import net.fbvictorhugo.barcode.ui.adapter.HistoryAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * By fbvictorhugo on 16/03/17.
 */

public class HistoryFragment extends Fragment {

    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View baseView = inflater.inflate(R.layout.fragment_history, parent, false);
        findViews(baseView);

        //TODO Lorem barcode
        List<BarcodeModelView> barcodes = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            Barcode b = new Barcode();
            b.displayValue = ("QR" + new Random().nextLong());
            BarcodeModelView barcodeView = new BarcodeModelView(b);
            barcodes.add(barcodeView);
        }

        HistoryAdapter adapter = new HistoryAdapter(barcodes, getContext());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return baseView;
    }

    private void findViews(View baseView) {
        mRecyclerView = (RecyclerView) baseView.findViewById(R.id.history_recycler);
    }
}