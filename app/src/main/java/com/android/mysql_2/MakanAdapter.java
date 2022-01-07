package com.android.mysql_2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MakanAdapter extends BaseAdapter {

    Activity activity;
    List<Makan> makan;
    private LayoutInflater inflater;

    public MakanAdapter(Activity activity, List<Makan> makan) {
        this.activity = activity;
        this.makan = makan;
    }

    @Override
    public int getCount() {
        return makan.size();
    }

    @Override
    public Object getItem(int position) {
        return makan.get(position);
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

        TextView kd_mkn = view.findViewById(R.id.kdMkn);
        TextView nm_mkn = view.findViewById(R.id.nmMkn);
        TextView jns_mkn = view.findViewById(R.id.jnsMkn);
        TextView hrg_mkn = view.findViewById(R.id.hrgMkn);
        TextView stok = view.findViewById(R.id.stok);

        Makan mkn = makan.get(position);

        kd_mkn.setText(mkn.getKdMkn());
        nm_mkn.setText(mkn.getNmMkn());
        jns_mkn.setText(mkn.getJnsMkn());
        hrg_mkn.setText(String.valueOf(mkn.getHrgMkn()));
        stok.setText(String.valueOf(mkn.getStok()));

        return view;
    }
}
