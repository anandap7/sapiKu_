package com.ndondot.sapiku.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.ndondot.sapiku.Adapter.KolaborasiAdapter;
import com.ndondot.sapiku.Adapter.KolaborasiModel;
import com.ndondot.sapiku.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColaborationFragment extends Fragment {

    private List<KolaborasiModel> listKolaborasi;
    private RecyclerView recyclerView;
    private KolaborasiAdapter kolaborasiAdapter;

    private FirebaseAuth mAuth;

    public ColaborationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_colaboration, container, false);

        recyclerView = view.findViewById(R.id.recycler_colab);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        mAuth = FirebaseAuth.getInstance();

        listKolaborasi = new ArrayList<>();
        listKolaborasi.add(new KolaborasiModel("111","","Supardi","2000000","5"));
        listKolaborasi.add(new KolaborasiModel("222","","Paijo","5000000","2"));

        kolaborasiAdapter = new KolaborasiAdapter(getContext(),listKolaborasi);
        recyclerView.setAdapter(kolaborasiAdapter);
        kolaborasiAdapter.notifyDataSetChanged();

        return view;
    }
}
