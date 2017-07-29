package com.tlz.guide.shapes

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF


/**
 *
 * Created by Tomlezen.
 * Date: 2017/6/22.
 * Time: 10:59.
 */
open class RightRect : Shape() {

    protected var x = 0
    protected var y = 0
    protected var width = 0
    protected var height = 0

    fun applySize(x: Int, y: Int, width: Int, height: Int) {
        this.x = x
        this.y = y
        this.width = width
        this.height = height
    }

    override fun drawOn(canvas: Canvas) {
        if (isDisplayBorder) {
            drawRect(canvas,
                    x - borderWidth,
                    y - borderWidth,
                    x + width + borderWidth,
                    y + height + borderWidth,
                    borderPaint)
        }
        drawRect(canvas, x.toFloat(), y.toFloat(), (x + width).toFloat(), (y + height).toFloat(), paint)
    }

    companion object {

        private fun drawRect(canvas: Canvas, left: Float, top: Float, right: Float, bottom: Float, paint: Paint) {
            val rectF = RectF(left, top, right, bottom)
            canvas.drawRect(rectF, paint)
        }
    }
}
