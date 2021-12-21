package com.android.mysql_2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class BarangAdapter extends BaseAdapter {

    Activity activity;
    List<Barang> barang;
    private LayoutInflater inflater;

    public BarangAdapter(Activity activity, List<Barang> barang) {
        this.activity = activity;
        this.barang = barang;
    }

    @Override
    public int getCount() {
        return barang.size();
    }

    @Override
    public Object getItem(int position) {
        return barang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (view == null) view = inflater.inflate(R.layout.activity_custom_adapter, null);

        TextView kd_brg = view.findViewById(R.id.kdBrg);
        TextView nm_brg = view.findViewById(R.id.nmBrg);
        TextView hrg_beli = view.findViewById(R.id.hrgBeli);
        TextView hrg_jual = view.findViewById(R.id.hrgJual);
        TextView stok = view.findViewById(R.id.stok);

        Barang brg = barang.get(position);

        kd_brg.setText(brg.getKdBrg());
        nm_brg.setText(brg.getNmBrg());
        hrg_beli.setText(String.valueOf(brg.getHrgBeli()));
        hrg_jual.setText(String.valueOf(brg.getHrgJual()));
        stok.setText(String.valueOf(brg.getStok()));

        return view;
    }
}
