package net.fbvictorhugo.barcode.ui.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.fbvictorhugo.barcode.model.MyBarcode;
import net.fbvictorhugo.barcode.R;
import net.fbvictorhugo.barcode.ui.BarcodeModelView;

import java.util.ArrayList;
import java.util.List;

/**
 * By fbvictorhugo on 16/03/17.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<MyBarcode> mData;
    private OnItemClickListener onItemClickListener;

    public HistoryAdapter(List<MyBarcode> data) {
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        BarcodeModelView barcodeModelView = new BarcodeModelView(mData.get(holder.getAdapterPosition()));
        holder.contentTextView.setText(barcodeModelView.getBarcodeValue());
        holder.dateTextView.setText(barcodeModelView.getReadingDate());
        holder.contentSecondaryTextView.setText(barcodeModelView.ReadingSourceWas());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onClick(mData.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void clear() {
        mData = new ArrayList<>();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final AppCompatTextView contentTextView;
        private final AppCompatTextView dateTextView;
        private final AppCompatTextView contentSecondaryTextView;

        ViewHolder(View view) {
            super(view);
            contentTextView = (AppCompatTextView) view.findViewById(R.id.history_item_content);
            dateTextView = (AppCompatTextView) view.findViewById(R.id.history_item_date);
            contentSecondaryTextView = (AppCompatTextView) view.findViewById(R.id.history_item_secondary_content);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public interface OnItemClickListener {

        void onClick(MyBarcode barcode);

    }

}
