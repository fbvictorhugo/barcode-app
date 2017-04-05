package net.fbvictorhugo.barcode.ui.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.fbvictorhugo.barcode.MyBarcode;
import net.fbvictorhugo.barcode.R;
import net.fbvictorhugo.barcode.ui.BarcodeModelView;

import java.util.List;

/**
 * By fbvictorhugo on 16/03/17.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private final List<MyBarcode> mData;
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
        holder.contentSecondaryTextView.setText(barcodeModelView.getDateReaded());

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

    class ViewHolder extends RecyclerView.ViewHolder {

        private final AppCompatTextView contentTextView;
        private final AppCompatTextView contentSecondaryTextView ;

        ViewHolder(View view) {
            super(view);
            contentTextView = (AppCompatTextView) view.findViewById(R.id.content_text);
            contentSecondaryTextView = (AppCompatTextView) view.findViewById(R.id.content_text_secondary);
        }

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public interface OnItemClickListener {

        void onClick(MyBarcode barcode);

    }

}
