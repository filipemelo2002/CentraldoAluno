package com.feathercompany.www;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import com.feathercompany.www.Adapters.BoletimAdapter;
import com.feathercompany.www.Adapters.FaltasBoletimAdapter;
import com.feathercompany.www.InternalDB.TabelaBoletins;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class FaltasBoletim extends AppCompatActivity {


    private List<Boletim> dataset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faltas_boletim);

        AdsHandler adsHandler = new AdsHandler(this);
        AdView mAdView = findViewById(R.id.adView);
        mAdView.loadAd(adsHandler.getAdRequest());


        ListView list = findViewById(R.id.faltas);
        TabelaBoletins boletins = new TabelaBoletins(this);
        boletins.createDB();
        Intent intent = getIntent();
        if(intent.hasExtra("apiResponse")){
            try {
                boletins.clearTable();
                JSONArray myArray = new JSONArray(intent.getExtras().getString("apiResponse"));
                for(int i =0; i<myArray.length(); i++){
                    JSONObject obj = myArray.getJSONObject(i);
                    String label = obj.getString("label");
                    int ano = obj.getInt("ano");
                    int boletimId = obj.getInt("boletimId");
                    if(boletins.isThereItensAlready((i+1))){
                        boletins.autalizarInformacao(label, ano, boletimId,(i+1));
                    }else{
                        boletins.salvarInformacao(label, ano, boletimId, (i+1));
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(this,"Você está vendo os dados em modo offline!",Toast.LENGTH_LONG).show();
        }
        dataset = boletins.getBoletimsData();
        boletins.disconnectDB();

        if(dataset.size()>0){
            FaltasBoletimAdapter adapter = new FaltasBoletimAdapter(this, dataset);
            list.setAdapter(adapter);
        }else{
            list.setVisibility(View.INVISIBLE);
            ImageView img = findViewById(R.id.notfound);
            img.setVisibility(View.VISIBLE);
        }



    }
}
