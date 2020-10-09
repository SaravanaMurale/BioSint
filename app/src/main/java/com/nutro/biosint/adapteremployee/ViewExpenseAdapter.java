package com.nutro.biosint.adapteremployee;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewExpenseAdapter extends RecyclerView.Adapter<ViewExpenseAdapter.ViewExpenseViewHolder>  {

    @NonNull
    @Override
    public ViewExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewExpenseViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewExpenseViewHolder extends RecyclerView.ViewHolder{

        public ViewExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
