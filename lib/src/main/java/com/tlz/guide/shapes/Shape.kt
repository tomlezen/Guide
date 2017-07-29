package com.tlz.guide.shapes

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff.Mode.DST_ATOP
import android.graphics.PorterDuffXfermode


/**
 *
 * Created by Tomlezen.
 * Date: 2017/6/22.
 * Time: 10:54.
 */
abstract class Shape {
    var color = Color.argb(0, 0, 0, 0)
        set(color) {
            field = color
            this.paint.color = this.color
        }

    var paint: Paint
        protected set

    var borderColor = Color.parseColor("#AA999999")
        set(borderColor) {
            field = borderColor
            this.borderPaint.color = borderColor
        }

    var borderWidth = 30f

    val borderPaint: Paint

    var isDisplayBorder = false

    init {
        this.paint = Paint()
        this.paint.color = color
        this.paint.isAntiAlias = true
        this.paint.xfermode = PorterDuffXfermode(DST_ATOP)

        this.borderPaint = Paint()
        this.borderPaint.isAntiAlias = true
        this.borderPaint.color = this.borderColor
    }

    abstract fun drawOn(canvas: Canvas)
}