package net.fbvictorhugo.barcode.ui.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.fbvictorhugo.barcode.R;
import net.fbvictorhugo.barcode.model.MyBarcode;
import net.fbvictorhugo.barcode.ui.BarcodeModelView;

import java.util.ArrayList;
import java.util.List;

/**
 * By fbvictorhugo on 16/03/17.
 */

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MyBarcode> mData;
    private OnItemClickListener onItemClickListener;

    private final int VIEW_EMPTY = 0;
    private final int VIEW_HISTORY = 1;

    public HistoryAdapter(List<MyBarcode> data) {
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_HISTORY:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_history_item, parent, false);
                return new ViewHolderHistory(view);

            default:
                View viewEmpty = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_state, parent, false);
                return new ViewHolderEmptyState(viewEmpty);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.size() == 0) {
            return VIEW_EMPTY;
        } else {
            return VIEW_HISTORY;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        switch (holder.getItemViewType()) {
            case VIEW_EMPTY:
                //
                break;

            case VIEW_HISTORY:
                ViewHolderHistory viewHolder = (ViewHolderHistory) holder;

                BarcodeModelView barcodeModelView = new BarcodeModelView(mData.get(holder.getAdapterPosition()));
                viewHolder.contentTextView.setText(barcodeModelView.getBarcodeValue());
                viewHolder.dateTextView.setText(barcodeModelView.getReadingDate());
                viewHolder.contentSecondaryTextView.setText(barcodeModelView.getBarcodeContentTypeResValue());
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.onClick(mData.get(position));
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (mData.size() == 0) {
            return 1; // woraround do show ViewHolderEmptyState
        } else {
            return mData.size();
        }
    }

    public void clear() {
        mData = new ArrayList<>();
        notifyDataSetChanged();
    }

    private class ViewHolderHistory extends RecyclerView.ViewHolder {

        private final AppCompatTextView contentTextView;
        private final AppCompatTextView dateTextView;
        private final AppCompatTextView contentSecondaryTextView;

        ViewHolderHistory(View view) {
            super(view);
            contentTextView = (AppCompatTextView) view.findViewById(R.id.history_item_content);
            dateTextView = (AppCompatTextView) view.findViewById(R.id.history_item_date);
            contentSecondaryTextView = (AppCompatTextView) view.findViewById(R.id.history_item_secondary_content);
        }
    }

    private class ViewHolderEmptyState extends RecyclerView.ViewHolder {

        ViewHolderEmptyState(View view) {
            super(view);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public interface OnItemClickListener {

        void onClick(MyBarcode barcode);

    }

}
