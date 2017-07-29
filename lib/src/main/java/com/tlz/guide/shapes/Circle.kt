package com.tlz.guide.shapes

import android.graphics.Canvas

/**
 *
 * Created by Tomlezen.
 * Date: 2017/6/22.
 * Time: 10:54.
 */
class Circle : Shape() {

    private var x = 0f
    private var y = 0f
    private var radius = 0f

    fun applySize(x: Float, y: Float, radius: Float) {
        this.x = x
        this.y = y
        this.radius = radius
    }

    override
    fun drawOn(canvas: Canvas) {
        if (isDisplayBorder) {
            canvas.drawCircle(x, y, radius + borderWidth, borderPaint)
        }

        canvas.drawCircle(x, y, radius, paint)
    }

}