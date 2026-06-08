package com.era.hesapmakinesi

import android.app.Activity
import android.view.animation.OvershootInterpolator
import android.widget.LinearLayout
import androidx.cardview.widget.CardView

class Animations(val activity: Activity) {

    val activityy=activity

    fun slideUP(duration: Long = 1000L,content: Int) {

        val contentView=activityy.findViewById<LinearLayout>(content)
        contentView.post {
            contentView.translationY = contentView.height.toFloat()
            contentView.alpha = 0f
            contentView.animate()
                .translationY(0f)
                .alpha(1f)
                .setInterpolator(OvershootInterpolator())
                .setDuration(duration)
                .start()
        }
    }
    fun slideUPCardView(duration: Long = 300L, contentViewCard: Int) {
        val contentView=activityy.findViewById<CardView>(contentViewCard)
        contentView.post {

            contentView.alpha = 0.80f
            contentView.animate()

                .alpha(1f)
                .setInterpolator(OvershootInterpolator())
                .setDuration(duration)
                .start()
        }
    }
}
