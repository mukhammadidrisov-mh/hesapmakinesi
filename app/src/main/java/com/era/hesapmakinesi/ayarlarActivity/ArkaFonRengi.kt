package com.era.hesapmakinesi.ayarlarActivity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView


import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.era.hesapmakinesi.R

import com.google.android.material.card.MaterialCardView
import androidx.core.content.edit

@Suppress("DEPRECATION")
class ArkaFonRengi() {

    @SuppressLint("ResourceAsColor")
    fun RenkDeğiştirMainActivity(context: Context, window: Window, activity: Activity, layoutId: Array<Int>){
        val prefs = context.getSharedPreferences("arkaplanrengi", Context.MODE_PRIVATE)
        if (prefs.getString("arkaplanrengi", "") == "Kırmızı") {

            val color = ContextCompat.getColor(context, R.color.redColor)
            window.statusBarColor = color
            for (i in layoutId.indices) {
                val layout = activity.findViewById<View>(layoutId[i])
                layout.setBackgroundColor(color)
            }
        }
        if (prefs.getString("arkaplanrengi", "") == "Yeşil") {

            val color = ContextCompat.getColor(context, R.color.green)
            window.statusBarColor = color
            for (i in layoutId.indices) {
                val layout = activity.findViewById<View>(layoutId[i])
                layout.setBackgroundColor(color)
            }
        }
        if (prefs.getString("arkaplanrengi", "") == "Mavi") {

            val color = ContextCompat.getColor(context, R.color.blue)
            window.statusBarColor = color
            for (i in layoutId.indices) {
                val layout = activity.findViewById<View>(layoutId[i])
                layout.setBackgroundColor(color)
            }
        }
        if (prefs.getString("arkaplanrengi", "") == "Mor") {

            val color = ContextCompat.getColor(context, R.color.mor)
            window.statusBarColor = color
            for (i in layoutId.indices) {
                val layout = activity.findViewById<View>(layoutId[i])
                layout.setBackgroundColor(color)
            }
        }
        if (prefs.getString("arkaplanrengi", "") == "Beyaz") {

            val color = ContextCompat.getColor(context, R.color.white)
            window.statusBarColor = color
            for (i in layoutId.indices) {
                val layout = activity.findViewById<View>(layoutId[i])
                layout.setBackgroundColor(color)
            }
        }
    }
    fun renkDegistir(context: Context, window: Window, activity: Activity, layoutId: Int, textViewId: Int){
        val prefs = context.getSharedPreferences("arkaplanrengi", Context.MODE_PRIVATE)
        if (prefs.getString("arkaplanrengi", "") == "Kırmızı") {

                val color = ContextCompat.getColor(context, R.color.redColor)
                window.statusBarColor = color
                val layout = activity.findViewById<ConstraintLayout>(layoutId)
                layout.setBackgroundColor(color)
                val text = activity.findViewById<TextView>(textViewId)
                text.setTextColor(ContextCompat.getColor(context, R.color.white))


        }

        if (prefs.getString("arkaplanrengi", "") == "Beyaz") {
            val color = ContextCompat.getColor(context, R.color.backgroundColore)
            window.statusBarColor = color
            val layout = activity.findViewById<ConstraintLayout>(layoutId)
            layout.setBackgroundColor(color)
            val text = activity.findViewById<TextView>(textViewId)
            text.setTextColor(ContextCompat.getColor(context, R.color.black))
        }
        if (prefs.getString("arkaplanrengi", "") == "Mavi") {
            val color = ContextCompat.getColor(context, R.color.blue)
            window.statusBarColor = color
            val layout = activity.findViewById<ConstraintLayout>(layoutId)
            layout.setBackgroundColor(color)
            val text = activity.findViewById<TextView>(textViewId)
            text.setTextColor(ContextCompat.getColor(context, R.color.white))
        }
        if (prefs.getString("arkaplanrengi", "") == "Yeşil") {
            val color = ContextCompat.getColor(context, R.color.green)
            window.statusBarColor = color
            val layout = activity.findViewById<ConstraintLayout>(layoutId)
            layout.setBackgroundColor(color)
            val text = activity.findViewById<TextView>(textViewId)
            text.setTextColor(ContextCompat.getColor(context, R.color.white))
        }
        if (prefs.getString("arkaplanrengi", "") == "Mor") {
            val color = ContextCompat.getColor(context, R.color.mor)
            window.statusBarColor = color
            val layout = activity.findViewById<ConstraintLayout>(layoutId)
            layout.setBackgroundColor(color)
            val text = activity.findViewById<TextView>(textViewId)
            text.setTextColor(ContextCompat.getColor(context, R.color.white))
        }
        }

    fun RenkCardView(context: Context, window: Window, activity: AyarlarActivity,cardViewId: Int, color: String) {
        val prefs = context.getSharedPreferences("arkaplanrengi", Context.MODE_PRIVATE)
        activity.findViewById<CardView>(cardViewId).setOnClickListener {

            prefs.edit() { putString("arkaplanrengi", color) }
            renkDegistir(context, window, activity, R.id.ayarlarConstrainLayout, R.id.ayarlartextView3)
        }
    }

    fun bosTiklama(cardViewId: Int,activity: Activity){
        activity.findViewById<CardView>(cardViewId).setOnClickListener {

        }
    }
    @SuppressLint("ResourceType")
    fun setClickandTextColor(context: Context, activity: Activity, cardViewId: Array<String>, snumbers: Array<String>) {
        val prefs = context.getSharedPreferences("arkaplanrengi", Context.MODE_PRIVATE)
        if (prefs.getString("arkaplanrengi", "") == "Kırmızı") {
            for (i in cardViewId.indices) {
                val id = context.resources.getIdentifier(cardViewId[i], "id", context.packageName)
                val cardView = activity.findViewById<MaterialCardView>(id)
                cardView.setRippleColorResource(R.color.redColor)


            }
            for (i in snumbers.indices) {
                val idN = context.resources.getIdentifier(snumbers[i], "id", context.packageName)
                val numbers = activity.findViewById<TextView>(idN)
                numbers.setTextColor(ContextCompat.getColor(context, R.color.redColor))

            }
            activity.findViewById<EditText>(R.id.editTextText).setTextColor(ContextCompat.getColor(context, R.color.white))
    }
        if (prefs.getString("arkaplanrengi", "") == "Mavi") {
            for (i in cardViewId.indices) {
                val id = context.resources.getIdentifier(cardViewId[i], "id", context.packageName)
                val cardView = activity.findViewById<MaterialCardView>(id)
                cardView.setRippleColorResource(R.color.blue)

            }
            for (i in snumbers.indices) {
                val idN = context.resources.getIdentifier(snumbers[i], "id", context.packageName)
                val numbers = activity.findViewById<TextView>(idN)
                numbers.setTextColor(ContextCompat.getColor(context, R.color.blue))

            }
            activity.findViewById<EditText>(R.id.editTextText).setTextColor(ContextCompat.getColor(context, R.color.white))
        }
        if (prefs.getString("arkaplanrengi", "") == "Yeşil") {
            for (i in cardViewId.indices) {
                val id = context.resources.getIdentifier(cardViewId[i], "id", context.packageName)
                val cardView = activity.findViewById<MaterialCardView>(id)
                cardView.setRippleColorResource(R.color.green)

            }
            for (i in snumbers.indices) {
                val idN = context.resources.getIdentifier(snumbers[i], "id", context.packageName)
                val numbers = activity.findViewById<TextView>(idN)
                numbers.setTextColor(ContextCompat.getColor(context, R.color.green))

            }
            activity.findViewById<EditText>(R.id.editTextText).setTextColor(ContextCompat.getColor(context, R.color.white))
        }
        if (prefs.getString("arkaplanrengi", "") == "Mor") {
            for (i in cardViewId.indices) {
                val id = context.resources.getIdentifier(cardViewId[i], "id", context.packageName)
                val cardView = activity.findViewById<MaterialCardView>(id)
                cardView.setRippleColorResource(R.color.mor)

            }
            for (i in snumbers.indices) {
                val idN = context.resources.getIdentifier(snumbers[i], "id", context.packageName)
                val numbers = activity.findViewById<TextView>(idN)
                numbers.setTextColor(ContextCompat.getColor(context, R.color.mor))

            }
            activity.findViewById<EditText>(R.id.editTextText).setTextColor(ContextCompat.getColor(context, R.color.white))
        }

}}