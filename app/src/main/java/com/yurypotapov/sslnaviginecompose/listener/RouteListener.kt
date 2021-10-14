package com.yurypotapov.sslnaviginecompose.listener

import android.util.Log
import com.navigine.idl.java.RouteListener
import com.navigine.idl.java.RoutePath
import java.util.ArrayList


class RouteListener: RouteListener() {
    override fun onPathsUpdated(p0: ArrayList<RoutePath>?) {
        if(p0 != null) {

            Log.println(Log.DEBUG, "ROUTE_LISTENER", "${p0[0].events[0].distance}");
            Log.println(Log.DEBUG, "ROUTE_LISTENER", "${p0[0].events[0].value}");
            Log.println(Log.DEBUG, "ROUTE_LISTENER", "${p0[0].events[0].type}");
            println("Route Listener ${p0[0].points}")
        }
    }
}