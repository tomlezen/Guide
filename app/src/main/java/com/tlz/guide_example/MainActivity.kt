package com.tlz.guide_example

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
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
          .end()
          .displayText()
//                    .anchor(R.id.guide_rect)
          .text("show a circle")
          .location(Location.BOTTOM or Location.CENTER_HORIZONTAL)
          .textColor(Color.WHITE)
          .textSize(R.dimen.font)
          .offsetY(50)
          .animated()
          .end()
          .on(btn_round_rect)
          .displayImage()
          .image(R.mipmap.ic_launcher)
          .location(Location.CENTER)
          .animated()
          .show()
          .dismissWithAnyWhere()
    }

    btn_rect.setOnClickListener {
      guideAction = Guide.from(this)
          .fitsSystemWindows(true)
          .on(it)
          .displayRect()
          .id(R.id.guide_rect)
          .clickListener {
            Log.e("MainActivity", "onClick event")
          }
          .end()
          .displayCustomText("this is a rect")
          .show()
          .dismissWithView(R.id.guide_rect)
    }

    btn_round_rect.setOnClickListener {
      guideAction = Guide.from(this)
          .fitsSystemWindows(true)
          .on(it)
          .displayRoundRect()
          .end()
          .displayCustomText(btn_round_rect.text.toString())
          .show()
          .dismissWithAnyWhere()
    }
  }

  private fun ViewActions.displayCustomText(text: String): ViewActions =
      displayText()
          .text(text)
          .location(Location.BOTTOM_INSIDE)
          .textColor(Color.WHITE)
          .textSizeSp(15f)
          .animated()
          .end()

  override fun onBackPressed() {
    if (guideAction?.onBackPress() != true) {
      super.onBackPressed()
    }
  }

}
