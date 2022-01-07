package com.android.mysql_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    public static final String URL_showMakan = "http://10.0.2.2/AndroidToSQL/makanan/makanan.php";
    public static final String URL_updateMkn = "http://10.0.2.2/AndroidToSQL/makanan/updateMakan.php";
    public static final String URL_deleteMkn = "http://10.0.2.2/AndroidToSQL/makanan/deleteMakan.php";
    public static final String URL_insertMkn = "http://10.0.2.2/AndroidToSQL/makanan/insertMakan.php";
    ListView listView;
    SwipeRefreshLayout refreshLayout;
    List<Makan> listMkn = new ArrayList<Makan>();
    MakanAdapter ba;
    EditText edKode, edNama, edJenis, edHarga, edStok;
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
                Intent intent = new Intent(getApplicationContext(), AddMakan.class);
                startActivity(intent);
            }
        });

        ba = new MakanAdapter(MainActivity.this, listMkn);
        listView.setAdapter(ba);

        refreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) this);

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                listMkn.clear();
                ba.notifyDataSetChanged();
                callVolley();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                final String code = listMkn.get(position).getKdMkn();
                updateData(code);
                return false;
            }
        });

    }

    @Override
    public void onRefresh(){
        listMkn.clear();
        ba.notifyDataSetChanged();
        callVolley();
    }

    private void callVolley(){
        listMkn.clear();
        ba.notifyDataSetChanged();
        refreshLayout.setRefreshing(true);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_showMakan, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        Makan mkn = new Makan();

                        mkn.setKdMkn(jsonObject.getString("kd_mkn"));
                        mkn.setNmMkn(jsonObject.getString("nm_mkn"));
                        mkn.setJnsMkn(jsonObject.getString("jns_mkn"));
                        mkn.setHrgMkn(Integer.valueOf(jsonObject.getString("hrg_mkn")));
                        mkn.setStok(Integer.valueOf(jsonObject.getString("stok")));

                        listMkn.add(mkn);
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
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
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
                    Intent intent = new Intent(getApplicationContext(), EditMakan.class);
                    startActivity(intent);
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public void setData(String kode, String nama, String jenis, String harga, String stok){
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_edit_makan, null);

        edKode = view.findViewById(R.id.edKdMkn);
        edNama = view.findViewById(R.id.edNmMkn);
        edJenis = view.findViewById(R.id.edJnsMkn);
        edHarga = view.findViewById(R.id.edHrgMkn);
        edStok = view.findViewById(R.id.edStok );

        if (kode.isEmpty()) {
            edNama.setText(null);
            edJenis.setText(null);
            edHarga.setText(null);
            edStok.setText(null);
        }else {
            edKode.setText(kode);
            edNama.setText(nama);
            edJenis.setText(jenis);
            edHarga.setText(harga);
            edStok.setText(stok);
        }
    }

    public void updateData(String kode){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_updateMkn, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String kd = jsonObject.getString("kd_mkn");
                    String nm = jsonObject.getString("nm_mkn");
                    String jn = jsonObject.getString("jns_mkn");
                    String hr = jsonObject.getString("hrg_mkn");
                    String st = jsonObject.getString("stok");

//                    setData(kd, nm, bl, jl, st);

                    ba.notifyDataSetChanged();

                    Intent intent = new Intent(MainActivity.this, EditMakan.class);
                    intent.putExtra("kd_i", kd);
                    intent.putExtra("nm_i", nm);
                    intent.putExtra("jns_i", jn);
                    intent.putExtra("hrg_i", hr);
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

                params.put("kd_mkn", kode);

                return params;

            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }

}