package com.tlz.guide

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.annotation.DimenRes
import android.support.annotation.DrawableRes
import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.util.TypedValue
import android.view.View
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
class ViewEditor<out T : View> internal constructor(internal val view: T, internal val viewActions: ViewActions): Editor {

    internal var anchorViewId: Int? = null
    internal var location = Location.CENTER
    internal var offsetX = 0
    internal var offsetY = 0
    internal var animated = false
    internal var delay = 100L
    internal var duration = 600L
    internal var interpolator: Interpolator = DecelerateInterpolator()

    fun id(@IdRes id: Int): ViewEditor<T>{
        view.id = id
        return this
    }

    @Deprecated("there will be caton")
    fun anchor(@IdRes id: Int): ViewEditor<T>{
        this.anchorViewId = id
        return this
    }

    fun location(location: Int): ViewEditor<T> {
        this.location = location
        return this
    }

    fun offsetX(x: Int): ViewEditor<T> {
        this.offsetX = x
        return this
    }

    fun offsetY(y: Int): ViewEditor<T> {
        this.offsetY = y
        return this
    }

    fun clickListener(block: (view: View) -> Unit): ViewEditor<T> {
        view.setOnClickListener {
            block(it)
        }
        return this
    }

    fun animated(): ViewEditor<T>{
        animated(delay, duration)
        return this
    }

    fun animated(delay: Long, duration: Long): ViewEditor<T>{
        animated(delay, duration, interpolator)
        return this
    }

    fun animated(delay: Long, duration: Long, interpolator: Interpolator): ViewEditor<T>{
        this.animated = true
        this.delay = delay
        this.duration =duration
        this.interpolator = interpolator
        return this
    }

    fun background(resId: Int): ViewEditor<T>{
        view.setBackgroundResource(resId)
        return this
    }

    fun backgroundColor(color: Int): ViewEditor<T>{
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

    override fun showWithDelay(delayMills: Long): GuideAction {
        end()
        return viewActions.showWithDelay(delayMills)
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

fun ViewEditor<TextView>.textSize(@DimenRes size: Int): ViewEditor<TextView> {
    view.setTextSize(TypedValue.COMPLEX_UNIT_SP, view.resources.getDimension(size))
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