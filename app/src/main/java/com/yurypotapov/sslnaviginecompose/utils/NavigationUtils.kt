package com.yurypotapov.sslnaviginecompose.utils

import com.navigine.idl.java.Point
import com.navigine.idl.java.Zone


class NavigationUtils(private val userPosition: Point?, private val zones: ArrayList<Zone>?) {
    private val infiniteVal = 10000;

    private fun onSegment(p: Point, q: Point, r: Point): Boolean {
        return q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) && q.y <= Math.max(
            p.y,
            r.y
        ) && q.y >= Math.min(p.y, r.y)
    }

    private fun orientation(p: Point, q: Point, r: Point): Int {
        val value = ((q.y - p.y) * (r.x - q.x)
                - (q.x - p.x) * (r.y - q.y))
        if (value == 0F) {
            return 0 // collinear
        }
        return if (value > 0) 1 else 2 // clock or counterclock wise
    }

    private fun doIntersect(
        p1: Point, q1: Point,
        p2: Point, q2: Point
    ): Boolean {
        // Find the four orientations needed for
        // general and special cases
        val o1 = orientation(p1, q1, p2)
        val o2 = orientation(p1, q1, q2)
        val o3 = orientation(p2, q2, p1)
        val o4 = orientation(p2, q2, q1)

        // General case
        if (o1 != o2 && o3 != o4) {
            return true
        }

        // Special Cases
        // p1, q1 and p2 are collinear and
        // p2 lies on segment p1q1
        if (o1 == 0 && onSegment(p1, p2, q1)) {
            return true
        }

        // p1, q1 and p2 are collinear and
        // q2 lies on segment p1q1
        if (o2 == 0 && onSegment(p1, q2, q1)) {
            return true
        }

        // p2, q2 and p1 are collinear and
        // p1 lies on segment p2q2
        if (o3 == 0 && onSegment(p2, p1, q2)) {
            return true
        }

        // p2, q2 and q1 are collinear and
        // q1 lies on segment p2q2
        return o4 == 0 && onSegment(p2, q1, q2)

        // Doesn't fall in any of the above cases
    }

    private fun isInside(polygon: ArrayList<Point>, n: Int, p: Point): Boolean {
        // There must be at least 3 vertices in polygon[]
        if (n < 3) {
            return false
        }

        // Create a point for line segment from p to infinite
        val extreme = Point(infiniteVal.toFloat(), p.y)

        // Count intersections of the above line
        // with sides of polygon
        var count = 0
        var i = 0
        do {
            val next = (i + 1) % n

            // Check if the line segment from 'p' to
            // 'extreme' intersects with the line
            // segment from 'polygon[i]' to 'polygon[next]'
            if (doIntersect(polygon[i], polygon[next], p, extreme)) {
                // If the point 'p' is collinear with line
                // segment 'i-next', then check if it lies
                // on segment. If it lies, return true, otherwise false
                if (orientation(polygon[i], p, polygon[next]) == 0) {
                    return onSegment(
                        polygon[i], p,
                        polygon[next]
                    )
                }
                count++
            }
            i = next
        } while (i != 0)

        // Return true if count is odd, false otherwise
        return count % 2 == 1 // Same as (count%2 == 1)
    }

    fun checkLeaveZone(): Boolean {
        var result = false;
        if (zones != null && zones.count() > 0 && userPosition != null) {
            zones.forEach {
                val points: ArrayList<Point>  = it.polygon.points;
                val pointsSize: Int = points.size;
                return isInside(points, pointsSize, userPosition);
            }
        } else {
            println("ZONE_POLYGON_NOT_SIZE")
        }
        return result;
    }
}

//internal object GFG {
//    @JvmStatic
//    fun main(args: Array<String>) {
//        val polygon1 = arrayOf(
//            Point(0, 0),
//            Point(10, 0),
//            Point(10, 10),
//            Point(0, 10)
//        )
//        var n = polygon1.size
//        var p = Point(20, 20)
//        if (isInside(polygon1, n, p)) {
//            println("Yes")
//        } else {
//            println("No")
//        }
//        p = Point(5, 5)
//        if (isInside(polygon1, n, p)) {
//            println("Yes")
//        } else {
//            println("No")
//        }
//        val polygon2 = arrayOf(
//            Point(0, 0),
//            Point(5, 5), Point(5, 0)
//        )
//        p = Point(3, 3)
//        n = polygon2.size
//        if (isInside(polygon2, n, p)) {
//            println("Yes")
//        } else {
//            println("No")
//        }
//        p = Point(5, 1)
//        if (isInside(polygon2, n, p)) {
//            println("Yes")
//        } else {
//            println("No")
//        }
//        p = Point(8, 1)
//        if (isInside(polygon2, n, p)) {
//            println("Yes")
//        } else {
//            println("No")
//        }
//        val polygon3 = arrayOf(
//            Point(0, 0),
//            Point(10, 0),
//            Point(10, 10),
//            Point(0, 10)
//        )
//        p = Point(-1, 10)
//        n = polygon3.size
//        if (isInside(polygon3, n, p)) {
//            println("Yes")
//        } else {
//            println("No")
//        }
//    }
//
//    internal class Point(var x: Int, var y: Int)
//}