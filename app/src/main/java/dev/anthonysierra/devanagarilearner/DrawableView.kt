package dev.anthonysierra.devanagarilearner

import android.content.Context
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.MotionEvent.ACTION_MOVE
import android.view.View

/**
 * A view that can be used to draw on top of another view.
 */
class DrawableView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet?,
    defaultStyle: Int = 0
) : View(context, attr, defaultStyle) {
    init {
        setWillNotDraw(false)
    }

    /**
     * Class to contain the list of paths drawn.
     */
    private class PathContainer {
        fun addPoint(pointF: PointF) {
            touchPoints.add(pointF)
        }

        val touchPoints = mutableListOf<PointF>()
    }

    private val paths = mutableListOf<PathContainer>()

    private var currentPath: PathContainer? = null

    private val paint = Paint(ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 20f
    }

    override fun onTrackballEvent(event: MotionEvent?): Boolean {
        Log.i("TOUCHEVENTLOG", "oNTrackballEvent ; " + event?.actionMasked)
        return super.onTrackballEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.i("TOUCHEVENTLOG", "" + event?.actionMasked)
        event?.let { touch ->
            when (touch.actionMasked) {
                ACTION_DOWN -> {
                    currentPath = PathContainer()
                }
                ACTION_MOVE -> {
                    currentPath?.addPoint(PointF(touch.x, touch.y))
                }
                else -> {
                    currentPath?.let {
                        paths.add(it)
                    }
                    currentPath = null
                }
            }

        }
        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        currentPath?.let {
            drawContainer(canvas, it)
        }
        for (container in paths) {
            drawContainer(canvas, container)
        }
    }

    private fun drawContainer(canvas: Canvas?, container: PathContainer) {
        if (container.touchPoints.size > 0) {
            val path = Path()
            var previousPoint = container.touchPoints[0]
            path.moveTo(previousPoint.x, previousPoint.y)
            for (point in container.touchPoints) {
                path.cubicTo(previousPoint.x, previousPoint.y, point.x, point.y, point.x, point.y)
                previousPoint = point
            }
            canvas?.drawPath(path, paint)
        }
    }
}