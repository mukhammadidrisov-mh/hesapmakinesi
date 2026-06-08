package com.era.hesapmakinesi.ayarlarActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.graphics.toColorInt
import androidx.core.view.WindowCompat
import com.era.hesapmakinesi.R
import com.era.hesapmakinesi.databinding.ActivityAyarlarBinding
import com.era.hesapmakinesi.mainActivity.MainActivity
import androidx.core.view.isVisible

lateinit var arkaPlanRengiSharedPreference: SharedPreferences
@Suppress("DEPRECATION")
class AyarlarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAyarlarBinding
    private lateinit var geceModuSharedPreference: SharedPreferences

    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAyarlarBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val appVersion = ": 1.4"
        binding.versiontextView.append(appVersion)


        val windowInsetsController =
            WindowCompat.getInsetsController(window, window.decorView) ?: return
        windowInsetsController.isAppearanceLightStatusBars = true




        geceModuSharedPreference = getSharedPreferences("gecemodu", MODE_PRIVATE)
        val geceModuAktifMi = geceModuSharedPreference.getBoolean("gecemodu", false)
        if (geceModuAktifMi == true) {
            window.statusBarColor = "#121212".toColorInt() // Status bar rengi
            binding.gecemoduswitch.isChecked = true

            GeceModuİslemleri().isGeceModuAktifAyarlarActivity(
                this,
                geceModuSharedPreference.getBoolean("gecemodu", false)
            )

        } else {
            window.statusBarColor = "#F7F8FA".toColorInt() // Status bar rengi
            binding.gecemoduswitch.isChecked = false
            ArkaFonRengi().RenkCardView(this, window, this, R.id.redColorCardView, "Kırmızı")
            ArkaFonRengi().RenkCardView(this, window, this, R.id.whiteColorCardView, "Beyaz")
            ArkaFonRengi().RenkCardView(this, window, this, R.id.blueColorCardView, "Mavi")
            ArkaFonRengi().RenkCardView(this, window, this, R.id.greenColorCardView, "Yeşil")
            ArkaFonRengi().RenkCardView(this, window, this, R.id.morColorCardView, "Mor")
            ArkaFonRengi().renkDegistir(
                this,
                window,
                this,
                R.id.ayarlarConstrainLayout,
                R.id.ayarlartextView3
            )
        }


        binding.gecemoduswitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (binding.gecemoduswitch.isChecked) {
                geceModuSharedPreference
                    .edit() {
                        putBoolean("gecemodu", true)
                    }
                window.statusBarColor = "#121212".toColorInt() // Status bar rengi
                GeceModuİslemleri().isGeceModuAktifAyarlarActivity(
                    this,
                    geceModuSharedPreference.getBoolean("gecemodu", false)
                )
                ArkaFonRengi().bosTiklama(R.id.redColorCardView, this)
                ArkaFonRengi().bosTiklama(R.id.whiteColorCardView, this)
                ArkaFonRengi().bosTiklama(R.id.morColorCardView, this)
                ArkaFonRengi().bosTiklama(R.id.blueColorCardView, this)
                ArkaFonRengi().bosTiklama(R.id.greenColorCardView, this)
            } else if (binding.gecemoduswitch.isChecked == false) {

                geceModuSharedPreference.edit() { putBoolean("gecemodu", false) }
                window.statusBarColor = "#F7F8FA".toColorInt() // Status bar rengi
                GeceModuİslemleri().isGeceModuAktifAyarlarActivity(
                    this,
                    geceModuSharedPreference.getBoolean("gecemodu", false)
                )
                ArkaFonRengi().RenkCardView(this, window, this, R.id.redColorCardView, "Kırmızı")
                ArkaFonRengi().RenkCardView(this, window, this, R.id.whiteColorCardView, "Beyaz")
                ArkaFonRengi().RenkCardView(this, window, this, R.id.blueColorCardView, "Mavi")
                ArkaFonRengi().RenkCardView(this, window, this, R.id.greenColorCardView, "Yeşil")
                ArkaFonRengi().RenkCardView(this, window, this, R.id.morColorCardView, "Mor")
                ArkaFonRengi().renkDegistir(
                    this,
                    window,
                    this,
                    R.id.ayarlarConstrainLayout,
                    R.id.ayarlartextView3
                )

            }
        }



        arkaPlanRengiSharedPreference = getSharedPreferences("arkaplanrengi", MODE_PRIVATE)








        binding.setBackgroundColorCardView.setOnClickListener {
            if (binding.setBackColorCardView.isVisible) {
                binding.setBackColorCardView.visibility = View.GONE
            } else {
                binding.setBackColorCardView.visibility = View.VISIBLE
            }


        }
    }



        @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
        override fun onBackPressed() {
            super.onBackPressed()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
