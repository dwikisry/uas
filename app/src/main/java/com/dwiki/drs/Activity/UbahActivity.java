package com.dwiki.drs.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dwiki.drs.API.APIRequestData;
import com.dwiki.drs.API.RetroServer;
import com.dwiki.drs.Model.ModelResponse;
import com.dwiki.drs.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahActivity extends AppCompatActivity {
    private String yId, yNama, yFoto, yDeskripsi, yLokasi, yKoordinat;
    private EditText etNama, etFoto, etDeskripsi, etLokasi, etKoordinat;
    private Button btnUbah;
    private String Nama, Foto, Deskripsi, Lokasi, Koordinat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah);

        etNama = findViewById(R.id.et_nama);
        etFoto = findViewById(R.id.et_foto);
        etDeskripsi = findViewById(R.id.et_deskripsi);
        etLokasi = findViewById(R.id.et_lokasi);
        etKoordinat =findViewById(R.id.et_koordinat);
        btnUbah = findViewById(R.id.btn_ubah);

        Intent terima = getIntent();
        yId = terima.getStringExtra("xId");
        yNama = terima.getStringExtra("xNama");
        yKoordinat = terima.getStringExtra("xKoordinat");
        yFoto = terima.getStringExtra("xFoto");
        yDeskripsi = terima.getStringExtra("xDeskripsi");
        yLokasi = terima.getStringExtra("xLokasi");

        etNama.setText(yNama);
        etKoordinat.setText(yKoordinat);
        etFoto.setText(yFoto);
        etDeskripsi.setText(yDeskripsi);
        etLokasi.setText(yLokasi);

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nama = etNama.getText().toString();
                Koordinat = etKoordinat.getText().toString();
                Foto = etFoto.getText().toString();
                Deskripsi = etDeskripsi.getText().toString();
                Lokasi = etLokasi.getText().toString();

                if(Nama.trim().isEmpty()){
                    etNama.setError("Nama harus diisi");
                } else if (Koordinat.trim().isEmpty()) {
                    etKoordinat.setError("Harus Di isi");
                } else if (Foto.trim().isEmpty()) {
                    etFoto.setError("Harus di isi");
                } else if (Lokasi.trim().isEmpty()) {
                    etLokasi.setError("Harus di isi");
                } else if (Deskripsi.trim().isEmpty()) {
                    etDeskripsi.setError("Harus Di Isi");
                }else {
                    ubahRumahSakit();
                }
            }
        });
    }
    private void ubahRumahSakit(){
        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.ardUpdate(yId, Nama, Foto, Deskripsi, Lokasi, Koordinat);

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode, pesan;
                kode = response.body().getKode();
                pesan = response.body().getPesan();

                Toast.makeText(UbahActivity.this, "kode : " + pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(UbahActivity.this, "Gagal ubah Data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}