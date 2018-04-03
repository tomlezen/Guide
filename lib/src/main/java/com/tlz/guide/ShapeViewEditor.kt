package com.tlz.guide

import android.support.annotation.IdRes
import android.view.View
import android.view.ViewGroup
import com.tlz.guide.shapes.Shape

/**
 *
 * Created by Tomlezen.
 * Date: 2017/6/22.
 * Time: 11:25.
 */
open class ShapeViewEditor<T : Shape> internal constructor(
    internal val shape: T,
    internal val view: View,
    private val viewActions: ViewActions)
  : Editor<ShapeViewEditor<T>> {

  internal var additionalRatio = 0f
  internal var padding = 0
  private var onClickDismiss = false
  private var onClick: ((View) -> Unit)? = null

  init {
    view.layoutParams = ViewGroup.MarginLayoutParams(100, 100)
    view.setOnClickListener{
      onClick?.invoke(it)
      if(onClickDismiss){
        viewActions.dismiss()
      }
    }
  }

  fun id(@IdRes id: Int): ShapeViewEditor<T> {
    view.id = id
    return this
  }

  fun color(color: Int): ShapeViewEditor<T> {
    shape.color = color
    return this
  }

  fun border(borderColor: Int = shape.borderColor, borderWidth: Float = shape.borderWidth)
      : ShapeViewEditor<T> {
    shape.isDisplayBorder = true
    shape.borderColor = borderColor
    shape.borderWidth = borderWidth
    checkBorder()
    return this
  }

  fun border(borderWidth: Float = shape.borderWidth): ShapeViewEditor<T> {
    shape.isDisplayBorder = true
    shape.borderWidth = borderWidth
    checkBorder()
    return this
  }

  fun padding(padding: Int): ShapeViewEditor<T> {
    this.padding = padding
    return this
  }

  fun additionalRatio(radio: Float): ShapeViewEditor<T> {
    this.additionalRatio = radio
    return this
  }

  override fun onClick(onClick: (view: View) -> Unit): ShapeViewEditor<T> {
    this.onClick = onClick
    return this
  }

  override fun onClickWithDismiss(onClick: ((View) -> Unit)?): ShapeViewEditor<T> {
    onClickDismiss = true
    this.onClick = onClick
    return this
  }

  override fun end(): ViewActions {
    viewActions.addView(view, this)
    return viewActions
  }

  override fun show(): GuideAction {
    end()
    return viewActions.show()
  }

  private fun checkBorder() {
    if (shape.borderWidth < 0) {
      throw IllegalArgumentException("border width must greater than equal to zero")
    }
  }

}