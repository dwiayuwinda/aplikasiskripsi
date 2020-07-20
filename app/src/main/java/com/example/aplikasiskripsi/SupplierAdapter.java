package com.example.aplikasiskripsi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SupplierAdapter extends RecyclerView.Adapter<SupplierAdapter.MyViewHolder> {

    private ArrayList<SupplierDB> daftarsupplier;
    FirebaseDataListener listener;

    public SupplierAdapter(ArrayList<SupplierDB> s, Context context){
        this.daftarsupplier = s;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView kdsupp,namasupp,alamat,telepon;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            kdsupp = itemView.findViewById(R.id.kdsupp);
            namasupp = itemView.findViewById(R.id.namasupp);
            alamat = itemView.findViewById(R.id.alamat);
            telepon = itemView.findViewById(R.id.telepon);
        }
    }

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SupplierAdapter.MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_itemsupp,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.kdsupp.setText("Kode Pemasok: " + daftarsupplier.get(position).getKode());
        holder.namasupp.setText("Nama Pemasok: " + daftarsupplier.get(position).getNama());
        holder.alamat.setText("Alamat: "+ daftarsupplier.get(position).getAlamat());
        holder.telepon.setText("Telepon: " + daftarsupplier.get(position).getTelepon());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                listener.onDataClick(daftarsupplier.get(position), position);
            }
        });
    }

    public interface FirebaseDataListener{
        void onDataClick(SupplierDB supplier, int position);
    }

    @Override
    public int getItemCount() { return daftarsupplier.size(); }

    public void filterList(ArrayList<SupplierDB> filteredList){
        daftarsupplier = filteredList;
        notifyDataSetChanged();
    }

}
