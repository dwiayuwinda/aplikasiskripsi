package com.example.aplikasiskripsi;

import com.google.firebase.database.IgnoreExtraProperties;
import java.io.Serializable;

@IgnoreExtraProperties
public class BarangDB implements Serializable {
    private String kode;
    private String nama;
    private String harga;
    private String stok;
    private String key;

    public BarangDB(){ }

    public BarangDB (String kode, String nama, String harga, String stok){
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }

    public String getKey() { return key;}

    public void setKey(String key) { this.key = key; }

    public String getKode() { return kode;}

    public void setKode(String kode) { this.kode = kode; }

    public String getNama() { return nama; }

    public void setNama(String nama) { this.nama = nama; }

    public String getHarga() { return harga; }

    public void setHarga(String harga) { this.harga = harga; }

    public String getStok() { return stok; }

    public void setStok(String stok) { this.stok = stok; }

    @Override
    public String toString() {
        return " "+kode+"\n" + " "+nama+"\n" + " "+harga+ "\n" + " "+stok;
    }
}
