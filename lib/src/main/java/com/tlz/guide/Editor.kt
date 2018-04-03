package com.tlz.guide

import android.view.View

/**
 *
 * Created by Tomlezen.
 * Date: 2017/7/29.
 * Time: 10:50.
 */
interface Editor<T> {

  fun onClick(onClick: (View) -> Unit): T

  fun onClickWithDismiss(onClick: ((View) -> Unit)? = null): T

  fun end(): ViewActions

  fun show(): GuideAction

}