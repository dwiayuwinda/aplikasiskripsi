package com.example.aplikasiskripsi;

import java.io.Serializable;

public class PenjualanDB implements Serializable {
    private String tgl;
    private String kdbrg;
    private String namabrg;
    private String hrgbrg;
    private String jmlbeli;
    private String total;
    private String sisa;
    private String key;

    public PenjualanDB(){}

    public PenjualanDB(String tgl, String kdbrg, String hrgbrg,
                       String total, String namabrg, String jmlbeli, String sisa) {
        this.tgl = tgl;
        this.kdbrg = kdbrg;
        this.namabrg = namabrg;
        this.hrgbrg = hrgbrg;
        this.jmlbeli = jmlbeli;
        this.total = total;
        this.sisa = sisa;
    }

    public String getTgl() {return tgl;}

    public void setTgl(String tgl) {this.tgl = tgl;}

    public String getKdbrg() {return kdbrg;}

    public void setKdbrg(String kdbrg) {this.kdbrg = kdbrg;}

    public String getNamabrg() {return namabrg;}

    public void setNamabrg(String namabrg) {this.namabrg = namabrg;}

    public String getHrgbrg() { return hrgbrg;}

    public void setHrgbrg(String hrgbrg) {this.hrgbrg = hrgbrg;}

    public String getJmlbeli() {return jmlbeli;}

    public void setJmlbeli(String jmlbeli) {this.jmlbeli = jmlbeli;}

    public String getSisa() {return sisa;}

    public void setSisa(String sisa) {this.sisa = sisa;}

    public String getTotal() {return total;}

    public void setTotal(String total) {this.total = total;}

    public String getKey() {return key;}

    public void setKey(String key) {this.key = key;}

    @Override
    public String toString() {
        return " "+tgl+"\n"
                + " "+key+"\n"
                + " "+kdbrg+ "\n"
                + " "+hrgbrg+ "\n"
                + " "+jmlbeli+ "\n"
                + " "+sisa+ "\n"
                + " "+total;
    }
}
