package com.nutro.biosint.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nutro.biosint.R;
import com.nutro.biosint.adapteremployee.ViewExpenseAdapter;
import com.nutro.biosint.modelresponse.ViewExpenseResponse;

import java.util.List;

public class ViewMyEmpExpenseAdapter extends RecyclerView.Adapter<ViewMyEmpExpenseAdapter.ViewMyEmpExpenseViewHolder> {

    private Context mCtx;
    private List<ViewExpenseResponse> viewExpenseResponseList;
    MyEmpExpenseStatusClickListener myEmpExpenseStatusClickListener;

    public interface MyEmpExpenseStatusClickListener {

        public void onClickMyEmpExpenseStatus();

    }

    public ViewMyEmpExpenseAdapter(Context mCtx, List<ViewExpenseResponse> viewExpenseResponseList, MyEmpExpenseStatusClickListener myEmpExpenseStatusClickListener) {
        this.mCtx = mCtx;
        this.viewExpenseResponseList = viewExpenseResponseList;
        this.myEmpExpenseStatusClickListener = myEmpExpenseStatusClickListener;
    }

    public void setData(List<ViewExpenseResponse> viewExpenseResponseList) {
        this.viewExpenseResponseList = viewExpenseResponseList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewMyEmpExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mCtx);
        View view = layoutInflater.inflate(R.layout.my_emp_expense_adapter, parent, false);

        return new ViewMyEmpExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewMyEmpExpenseViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return viewExpenseResponseList == null ? 0 : viewExpenseResponseList.size();
    }

    public class ViewMyEmpExpenseViewHolder extends RecyclerView.ViewHolder {

        TextView myEmpExpensePurposeDetail, myEmpExpensePacksTaken, myEmpExpenseViewExpDate, myEmpExpenseViewExpPrice,myEmpExpenseAccept,myEmpExpenseReject,myEmpExpenseDelete;

        public ViewMyEmpExpenseViewHolder(@NonNull View itemView) {
            super(itemView);

            myEmpExpensePurposeDetail = (TextView) itemView.findViewById(R.id.myEmpPurposeDetail);
            myEmpExpensePacksTaken = (TextView) itemView.findViewById(R.id.myEmpPacksTaken);
            myEmpExpenseViewExpDate = (TextView) itemView.findViewById(R.id.myEmpViewExpDate);
            myEmpExpenseViewExpPrice = (TextView) itemView.findViewById(R.id.myEmpViewExpPrice);

            myEmpExpenseAccept=(TextView)itemView.findViewById(R.id.myEmpExpenseAccept);
            myEmpExpenseDelete=(TextView)itemView.findViewById(R.id.myEmpExpenseDelete);
            myEmpExpenseReject=(TextView)itemView.findViewById(R.id.myEmpExpenseReject);

        }
    }
}
