package com.era.hesapmakinesi.mainActivity


import Combinations
import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.graphics.toColorInt
import androidx.core.view.WindowCompat
import com.era.hesapmakinesi.MenuFragment
import com.era.hesapmakinesi.R
import com.era.hesapmakinesi.active
import com.era.hesapmakinesi.ayarlarActivity.ArkaFonRengi
import com.era.hesapmakinesi.ayarlarActivity.GeceModuİslemleri
import com.era.hesapmakinesi.editText
import com.era.hesapmakinesi.editText2
import com.era.hesapmakinesi.editText3
import com.era.hesapmakinesi.editText5
import com.era.hesapmakinesi.history
import com.era.hesapmakinesi.islemTuru
import com.era.hesapmakinesi.logoBtnCardView
import com.era.hesapmakinesi.numbers
import com.era.hesapmakinesi.operators
import com.era.hesapmakinesi.resultv
import com.era.hesapmakinesi.sayi
import com.era.hesapmakinesi.speechBtnCardView

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    //İşlem Cartları Değişkenleri
    lateinit var  plusCardView: CardView
    lateinit var  minusCardView: CardView
    lateinit var  equalCardView: CardView
    lateinit var  acCardView: CardView
    lateinit var multiplicationCardView: CardView
    lateinit var devideCardView: CardView
    lateinit var persantageCardView: CardView
    lateinit var squareCardView: CardView

    private lateinit var geceModuSharedPreference: SharedPreferences

    @SuppressLint("CutPasteId", "ResourceType")
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        overridePendingTransition(0, 0)


        val sharedPreferences = getSharedPreferences("history", MODE_PRIVATE)
        history.add(sharedPreferences.getString("history", MODE_PRIVATE.toString()).toString())


        val snumbers=arrayOf("pointtextView","equaltextView","minustextView","plustextView","multiplicationtextView","bolmetextView","yuzdetextView","koktextView","actextView","seventextView","n8textView","n9textView","fourtextView","n5textView","n6textView","onetextView","n2textView","n3textView","zerotextView")
        val CardViews=arrayOf("n1CardView","n2CardView","n3CardView","n4CardView","n5CardView","n6CardView","n7CardView","n8CardView","n9CardView","n0CardView","setBackgroundColorCardView","pointCardView","squarerootCardView","divisionCardView","multiplicationCardView","plusCardView","minusCardView","equalCardView")
        ArkaFonRengi().setClickandTextColor(this,this,CardViews,snumbers)





        //Dış Classlar
        val SpeechToCalculate= SpeechToCalculate()
        val Combinations = Combinations(this)



        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView) ?: return
        windowInsetsController.isAppearanceLightStatusBars = true








        geceModuSharedPreference=getSharedPreferences("gecemodu", MODE_PRIVATE)
        val geceModuAktifMi=geceModuSharedPreference.getBoolean("gecemodu",false)
       if (geceModuAktifMi==true){
           window.statusBarColor = "#121212".toColorInt() // Status bar rengi
           GeceModuİslemleri().isGeceModuAktifMainActivity(this)
       }
       else{
           window.statusBarColor = "#F7F8FA".toColorInt() // Status bar rengi
           val arrayOfLayouts= arrayOf(R.id.main,R.id.linearLayout,R.id.mainLinearLayout)
           ArkaFonRengi().RenkDeğiştirMainActivity(this,window,this,arrayOfLayouts )
       }





        Animations()

        speechBtnCardView =findViewById<CardView>(R.id.speechCardView)
        speechBtnCardView.setOnClickListener {
            SpeechToCalculate.startSpeechToText(this)
        }

        logoBtnCardView =findViewById<CardView>(R.id.eraplusCardView)
        logoBtnCardView.setOnClickListener {
            val fragment= MenuFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.menu_frameLayout, fragment)
                .addToBackStack(null)
                .commit()
        }

        editText = findViewById(R.id.editTextText)
        editText.addTextChangedListener(object : TextWatcher {
            var isFormatting = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isFormatting) return
                isFormatting = true

                s?.let {
                    val raw = it.toString().replace(" ", "") // Tüm boşlukları sil

                    // Sayılar ve işlem sembollerini sırayla ayır
                    val parts = Regex("(\\d+(?:\\.\\d+)?|[+\\-×÷%,])")
                        .findAll(raw)
                        .map { it.value }
                        .toList()

                    val formattedParts = parts.map { part ->
                        if (part.matches(Regex("\\d+"))) {
                            formatNumber(part) // Boşluk bırakmayacak
                        } else {
                            part
                        }
                    }

                    val final = formattedParts.joinToString("").trim()

                    editText.setText(final)
                    editText.setSelection(final.length)
                }

                isFormatting = false
            }

            private fun formatNumber(number: String): String {
                return number // Boşluk ekleme yok
            }
        })





        editText2 = findViewById(R.id.editTextText2)

        editText3 = findViewById(R.id.editTextText)

        editText5 = findViewById<EditText>(R.id.editTextText5)



        onNumberButtonClick()





        //İşlem Cartları
        plusCardView= findViewById(R.id.plusCardView)
        plusCardView.setOnClickListener {
               Combinations.plusCombination()
           }


        minusCardView=findViewById<CardView>(R.id.minusCardView)
        minusCardView.setOnClickListener {
    Combinations.minusCombination()
}

        equalCardView=findViewById<CardView>(R.id.equalCardView)
        equalCardView.setOnClickListener {
            Combinations.equalsCombination(this)
        }

        acCardView=findViewById(R.id.setBackgroundColorCardView)
        acCardView.setOnClickListener {
            islemTuru = ""
            sayi = ""
            resultv = 0
            editText.setText("")
            editText2.setText("")
            numbers.clear()
            operators.clear()


        }

        multiplicationCardView=findViewById(R.id.multiplicationCardView)
        multiplicationCardView.setOnClickListener {
            Combinations.multiplyCombination()
        }

        devideCardView=findViewById(R.id.divisionCardView)
        devideCardView.setOnClickListener {
            Combinations.divideCombination()
        }

        persantageCardView=findViewById(R.id.persentageCardView)
        persantageCardView.setOnClickListener {
            Combinations.persentageCombination()
        }

        squareCardView=findViewById(R.id.squarerootCardView)
        squareCardView.setOnClickListener {
            Combinations.clearCombination()
        }


    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        SpeechToCalculate().onActivityResult(requestCode,resultCode,data)
    }

    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    fun Animations() {

        com.era.hesapmakinesi.Animations(this).slideUP(1000L,R.id.firstLinearLayout)
        com.era.hesapmakinesi.Animations(this).slideUP(1000L,R.id.secondLinearLayout)
        com.era.hesapmakinesi.Animations(this).slideUP(1000L,R.id.thirdLinearLayout)
        com.era.hesapmakinesi.Animations(this).slideUP(1000L,R.id.fourLinearLayout)
        com.era.hesapmakinesi.Animations(this).slideUP(1000L,R.id.fiveLinearLayout)

    }



    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    @SuppressLint("SuspiciousIndentation")
    private fun onNumberButtonClick() {
        val cardViewIds = arrayOf(
            R.id.n1CardView,
            R.id.n2CardView,
            R.id.n3CardView,
            R.id.n4CardView,
            R.id.n5CardView,
            R.id.n6CardView,
            R.id.n7CardView,
            R.id.n8CardView,
            R.id.n9CardView,
            R.id.n0CardView
        )
        for (i in cardViewIds.indices) {
            val view = findViewById<CardView>(cardViewIds[i])
            var index = i + 1
            if (index == 10) {
                index = 0
            }
            view.setOnClickListener {
                // Her rakam düğmesi için onClick metoduna ekleyin



// Diğer rakamlar için de benzer kodlar...
                if (active == true) {
                    editText.setText("")
                    active = false
                }

                editText2.visibility = View.VISIBLE
                findViewById<EditText>(R.id.editTextText5).visibility = View.GONE
                Combinations(this).numberPressed(index.toString())

            }

        }

        val pointCardView = findViewById<CardView>(R.id.pointCardView)
        pointCardView.setOnClickListener {
            if (editText.text.isNotEmpty()) {
                editText.append(','.toString())
                sayi = sayi + '.'
                editText2.visibility = View.VISIBLE
                findViewById<EditText>(R.id.editTextText5).visibility = View.GONE
            }else{
                Toast.makeText(this,"Sayı Giriniz",Toast.LENGTH_SHORT).show()
            }
        }
    }


}