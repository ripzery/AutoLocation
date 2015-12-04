package com.ripzery.autolocate;

import com.google.android.gms.location.LocationRequest;

/**
 * Created by Euro on 12/2/2015 AD.
 */
public class LocationUpdater {
    public static int PRIORITY_HIGH_ACCURACY = LocationRequest.PRIORITY_HIGH_ACCURACY;
    public static int PRIORITY_BALANCED_POWER_ACCURACY = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;
    public static int PRIORITY_LOW_POWER = LocationRequest.PRIORITY_LOW_POWER;
    public static int PRIORITY_NO_POWER = LocationRequest.PRIORITY_NO_POWER;

    public static final int DEFAULT_INTERVAL = 5000;
    public static final int DEFAULT_FASTEST_INTERVAL = 5000;
    public static final int DEFAULT_PRIORITY = PRIORITY_BALANCED_POWER_ACCURACY;

    private int interval;
    private int fastestInterval;
    private int priority;

    public static LocationUpdater create(){
        return new LocationUpdater(DEFAULT_INTERVAL, DEFAULT_FASTEST_INTERVAL, DEFAULT_PRIORITY);
    }

    public LocationUpdater(int interval, int fastestInterval,int priority) {
        this.interval = interval;
        this.fastestInterval = fastestInterval;
        this.priority = priority;
    }

    public int getInterval() {
        return interval;
    }

    public int getFastestInterval() {
        return fastestInterval;
    }

    public LocationUpdater setInterval(int interval) {
        this.interval = interval;
        return this;
    }

    public LocationUpdater setFastestInterval(int fastestInterval) {
        this.fastestInterval = fastestInterval;
        return this;
    }

    public LocationUpdater setPriority(int priority){
        this.priority = priority;
        return this;
    }

    public int getPriority() {
        return priority;
    }
}
