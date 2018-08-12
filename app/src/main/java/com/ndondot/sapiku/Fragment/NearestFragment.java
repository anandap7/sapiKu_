package com.ndondot.sapiku.Fragment;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ndondot.sapiku.Adapter.GPSTracker;
import com.ndondot.sapiku.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NearestFragment extends Fragment implements OnMapReadyCallback {

    MapView mapView;
    GoogleMap gMap;
    LocationManager mLocationManager;
    android.location.LocationListener mLocationListener;

    GPSTracker mGpsTracker;
    TextView mLatitudeText, mLongitudeText;

    private double mLatitude,mLongitude;
    DatabaseReference mRootRef;

    public NearestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mGpsTracker = new GPSTracker(getContext());

        mapView = (MapView) view.findViewById(R.id.map);
        mLatitudeText = view.findViewById(R.id.latitude);
        mLongitudeText = view.findViewById(R.id.longitude);
        mapView.onCreate(savedInstanceState);
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mapView.getMapAsync(this);

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
        return inflater.inflate(R.layout.fragment_nearest, container, false);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Location mylocation = mLocationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        mLatitude =  mylocation.getLatitude();
        mLongitude = mylocation.getLongitude();

        mLocationListener = new android.location.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mLatitude =  location.getLatitude();
                mLongitude = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        mLocationManager.requestLocationUpdates("gps", 5000, 0, mLocationListener);

        Location loc2 = new Location("");
        loc2.setLatitude(mLatitude);
        loc2.setLongitude(mLongitude);

        gMap.setMyLocationEnabled(true);
        gMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLatitude, mLongitude),15));
                addMarkers();
                return true;
            }
        });
        CameraUpdate cameraUpdate = null;
        gMap.getUiSettings().setZoomControlsEnabled(true);
    }

    private void addMarkers(){
        final DatabaseReference ref = mRootRef.child("peternak");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot user: dataSnapshot.getChildren()) {
                    final String sPeternakId = user.getKey();
                    final String sNamaPeternak = user.child("nama").getValue(String.class);
                    DatabaseReference refLoc = ref.child(sPeternakId).child("latlng");
                    refLoc.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String latitude = dataSnapshot.child("latitude").getValue(String.class);
                            String longitude = dataSnapshot.child("longitude").getValue(String.class);

                            double dLatitude = Double.valueOf(latitude);
                            double dLongitude = Double.valueOf(longitude);

                            gMap.addMarker(new MarkerOptions().position(new LatLng(dLatitude,dLongitude)).title(sNamaPeternak));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
