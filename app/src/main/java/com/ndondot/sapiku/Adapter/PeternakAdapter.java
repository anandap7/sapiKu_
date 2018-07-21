package com.ndondot.sapiku.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ndondot.sapiku.R;

import java.util.List;

public class PeternakAdapter extends RecyclerView.Adapter<PeternakAdapter.ViewHolder> {

    private Context mContext;
    private List<PeternakModel> listPeternak;

    public PeternakAdapter(Context mContext, List<PeternakModel> listPeternak) {
        this.mContext = mContext;
        this.listPeternak = listPeternak;
    }

    @Override
    public PeternakAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.peternak_model, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PeternakAdapter.ViewHolder holder, int position) {
        final PeternakModel posting = listPeternak.get(position);

        holder.id.setText(posting.getmId());
        holder.nama.setText(posting.getmNama());
        holder.umur.setText(posting.getmUmur());
        holder.harga.setText(posting.getmHarga());
        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext.getApplicationContext(), posting.getmId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPeternak.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id, nama,umur,harga;
        ImageView image;
        RatingBar rating;
        Button detail;

        public ViewHolder(View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.id_sapi);
            image = itemView.findViewById(R.id.image);
            nama = itemView.findViewById(R.id.nama_peternak);
            umur= itemView.findViewById(R.id.umur_sapi);
            rating = itemView.findViewById(R.id.rating_sapi);
            harga = itemView.findViewById(R.id.harga_sapi);
            detail = itemView.findViewById(R.id.detail_button);
        }
    }
}
