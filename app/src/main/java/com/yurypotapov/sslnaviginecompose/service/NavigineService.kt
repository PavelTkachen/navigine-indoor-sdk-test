package com.yurypotapov.sslnaviginecompose.service

import android.content.Context
import com.navigine.idl.java.*
import com.navigine.sdk.Navigine
import com.navigine.view.LocationView
import com.yurypotapov.sslnaviginecompose.listener.LocationListener
import com.yurypotapov.sslnaviginecompose.listener.PositionListener
import com.yurypotapov.sslnaviginecompose.listener.RouteListener
import kotlin.reflect.KFunction1

class NavigineService(
    private val context: Context,
    private val userHash: String,
    private val defaultLocationId: Int,
    private val setVenues: KFunction1<ArrayList<Venue>, Unit>,
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

    private val mRouteManager: RouteManager = mNavigineSdk.getRouteManager(mLocationManager, mNavigationManager);

    public fun init() {
        mLocationManager.addLocationListener(LocationListener(context, setVenues));
        mNavigationManager.addPositionListener(PositionListener());
        mRouteManager.addRouteListener(RouteListener());
        mLocationManager.locationId = this.defaultLocationId; //91226
    }

    public fun getNavigineSdk(): NavigineSdk? {
        return this.mNavigineSdk;
    }

    public fun getLocationManager(): LocationManager {
        return this.mLocationManager;
    }

    public fun getLocationList(): HashMap<Int, LocationInfo> {
        return this.mNavigineSdk.locationListManager.locationList;
    }

    public fun getRouteManager(): RouteManager {
        return this.mRouteManager;
    }


    public fun getNavigationManager(): NavigationManager? {
        return this.mNavigationManager;
    }

    public fun getLocationView(): LocationView {
        this.mLocationView.setZoomScale(20F)
        return this.mLocationView.apply {
            showBeacons(true)
            showVenues(true)
            setSublocation(133764)
            setZoomScale(30F)
        }

    }
}