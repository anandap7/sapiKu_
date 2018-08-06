package com.ndondot.sapiku.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ndondot.sapiku.R;

import java.text.DecimalFormat;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterFragment extends Fragment {
    TextView mUsiaText,mHargaText,mJarakText,mKolaborasiText;
    SeekBar mJarakSeekbar,mUsiaSeekbar,mHargaSeekbar,mKolaborasiSeekbar;
    Button mCariBtn;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    private String mUsia,mHarga;
    private int mHargaInt;


    public FilterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_filter, container, false);

        mUsiaText = v.findViewById(R.id.filter_usia);
        mHargaText = v.findViewById(R.id.filter_harga);
        mJarakText = v.findViewById(R.id.filter_jarak);
        mKolaborasiText = v.findViewById(R.id.filter_kolab);
        mUsiaSeekbar = v.findViewById(R.id.seekbar_usia);
        mHargaSeekbar = v.findViewById(R.id.seekbar_harga);
        mJarakSeekbar = v.findViewById(R.id.seekbar_jarak);
        mKolaborasiSeekbar = v.findViewById(R.id.seekbar_kolaborasi);
        mCariBtn = v.findViewById(R.id.filter_cariBtn);

        mJarakSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String value = String.valueOf(mJarakSeekbar.getProgress());
                mJarakText.setText(value+" Km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                String value = String.valueOf(mJarakSeekbar.getProgress());
                mJarakText.setText(value+" Km");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                String value = String.valueOf(mJarakSeekbar.getProgress());
                mJarakText.setText(value+" Km");
            }
        });

        mUsiaSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String value = String.valueOf(mUsiaSeekbar.getProgress());
                mUsiaText.setText(value+" tahun");
                mUsia = mUsiaText.getText().toString();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                String value = String.valueOf(mUsiaSeekbar.getProgress());
                mUsiaText.setText(value+" tahun");
                mUsia = mUsiaText.getText().toString();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                String value = String.valueOf(mUsiaSeekbar.getProgress());
                mUsiaText.setText(value+" tahun");
                mUsia = mUsiaText.getText().toString();
            }
        });

        mKolaborasiSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String value = String.valueOf(mKolaborasiSeekbar.getProgress());
                mKolaborasiText.setText(value+" Orang");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                String value = String.valueOf(mKolaborasiSeekbar.getProgress());
                mKolaborasiText.setText(value+" Orang");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                String value = String.valueOf(mKolaborasiSeekbar.getProgress());
                mKolaborasiText.setText(value+" Orang");
            }
        });

        mHargaSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String value = String.valueOf(mHargaSeekbar.getProgress());
                mHargaInt = Integer.parseInt(value);
                double amount= Double.parseDouble(value);
                DecimalFormat format = new DecimalFormat("#,###");
                String harga = format.format(amount);
                mHargaText.setText("Rp. "+harga);
//                mHarga = value;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                String value = String.valueOf(mHargaSeekbar.getProgress());
                mHargaInt = Integer.parseInt(value);
                double amount= Double.parseDouble(value);
                DecimalFormat format = new DecimalFormat("#,###");
                String harga = format.format(amount);
                mHargaText.setText("Rp. "+harga);
//                mHarga = value;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                String value = String.valueOf(mHargaSeekbar.getProgress());
                double amount= Double.parseDouble(value);
                DecimalFormat format = new DecimalFormat("#,###");
                String harga = format.format(amount);
                mHargaText.setText("Rp. "+harga);
//                mHarga = value;
            }
        });

        mCariBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference ref = mRootRef.child("peternak");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                            String peternakId = snapshot.getKey();

                            DatabaseReference refSapi = ref.child(peternakId).child("sapi");
                            refSapi.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot sapi: dataSnapshot.getChildren()){
                                        String sapiId = sapi.getKey();
                                        String usia = sapi.child("umur").getValue(String.class);
                                        String harga = sapi.child("harga").getValue(String.class);
                                        int hargaInt = Integer.parseInt(harga);

                                        if  (usia.equals(mUsia) && mHargaInt>=hargaInt){
                                            Toast.makeText(getContext(), sapiId, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        return v;
    }

}
