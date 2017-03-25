package net.fbvictorhugo.barcode.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.fbvictorhugo.barcode.model.MyBarcode;
import net.fbvictorhugo.barcode.R;
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
        List<MyBarcode> barcodes = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            MyBarcode barcode = new MyBarcode();
            barcode.setCode("QR" + new Random().nextLong());
            barcodes.add(barcode);
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