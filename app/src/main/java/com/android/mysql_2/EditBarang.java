package com.android.mysql_2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditBarang extends AppCompatActivity {
    EditText kdBrg, nmBrg, hrgJual, hrgBeli, stokBrg;
    Button btnUpdate, btnDelete;
    List<Barang> listBrg = new ArrayList<Barang>();
    BarangAdapter ba;
    String sKode, sNama, sBeli, sJual, sStok;
    String vKode, vNama, vBeli, vJual, vStok;

    public static final String URL_deleteBrg = "http://10.0.2.2/AndroidToSQL/deleteBarang.php";
    public static final String URL_insertBrg = "http://10.0.2.2/AndroidToSQL/insertBarang.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_barang);

        kdBrg = findViewById(R.id.edKdBrg);
        nmBrg = findViewById(R.id.edNmBrg);
        hrgJual = findViewById(R.id.edHrgJual);
        hrgBeli = findViewById(R.id.edHrgBeli);
        stokBrg = findViewById(R.id.edStok);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        vKode = getIntent().getStringExtra("kd_i");
        vNama = getIntent().getStringExtra("nm_i");
        vBeli = getIntent().getStringExtra("hrg_ib");
        vJual = getIntent().getStringExtra("hrg_ij");
        vStok = getIntent().getStringExtra("stok_i");

        kdBrg.setText(vKode);
        nmBrg.setText(vNama);
        hrgBeli.setText(vBeli);
        hrgJual.setText(vJual);
        stokBrg.setText(vStok);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getData();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sKode = kdBrg.getText().toString();
                deleteData(sKode);
            }
        });
    }

    public void getData(){
        sKode = kdBrg.getText().toString();
        sNama = nmBrg.getText().toString();
        sBeli = hrgBeli.getText().toString();
        sJual = hrgJual.getText().toString();
        sStok = stokBrg.getText().toString();

        saveData();
    }

    public void saveData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_insertBrg, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), (CharSequence) error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();

                if (sKode.isEmpty()) {
                    params.put("nm_brg", sNama);
                    params.put("hrg_beli", sBeli);
                    params.put("hrg_jual", sJual);
                    params.put("stok", sStok);
                    return params;
                }else{
                    params.put("kd_brg", sKode);
                    params.put("nm_brg", sNama);
                    params.put("hrg_beli", sBeli);
                    params.put("hrg_jual", sJual);
                    params.put("stok", sStok);
                    return params;
                }
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }

    public void deleteData(String kd_brg){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_deleteBrg, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Gagal Menghapus Barang", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();

                params.put("kd_brg", kd_brg);
                return params;

            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }
}