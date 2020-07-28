package com.example.aplikasiskripsi;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SupplierAdapter extends RecyclerView.Adapter<SupplierAdapter.MyViewHolder> {

    ArrayList<SupplierDB> daftarsupplier;
    ItemClickListener listener;

    public SupplierAdapter(ArrayList<SupplierDB> s, Context context, ItemClickListener listener){

        this.daftarsupplier = s;
        this.listener = listener;
    }

    public interface ItemClickListener {
        void onDataClick(SupplierDB supplier);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView kdsupp,namasupp,alamat,telepon, ket;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            kdsupp = itemView.findViewById(R.id.kdsupp);
            namasupp = itemView.findViewById(R.id.namasupp);
            alamat = itemView.findViewById(R.id.alamat);
            telepon = itemView.findViewById(R.id.telepon);
            ket = itemView.findViewById(R.id.ket);
        }
    }

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SupplierAdapter.MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_itemsupp,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final SupplierDB supplier = daftarsupplier.get(position);
        holder.kdsupp.setText("Kode Pemasok: " + daftarsupplier.get(position).getKode());
        holder.namasupp.setText("Nama Pemasok: " + daftarsupplier.get(position).getNama());
        holder.alamat.setText("Alamat: "+ daftarsupplier.get(position).getAlamat());
        holder.telepon.setText("Telepon: " + daftarsupplier.get(position).getTelepon());
        holder.ket.setText("Keterangan: " + daftarsupplier.get(position).getKet());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDataClick(supplier);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String string = daftarsupplier.get(position).getNama();
                ClipboardManager manager = (ClipboardManager) v.getContext()
                        .getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text", string);
                manager.setPrimaryClip(clipData);
                Toast.makeText(v.getContext(), "Teks berhasil di copy", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() { return daftarsupplier.size(); }

    public void filterList(ArrayList<SupplierDB> filteredList){
        daftarsupplier = filteredList;
        notifyDataSetChanged();
    }

}
