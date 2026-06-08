package com.era.hesapmakinesi

import android.annotation.SuppressLint
import android.widget.EditText
import androidx.cardview.widget.CardView

var harfler = arrayOf(
    "a",
    "b",
    "c",
    "ç",
    "d",
    "e",
    "f",
    "g",
    "ğ",
    "h",
    "ı",
    "i",
    "j",
    "k",
    "l",
    "m",
    "n",
    "o",
    "ö",
    "p",
    "r",
    "s",
    "ş",
    "t",
    "u",
    "ü",
    "v",
    "y",
    "z",
    "=",
    "'"
)
@SuppressLint("StaticFieldLeak")
lateinit var editText: EditText
@SuppressLint("StaticFieldLeak")
lateinit var editText2: EditText
@SuppressLint("StaticFieldLeak")
lateinit var editText3: EditText
@SuppressLint("StaticFieldLeak")
lateinit var editText5: EditText
lateinit var speechBtnCardView : CardView
lateinit var logoBtnCardView : CardView

val SPEECH_REQUEST_CODE = 0
var active = false
var resultv = 0
var sayi = ""
var islemTuru = ""
val numbers = mutableListOf<Double>()
val operators = mutableListOf<Char>()

val history= mutableListOf<String>()
