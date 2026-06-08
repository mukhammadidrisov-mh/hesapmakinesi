package com.era.hesapmakinesi

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.graphics.toColorInt
import com.era.hesapmakinesi.ayarlarActivity.GeceModuİslemleri

@Suppress("DEPRECATION")
class HistoryActivty: AppCompatActivity() {
    private lateinit var geceModuSharedPreference: SharedPreferences
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history_activity)
        geceModuSharedPreference = getSharedPreferences("gecemodu", MODE_PRIVATE)
        val geceModuAktifMi = geceModuSharedPreference.getBoolean("gecemodu", false)

        val sharedPreferences = getSharedPreferences("history", MODE_PRIVATE)
        val objects = sharedPreferences.getString("history", "")

        val items = (objects?.split(",","[","]")?.map { it.trim() }?.filter { it.isNotEmpty()&& it != "0" } ?: listOf()).toMutableList()

        val listView = findViewById<ListView>(R.id.listView)
        val adapter = object : ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items) {
            @SuppressLint("SuspiciousIndentation")
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val textView = view.findViewById<TextView>(android.R.id.text1)
                if (geceModuAktifMi==true)
                textView.setTextColor(Color.WHITE) 
                return view
            }
        }

        listView.adapter = adapter

        window.statusBarColor = "#F7F8FA".toColorInt()

        val deleteHistoryBtn=findViewById<ImageView>(R.id.deleteHistoryButton)
        deleteHistoryBtn.setOnClickListener {
            
            items.clear()
            adapter.notifyDataSetChanged()
            history.clear()
            sharedPreferences.edit() { remove("history") }

        }

        if (geceModuAktifMi == true) {
            window.statusBarColor = "#121212".toColorInt()

            GeceModuİslemleri().isGeceModuAktifHistoryActivity(
                this,
                geceModuSharedPreference.getBoolean("gecemodu", false)
            )

        }
    }
}
