package com.dwiki.drs.Model;

import java.util.List;

public class ModelResponse {
    private String kode, pesan;
    private List<ModelRumahSakit> data;

    public String getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public List<ModelRumahSakit> getData() {
        return data;
    }
}
