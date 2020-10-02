package com.nutro.biosint.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nutro.biosint.R;
import com.nutro.biosint.modelrequest.EmployeeNameDTO;

import java.util.List;

public class ViewUserAdapter extends RecyclerView.Adapter<ViewUserAdapter.ManagerCheckInViewHolder> {

    private Context mCtx;
    private List<EmployeeNameDTO> employeeNameDTOList;

    public ViewUserAdapter(Context mCtx, List<EmployeeNameDTO> employeeNameDTOList) {
        this.mCtx = mCtx;
        this.employeeNameDTOList = employeeNameDTOList;
    }

    public void setData(List<EmployeeNameDTO> employeeNameDTOList) {
        this.employeeNameDTOList = employeeNameDTOList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ManagerCheckInViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mCtx);
        View view = layoutInflater.inflate(R.layout.layout_employee_name, parent, false);
        return new ManagerCheckInViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManagerCheckInViewHolder holder, int position) {

        //holder.assignWork.setText(employeeNameDTOList.get(position).getEmployeeName());

    }

    @Override
    public int getItemCount() {
        return employeeNameDTOList == null ? 0 : employeeNameDTOList.size();
    }

    public class ManagerCheckInViewHolder extends RecyclerView.ViewHolder {

        TextView assignWork,viewCheckIns;

        public ManagerCheckInViewHolder(@NonNull View itemView) {
            super(itemView);

            assignWork = (TextView) itemView.findViewById(R.id.assignWork);
            viewCheckIns = (TextView) itemView.findViewById(R.id.viewCheckIns);
        }
    }

}
