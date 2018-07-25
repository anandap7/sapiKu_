package com.ndondot.sapiku.Adapter;

public class PeternakModel {
    private String mId, mGambar, mNama, mUmur, mHarga,mIdSapi;

    public PeternakModel(String mId, String mGambar, String mNama, String mUmur, String mHarga, String mIdSapi) {
        this.mId = mId;
        this.mGambar = mGambar;
        this.mNama = mNama;
        this.mUmur = mUmur;
        this.mHarga = mHarga;
        this.mIdSapi = mIdSapi;
    }

    public String getmId() {
        return mId;
    }

    public String getmGambar() {
        return mGambar;
    }

    public String getmNama() {
        return mNama;
    }

    public String getmUmur() {
        return mUmur;
    }

    public String getmHarga() {
        return mHarga;
    }

    public String getmIdSapi() {
        return mIdSapi;
    }
}
