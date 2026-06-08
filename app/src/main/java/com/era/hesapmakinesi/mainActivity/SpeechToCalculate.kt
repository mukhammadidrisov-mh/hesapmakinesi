package com.era.hesapmakinesi.mainActivity

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.speech.RecognizerIntent
import androidx.annotation.RequiresApi
import androidx.compose.ui.text.intl.Locale
import androidx.core.content.ContextCompat.getString
import com.era.hesapmakinesi.R
import com.era.hesapmakinesi.editText
import com.era.hesapmakinesi.editText2
import com.era.hesapmakinesi.editText3
import com.era.hesapmakinesi.harfler
import com.era.hesapmakinesi.islemTuru
import com.era.hesapmakinesi.numbers
import com.era.hesapmakinesi.operators
import com.era.hesapmakinesi.resultv
import com.era.hesapmakinesi.sayi
import net.objecthunter.exp4j.ExpressionBuilder

@Suppress("DEPRECATION")
class SpeechToCalculate{

    val SPEECH_REQUEST_CODE = 0
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        islemTuru = ""
        sayi = ""
        resultv = 0
        editText.setText("")
        editText2.setText("")
        numbers.clear()
        operators.clear()
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            result?.let {
                var spokenText = it[0].lowercase()

                // 1. Temizlik
                spokenText = spokenText.replace("x", "*")
                spokenText = spokenText.replace("çarpı", "*").replace("Çarpı", "*")
                spokenText = spokenText.replace("artı", "+").replace("Artı", "+")
                spokenText = spokenText.replace("eksi", "-").replace("Eksi", "-")
                spokenText = spokenText.replace("bölü", "/").replace("Bölü", "/")

                spokenText = spokenText.replace(".", "")
                spokenText = spokenText.replace(",", ".") // Virgülü noktaya çevir
                spokenText = spokenText.replace(" ", "") // 🔥 Boşlukları tamamen kaldır

                // 2. Gereksiz harfleri sil
                for (harf in harfler) {
                    spokenText = spokenText.replace(harf, "")
                }

                try {
                    val expression = ExpressionBuilder(spokenText).build()
                    val resultValue = expression.evaluate()

                    val resultText = if (resultValue % 1f == 0f.toDouble()) {
                        resultValue.toInt().toString()
                    } else {
                        resultValue.toString()
                    }

                    editText3.setText(resultText.replace('.', ',')) // Türkçe gösterim
                    sayi += resultText.replace(',', '.')
                } catch (_: Exception) {

                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun startSpeechToText(activity: MainActivity) {
        val speakText=getString(activity, R.string.speak_Text)
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.Companion.current.toString())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, speakText)
        intent.putExtra(RecognizerIntent.EXTRA_AUDIO_INJECT_SOURCE, "")




        try {

            activity.startActivityForResult(intent, com.era.hesapmakinesi.SPEECH_REQUEST_CODE)
        } catch (_: Exception) {

        }


    }

    }