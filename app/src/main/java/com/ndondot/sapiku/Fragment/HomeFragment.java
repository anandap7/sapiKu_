package com.ndondot.sapiku.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private FirebaseUser mUser;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_layout);

        mUser = FirebaseAuth.getInstance().getCurrentUser();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        listPeternak = new ArrayList<>();

        getDataPeternak();

        return view;
    }

    public void getDataPeternak(){
        final DatabaseReference ref = mRootRef.child("peternak");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot user: dataSnapshot.getChildren()){
                    final String sPeternakId = user.getKey();
                    final String sNamaPeternak = user.child("nama").getValue(String.class);
                    final String sImageUri = user.child("imageUri").getValue(String.class);

                    DatabaseReference refSapi = ref.child(sPeternakId).child("sapi");
                    refSapi.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot sapi: dataSnapshot.getChildren()){
                                String sapiId = sapi.getKey();
                                String umurSapi = sapi.child("umur").getValue(String.class);
                                String hargaSapi = sapi.child("harga").getValue(String.class);
                                listPeternak.add(new PeternakModel(sPeternakId,sImageUri,sNamaPeternak,
                                        umurSapi,hargaSapi,sapiId));
                            }


                            peternakAdapter = new PeternakAdapter(getContext(), listPeternak);
                            recyclerView.setAdapter(peternakAdapter);
                            peternakAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
