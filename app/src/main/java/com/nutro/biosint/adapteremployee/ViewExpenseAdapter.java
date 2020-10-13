package com.nutro.biosint.adapteremployee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nutro.biosint.R;
import com.nutro.biosint.modelresponse.ViewExpenseResponse;

import java.util.List;

public class ViewExpenseAdapter extends RecyclerView.Adapter<ViewExpenseAdapter.ViewExpenseViewHolder> {


    Context mCtx;
    List<ViewExpenseResponse> viewExpenseResponseList;
    ViewExpenseClickListener viewExpenseClickListener;


    public interface ViewExpenseClickListener {
        public void onViewExpenseClick();
    }

    public ViewExpenseAdapter(Context mCtx, List<ViewExpenseResponse> viewExpenseResponseList, ViewExpenseClickListener viewExpenseClickListener) {
        this.mCtx = mCtx;
        this.viewExpenseResponseList = viewExpenseResponseList;
        this.viewExpenseClickListener = viewExpenseClickListener;
    }

    public void setData(List<ViewExpenseResponse> viewExpenseResponseList) {
        this.viewExpenseResponseList = viewExpenseResponseList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mCtx);
        View view = layoutInflater.inflate(R.layout.employee_view_expense_adapter, parent,false);

        return new ViewExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewExpenseViewHolder holder, int position) {

        holder.purposeDetail.setText(viewExpenseResponseList.get(position).getPurposeDetail());
        holder.packsTaken.setText(viewExpenseResponseList.get(position).getPacksTaken());
        holder.viewExpDate.setText(viewExpenseResponseList.get(position).getViewExpDate());
        holder.viewExpPrice.setText(viewExpenseResponseList.get(position).getViewExpPrice());

    }

    @Override
    public int getItemCount() {
        return viewExpenseResponseList == null ? 0 : viewExpenseResponseList.size();
    }

    public class ViewExpenseViewHolder extends RecyclerView.ViewHolder {

        TextView purposeDetail, packsTaken, viewExpDate, viewExpPrice;
        ImageView viewExpStatus;

        public ViewExpenseViewHolder(@NonNull View itemView) {
            super(itemView);

            purposeDetail = (TextView) itemView.findViewById(R.id.purposeDetail);
            packsTaken = (TextView) itemView.findViewById(R.id.packsTaken);
            viewExpDate = (TextView) itemView.findViewById(R.id.viewExpDate);
            viewExpPrice = (TextView) itemView.findViewById(R.id.viewExpPrice);

            viewExpStatus = (ImageView) itemView.findViewById(R.id.viewExpStatus);


        }
    }

}
