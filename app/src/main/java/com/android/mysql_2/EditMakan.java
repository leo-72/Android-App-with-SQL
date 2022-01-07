package com.android.mysql_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditMakan extends AppCompatActivity {
    EditText kdMkn, nmMkn, jnsMkn, hrgMkn, stokMkn;
    Button btnUpdate, btnDelete;
    List<Makan> listMkn = new ArrayList<Makan>();
    MakanAdapter ba;
    String sKode, sNama, sJenis, sHarga, sStok;
    String vKode, vNama, vJenis, vHarga, vStok;

    public static final String URL_deleteMkn = "http://10.0.2.2/AndroidToSQL/makanan/deleteMakan.php";
    public static final String URL_insertMkn = "http://10.0.2.2/AndroidToSQL/makanan/insertMakan.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_makan);

        kdMkn = findViewById(R.id.edKdMkn);
        nmMkn = findViewById(R.id.edNmMkn);
        jnsMkn = findViewById(R.id.edJnsMkn);
        hrgMkn = findViewById(R.id.edHrgMkn);
        stokMkn = findViewById(R.id.edStok);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        vKode = getIntent().getStringExtra("kd_i");
        vNama = getIntent().getStringExtra("nm_i");
        vJenis = getIntent().getStringExtra("jns_i");
        vHarga = getIntent().getStringExtra("hrg_i");
        vStok = getIntent().getStringExtra("stok_i");

        kdMkn.setText(vKode);
        nmMkn.setText(vNama);
        jnsMkn.setText(vJenis);
        hrgMkn.setText(vHarga);
        stokMkn.setText(vStok);

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
                sKode = kdMkn.getText().toString();
                deleteData(sKode);
            }
        });
    }

    public void getData(){
        sKode = kdMkn.getText().toString();
        sNama = nmMkn.getText().toString();
        sJenis = jnsMkn.getText().toString();
        sHarga = hrgMkn.getText().toString();
        sStok = stokMkn.getText().toString();

        saveData();
    }

    public void saveData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_insertMkn, new Response.Listener<String>() {
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
                    params.put("nm_mkn", sNama);
                    params.put("jns_mkn", sJenis);
                    params.put("hrg_mkn", sHarga);
                    params.put("stok", sStok);
                    return params;
                }else{
                    params.put("kd_mkn", sKode);
                    params.put("nm_mkn", sNama);
                    params.put("jns_mkn", sJenis);
                    params.put("hrg_mkn", sHarga);
                    params.put("stok", sStok);
                    return params;
                }
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }

    public void deleteData(String kd_mkn){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_deleteMkn, new Response.Listener<String>() {
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
                Toast.makeText(getApplicationContext(), "Gagal Menghapus Makanan", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();

                params.put("kd_mkn", kd_mkn);
                return params;

            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }
}