package com.era.hesapmakinesi

import android.app.Activity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class MobileAdsInterstitial() {
    private var mInterstitialAd: InterstitialAd? = null

    fun intializeAd(activity: Activity){
        MobileAds.initialize(activity) {}
        showInterstitial(activity)
    }
    fun showInterstitial(activity: Activity){
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            activity,
            "ca-app-pub-8392812267777981/8500194124",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    mInterstitialAd = ad

                    mInterstitialAd?.show(activity) // Reklam hemen gösteriliyor
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {

                    mInterstitialAd = null
                }
            })
    }
}
