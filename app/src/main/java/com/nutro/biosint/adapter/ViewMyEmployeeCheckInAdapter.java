package com.nutro.biosint.adapter;

import android.content.Context;
import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.GeoPoint;
import com.nutro.biosint.R;
import com.nutro.biosint.modelresponse.ViewCheckInResponse;
import com.nutro.biosint.utils.GpsUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewMyEmployeeCheckInAdapter extends RecyclerView.Adapter<ViewMyEmployeeCheckInAdapter.ViewMyEmployeeViewHolder> {

    private Context mCtx;
    private List<ViewCheckInResponse> myEmpCheckInResponseList;

    public ViewMyEmployeeCheckInAdapter(Context mCtx, List<ViewCheckInResponse> viewCheckInResponseList) {
        this.mCtx = mCtx;
        this.myEmpCheckInResponseList = viewCheckInResponseList;
    }

    public void setData(List<ViewCheckInResponse> viewCheckInResponseList) {
        this.myEmpCheckInResponseList = viewCheckInResponseList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewMyEmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mCtx);
        View view = layoutInflater.inflate(R.layout.viewmyemployeecheckinadapter, parent, false);

        return new ViewMyEmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewMyEmployeeViewHolder holder, int position) {

        GeoPoint geoPoint = myEmpCheckInResponseList.get(position).getGeoPoint();
        List<Address> geoAddresses = GpsUtils.getAddressFromMap(mCtx, geoPoint.getLatitude(), geoPoint.getLongitude());

        if (geoAddresses.size() != 0) {

            String address = geoAddresses.get(0).getAddressLine(0);
            String area = geoAddresses.get(0).getLocality();
            String city = geoAddresses.get(0).getAdminArea();
            String country = geoAddresses.get(0).getCountryName();
            String postalCode = geoAddresses.get(0).getPostalCode();
            String subAdminArea = geoAddresses.get(0).getSubAdminArea();
            String subLocality = geoAddresses.get(0).getSubLocality();
            String premises = geoAddresses.get(0).getPremises();
            String addressLine = geoAddresses.get(0).getAddressLine(0);
            holder.myEmpCheckInArea.setText(area);
            holder.myEmpCheckInAddress.setText(address + " " + area + " " + city + " " + postalCode);

        } else {

            Toast.makeText(mCtx, "Please try again", Toast.LENGTH_LONG).show();
        }
        holder.myEmpCheckInDate.setText(myEmpCheckInResponseList.get(position).getViewCheckInDate());
        holder.myEmpCheckInDetails.setText(myEmpCheckInResponseList.get(position).getViewCheckInDetails());


    }

    @Override
    public int getItemCount() {
        return myEmpCheckInResponseList == null ? 0 : myEmpCheckInResponseList.size();
    }

    public class ViewMyEmployeeViewHolder extends RecyclerView.ViewHolder {

        CircleImageView myEmpImage;
        TextView myEmpCheckInArea, myEmpCheckInDate, myEmpCheckInDetails, myEmpCheckInAddress;

        public ViewMyEmployeeViewHolder(@NonNull View itemView) {
            super(itemView);

            myEmpImage = (CircleImageView) itemView.findViewById(R.id.myEmpImage);

            myEmpCheckInArea = (TextView) itemView.findViewById(R.id.myEmpCheckInArea);
            myEmpCheckInDate = (TextView) itemView.findViewById(R.id.myEmpCheckInDate);
            myEmpCheckInDetails = (TextView) itemView.findViewById(R.id.myEmpCheckInDetails);
            myEmpCheckInAddress = (TextView) itemView.findViewById(R.id.myEmpCheckInAddress);

        }
    }

}
