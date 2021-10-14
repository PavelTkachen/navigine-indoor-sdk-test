package com.yurypotapov.sslnaviginecompose.store

import android.app.Application
import com.navigine.idl.java.Point
import com.navigine.idl.java.Zone


class GlobalVariable : Application() {
    private var zones: ArrayList<Zone>? = null;
    private var userPosition: Point? = null;

    fun getZones() : ArrayList<Zone>? {
        return this.zones;
    }
    fun setZones(value: ArrayList<Zone>) {
        this.zones = value
    }

    fun getUserPosition() : Point? {
        return this.userPosition;
    }
    fun setUserPosition(value: Point) {
        this.userPosition = value
    }
}
