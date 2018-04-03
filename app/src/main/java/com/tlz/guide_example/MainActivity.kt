package com.tlz.guide_example

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import com.tlz.guide.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  private var guideAction: GuideAction? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    Gravity.CENTER

    btn_circle.setOnClickListener {
      guideAction = Guide.from(this)
          .fitsSystemWindows(true)
          .on(it)
          .displayCircle()
          .id(R.id.guide_rect)
          .border()
          .onClickWithDismiss()
          .end()
          .on(btn_round_rect)
          .displayImage()
          .image(R.mipmap.ic_launcher)
          .location(Location.CENTER)
          .animated()
          .showWithAnimate()
    }

    btn_rect.setOnClickListener {
      guideAction = Guide.from(this)
          .fitsSystemWindows(true)
          .on(it)
          .displayRect()
          .id(R.id.guide_rect)
          .onClick {
            Toast.makeText(this, "Click!", Toast.LENGTH_LONG).show()
          }
          .end()
          .displayCustomText("this is a rect")
          .location(Location.START or Location.CENTER_VERTICAL)
          .offsetX(-10)
          .end()
          .showWithAnimate{
            it.setDuration(2000)
                .alpha(1.0f)
          }
    }

    btn_round_rect.setOnClickListener {
      guideAction = Guide.from(this)
          .fitsSystemWindows(true)
          .on(it)
          .displayRoundRect()
          .id(R.id.round_rect)
          .end()
          .displayCustomText(btn_round_rect.text.toString())
          .showWithAnimate()
          .dismissWithAnyWhere()
    }
  }

  private fun ViewActions.displayCustomText(text: String): ViewEditor<TextView> =
      displayText()
          .text(text)
          .location(Location.BOTTOM_INSIDE)
          .textColor(Color.WHITE)
          .textSizeSp(15f)
          .animated()


  override fun onBackPressed() {
    if (guideAction?.onBackPress() != true) {
      super.onBackPressed()
    }
  }

}
