package com.yurypotapov.sslnaviginecompose.listener

import android.content.Context
import com.navigine.idl.java.Location
import com.navigine.idl.java.LocationListener
import com.navigine.idl.java.Venue
import java.lang.Error
import kotlin.reflect.KFunction1
import com.yurypotapov.sslnaviginecompose.store.GlobalVariable




class LocationListener(
    private val context: Context,
    private val setVenues: KFunction1<ArrayList<Venue>, Unit>,
) :
    LocationListener() {

    override fun onLocationLoaded(p0: Location?) {

        if (p0 !== null) {
            p0.sublocations.forEach {
                println("zones ${it.zones}")
                val appState = context as GlobalVariable;
                if (it.zones.count() > 0) {
                    appState.setZones(it.zones)
                }
                if (it.venues.count() > 0) {
                    setVenues(it.venues);
                }
            }
        }
    }

    override fun onDownloadProgress(p0: Int, p1: Int) {
    }

    override fun onLocationFailed(p0: Error?) {
    }
}