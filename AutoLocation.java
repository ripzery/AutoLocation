package com.ripzery.autolocate;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.renderscript.Double2;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.ripzery.autolocate.Listener.AutoLocationListener;

/**
 * Created by Euro on 12/2/2015 AD.
 */
public class AutoLocation implements GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, LocationListener {
    public static final String LAST_LOCATION = "LAST_LOCATION";
    private GoogleApiClient mGoogleApiClient;
    private final Context context;
    private AutoLocationListener autoLocationListener;
    private LocationRequest mLocationRequest;

    public AutoLocation(Context context){
        this.context = context;

        mGoogleApiClient = new GoogleApiClient.Builder(context)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build();
    }

    /**
     * Start/Stop listening to location update
     */
    public void listen(boolean isListen){
        if(isListen){
            mGoogleApiClient.connect();
        }else{
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * start location finding
     * @param locationUpdater update location properties object
     */
    public void startLocationUpdate(LocationUpdater locationUpdater){
        updateLocationUpdater(locationUpdater);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        final Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        /* Get location as fast as possible! */
        if(lastLocation != null){
            autoLocationListener.onLocationChanged(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()));
        }
    }

    /**
     * Update location finding rules
     * @param locationUpdater update location properties object
     */
    public void updateLocationUpdater(LocationUpdater locationUpdater){
        updateLocationRequest(locationUpdater);
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    private void updateLocationRequest(LocationUpdater locationUpdater){
        mLocationRequest = LocationRequest.create()
            .setPriority(locationUpdater.getPriority())
            .setInterval(locationUpdater.getInterval())
            .setFastestInterval(locationUpdater.getFastestInterval());
    }

    /**
     * Bind listener
     * @param autoLocationListener notify location event
     */
    public void setAutoLocationListener(AutoLocationListener autoLocationListener) {
        if (autoLocationListener != null) {
            this.autoLocationListener = autoLocationListener;
            if(!mGoogleApiClient.isConnected()){
                mGoogleApiClient.connect();
            }
        } else {
            mGoogleApiClient.disconnect();
        }
    }

    public void saveLastLocation(String lastLocation){
        try {
            SharedPreferences sp = context.getSharedPreferences("LOCATION_" + context.getPackageName(), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(LAST_LOCATION, lastLocation);
        }catch (Exception e){

        }
    }

    public LatLng getLastLocation(){
        try {
            SharedPreferences sp = context.getSharedPreferences("LOCATION_" + context.getPackageName(), Context.MODE_PRIVATE);
            final String[] lastLocation = sp.getString(LAST_LOCATION, "").split(",");
            return new LatLng(Double.parseDouble(lastLocation[0]), Double.parseDouble(lastLocation[1]));
        }catch (Exception e){
            return null;
        }
    }

    /**
     * Successfully connected to GoogleApiClient
     * @param bundle bundle from GoogleApiClient
     */
    @Override public void onConnected(Bundle bundle) {
        autoLocationListener.onReady();
    }

    @Override public void onConnectionSuspended(int i) {

    }

    @Override public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("AutoLocation", connectionResult.getErrorMessage());
    }

    @Override public void onLocationChanged(Location location) {
        autoLocationListener.onLocationChanged(new LatLng(location.getLatitude(), location.getLongitude()));
        saveLastLocation(location.getLatitude() + "," + location.getLongitude());
    }
}
