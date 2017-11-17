package net.fbvictorhugo.barcode.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.fbvictorhugo.barcode.R;
import net.fbvictorhugo.barcode.datasource.DatabaseHelper;
import net.fbvictorhugo.barcode.model.MyBarcode;
import net.fbvictorhugo.barcode.ui.adapter.HistoryAdapter;
import net.fbvictorhugo.barcode.util.DialogUtils;

import java.util.List;

/**
 * By fbvictorhugo on 16/03/17.
 */

public class HistoryFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private DatabaseHelper mDatabaseHelper;
    private HistoryAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View baseView = inflater.inflate(R.layout.fragment_history, parent, false);
        findViews(baseView);
        this.setHasOptionsMenu(true);

        mDatabaseHelper = new DatabaseHelper(getContext());
        List<MyBarcode> barcodes = mDatabaseHelper.loadBarcodes();

        mAdapter = new HistoryAdapter(barcodes);
        mAdapter.setOnItemClickListener(new HistoryAdapter.OnItemClickListener() {
            @Override
            public void onClick(MyBarcode barcode) {
                BarcodeDialogFragment dialogCode = DialogUtils.createBarcodeDialog(barcode);
                dialogCode.show(getFragmentManager(), BarcodeDialogFragment.TAG);
            }
        });

        mRecyclerView.setAdapter(mAdapter);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_history, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                clearHistory();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void clearHistory() {
        mDatabaseHelper.cleanBarcodes();
        mAdapter.clear();
        Toast.makeText(getContext(), R.string.message_hostory_clear, Toast.LENGTH_SHORT).show();
    }

}