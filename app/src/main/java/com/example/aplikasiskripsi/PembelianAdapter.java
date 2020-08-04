package com.example.aplikasiskripsi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PembelianAdapter extends RecyclerView.Adapter<PembelianAdapter.MyViewHolder> {

    private ArrayList<PembelianDB> riwayatpemb;
    Context context;

    public PembelianAdapter(ArrayList<PembelianDB> pemb, Context context){
        this.context = context;
        this.riwayatpemb = pemb;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tgl,key,kdsupp, namasupp, kd_brg,nama_brg, kuantitas, hrg_beli, totalpem;
        CardView cvlappemb;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tgl = itemView.findViewById(R.id.tgl);
            key = itemView.findViewById(R.id.key);
            kdsupp = itemView.findViewById(R.id.kdsupp);
            namasupp = itemView.findViewById(R.id.namasupp);
            kd_brg = itemView.findViewById(R.id.kd_brg);
            nama_brg = itemView.findViewById(R.id.nama_brg);
            kuantitas = itemView.findViewById(R.id.kuantitas);
            hrg_beli = itemView.findViewById(R.id.hrg_beli);
            totalpem = itemView.findViewById(R.id.totalpem);
            cvlappemb = itemView.findViewById(R.id.cvlappemb);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PembelianAdapter.MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_itemlappem,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PembelianAdapter.MyViewHolder holder, int position) {
        final PembelianDB pembelian = riwayatpemb.get(position);
        holder.tgl.setText("Tanggal: "+riwayatpemb.get(position).getTgl());
        holder.key.setText("Kode Pembelian: " + riwayatpemb.get(position).getKey());
        holder.kdsupp.setText("Kode Pemasok: " + riwayatpemb.get(position).getKdsupp());
        holder.namasupp.setText("Nama Pemasok: " + riwayatpemb.get(position).getNamasupp());
        holder.kd_brg.setText("Kode Barang: " + riwayatpemb.get(position).getKdbrg());
        holder.nama_brg.setText("Nama Barang: " + riwayatpemb.get(position).getNamabrg());
        holder.kuantitas.setText("Kuantitas: "+ riwayatpemb.get(position).getKuantitas());
        holder.hrg_beli.setText("Harga Beli: "+ riwayatpemb.get(position).getHrgbeli());
        holder.totalpem.setText("Total: " + riwayatpemb.get(position).getTotal());
    }

    @Override
    public int getItemCount() { return riwayatpemb.size();}

}
