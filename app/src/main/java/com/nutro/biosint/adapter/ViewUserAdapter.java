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
import com.nutro.biosint.modelresponse.ManageEmployeeResponse;

import java.util.List;

public class ViewUserAdapter extends RecyclerView.Adapter<ViewUserAdapter.ManagerCheckInViewHolder> {

    private Context mCtx;
    private List<ManageEmployeeResponse> employeeNameDTOList;
    public EmployeeClickListener employeeClickListener;

    public interface EmployeeClickListener {
        public void onWorkAssignClick(ManageEmployeeResponse manageEmployeeResponse);
        public void onViewCheckInClick(ManageEmployeeResponse manageEmployeeResponse);
    }

    public ViewUserAdapter(Context mCtx, List<ManageEmployeeResponse> employeeNameDTOList, EmployeeClickListener employeeClickListener) {
        this.mCtx = mCtx;
        this.employeeNameDTOList = employeeNameDTOList;
        this.employeeClickListener = employeeClickListener;
    }

    public void setData(List<ManageEmployeeResponse> employeeNameDTOList) {
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

        holder.empName.setText(employeeNameDTOList.get(position).getName());
        holder.empDesignation.setText(employeeNameDTOList.get(position).getEmpDesi());

    }

    @Override
    public int getItemCount() {
        return employeeNameDTOList == null ? 0 : employeeNameDTOList.size();
    }

    public class ManagerCheckInViewHolder extends RecyclerView.ViewHolder {

        TextView empName, empDesignation, assignWork, viewCheckIn;

        public ManagerCheckInViewHolder(@NonNull View itemView) {
            super(itemView);

            empName = (TextView) itemView.findViewById(R.id.employeeName);
            empDesignation = (TextView) itemView.findViewById(R.id.employeeDesignation);
            assignWork = (TextView) itemView.findViewById(R.id.assignWork);
            viewCheckIn = (TextView) itemView.findViewById(R.id.viewCheckIns);

            //ManageEmployeeResponse manageEmployeeResponse=employeeNameDTOList.get(getAdapterPosition());

            assignWork.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ManageEmployeeResponse manageEmployeeResponse=employeeNameDTOList.get(getAdapterPosition());

                    employeeClickListener.onWorkAssignClick(manageEmployeeResponse);

                    //call assign job fragment
                }
            });

            viewCheckIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ManageEmployeeResponse manageEmployeeResponse=employeeNameDTOList.get(getAdapterPosition());

                    employeeClickListener.onViewCheckInClick(manageEmployeeResponse);

                }
            });
        }
    }

}
