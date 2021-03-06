package com.ndondot.sapiku;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ndondot.sapiku.Adapter.PeternakModel;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class DetailSapiActivity extends AppCompatActivity {
    TextView mNamaText,mUmurText,mHargaText,mJenisText,mAlamatText,mGenderText,mDeskripsiText;
    ImageView mImageSapi;
    Button mBeliBtn;
    private String mSapiUri,mNama,mUmur,mHarga,mJenis,mAlamat,mGender,mDeskripsi,mPeternakId,mSapiId;
    private String hintJenis = "Jenis    : ",
                   hintUmur = "Umur    : ",
                   hintAlamat = "Alamat : ",
                   hintGender = "Gender : ",
                   hintHarga = "Harga   : Rp. ";




    FirebaseUser user;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sapi);

        user = FirebaseAuth.getInstance().getCurrentUser();

        mNamaText = findViewById(R.id.detail_nama);
        mUmurText = findViewById(R.id.detail_umur);
        mHargaText = findViewById(R.id.detail_harga);
        mJenisText = findViewById(R.id.detail_jenis);
        mAlamatText = findViewById(R.id.detail_alamat);
        mGenderText = findViewById(R.id.detail_gender);
        mDeskripsiText = findViewById(R.id.detail_deskripsi);
        mImageSapi = findViewById(R.id.image_sapi);
        mBeliBtn = findViewById(R.id.detail_buy_button);

        if (user==null){
            mBeliBtn.setEnabled(false);
            mBeliBtn.setBackgroundColor(getResources().getColor(R.color.gray));
        }

        if(getIntent().hasExtra("peternakId") && getIntent().hasExtra("sapiId")){
            mPeternakId = getIntent().getStringExtra("peternakId");
            mSapiId = getIntent().getStringExtra("sapiId");
        }

        getDataPeternak();

        mBeliBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    public void getDataPeternak(){
        DatabaseReference ref = mRootRef.child("peternak").child(mPeternakId);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mNama = dataSnapshot.child("nama").getValue(String.class);
                mAlamat = dataSnapshot.child("alamat").getValue(String.class);

                mNamaText.setText(mNama);
                mAlamatText.setText(hintAlamat+mAlamat);
                getDetailSapi();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void getDetailSapi(){
        DatabaseReference ref = mRootRef.child("peternak").child(mPeternakId).child("sapi").child(mSapiId);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mJenis = dataSnapshot.child("jenis").getValue(String.class);
                mUmur = dataSnapshot.child("umur").getValue(String.class);
                mGender = dataSnapshot.child("gender").getValue(String.class);
                mHarga = dataSnapshot.child("harga").getValue(String.class);
                double amount= Double.parseDouble(mHarga);
                DecimalFormat format = new DecimalFormat("#,###");
                String harga = format.format(amount);

                mDeskripsi = dataSnapshot.child("deskripsi").getValue(String.class);
                mSapiUri = dataSnapshot.child("sapiUri").getValue(String.class);

                Picasso.get().load(mSapiUri).fit().centerCrop().into(mImageSapi);
                mJenisText.setText(hintJenis+mJenis);
                mUmurText.setText(hintUmur+mUmur);
                mGenderText.setText(hintGender+mGender);
                mHargaText.setText(hintHarga+harga);
                mDeskripsiText.setText(mDeskripsi);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Konfirmasi");
        alertDialogBuilder
                .setMessage("Apakah Anda ingin melakukan kolaborasi qurban?")
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                        intent.putExtra("status", "true");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
