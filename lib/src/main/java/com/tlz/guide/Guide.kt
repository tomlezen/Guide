package com.tlz.guide

import android.app.Activity
import android.support.annotation.ColorInt
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout


/**
 *
 * Created by Tomlezen.
 * Date: 2017/6/22.
 * Time: 11:00.
 */
class Guide private constructor(private val activity: Activity) {

  internal val container = FrameLayout(activity.applicationContext).apply {
    setOnClickListener { }
    visibility = View.GONE
    alpha = 0f
  }
  internal val shapeContainer = ShapeContainerView(activity.applicationContext)
  private var fitsSystemWindows = false
  private val views = mutableMapOf<Int, View>()

  private var guideAction: GuideAction? = null
    get() {
      if (field == null)
        field = GuideAction(this)
      return field
    }

  private fun initContainer(){
    if(container.parent == null){
      activity.window?.apply {
        val content: ViewGroup = (decorView as ViewGroup).findViewById(android.R.id.content)
        content.addView(this@Guide.container, ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)
        this@Guide.container.addView(shapeContainer, ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)
        if (android.os.Build.VERSION.SDK_INT >= 16) {
          val inflatedLayout = content.getChildAt(0)
          this@Guide.fitsSystemWindows = inflatedLayout?.fitsSystemWindows ?: false
        }
      }
    }
  }

  fun backgroundColor(@ColorInt color: Int): Guide {
    container.setBackgroundColor(color)
    return this
  }

  fun shapeContainerBackgroundColor(@ColorInt color: Int): Guide {
    shapeContainer.backgroundOverlayColor = color
    return this
  }

  fun fitsSystemWindows(fitsSystemWindows: Boolean): Guide {
    this.fitsSystemWindows = fitsSystemWindows
    return this
  }

  fun show(): GuideAction {
    initContainer()
    if (container.visibility != View.VISIBLE) {
      container.visibility = View.VISIBLE
      container.alpha = 1f
    }
    return guideAction!!
  }

  fun showWithAnimate(animate: ((ViewPropertyAnimatorCompat) -> ViewPropertyAnimatorCompat))
      : GuideAction {
    initContainer()
    if (container.visibility != View.VISIBLE) {
      container.visibility = View.VISIBLE
      animate(ViewCompat.animate(container))
          .start()
    }
    return guideAction!!
  }

  internal fun dismiss(){
    guideAction?.dismiss()
  }

  private fun findViewById(@IdRes viewId: Int): View? {
    val context = shapeContainer.context
    var view: View? = null
    if (context is Activity) {
      view = context.findViewById(viewId)
    }
    return view
  }

  fun on(@IdRes viewId: Int): ViewActions {
    val view = findViewById(viewId)
    return view?.let { on(it) } ?: throw IllegalAccessException("not find view by id")
  }

  fun on(view: View): ViewActions = ViewActions(this, view, fitsSystemWindows)

  internal fun addIntoViews(view: View) {
    if (view.id != -1 && views.containsKey(view.id)) {
      throw IllegalAccessException("repeat view id: ${view.id}")
    }
    if (view.id != -1) {
      views[view.id] = view
    }
  }

  internal fun findViewByIdFromInner(@IdRes viewId: Int): View? = views[viewId]

  internal fun release() {
    views.clear()
    shapeContainer.clearShape()
    container.removeAllViews()
    container.setOnClickListener {  }
    activity.window?.apply {
      (decorView as ViewGroup).removeView(this@Guide.container)
    }
    guideAction = null
  }

  companion object {
    fun from(activity: Activity): Guide = Guide(activity)
  }

}