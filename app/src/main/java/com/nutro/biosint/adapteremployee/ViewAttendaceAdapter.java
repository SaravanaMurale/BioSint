package com.nutro.biosint.adapteremployee;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nutro.biosint.modelresponse.ViewAttendanceResponse;

import java.util.List;

public class ViewAttendaceAdapter extends RecyclerView.Adapter<ViewAttendaceAdapter.ViewAttendaceViewHolder> {

    Context mCtx;
    List<ViewAttendanceResponse> viewAttendanceResponseList;

    public ViewAttendaceAdapter(Context mCtx, List<ViewAttendanceResponse> viewAttendanceResponseList) {
        this.mCtx = mCtx;
        this.viewAttendanceResponseList = viewAttendanceResponseList;
    }

    public void setData(List<ViewAttendanceResponse> viewAttendanceResponseList) {
        this.viewAttendanceResponseList = viewAttendanceResponseList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewAttendaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAttendaceViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewAttendaceViewHolder extends RecyclerView.ViewHolder {

        public ViewAttendaceViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
