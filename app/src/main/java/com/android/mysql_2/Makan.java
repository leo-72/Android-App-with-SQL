package com.android.mysql_2;

public class Makan {
    String kdMkn;
    String nmMkn;
    String jnsMkn;
    Integer hrgMkn;
    Integer stok;

    public Makan() {
    }

    public Makan(String kdMkn, String nmMkn, String jnsMkn, Integer hrgMkn, Integer stok) {
        this.kdMkn = kdMkn;
        this.nmMkn = nmMkn;
        this.jnsMkn = jnsMkn;
        this.hrgMkn = hrgMkn;
        this.stok = stok;
    }


    public String getKdMkn() {
        return kdMkn;
    }
    public void setKdMkn(String kdMkn) {
        this.kdMkn = kdMkn;
    }

    public String getNmMkn() {
        return nmMkn;
    }
    public void setNmMkn(String nmMkn) {
        this.nmMkn = nmMkn;
    }

    public String getJnsMkn() {
        return jnsMkn;
    }
    public void setJnsMkn(String jnsMkn) {
        this.jnsMkn = jnsMkn;
    }

    public Integer getHrgMkn() {
        return hrgMkn;
    }
    public void setHrgMkn(Integer hrgMkn) {
        this.hrgMkn = hrgMkn;
    }

    public Integer getStok() {
        return stok;
    }
    public void setStok(Integer stok) {
        this.stok = stok;
    }

}
