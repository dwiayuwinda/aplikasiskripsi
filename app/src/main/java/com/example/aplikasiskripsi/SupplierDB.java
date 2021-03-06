package com.example.aplikasiskripsi;

import com.google.firebase.database.IgnoreExtraProperties;
import java.io.Serializable;

@IgnoreExtraProperties
public class SupplierDB implements Serializable {
    private String kode;
    private String nama;
    private String alamat;
    private String telepon;
    private String ket;
    private String key;

    public SupplierDB(){ }

    public SupplierDB (String kode, String nama, String alamat, String telepon, String ket){
        this.kode = kode;
        this.nama = nama;
        this.alamat = alamat;
        this.telepon = telepon;
        this.ket = ket;
    }

    public String getKey() { return key;}

    public void setKey(String key) { this.key = key; }

    public String getKode() { return kode;}

    public void setKode(String kode) { this.kode = kode; }

    public String getNama() { return nama; }

    public void setNama(String nama) { this.nama = nama; }

    public String getAlamat() { return alamat; }

    public void setAlamat(String alamat) { this.alamat = alamat; }

    public String getTelepon() { return telepon; }

    public void setTelepon(String telepon) { this.telepon = telepon; }

    public String getKet() { return ket; }

    public void setKet(String ket) { this.ket = ket; }

    @Override
    public String toString() {
        return " "+kode+"\n" +
                " "+nama +"\n" +
                " "+alamat + "\n" +
                " "+telepon + "\n" +
                " "+ket;
    }
}
