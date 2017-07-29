package com.tlz.guide

/**
 *
 * Created by Tomlezen.
 * Date: 2017/7/29.
 * Time: 10:50.
 */
interface Editor {

    fun end(): ViewActions

    fun show(): GuideAction

    fun showWithDelay(delayMills: Long): GuideAction

}