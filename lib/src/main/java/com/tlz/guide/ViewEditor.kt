package com.tlz.guide

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.annotation.*
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPropertyAnimatorCompat
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import android.widget.ImageView
import android.widget.TextView

/**
 *
 * Created by Tomlezen.
 * Date: 2017/7/28.
 * Time: 17:25.
 */
class ViewEditor<T : View> internal constructor(
    internal val view: T,
    internal val viewActions: ViewActions)
  : Editor<ViewEditor<T>> {

  internal var anchorViewId: Int? = null
  internal var layoutParams: ViewGroup.LayoutParams =
      ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
  internal var location = Location.CENTER
  internal var offsetX = 0
  internal var offsetY = 0
  internal var animated = false
  internal var delay = 100L
  internal var duration = 600L
  internal var interpolator: Interpolator = DecelerateInterpolator()

  fun id(@IdRes id: Int): ViewEditor<T> {
    view.id = id
    return this
  }

  fun layoutParams(layoutParams: ViewGroup.LayoutParams): ViewEditor<T> {
    this.layoutParams = layoutParams
    return this
  }

  @Deprecated("Not recommended")
  fun anchor(@IdRes id: Int): ViewEditor<T> {
    this.anchorViewId = id
    return this
  }

  fun location(location: Int): ViewEditor<T> {
    this.location = location
    return this
  }

  fun offsetX(px: Int): ViewEditor<T> {
    this.offsetX = px
    return this
  }

  fun offsetY(px: Int): ViewEditor<T> {
    this.offsetY = px
    return this
  }

  override fun onClick(onClick: (view: View) -> Unit): ViewEditor<T> {
    view.setOnClickListener {
      onClick(it)
    }
    return this
  }

  override fun onClickWithDismiss(onClick: ((View) -> Unit)?): ViewEditor<T> {
    return this
  }

  fun animated(): ViewEditor<T> {
    animated(delay, duration)
    return this
  }

  fun animated(delay: Long, duration: Long): ViewEditor<T> {
    animated(delay, duration, interpolator)
    return this
  }

  fun animated(delay: Long, duration: Long, interpolator: Interpolator): ViewEditor<T> {
    this.animated = true
    this.delay = delay
    this.duration = duration
    this.interpolator = interpolator
    return this
  }

  fun background(resId: Int): ViewEditor<T> {
    view.setBackgroundResource(resId)
    return this
  }

  fun backgroundColor(color: Int): ViewEditor<T> {
    view.setBackgroundColor(color)
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
}

fun ViewEditor<TextView>.text(@StringRes text: Int): ViewEditor<TextView> {
  view.setText(text)
  return this
}

fun ViewEditor<TextView>.text(text: String): ViewEditor<TextView> {
  view.text = text
  return this
}

fun ViewEditor<TextView>.textColor(color: Int): ViewEditor<TextView> {
  view.setTextColor(color)
  return this
}

fun ViewEditor<TextView>.textColorRes(@ColorRes color: Int): ViewEditor<TextView> {
  view.setTextColor(ContextCompat.getColor(view.context, color))
  return this
}

fun ViewEditor<TextView>.textSize(@DimenRes size: Int): ViewEditor<TextView> {
  view.setTextSize(TypedValue.COMPLEX_UNIT_PX, view.resources.getDimension(size))
  return this
}


fun ViewEditor<TextView>.textSizePx(size: Float): ViewEditor<TextView> {
  view.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
  return this
}

fun ViewEditor<TextView>.textSizeSp(size: Float): ViewEditor<TextView> {
  view.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
  return this
}

fun ViewEditor<ImageView>.image(drawable: Drawable): ViewEditor<ImageView> {
  view.setImageDrawable(drawable)
  return this
}

fun ViewEditor<ImageView>.image(bitmap: Bitmap): ViewEditor<ImageView> {
  view.setImageBitmap(bitmap)
  return this
}

fun ViewEditor<ImageView>.image(@DrawableRes icon: Int): ViewEditor<ImageView> {
  view.setImageResource(icon)
  return this
}

fun ViewEditor<ImageView>.scaleType(scaleType: ImageView.ScaleType): ViewEditor<ImageView> {
  view.scaleType = scaleType
  return this
}

fun <T : View> ViewEditor<T>.showWithAnimate(animate: ((ViewPropertyAnimatorCompat) -> ViewPropertyAnimatorCompat) = {
  it.alpha(1f)
      .setDuration(view.resources.getInteger(android.R.integer.config_longAnimTime).toLong())
}): GuideAction {
  end()
  return viewActions.showWithAnimate(animate)
}