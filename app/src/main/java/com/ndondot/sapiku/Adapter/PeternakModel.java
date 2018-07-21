package com.ndondot.sapiku.Adapter;

public class PeternakModel {
    private String mId, mGambar, mNama, mUmur, mHarga;

    public PeternakModel(String mId, String mNama, String mUmur, String mHarga) {
        this.mId = mId;
        this.mNama = mNama;
        this.mUmur = mUmur;
        this.mHarga = mHarga;
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
}
