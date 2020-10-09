package com.nutro.biosint.fragmentemployee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nutro.biosint.R;
import com.nutro.biosint.adapteremployee.ViewExpenseAdapter;
import com.nutro.biosint.modelresponse.ViewExpenseResponse;

import java.util.ArrayList;
import java.util.List;

public class ViewExpenseFragment extends Fragment implements ViewExpenseAdapter.ViewExpenseClickListener {

    RecyclerView viewExpenseRecyclerView;
    ViewExpenseAdapter viewExpenseAdapter;
    List<ViewExpenseResponse> viewExpenseResponseList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_expense__deatails_fragment, container, false);

        viewExpenseRecyclerView = (RecyclerView) view.findViewById(R.id.viewExpenseRecyclerView);
        viewExpenseRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewExpenseRecyclerView.setHasFixedSize(true);

        viewExpenseResponseList = new ArrayList<>();

        viewExpenseAdapter = new ViewExpenseAdapter(getActivity(), viewExpenseResponseList, ViewExpenseFragment.this);


        getExpenseReport();
        
        return view;
    }

    private void getExpenseReport() {
    }

    @Override
    public void onViewExpenseClick() {

    }
}
