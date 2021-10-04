package com.yurypotapov.sslnaviginecompose.service

import android.content.Context
import androidx.compose.runtime.Composable
import com.navigine.idl.java.LocationManager
import com.navigine.idl.java.NavigationManager
import com.navigine.idl.java.NavigineSdk
import com.navigine.sdk.Navigine
import com.navigine.view.LocationView
import com.yurypotapov.sslnaviginecompose.listener.LocationListener
import com.yurypotapov.sslnaviginecompose.listener.PositionListener

class NavigineService(
    private val context: Context,
    private val userHash: String,
    private val defaultLocationId: Int
) {

    init {
        // Initialize Navigine library
        Navigine.initialize(context);
        NavigineSdk.setUserHash(userHash); //
    }

    private val mNavigineSdk = NavigineSdk.getInstance();

    private val mLocationManager: LocationManager = mNavigineSdk.locationManager;

    private val mNavigationManager = mNavigineSdk.getNavigationManager(mLocationManager);

    private val mLocationView = LocationView(context);

    public fun init() {
        mLocationManager.addLocationListener(LocationListener());
        mNavigationManager.addPositionListener(PositionListener());
        mLocationManager.locationId = this.defaultLocationId; //91226
    }

    public fun getNavigineSdk(): NavigineSdk? {
        return this.mNavigineSdk;
    }

    public fun getLocationManager(): LocationManager {
        return this.mLocationManager;
    }

    public fun getNavigationManager(): NavigationManager? {
        return this.mNavigationManager;
    }

    public fun getLocationView(): LocationView {
        this.mLocationView.setZoomScale(20F)
        return this.mLocationView.apply {
            showBeacons(true)

            setSublocation(133764)
            setZoomScale(30F)
        }

    }
}