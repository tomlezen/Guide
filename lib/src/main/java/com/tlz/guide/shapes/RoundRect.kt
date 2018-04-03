package com.tlz.guide.shapes

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF


/**
 *
 * Created by Tomlezen.
 * Date: 2017/6/22.
 * Time: 10:56.
 */
class RoundRect(private val radius: Float) : RightRect() {

  override
  fun drawOn(canvas: Canvas) {
    if (isDisplayBorder) {
      drawRoundedRect(canvas, (x - borderWidth), (y - borderWidth), (x + width + borderWidth), (y + height + borderWidth),
          radius, borderPaint)
    }
    drawRoundedRect(canvas, x.toFloat(), y.toFloat(), (x + width).toFloat(), (y + height).toFloat(),
        radius, paint)
  }

  companion object {

    private fun drawRoundedRect(canvas: Canvas, left: Float, top: Float, right: Float,
                                bottom: Float, radius: Float, paint: Paint) {
      var culRadius = radius
      if (radius <= 0) {
        culRadius = (bottom - top) / 2f
      }

      val rectF = RectF(left, top, right, bottom)
      canvas.drawRoundRect(rectF, culRadius, culRadius, paint)
    }
  }
}