package com.ripzery.autolocate.Listener;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Euro on 12/2/2015 AD.
 */
public interface AutoLocationListener {
    void onLocationChanged(LatLng latLng);
    void onReady();
}
