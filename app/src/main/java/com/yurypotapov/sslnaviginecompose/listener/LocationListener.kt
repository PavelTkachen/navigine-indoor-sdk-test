package com.yurypotapov.sslnaviginecompose.listener

import android.util.Log
import com.navigine.idl.java.Location
import com.navigine.idl.java.LocationListener
import java.lang.Error

class LocationListener: LocationListener() {
    override fun onLocationLoaded(p0: Location?) {
        Log.println(Log.DEBUG, "LOCATION_LISTENER", "Location Loaded");
    }

    override fun onDownloadProgress(p0: Int, p1: Int) {
        Log.println(Log.DEBUG, "LOCATION_LISTENER", "DOWNLOAD PROGRESS");
    }

    override fun onLocationFailed(p0: Error?) {
        Log.println(Log.ERROR, "LOCATION_LISTENER", "Location Loading Error: " + p0?.message);
    }
}