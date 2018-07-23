package com.ndondot.sapiku.Adapter;

public class KolaborasiModel {
    private String mId,mPhoto,mName,mPrice,mTotalMember;

    public KolaborasiModel(String mId, String mPhoto, String mName, String mPrice, String mTotalMember) {
        this.mId = mId;
        this.mPhoto = mPhoto;
        this.mName = mName;
        this.mPrice = mPrice;
        this.mTotalMember = mTotalMember;
    }

    public String getmId() {
        return mId;
    }

    public String getmPhoto() {
        return mPhoto;
    }

    public String getmName() {
        return mName;
    }

    public String getmPrice() {
        return mPrice;
    }

    public String getmTotalMember() {
        return mTotalMember;
    }
}
