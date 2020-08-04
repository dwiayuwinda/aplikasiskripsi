package com.example.aplikasiskripsi;

import java.io.Serializable;

public class PembelianDB implements Serializable {
    private String tgl;
    private String kdsupp;
    private String namasupp;
    private String kdbrg;
    private String namabrg;
    private String kuantitas;
    private String hrgbeli;
    private String total;
    private String key;


    public PembelianDB(){}

    public PembelianDB(String tgl, String kdsupp, String namasupp, String kdbrg,
                       String namabrg, String kuantitas, String hrgbeli, String total) {
        this.tgl = tgl;
        this.kdsupp = kdsupp;
        this.namasupp = namasupp;
        this.kdbrg = kdbrg;
        this.namabrg = namabrg;
        this.kuantitas = kuantitas;
        this.hrgbeli = hrgbeli;
        this.total = total;
    }

    public String getTgl() { return tgl;}

    public void setTgl(String tgl) {this.tgl = tgl;}

    public String getKdsupp() {return kdsupp;}

    public void setKdsupp(String kdsupp) {this.kdsupp = kdsupp;}

    public String getNamasupp() {return namasupp;}

    public void setNamasupp(String namasupp) {this.namasupp = namasupp;}

    public String getKdbrg() {return kdbrg;}

    public void setKdbrg(String kdbrg) {this.kdbrg = kdbrg;}

    public String getNamabrg() {return namabrg;}

    public void setNamabrg(String namabrg) {this.namabrg = namabrg;}

    public String getKuantitas() {return kuantitas;}

    public void setKuantitas(String kuantitas) {this.kuantitas = kuantitas;}

    public String getHrgbeli() { return hrgbeli;}

    public void setHrgbeli(String hrgbeli) {this.hrgbeli = hrgbeli;}

    public String getTotal() { return total;}

    public void setTotal(String total) {this.total = total;}

    public String getKey() { return key;}

    public void setKey(String key) {this.key = key;}

    @Override
    public String toString() {
        return " "+tgl+"\n"
                + " "+key+"\n"
                + " "+kdsupp+ "\n"
                + " "+namasupp+ "\n"
                + " "+kdbrg+ "\n"
                + " "+namabrg+ "\n"
                + " "+kuantitas+ "\n"
                + " "+hrgbeli+ "\n"
                + " "+total;
    }
}
