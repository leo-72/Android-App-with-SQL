package com.android.mysql_2;

public class Barang {
    String kdBrg;
    String nmBrg;
    Integer hrgBeli;
    Integer hrgJual;
    Integer stok;

    public Barang() {
    }

    public Barang(String kdBrg, String nmBrg, Integer hrgBeli, Integer hrgJual, Integer stok) {
        this.kdBrg = kdBrg;
        this.nmBrg = nmBrg;
        this.hrgBeli = hrgBeli;
        this.hrgJual = hrgJual;
        this.stok = stok;
    }


    public String getKdBrg() {
        return kdBrg;
    }

    public void setKdBrg(String kdBrg) {
        this.kdBrg = kdBrg;
    }

    public String getNmBrg() {
        return nmBrg;
    }

    public void setNmBrg(String nmBrg) {
        this.nmBrg = nmBrg;
    }

    public Integer getHrgBeli() {
        return hrgBeli;
    }

    public void setHrgBeli(Integer hrgBeli) {
        this.hrgBeli = hrgBeli;
    }

    public Integer getHrgJual() {
        return hrgJual;
    }

    public void setHrgJual(Integer hrgJual) {
        this.hrgJual = hrgJual;
    }

    public Integer getStok() {
        return stok;
    }

    public void setStok(Integer stok) {
        this.stok = stok;
    }

}
