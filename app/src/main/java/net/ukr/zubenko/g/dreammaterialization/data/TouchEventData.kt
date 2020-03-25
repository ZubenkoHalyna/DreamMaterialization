package net.ukr.zubenko.g.dreammaterialization.data

import android.graphics.PointF
import android.view.MotionEvent

class TouchEventData {
    val mOriginTouchPoints = mutableListOf<PointF>()
    val mCurrentTouchPoints = mutableListOf<PointF>()
    private var previousTouchPoints = listOf<PointF>()

    val mPreviousTouchPoints: List<PointF>
    get() = if (previousTouchPoints.isEmpty())
                mOriginTouchPoints
            else
                previousTouchPoints

    val size: Int
        get() = Math.min(mOriginTouchPoints.size, mCurrentTouchPoints.size)

    fun setOrigin(event: MotionEvent) {
        for (i in 0 until event.pointerCount) {
            mOriginTouchPoints.add(i, PointF(event.getX(i), event.getY(i)))
        }
    }

    fun setOrigin(p: PointF, i: Int) {
        mOriginTouchPoints.add(i, p)
    }

    fun setCurrent(event: MotionEvent) {
        previousTouchPoints = mCurrentTouchPoints.toList()
        for (i in 0 until event.pointerCount) {
            mCurrentTouchPoints.add(i, PointF(event.getX(i), event.getY(i)))
        }
    }

    fun clearOrigin() {
        mOriginTouchPoints.clear()
    }

    fun clearCurrent() {
        mCurrentTouchPoints.clear()
        previousTouchPoints = listOf()
    }

    fun clear() {
        clearCurrent()
        clearOrigin()
    }
}