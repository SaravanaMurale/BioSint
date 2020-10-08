package com.nutro.biosint.adapteremployee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nutro.biosint.R;
import com.nutro.biosint.modelresponse.ViewCheckInResponse;

import java.util.List;

public class ViewCheckInAdapter extends RecyclerView.Adapter<ViewCheckInAdapter.MyCheckInViewHolder> {

    Context mCtx;
    List<ViewCheckInResponse> viewCheckInResponseList;
    CheckInClickListener checkInClickListener;

    public interface CheckInClickListener {
        public void onCheckInClick();

    }

    public ViewCheckInAdapter(Context mCtx, List<ViewCheckInResponse> viewCheckInResponseList, CheckInClickListener checkInClickListener) {
        this.mCtx = mCtx;
        this.viewCheckInResponseList = viewCheckInResponseList;
        this.checkInClickListener = checkInClickListener;
    }

    public void setDate(List<ViewCheckInResponse> viewCheckInResponseList) {
        this.viewCheckInResponseList = viewCheckInResponseList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyCheckInViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mCtx);
        View view = layoutInflater.inflate(R.layout.employee_view_checkin_adapter, parent);

        return new MyCheckInViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCheckInViewHolder holder, int position) {

        holder.viewChecInName.setText(viewCheckInResponseList.get(position).getViewCheckInName());
        holder.viewChecInDetais.setText(viewCheckInResponseList.get(position).getViewCheckInDetails());
        holder.viewCheckInAddress.setText(viewCheckInResponseList.get(position).getViewCheckInAddress());
        holder.viewCheckInDate.setText(viewCheckInResponseList.get(position).getViewCheckInDate());

    }

    @Override
    public int getItemCount() {
        return viewCheckInResponseList == null ? 0 : viewCheckInResponseList.size();
    }

    class MyCheckInViewHolder extends RecyclerView.ViewHolder {

        TextView viewChecInName, viewChecInDetais, viewCheckInAddress, viewCheckInDate;

        public MyCheckInViewHolder(@NonNull View itemView) {
            super(itemView);

            viewChecInName = (TextView) itemView.findViewById(R.id.viewChecInName);
            viewChecInDetais = (TextView) itemView.findViewById(R.id.viewChecInDetais);
            viewCheckInAddress = (TextView) itemView.findViewById(R.id.viewCheckInAddress);
            viewCheckInDate = (TextView) itemView.findViewById(R.id.viewCheckInDate);

        }
    }

}
