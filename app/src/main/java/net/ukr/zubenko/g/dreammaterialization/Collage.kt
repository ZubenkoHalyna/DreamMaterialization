package net.ukr.zubenko.g.dreammaterialization

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import net.ukr.zubenko.g.dreammaterialization.data.TouchEventData
import net.ukr.zubenko.g.dreammaterialization.data.database.labs.DreamLab
import net.ukr.zubenko.g.dreammaterialization.data.database.tables.data.DreamView
import net.ukr.zubenko.g.dreammaterialization.data.database.labs.DreamViewLab

class Collage(context: Context, private val attrs: AttributeSet? = null): View(context, attrs) {
    val mDreamViews = mutableMapOf<DreamView, Bitmap>()
    val touchEventData = TouchEventData()
    var mMovingView: DreamView? = null

    companion object {
        private const val TAG = "collage_log"
        private val BACKGROUND_COLOR = Paint()

        init {
            BACKGROUND_COLOR.color = 0xfff8efe0.toInt()
        }
    }

    fun load() {
        Log.i(TAG, "load start")
        for (dream in DreamLab.mItems) {
            Log.i(TAG, "read dream: ${dream.mId}")
            val mPictureFile = DreamLab.getPictureFile(dream)
            val dreamView = DreamViewLab.getItem(dream.mId)
            Log.i(TAG, "read dreamView: ${dreamView?.mId}")
            dreamView?.let {
                mDreamViews[dreamView] =
                    PictureUtils.getScaledBitmap(mPictureFile.path, dreamView.mWidth, dreamView.mHeight)
            }
        }
        Log.i(TAG, "load end")
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPaint(BACKGROUND_COLOR)  // Заполнение фона
        for (pic in mDreamViews.keys) {
            mDreamViews[pic]?.let {
                if (pic.mRotationAngle != 0) {
                    canvas.rotate(-pic.mRotationAngle.toFloat(), pic.mCenter.x, pic.mCenter.y)
                }

                canvas.drawBitmap(it, pic.mLeft.toFloat(), pic.mTop.toFloat(), BACKGROUND_COLOR)

                if (pic.mRotationAngle != 0) {
                    canvas.rotate(pic.mRotationAngle.toFloat(), pic.mCenter.x, pic.mCenter.y)
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val current = PointF(event.x, event.y)

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.i(TouchEventData.TAG, "ACTION_DOWN")
                touchEventData.setOrigin(event)

                val origin = touchEventData.OriginTouchPoints.first()
                mMovingView = findDreamViewToMove(origin)
            }
            MotionEvent.ACTION_MOVE -> {
                Log.i(TouchEventData.TAG, "ACTION_MOVE")

                if (event.pointerCount == 1)
                    mMovingView?.let {
                        move(
                            it,
                            touchEventData.CurrentTouchPoints.firstOrNull() ?: touchEventData.OriginTouchPoints.first(),
                            current
                        )
                        invalidate()
                    }

                if (event.pointerCount == 2)
                    mMovingView?.let { view ->
                        val current2 = PointF(event.getX(1), event.getY(1))
                        touchEventData.CurrentTouchPoints.getOrNull(1)?.let { previousPoint ->
                            rotate(
                                view,
                                previousPoint,
                                current2
                            )
                            invalidate()
                        }
                    }

                touchEventData.setCurrent(event)
            }
            MotionEvent.ACTION_UP -> {
                Log.i(TouchEventData.TAG, "ACTION_UP")
                mMovingView = null
                touchEventData.clear()
            }
            MotionEvent.ACTION_CANCEL -> {
                Log.i(TouchEventData.TAG, "ACTION_CANCEL")
                mMovingView = null
                touchEventData.clear()
            }
        }

        return true
    }

    private fun findDreamViewToMove(point: PointF): DreamView? {
        for (view in mDreamViews.keys)
            if (shouldMoveDreamView(view, point))
                return view
        return null
    }

    private fun shouldMoveDreamView(dreamView: DreamView, originTouchPoint: PointF) =
        originTouchPoint.x > dreamView.mLeft && originTouchPoint.x < dreamView.mRight &&
                originTouchPoint.y > dreamView.mTop && originTouchPoint.y < dreamView.mButtom

    private fun move(dreamView: DreamView, origin:PointF, current: PointF) {
        dreamView.move(current.x - origin.x, current.y - origin.y)
    }

    private fun rotate(dreamView: DreamView, originTouchPoint: PointF, currentTouchPoint: PointF) {
        val angle = ((currentTouchPoint.y - originTouchPoint.y)/height +
                (currentTouchPoint.x - originTouchPoint.x)/width) * 180

        dreamView.rotate(angle.toInt())
    }

}