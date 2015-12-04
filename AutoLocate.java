package com.ripzery.autolocate;

import android.content.Context;
import com.google.android.gms.maps.model.LatLng;
import com.ripzery.autolocate.Listener.AutoLocationListener;

/**
 * Created by Euro on 12/4/15 AD.
 */
public class AutoLocate {
    Context context;
    SearchLocationModule searchLocationModule;

    public AutoLocate(Context context) {
        this.context = context;
        searchLocationModule = new SearchLocationModule(context);
    }

    public void start(final LocationUpdaterModel locationUpdaterModel) {
        searchLocationModule.setAutoLocationListener(new AutoLocationListener() {
            @Override public void onLocationChanged(LatLng latLng) {

            }

            @Override public void onReady() {
                searchLocationModule.startLocationUpdate(locationUpdaterModel);
            }
        });
        searchLocationModule.listen(true);

    }

    public void stop() {
        searchLocationModule.listen(false);
    }
}
