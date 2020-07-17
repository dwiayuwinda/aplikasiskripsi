package com.example.aplikasiskripsi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    Context context;
    private ArrayList<BarangDB> daftarbarang;

    public ItemAdapter(Context c, ArrayList<BarangDB> b){
        context = c;
        daftarbarang = b;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.kd_brg.setText("Kode Barang: " + daftarbarang.get(position).getKode());
        holder.nama_brg.setText("Nama Barang: " + daftarbarang.get(position).getNama());
        holder.hrg_jual.setText("Harga Jual: "+ daftarbarang.get(position).getHarga());
        holder.stok_brg.setText("Stok: " + daftarbarang.get(position).getStok());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Recycle Click" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        try {
            return daftarbarang.size();
        } catch (Exception ex){return 0;}
    }

    public interface FirebaseDataListener{
        void OnDataClick(BarangDB barang, int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView kd_brg,nama_brg,hrg_jual,stok_brg;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            kd_brg = itemView.findViewById(R.id.kd_brg);
            nama_brg = itemView.findViewById(R.id.nama_brg);
            hrg_jual = itemView.findViewById(R.id.hrg_jual);
            stok_brg = itemView.findViewById(R.id.stok_brg);
        }
    }

}
