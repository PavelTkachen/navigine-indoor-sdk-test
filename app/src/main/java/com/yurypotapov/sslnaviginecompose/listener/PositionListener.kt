package com.yurypotapov.sslnaviginecompose.listener

import android.content.Context
import android.util.Log
import com.navigine.idl.java.*
import com.navigine.idl.java.PositionListener
import com.navigine.sdk.Navigine
import com.yurypotapov.sslnaviginecompose.store.GlobalVariable
import com.yurypotapov.sslnaviginecompose.utils.AudioManager
import com.yurypotapov.sslnaviginecompose.utils.NavigationUtils
import java.lang.Error

class PositionListener(private val context: Context) : PositionListener() {
    override fun onPositionUpdated(p0: Position?) {
//        Log.println(Log.DEBUG, "POSITION_LISTENER", "Position Loaded");
        if (null !== p0) {
            val appState = context as GlobalVariable;
            val zones: ArrayList<Zone>? = appState.getZones();
            val point: Point? = p0.point;
            var result = NavigationUtils(point, zones).checkLeaveZone();
            if(!result) {
                val am = AudioManager(context);
                am.soundEffect();
            }
            Log.i("POSITION_LISTENER", "$result")
        }
    }

    override fun onPositionError(p0: Error?) {
//        Log.println(Log.ERROR, "POSITION_LISTENER", "Position Loading Error: " + p0?.message);
    }
}