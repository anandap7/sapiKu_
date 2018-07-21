package com.ndondot.sapiku.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ndondot.sapiku.Adapter.PeternakAdapter;
import com.ndondot.sapiku.Adapter.PeternakModel;
import com.ndondot.sapiku.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private List<PeternakModel> listPeternak;
    private RecyclerView recyclerView;
    private PeternakAdapter peternakAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_layout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        listPeternak = new ArrayList<>();
        listPeternak.add(new PeternakModel("1084", "PT. Sapi Sejahtera", "30 Tahun", "Rp.2000000"));
        listPeternak.add(new PeternakModel("1825", "PT. Sapi Makmur", "20 Tahun", "Rp.1800000"));
        listPeternak.add(new PeternakModel("9411", "PT. Sapi Jaya", "28 Tahun", "Rp.2200000"));

        peternakAdapter = new PeternakAdapter(getContext(), listPeternak);
        recyclerView.setAdapter(peternakAdapter);
        peternakAdapter.notifyDataSetChanged();
        return view;
    }

}
