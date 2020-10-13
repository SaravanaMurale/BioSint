package com.nutro.biosint.adapteremployee;

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
        View view = layoutInflater.inflate(R.layout.employee_view_checkin_adapter, parent,false);

        return new MyCheckInViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCheckInViewHolder holder, int position) {

        holder.viewChecInName.setText(viewCheckInResponseList.get(position).getViewCheckInName());
        holder.viewChecInDetais.setText(viewCheckInResponseList.get(position).getViewCheckInDetails());
        GeoPoint geoPoint = viewCheckInResponseList.get(position).getGeoPoint();
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
            holder.viewCheckInAddress.setText(address + " " + area + " " + city + " " + postalCode);

        } else {

            Toast.makeText(mCtx, "Please try again", Toast.LENGTH_LONG).show();
        }

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
