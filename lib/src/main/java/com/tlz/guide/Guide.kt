package com.tlz.guide

import android.app.Activity
import android.support.annotation.ColorInt
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.view.ViewCompat
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
class Guide private constructor(activity: Activity) {

    companion object {
        fun from(activity: Activity): Guide {
            return Guide(activity)
        }
    }

    internal val container = FrameLayout(activity.applicationContext)
    internal val guideView = GuideView(activity.applicationContext)
    private var fitsSystemWindows = false
    internal var cancelable = true
    private val views = mutableMapOf<Int, View>()

    init {
        val window = activity.window
        if (window != null) {
            val decorView = window.decorView as ViewGroup
            val content: ViewGroup = decorView.findViewById(android.R.id.content)
            content.addView(container, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT)
            this.container.addView(guideView, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT)
            if (android.os.Build.VERSION.SDK_INT >= 16) {
                val inflatedLayout = content.getChildAt(0)
                this.fitsSystemWindows = inflatedLayout?.fitsSystemWindows ?: false
            }
            this.container.setOnClickListener {  }
        }
        this.container.visibility = View.GONE
        container.alpha = 0f
    }

    fun backgroundColor(@ColorInt color: Int): Guide {
        guideView.backgroundOverlayColor = color
        return this
    }

    fun fitsSystemWindows(fitsSystemWindows: Boolean): Guide {
        this.fitsSystemWindows = fitsSystemWindows
        return this
    }

    fun cancelable(cancelable: Boolean): Guide{
        this.cancelable = cancelable
        return this
    }

    fun contentView(@LayoutRes content: Int): Guide {
        val child = LayoutInflater.from(guideView.context).inflate(content, container, false)
        container.addView(child, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        return this
    }

    fun onClickContentView(id: Int, onClickListener: View.OnClickListener): Guide {
        val view: View = container.findViewById(id)
        view.setOnClickListener(onClickListener)
        return this
    }

    fun show(): GuideAction {
        showWithDelay(0)
        return GuideAction(this)
    }

    fun showWithDelay(delayMillis: Long): GuideAction {
        container.postDelayed({
            container.visibility = View.VISIBLE
            ViewCompat.animate(container)
                    .alpha(1f)
                    .setDuration(container.resources.getInteger(android.R.integer.config_longAnimTime).toLong())
                    .start()
        }, delayMillis)
        return GuideAction(this)
    }

    private fun findViewById(@IdRes viewId: Int): View? {
        val context = guideView.context
        var view: View? = null
        if (context is Activity) {
            view = context.findViewById(viewId)
        }
        return view

    }

    fun on(@IdRes viewId: Int): ViewActions {
        return ViewActions(this, findViewById(viewId)!!, fitsSystemWindows)
    }

    fun on(view: View): ViewActions {
        return ViewActions(this, view, fitsSystemWindows)
    }

    internal fun addIntoViews(view: View) {
        if (view.id != -1 && views.containsKey(view.id)) {
            throw IllegalAccessException("repeat view id: ${view.id}")
        }
        if(view.id != -1){
            views.put(view.id, view)
        }
    }

    internal fun findViewByIdFromInner(@IdRes viewId: Int): View?{
        return views[viewId]
    }

    internal fun release(){
        views.clear()
    }

}