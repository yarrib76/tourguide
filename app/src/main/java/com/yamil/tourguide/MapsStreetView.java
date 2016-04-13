package com.yamil.tourguide;

import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by yamil on 4/1/16.
 */
public class MapsStreetView extends FragmentActivity
        implements OnStreetViewPanoramaReadyCallback {

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
        panorama.setPosition(new LatLng(-33.87365, 151.20689));
    }
}
