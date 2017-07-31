package com.tlz.guide

import android.graphics.Rect
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
class CustomShapeViewEditor<out T : Shape> internal constructor(internal  val onPreDraw: (rect: Rect) -> Unit, shape: T, view: View, viewActions: ViewActions) : ShapeViewEditor<T>(shape, view, viewActions) {


}