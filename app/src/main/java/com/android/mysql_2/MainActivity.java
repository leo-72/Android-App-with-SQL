package com.android.mysql_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    public static final String URL_showBarang = "http://10.0.2.2/AndroidToSQL/barang.php";
    public static final String URL_updateBrg = "http://10.0.2.2/AndroidToSQL/updateBarang.php";
    public static final String URL_deleteBrg = "http://10.0.2.2/AndroidToSQL/deleteBarang.php";
    public static final String URL_insertBrg = "http://10.0.2.2/AndroidToSQL/insertBarang.php";
    ListView listView;
    SwipeRefreshLayout refreshLayout;
    List<Barang> listBrg = new ArrayList<Barang>();
    BarangAdapter ba;
    EditText edKode, edNama, edBeli, edJual, edStok;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refreshLayout = findViewById(R.id.swipeLayout);
        listView = findViewById(R.id.listBarang);
        fab = findViewById(R.id.btnFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddBarang.class);
                startActivity(intent);
            }
        });

        ba = new BarangAdapter(MainActivity.this, listBrg);
        listView.setAdapter(ba);

        refreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) this);

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                listBrg.clear();
                ba.notifyDataSetChanged();
                callVolley();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                final String code = listBrg.get(position).getKdBrg();
                updateData(code);
                return false;
            }
        });

    }

    @Override
    public void onRefresh(){
        listBrg.clear();
        ba.notifyDataSetChanged();
        callVolley();
    }

    private void callVolley(){
        listBrg.clear();
        ba.notifyDataSetChanged();
        refreshLayout.setRefreshing(true);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_showBarang, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        Barang brg = new Barang();

                        brg.setKdBrg(jsonObject.getString("kd_brg"));
                        brg.setNmBrg(jsonObject.getString("nm_brg"));
                        brg.setHrgBeli(Integer.valueOf(jsonObject.getString("hrg_beli")));
                        brg.setHrgJual(Integer.valueOf(jsonObject.getString("hrg_jual")));
                        brg.setStok(Integer.valueOf(jsonObject.getString("stok")));

                        listBrg.add(brg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                ba.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), (CharSequence) error, Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Menu");
        menu.add(0,0,0,"Edit");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        try {
            switch (item.getItemId()){
                case 0 : {
                    Intent intent = new Intent(getApplicationContext(), EditBarang.class);
                    startActivity(intent);
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public void setData(String kode, String nama, String beli, String jual, String stok){
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_edit_barang, null);

        edKode = view.findViewById(R.id.edKdBrg);
        edNama = view.findViewById(R.id.edNmBrg);
        edBeli = view.findViewById(R.id.edHrgBeli);
        edJual = view.findViewById(R.id.edHrgJual);
        edStok = view.findViewById(R.id.edStok );

        if (kode.isEmpty()) {
            edNama.setText(null);
            edBeli.setText(null);
            edJual.setText(null);
            edStok.setText(null);
        }else {
            edKode.setText(kode);
            edNama.setText(nama);
            edBeli.setText(beli);
            edJual.setText(jual);
            edStok.setText(stok);
        }
    }

    public void updateData(String kode){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_updateBrg, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String kd = jsonObject.getString("kd_brg");
                    String nm = jsonObject.getString("nm_brg");
                    String bl = jsonObject.getString("hrg_beli");
                    String jl = jsonObject.getString("hrg_jual");
                    String st = jsonObject.getString("stok");

//                    setData(kd, nm, bl, jl, st);

                    ba.notifyDataSetChanged();

                    Intent intent = new Intent(MainActivity.this, EditBarang.class);
                    intent.putExtra("kd_i", kd);
                    intent.putExtra("nm_i", nm);
                    intent.putExtra("hrg_ib", bl);
                    intent.putExtra("hrg_ij", jl);
                    intent.putExtra("stok_i", st);
                    startActivity(intent);
                    finish();

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), (CharSequence) error, Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("kd_brg", kode);

                return params;

            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }

}