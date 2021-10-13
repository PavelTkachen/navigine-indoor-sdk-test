package com.yurypotapov.sslnaviginecompose.listener

import com.navigine.idl.java.RouteListener
import com.navigine.idl.java.RoutePath
import java.util.ArrayList


class RouteListener: RouteListener() {
    override fun onPathsUpdated(p0: ArrayList<RoutePath>?) {
        if(p0 != null) {
            println("Route Listener ${p0[0].points}")
        }
    }
}