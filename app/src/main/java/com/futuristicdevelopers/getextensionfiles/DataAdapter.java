package com.futuristicdevelopers.getextensionfiles;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ExtensionFileViewHolder> {
    private List<ExtensionFile>  mDataset;
    RecyclerViewItemClickListener recyclerViewItemClickListener;

    public DataAdapter(List<ExtensionFile> myDataset, RecyclerViewItemClickListener listener) {
        mDataset = myDataset;
        this.recyclerViewItemClickListener = listener;
    }

    @NonNull
    @Override
    public DataAdapter.ExtensionFileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.extension_file_item, parent, false);
        ExtensionFileViewHolder vh =
                new ExtensionFileViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.ExtensionFileViewHolder extensionFileViewHolder, int i) {
        extensionFileViewHolder.mTextView.setText(mDataset.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public  class ExtensionFileViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView mTextView;

        public ExtensionFileViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.tv_fileName);
            v.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            recyclerViewItemClickListener.clickOnItem(mDataset.get(this.getAdapterPosition()).path);
        }
    }

    public interface RecyclerViewItemClickListener {
        void clickOnItem(String data);
    }
}
