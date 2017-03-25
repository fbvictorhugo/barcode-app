package net.fbvictorhugo.barcode.ui.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import net.fbvictorhugo.barcode.model.MyBarcode;
import net.fbvictorhugo.barcode.R;

import java.util.List;

/**
 * By fbvictorhugo on 16/03/17.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private final Context mContext;
    private final List<MyBarcode> mData;

    public HistoryAdapter(List<MyBarcode> data, Context context) {
        mData = data;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.contentTextView.setText(mData.get(position).getCode());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final AppCompatTextView contentTextView;
        private final ImageButton actionButton;

        public ViewHolder(View view) {
            super(view);
            contentTextView = (AppCompatTextView) view.findViewById(R.id.content_text);
            actionButton = (ImageButton) view.findViewById(R.id.history_item_action_button);
        }
    }

}
