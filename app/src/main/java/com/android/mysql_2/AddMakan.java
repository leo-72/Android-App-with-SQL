package com.android.mysql_2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

import java.util.HashMap;
import java.util.Map;

public class AddMakan extends AppCompatActivity {

    EditText kdMkn, nmMkn, jnsMkn, hrgMkn, stokMkn;
    Button btnTambah;
    ProgressDialog progress;

    public static final String URL_addMkn = "http://10.0.2.2/AndroidToSQL/makanan/addMakan.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_makan);

        kdMkn = findViewById(R.id.edKdMkn);
        nmMkn = findViewById(R.id.edNmMkn);
        jnsMkn = findViewById(R.id.edJnsMkn);
        hrgMkn = findViewById(R.id.edHrgMkn);
        stokMkn = findViewById(R.id.edStok);
        btnTambah = findViewById(R.id.btnTambah);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();
            }
        });
    }

    void addData(){
        String kode = kdMkn.getText().toString();
        String nama = nmMkn.getText().toString();
        String jenis = jnsMkn.getText().toString();
        Integer harga = Integer.valueOf(hrgMkn.getText().toString());
        Integer stok = Integer.valueOf(stokMkn.getText().toString());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_addMkn, new Response.Listener<String>() {
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
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();

                params.put("kd_mkn", kode);
                params.put("nm_mkn", nama);
                params.put("jns_mkn", jenis);
                params.put("hrg_mkn", String.valueOf(harga));
                params.put("stok", String.valueOf(stok));
                return params;

            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }
}