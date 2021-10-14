package com.yurypotapov.sslnaviginecompose.listener

import android.util.Log
import com.navigine.idl.java.*
import com.navigine.idl.java.PositionListener
import java.lang.Error

class PositionListener() : PositionListener() {
    override fun onPositionUpdated(p0: Position?) {
        Log.println(Log.DEBUG, "POSITION_LISTENER", "Position Loaded");
        if (null !== p0) {
            val point: Point? = p0.point;
            Log.i("POSITION_LISTENER", "x: ${point?.x} y: ${point?.y}")
            Log.i("POSITION_LISTENER", "x: ${p0.azimuth}")
        }
    }

    override fun onPositionError(p0: Error?) {
        Log.println(Log.ERROR, "POSITION_LISTENER", "Position Loading Error: " + p0?.message);
    }
}