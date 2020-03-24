package net.ukr.zubenko.g.dreammaterialization.data

import android.graphics.PointF
import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.DreamView

class TouchEventData {
    val OriginTouchPoints = mutableListOf<PointF>()
    val CurrentTouchPoints = mutableListOf<PointF>()

    companion object {
        const val TAG = "TouchEventData"
    }

    val size: Int
        get() = Math.min(OriginTouchPoints.size, CurrentTouchPoints.size)

    val isNotEmpty: Boolean
        get() = OriginTouchPoints.isNotEmpty() && CurrentTouchPoints.isNotEmpty()

    fun setOrigin(event: MotionEvent) {
        Log.i(TAG, "setOrigin start")
        for (i in 0 until event.pointerCount) {
            OriginTouchPoints.add(i, PointF(event.getX(i), event.getY(i)))
            Log.i(TAG, "save point $i: (${OriginTouchPoints[i].x}, ${OriginTouchPoints[i].y})")
        }
        Log.i(TAG, "setOrigin end")
    }

    fun setCurrent(event: MotionEvent) {
        Log.i(TAG, "setCurrent start")
        for (i in 0 until event.pointerCount) {
            CurrentTouchPoints.add(i, PointF(event.getX(i), event.getY(i)))
            Log.i(TAG, "save point $i: (${CurrentTouchPoints[i].x}, ${CurrentTouchPoints[i].y})")
        }
        Log.i(TAG, "setCurrent start")
    }

    fun clearOrigin() {
        OriginTouchPoints.clear()
    }

    fun clearCurrent() {
        CurrentTouchPoints.clear()
    }

    fun clear() {
        clearCurrent()
        clearOrigin()
    }
}