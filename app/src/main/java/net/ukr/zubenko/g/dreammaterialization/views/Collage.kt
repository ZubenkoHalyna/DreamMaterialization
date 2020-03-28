package net.ukr.zubenko.g.dreammaterialization.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import net.ukr.zubenko.g.dreammaterialization.PictureUtils
import net.ukr.zubenko.g.dreammaterialization.data.TouchEventData
import net.ukr.zubenko.g.dreammaterialization.data.database.labs.DreamLab
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.DreamView
import net.ukr.zubenko.g.dreammaterialization.data.database.labs.DreamViewLab
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.Dream

class Collage(context: Context, attrs: AttributeSet? = null): View(context, attrs) {
    val mDreamViews = mutableMapOf<DreamView, Bitmap>()
    private val touchEventData = TouchEventData()
    private var mMovingView: DreamView? = null
    lateinit var mStartDreamInfoActivity: (DreamView) -> Unit

    companion object {
        private const val TAG = "collage_class_log"
        private val BACKGROUND_COLOR = Paint()

        init {
            BACKGROUND_COLOR.color = 0xffffffff.toInt()
        }
    }

    fun load() {
        Log.i(TAG, "start load")
        for (dream in DreamLab.mItems) {
            val mPictureFile = DreamLab.getPictureFile(dream)
            val dreamView = DreamViewLab.getItem(dream.mId)
            dreamView?.let {
                mDreamViews[dreamView] =
                    PictureUtils.getScaledBitmap(
                        mPictureFile.path,
                        dreamView.mWidth,
                        dreamView.mHeight
                    )
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPaint(BACKGROUND_COLOR)  // Заполнение фона
        for (pic in mDreamViews.keys) {
            mDreamViews[pic]?.let {
                if (pic.mRotationAngle != 0) {
                    canvas.rotate(pic.mRotationAngle.toFloat(), pic.mCenter.x, pic.mCenter.y)
                }

                canvas.drawBitmap(it, null, pic.mRect,
                    BACKGROUND_COLOR
                )

                if (pic.mRotationAngle != 0) {
                    canvas.rotate(-pic.mRotationAngle.toFloat(), pic.mCenter.x, pic.mCenter.y)
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                Log.i(TAG, "ACTION_DOWN ${event.pointerCount}")
                touchEventData.setOrigin(event)
                val origin = touchEventData.mOriginTouchPoints.first()
                mMovingView = findDreamViewToMove(origin)
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                Log.i(TAG, "ACTION_POINTER_DOWN ${event.pointerCount}")
                touchEventData.setOrigin(PointF(event.getX(1), event.getY(1)), event.pointerCount - 1)
            }
            MotionEvent.ACTION_MOVE -> {
                Log.i(TAG, "ACTION_MOVE")
                touchEventData.setCurrent(event)

                if (event.pointerCount == 1)
                    move()

                if (event.pointerCount == 2) {
                    scale()
                    rotate()
                }
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                Log.i(TAG, "ACTION_UP")
                if (touchEventData.notMoved)
                    onClick(event.x, event.y)
                mMovingView = null
                touchEventData.clear()
            }
            MotionEvent.ACTION_CANCEL -> {
                Log.i(TAG, "ACTION_CANCEL")
                mMovingView = null
                touchEventData.clear()
            }
        }

        return true
    }

    fun onClick(x: Float, y: Float) {
        val dv = findDreamViewToMove(PointF(x, y))
        dv?.let {
            mStartDreamInfoActivity(dv)
        }
    }

    fun deleteDreamCallback(dream: Dream) {
        mDreamViews.remove(DreamViewLab.getItem(dream.mId))
        invalidate()
    }

    private fun findDreamViewToMove(point: PointF): DreamView? {
        for (view in mDreamViews.keys)
            if (shouldMoveDreamView(view, point))
                return view
        return null
    }

    private fun shouldMoveDreamView(dreamView: DreamView, originTouchPoint: PointF) =
        originTouchPoint.x > dreamView.mLeft && originTouchPoint.x < dreamView.mRight &&
                originTouchPoint.y > dreamView.mTop && originTouchPoint.y < dreamView.mBottom

    private fun move() {
        val origin = touchEventData.mPreviousTouchPoints[0]
        val current = touchEventData.mCurrentTouchPoints[0]
        mMovingView?.move(current.x - origin.x, current.y - origin.y)
    }

    private fun scale() {
        val scaleParams = scaleMultiplier()

        mMovingView?.let {
            val view = it.copy()
            view.scale(scaleParams)
            mDreamViews[view]?.let { b ->
                mDreamViews.remove(view)
                mDreamViews[view] = b
            }
        }
    }

    private fun rotate() {
        if (shouldRotate()) {
            val previous = touchEventData.mPreviousTouchPoints.getOrNull(1) ?: touchEventData.mOriginTouchPoints[1]
            val current = touchEventData.mCurrentTouchPoints[1]
            var angle = ((current.y - previous.y) / height + (current.x - previous.x) / width) * 180

            angle = Math.max(-10.0f, Math.min(10.0f, angle))
            mMovingView?.rotate(angle.toInt())
        }
    }

    private fun shouldRotate(): Boolean {
        val origin1 = touchEventData.mOriginTouchPoints[0]
        val origin2 = touchEventData.mOriginTouchPoints[1]
        val current1 = touchEventData.mCurrentTouchPoints[0]
        val current2 = touchEventData.mCurrentTouchPoints[1]

        val vector1 = getVector(origin1, current1)
        val vector2 = getVector(origin2, current2)
        val vector3 = getVector(origin1, origin2)
        val vector4 = getVector(current1, current2)

        val z1 = vectorProduct(vector1, vector2)
        val z2 = vectorProduct(vector3, vector4)

        return Math.abs(z1 * 20.0) < Math.abs(z2)
    }

    private fun scaleMultiplier(): Double {
        val origin1 = touchEventData.mOriginTouchPoints[0]
        val origin2 = touchEventData.mOriginTouchPoints[1]
        val current1 = touchEventData.mCurrentTouchPoints[0]
        val current2 = touchEventData.mCurrentTouchPoints[1]

        val vector1 = getVector(origin1, origin2)
        val vector2 = getVector(current1, current2)

        val d3 = Math.sqrt(vector1.x * vector1.x + vector1.y * vector1.y.toDouble())
        val d4 = Math.sqrt(vector2.x * vector2.x + vector2.y * vector2.y.toDouble())

        return Math.max(Math.min(3.0, d4/d3),0.33)
    }

    private fun getVector(start: PointF, end: PointF): PointF {
        return PointF(end.x - start.x, end.y - start.y)
    }

    private fun vectorProduct(v1: PointF, v2: PointF): Float = v1.x * v2.y - v1.y * v2.x
}