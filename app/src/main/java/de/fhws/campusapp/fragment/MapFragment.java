package de.fhws.campusapp.fragment;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import de.fhws.campusapp.MainActivity;

public class MapFragment extends SupportMapFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );

       setUpMap();

    }

    private synchronized void setUpMap() {
        checkForGpsEnabled();

        getMapAsync( new OnMapReadyCallback() {
            @Override
            public void onMapReady( GoogleMap googleMap ) {
                try {
                    googleMap.setMyLocationEnabled( true );
                } catch ( SecurityException ex ) {
                    //die silently
                }
                if( MainActivity.location != null ) {
                    LatLng latlng = new LatLng( MainActivity.location.getLatitude(), MainActivity.location.getLongitude() );
                    googleMap.moveCamera( CameraUpdateFactory.newLatLng( latlng ) );
                    googleMap.animateCamera( CameraUpdateFactory.newLatLngZoom( latlng, (float) 14.6 ) );
                }

            }
        } );
    }

    @TargetApi( 19 )
    private void checkForGpsEnabled() {
        ContentResolver contentResolver = getContext().getContentResolver();
        int mode = Settings.Secure.getInt(
                contentResolver, Settings.Secure.LOCATION_MODE, Settings.Secure.LOCATION_MODE_OFF);

        if (mode == Settings.Secure.LOCATION_MODE_OFF) {
            MainActivity.startDialogFragment( getFragmentManager(), new GpsSettingsDialog() );
        }
    }



}