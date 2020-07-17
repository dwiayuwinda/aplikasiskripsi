package com.example.aplikasiskripsi;

import com.google.firebase.database.IgnoreExtraProperties;
import java.io.Serializable;

@IgnoreExtraProperties
public class SupplierDB implements Serializable {
    private String kode;
    private String nama;
    private String alamat;
    private String telepon;
    private String key;

    public SupplierDB(){ }

    public SupplierDB (String kode, String nama, String alamat, String telepon){
        this.kode = kode;
        this.nama = nama;
        this.alamat = alamat;
        this.telepon = telepon;
    }

    public String getKey() { return key;}

    public void setKey(String key) { this.key = key; }

    public String getKode() { return kode;}

    public void setKode(String kode) { this.kode = kode; }

    public String getNama() { return nama; }

    public void setNama(String nama) { this.nama = nama; }

    public String getHarga() { return alamat; }

    public void setHarga(String alamat) { this.alamat = alamat; }

    public String getStok() { return telepon; }

    public void setStok(String telepon) { this.telepon = telepon; }

    @Override
    public String toString() {
        return " "+kode+"\n" +
                " "+nama +"\n" +
                " "+alamat + "\n" +
                " "+telepon;
    }
}
