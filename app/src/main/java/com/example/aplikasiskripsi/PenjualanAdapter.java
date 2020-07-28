package com.example.aplikasiskripsi;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class PenjualanAdapter extends RecyclerView.Adapter<PenjualanAdapter.MyViewHolder>{

    private ArrayList<PenjualanDB> riwayatpenj;
    private ArrayList<BarangDB> daftarbarang;
    Context context;

    public PenjualanAdapter(ArrayList<PenjualanDB> penj, Context context){
        this.context = context;
        this.riwayatpenj = penj;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tgl,key,kd_brg,nama_brg,hrg_brg,jml_beli, total;
        CardView cvlappenj;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tgl = itemView.findViewById(R.id.tgl);
            key = itemView.findViewById(R.id.key);
            kd_brg = itemView.findViewById(R.id.kd_brg);
            nama_brg = itemView.findViewById(R.id.nama_brg);
            hrg_brg = itemView.findViewById(R.id.hrg_brg);
            jml_beli = itemView.findViewById(R.id.jml_beli);
            total = itemView.findViewById(R.id.total);
            cvlappenj = itemView.findViewById(R.id.cvlappenj);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_itemlappenj,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tgl.setText("Tanggal: "+riwayatpenj.get(position).getTgl());
        holder.key.setText("Kode Penjualan: " + riwayatpenj.get(position).getKey());
        holder.kd_brg.setText("Kode Barang: " + riwayatpenj.get(position).getKdbrg());
        holder.nama_brg.setText("Nama Barang: " + riwayatpenj.get(position).getNamabrg());
        holder.hrg_brg.setText("Harga Barang: "+ riwayatpenj.get(position).getHrgbrg());
        holder.jml_beli.setText("Jumlah beli: "+ riwayatpenj.get(position).getJmlbeli());
        holder.total.setText("Total: " + riwayatpenj.get(position).getTotal());
    }

    @Override
    public int getItemCount() {
        return riwayatpenj.size();
    }


}
