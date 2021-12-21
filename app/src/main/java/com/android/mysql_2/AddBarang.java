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
import com.google.gson.Gson;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddBarang extends AppCompatActivity {

    EditText kdBrg, nmBrg, hrgJual, hrgBeli, stokBrg;
    Button btnTambah;
    ProgressDialog progress;

    public static final String URL_addBrg = "http://10.0.2.2/AndroidToSQL/addBarang.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_barang);

        kdBrg = findViewById(R.id.edKdBrg);
        nmBrg = findViewById(R.id.edNmBrg);
        hrgJual = findViewById(R.id.edHrgJual);
        hrgBeli = findViewById(R.id.edHrgBeli);
        stokBrg = findViewById(R.id.edStok);
        btnTambah = findViewById(R.id.btnTambah);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();
            }
        });
    }

    void addData(){
        String kode = kdBrg.getText().toString();
        String nama = nmBrg.getText().toString();
        Integer beli = Integer.valueOf(hrgBeli.getText().toString());
        Integer jual = Integer.valueOf(hrgJual.getText().toString());
        Integer stok = Integer.valueOf(stokBrg.getText().toString());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_addBrg, new Response.Listener<String>() {
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
                Toast.makeText(getApplicationContext(), (CharSequence) error, Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();

                params.put("kd_brg", kode);
                params.put("nm_brg", nama);
                params.put("hrg_beli", String.valueOf(beli));
                params.put("hrg_jual", String.valueOf(jual));
                params.put("stok", String.valueOf(stok));
                return params;

            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }
}