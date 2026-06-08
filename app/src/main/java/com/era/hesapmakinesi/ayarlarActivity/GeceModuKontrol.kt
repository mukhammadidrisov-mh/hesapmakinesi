package com.era.hesapmakinesi.ayarlarActivity
import android.annotation.SuppressLint
import com.era.hesapmakinesi.R
import android.app.Activity
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class GeceModuİslemleri {
    fun isGeceModuAktifMainActivity(activity: Activity){
        activity.findViewById<ConstraintLayout>(R.id.main).setBackgroundResource(R.color.nightModeBackgroundColor)
        activity.findViewById<LinearLayout>(R.id.mainLinearLayout).setBackgroundResource(R.color.nightModeBackgroundColor)
        activity.findViewById<EditText>(R.id.editTextText).setTextColor(activity.getColor(R.color.white))

}
    fun isGeceModuAktifAyarlarActivity(activity: Activity, isGeceModuActive: Boolean){
        if (isGeceModuActive==true) {
            activity.findViewById<ConstraintLayout>(R.id.ayarlarConstrainLayout)
                .setBackgroundResource(R.color.nightModeBackgroundColor)
            activity.findViewById<TextView>(R.id.ayarlartextView3)
                .setTextColor(activity.getColor(R.color.white))
        }
        else{
            activity.findViewById<ConstraintLayout>(R.id.ayarlarConstrainLayout)
                .setBackgroundResource(R.color.dayModeBackgroundColor)
            activity.findViewById<TextView>(R.id.ayarlartextView3)
                .setTextColor(activity.getColor(R.color.black))
        }
    }
    @SuppressLint("ResourceAsColor")
    fun isGeceModuAktifHistoryActivity(activity: Activity, isGeceModuActive: Boolean){
        if (isGeceModuActive==true) {
            activity.findViewById<ConstraintLayout>(R.id.historyConstrainLayout)
                .setBackgroundResource(R.color.nightModeBackgroundColor)
              activity.findViewById<TextView>(R.id.historytextView).setTextColor(activity.getColor(R.color.white))
            activity.findViewById<ListView>(R.id.listView).setBackgroundResource(R.color.nightModeBackgroundColor)



        }
}}
