package com.tlz.guide

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import com.tlz.guide.shapes.Shape


/**
 *
 * Created by Tomlezen.
 * Date: 2017/6/22.
 * Time: 11:01.
 */
internal class ShapeContainerView : View {

  constructor(context: Context) : this(context, null)
  constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

  var backgroundOverlayColor = Color.argb(DEFAULT_ALPHA_COLOR, 0, 0, 0)
  private var shapes = mutableListOf<Shape>()

  init {
    isDrawingCacheEnabled = true
    setLayerType(View.LAYER_TYPE_SOFTWARE, null)
  }

  fun addShape(shape: Shape) {
    this.shapes.add(shape)
  }

  override fun onDraw(canvas: Canvas?) {
    super.onDraw(canvas)
    if (canvas != null) {
      canvas.drawColor(backgroundOverlayColor)
      for (shape in shapes) {
        shape.drawOn(canvas)
      }
    }
  }

  internal fun clearShape() {
    shapes.clear()
  }

  companion object {
    const val DEFAULT_ALPHA_COLOR = 200
  }

}
