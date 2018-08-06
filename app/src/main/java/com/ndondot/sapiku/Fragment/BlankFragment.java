package com.ndondot.sapiku.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ndondot.sapiku.Adapter.GPSTracker;
import com.ndondot.sapiku.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {
    GPSTracker mGpsTracker;
    TextView mLatitudeText,mLongitudeText;


    public BlankFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mGpsTracker = new GPSTracker(getContext());

        mLatitudeText = view.findViewById(R.id.langitude);
        mLongitudeText = view.findViewById(R.id.longitude);

        if (mGpsTracker.getIsGPSTrackingEnabled()){
            String latitude = String.valueOf(mGpsTracker.getLatitude());
            mLatitudeText.setText(latitude);

            String longitude = String.valueOf(mGpsTracker.getLongitude());
            mLongitudeText.setText(longitude);
        }else{
            mGpsTracker.showSettingsAlert();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

}
