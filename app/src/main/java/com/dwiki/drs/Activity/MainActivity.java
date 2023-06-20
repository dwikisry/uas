package com.dwiki.drs.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dwiki.drs.API.APIRequestData;
import com.dwiki.drs.API.RetroServer;
import com.dwiki.drs.Adapter.AdapterRumahSakit;
import com.dwiki.drs.Model.ModelResponse;
import com.dwiki.drs.Model.ModelRumahSakit;
import com.dwiki.drs.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvRumahSakit;
    private FloatingActionButton fabAdd;
    private ProgressBar pbRumahSakit;
    private RecyclerView.Adapter adRumahSakit;
    private RecyclerView.LayoutManager lmRumahSakit;
    private List<ModelRumahSakit> listRumahSakit = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvRumahSakit = findViewById(R.id.rv_rumahsakit);
        pbRumahSakit = findViewById(R.id.pb_rumahsakit);
        fabAdd = findViewById(R.id.fab_add);

        lmRumahSakit = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvRumahSakit.setLayoutManager(lmRumahSakit);
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveRumahSakit();
    }
    public void retrieveRumahSakit(){
        pbRumahSakit.setVisibility(View.VISIBLE);

        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.ardRetrieve();
        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();
                listRumahSakit = response.body().getData();
                adRumahSakit = new AdapterRumahSakit(MainActivity.this, listRumahSakit);
                rvRumahSakit.setAdapter(adRumahSakit);
                adRumahSakit.notifyDataSetChanged();
                pbRumahSakit.setVisibility(View.GONE);

                fabAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, TambahActivity.class));
                    }
                });
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal terhubung server", Toast.LENGTH_SHORT).show();
                pbRumahSakit.setVisibility(View.GONE);
            }
        });
    }
}