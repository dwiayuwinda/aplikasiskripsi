package com.example.aplikasiskripsi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    private ArrayList<BarangDB> daftarbarang;
    Context context;
    private ProgressDialog progressDialog;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    public ItemAdapter(ArrayList<BarangDB> b, Context context){
        this.context = context;
        this.daftarbarang = b;
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Mohon tunggu");
        progressDialog.setCanceledOnTouchOutside(false);
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

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item,parent,false));
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
                onDataClick(barang, holder);
            }
        });
    }

    private void onDataClick(final BarangDB barang, MyViewHolder holder) {
        String[] options = {"Edit","Hapus"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Pilih Aksi").setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i==0){
                    dialogUpdateBarang(barang);
                }else if (i==1){
                    hapusDataBarang(barang);
                }
            }
        })
        .show();
    }

    private void hapusDataBarang(BarangDB barang) {
        if (myRef != null) {
            myRef.child("Barang").child(barang.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(context, "Data dihapus", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void dialogUpdateBarang(final BarangDB barang) {
        final EditText kode_brg,nama_brg,hrg_jual,stok_brg;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Data");
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.layout_editbarang, null);

        kode_brg = view.findViewById(R.id.kode_brg);
        nama_brg = view.findViewById(R.id.nama_brg);
        hrg_jual = view.findViewById(R.id.hrg_jual);
        stok_brg = view.findViewById(R.id.stok_brg);

        kode_brg.setText(barang.getKode());
        nama_brg.setText(barang.getNama());
        hrg_jual.setText(barang.getHarga());
        stok_brg.setText(barang.getStok());
        builder.setView(view);

        if (barang != null) {
            builder.setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    barang.setKode(kode_brg.getText().toString());
                    barang.setNama(nama_brg.getText().toString());
                    barang.setHarga(hrg_jual.getText().toString());
                    barang.setStok(stok_brg.getText().toString());
                    updateDataBarang(barang);
                }
            });
        }
        builder.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }

    private void updateDataBarang(BarangDB barang) {
        myRef.child("Barang").child(barang.getKode())
                .setValue(barang).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Data berhasil di update", Toast.LENGTH_LONG).show();
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
