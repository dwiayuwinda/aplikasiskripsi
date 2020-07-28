package com.example.aplikasiskripsi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    ArrayList<BarangDB> daftarbarang;
    ItemClickListener listener;

    public ItemAdapter(ArrayList<BarangDB> b, Context c, ItemClickListener listener){
        this.daftarbarang = b;
        this.listener = listener;
    }

    public interface ItemClickListener {
        void onDataClick(BarangDB barang);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView kd_brg,nama_brg,hrg_jual,stok_brg;
        CardView cvbrg;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            kd_brg = itemView.findViewById(R.id.kd_brg);
            nama_brg = itemView.findViewById(R.id.nama_brg);
            hrg_jual = itemView.findViewById(R.id.hrg_jual);
            stok_brg = itemView.findViewById(R.id.stok_brg);
            cvbrg = itemView.findViewById(R.id.cvbrg);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final BarangDB barang = daftarbarang.get(position);
        holder.kd_brg.setText("Kode Barang: " + daftarbarang.get(position).getKode());
        holder.nama_brg.setText("Nama Barang: " + daftarbarang.get(position).getNama());
        holder.hrg_jual.setText("Harga Jual: "+ daftarbarang.get(position).getHarga());
        holder.stok_brg.setText("Stok: " + daftarbarang.get(position).getStok());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDataClick(barang);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String string = daftarbarang.get(position).getNama();
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
    public int getItemCount() {
        return daftarbarang.size();
    }

    public void filterList(ArrayList<BarangDB> filteredList){
        daftarbarang = filteredList;
        notifyDataSetChanged();
    }
}
