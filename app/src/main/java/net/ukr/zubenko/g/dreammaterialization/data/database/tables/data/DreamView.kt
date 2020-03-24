package net.ukr.zubenko.g.dreammaterialization.data.database.tables.data

import android.graphics.PointF
import android.graphics.Rect
import net.ukr.zubenko.g.dreammaterialization.data.database.labs.DreamLab
import net.ukr.zubenko.g.dreammaterialization.data.database.labs.DreamViewLab
import java.util.*

class DreamView(dreamId: UUID,
                var mLeft: Int,
                var mTop: Int,
                var mWidth: Int,
                var mHeight: Int,
                var mRotationAngle: Int): DbItem(dreamId) {
    val mRight: Int
        get() = mLeft + mWidth
    val mButtom: Int
        get() = mTop + mHeight
    val mCenter: PointF
        get() = PointF((mLeft + mRight)/2.0f, (mTop + mButtom)/2.0f)
    val dream: Dream?
        get() = DreamLab.getItem(mId)

    fun copy(
        id: UUID = mId,
        left: Int = mLeft,
        top: Int = mTop,
        width: Int = mWidth,
        height: Int = mHeight,
        angle: Int = mRotationAngle): DreamView
    {
        val dream =
            DreamView(id, left, top, width, height, angle)
        DreamViewLab.update(dream)

        return dream
    }

    fun move(x: Float, y: Float) {
        mLeft += x.toInt()
        mTop += y.toInt()
        DreamViewLab.update(this)
    }

    fun rotate(angle: Int) {
        mRotationAngle += angle
        DreamViewLab.update(this)
    }
}