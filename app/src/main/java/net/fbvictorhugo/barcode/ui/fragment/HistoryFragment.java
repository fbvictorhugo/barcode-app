package net.fbvictorhugo.barcode.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.fbvictorhugo.barcode.MyBarcode;
import net.fbvictorhugo.barcode.R;
import net.fbvictorhugo.barcode.ui.adapter.HistoryAdapter;
import net.fbvictorhugo.barcode.util.DialogUtils;

import java.util.ArrayList;
import java.util.Date;
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
        for (int i = 0; i < 5; i++) {
            MyBarcode b = new MyBarcode();
            b.displayValue = ("Bacon ipsum dolor at " + new Random().nextLong());
            b.format = i;
            b.setDateReading(new Date());
            barcodes.add(b);
        }

        HistoryAdapter adapter = new HistoryAdapter(barcodes);
        adapter.setOnItemClickListener(new HistoryAdapter.OnItemClickListener() {
            @Override
            public void onClick(MyBarcode barcode) {
                BarcodeDialogFragment dialogCode = DialogUtils.createBarcodeDialog(barcode);
                dialogCode.show(getFragmentManager(), BarcodeDialogFragment.TAG);
            }
        });

        mRecyclerView.setAdapter(adapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLayoutManager.getOrientation());

        mRecyclerView.addItemDecoration(mDividerItemDecoration);
        mRecyclerView.setLayoutManager(mLayoutManager);

        return baseView;
    }

    private void findViews(View baseView) {
        mRecyclerView = (RecyclerView) baseView.findViewById(R.id.history_recycler);
    }

}