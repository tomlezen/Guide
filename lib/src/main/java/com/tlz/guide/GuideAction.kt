package com.tlz.guide

import android.support.annotation.IdRes
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter
import android.view.View
import android.view.ViewGroup

/**
 *
 * Created by Tomlezen.
 * Date: 2017/7/29.
 * Time: 15:56.
 */
class GuideAction internal constructor(private val guide: Guide) {

  private var onDismissListener: OnGuideDismissListener? = null

  fun onDismissListener(block: () -> Unit): GuideAction {
    onDismissListener = object : OnGuideDismissListener {
      override fun onDismiss() {
        block()
      }
    }
    return this
  }

  fun dismiss(): GuideAction {
    if (!isShowing) {
      return this
    }
    isShowing = false
    ViewCompat.animate(guide.container)
        .alpha(0f)
        .setDuration(guide.container.resources.getInteger(android.R.integer.config_mediumAnimTime).toLong())
        .setListener(object : ViewPropertyAnimatorListenerAdapter() {
          override fun onAnimationEnd(view: View) {
            super.onAnimationEnd(view)
            val parent = view.parent
            if (parent is ViewGroup) {
              parent.removeView(view)
            }
            guide.release()
            onDismissListener?.onDismiss()
          }
        }).start()
    return this
  }

  fun dismissWithAnyWhere(): GuideAction {
    guide.container.setOnClickListener {
      dismiss()
    }
    return this
  }

  fun dismissWithView(@IdRes viewId: Int): GuideAction {
    val view = guide.container.findViewById<View>(viewId)
    view?.setOnClickListener { dismiss() }
    return this
  }

  fun onBackPress(): Boolean {
    if (isShowing) {
      if (guide.cancelable) {
        dismiss()
      }
      return true
    }
    return false
  }

  var isShowing = true

}