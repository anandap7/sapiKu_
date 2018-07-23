package com.ndondot.sapiku.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ndondot.sapiku.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class KolaborasiAdapter extends RecyclerView.Adapter<KolaborasiAdapter.ViewHolder> {
    private Context context;
    private List<KolaborasiModel> listKolaborasi;

    public KolaborasiAdapter(Context context, List<KolaborasiModel> listKolaborasi) {
        this.context = context;
        this.listKolaborasi = listKolaborasi;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kolaborasi_model, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final KolaborasiModel kolaborasi = listKolaborasi.get(position);

        holder.name.setText(kolaborasi.getmName());
        holder.harga.setText("Harga sapi Rp. "+kolaborasi.getmPrice());
        holder.totalMember.setText("Anggota kurban "+kolaborasi.getmTotalMember()+" orang");

    }

    @Override
    public int getItemCount() {
        return listKolaborasi.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView photoColab;
        TextView name,harga,totalMember;

        public ViewHolder(View itemView) {
            super(itemView);

            photoColab = itemView.findViewById(R.id.photo_colab);
            name = itemView.findViewById(R.id.name_colab);
            harga = itemView.findViewById(R.id.harga_colab);
            totalMember = itemView.findViewById(R.id.jumlah_anggota_colab);
        }
    }
}
