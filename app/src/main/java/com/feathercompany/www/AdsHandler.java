package com.feathercompany.www;

import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;


public class AdsHandler {
    private Context context;

    private final String ADS_APP_KEY = "ca-app-pub-4776387538701328~5824324554";

    private final String MAIN_BANNER = "ca-app-pub-4776387538701328/5821921139";

    private final AdSize ADS_BANNER_SIZE  = AdSize.BANNER;

    private AdView adView;
    private InterstitialAd mInterstitialAd;
    private AdRequest adRequest;
    public AdsHandler(Context context){
        this.context = context;
        MobileAds.initialize(context, ADS_APP_KEY );

        this.adRequest = new AdRequest.Builder().addTestDevice("D178CF3ABCC885ABC4368AA7FAD41847").build();

    }

    public AdRequest getAdRequest(){
        return adRequest;
    }
    public AdView loadMainActivityBanner(){
        adView = new AdView(context);
        adView.setAdSize(ADS_BANNER_SIZE);
        adView.setAdUnitId(MAIN_BANNER);
        adView.loadAd(adRequest);
        return adView;
    }
    public AdView loadBanner(String key){
        adView = new AdView(context);
        adView.setAdSize(ADS_BANNER_SIZE);
        adView.setAdUnitId(key);
        adView.loadAd(adRequest);
        return adView;
    }

    public InterstitialAd loadInterstitialAds(String key){
        mInterstitialAd  = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(key);
        mInterstitialAd.loadAd(adRequest);

        return this.mInterstitialAd;
    }
}
