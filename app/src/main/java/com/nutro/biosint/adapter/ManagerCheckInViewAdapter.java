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

public class ManagerCheckInViewAdapter extends RecyclerView.Adapter<ManagerCheckInViewAdapter.ManagerCheckInViewHolder> {

    private Context mCtx;
    private List<EmployeeNameDTO> employeeNameDTOList;

    public ManagerCheckInViewAdapter(Context mCtx, List<EmployeeNameDTO> employeeNameDTOList) {
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

        holder.employeeName.setText(employeeNameDTOList.get(position).getEmployeeName());

    }

    @Override
    public int getItemCount() {
        return employeeNameDTOList == null ? 0 : employeeNameDTOList.size();
    }

    public class ManagerCheckInViewHolder extends RecyclerView.ViewHolder {

        TextView employeeName;

        public ManagerCheckInViewHolder(@NonNull View itemView) {
            super(itemView);

            employeeName = (TextView) itemView.findViewById(R.id.employeeName);
        }
    }

}
