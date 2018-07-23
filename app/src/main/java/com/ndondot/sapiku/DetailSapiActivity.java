package com.ndondot.sapiku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DetailSapiActivity extends AppCompatActivity {
    TextView mNamaText,mUmurText,mHargaText;
    Button mBeliBtn;
    private String mNama,mUmur,mHarga;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sapi);

        user = FirebaseAuth.getInstance().getCurrentUser();

        mNamaText = findViewById(R.id.detail_nama);
        mUmurText = findViewById(R.id.detail_umur);
        mHargaText = findViewById(R.id.detail_harga);
        mBeliBtn = findViewById(R.id.detail_buy_button);

        if (user!=null){

        }else{
            mBeliBtn.setEnabled(false);
            mBeliBtn.setBackgroundColor(getResources().getColor(R.color.gray));
        }

        if(getIntent().hasExtra("nama") && getIntent().hasExtra("umur") && getIntent()
                .hasExtra("harga")){

            mNama = getIntent().getStringExtra("nama");
            mUmur = getIntent().getStringExtra("umur");
            mHarga = getIntent().getStringExtra("harga");
        }

        mNamaText.setText(mNama);
        mUmurText.setText("Umur         : "+mUmur+" tahun");
        mHargaText.setText("Harga        : "+mHarga);

        mBeliBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}
