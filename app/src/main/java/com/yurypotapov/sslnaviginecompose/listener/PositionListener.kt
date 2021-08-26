package com.yurypotapov.sslnaviginecompose.listener

import android.util.Log
import com.navigine.idl.java.Position
import com.navigine.idl.java.PositionListener
import java.lang.Error

class PositionListener: PositionListener() {
    override fun onPositionUpdated(p0: Position?) {
        Log.println(Log.DEBUG, "POSTION_LISTENER", "Position Loaded");
    }

    override fun onPositionError(p0: Error?) {
        Log.println(Log.ERROR, "POSTION_LISTENER", "Position Loading Error: " + p0?.message);
    }
}