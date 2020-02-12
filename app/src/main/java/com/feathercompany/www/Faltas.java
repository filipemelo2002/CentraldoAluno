package com.feathercompany.www;
import android.content.Intent;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.widget.*;
import org.json.*;
import android.support.v7.app.*;

import com.feathercompany.www.Adapters.MyFaltasAdapter;
import com.feathercompany.www.InternalDB.TabelaFaltas;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.*;

public class Faltas extends AppCompatActivity
{

	@Override
    protected void onCreate(Bundle savedInstanceState)
    {

		super.onCreate ( savedInstanceState ) ;
		setContentView ( R.layout.faltashow) ;

		AdsHandler adsHandler = new AdsHandler(this);
		AdView mAdView = findViewById(R.id.adView);
		mAdView.loadAd(adsHandler.getAdRequest());

	    ListView lista = findViewById(R.id.lisId);
		TabelaFaltas bd = new TabelaFaltas(this);
		bd.createDB();
		Intent intent = getIntent();
		int boletinId = intent.getExtras().getInt("boletinId");
		int positionBoletin  =  intent.getExtras().getInt("boletinPosition");
		if(intent.hasExtra("apiResponse")){
			String response  = intent.getExtras().getString("apiResponse");
			bd.clearTable();
			if(bd.isThereItemAlready(positionBoletin)){
				bd.updateItemAtPosition(positionBoletin, response);
			}else{
				bd.salvarInformacoes(positionBoletin, response);
			}
		}else{
			Toast.makeText(this,"Você está vendo os dados em modo offline!",Toast.LENGTH_LONG).show();
		}
		List<Float> f = bd.getPercentList(positionBoletin);

		if(f.size()>0){

			MyFaltasAdapter adapter = new MyFaltasAdapter(this, f,positionBoletin);
			lista.setAdapter(adapter);




		}else{
			lista.setVisibility(View.INVISIBLE);
			ImageView img = findViewById(R.id.notfound);
			img.setVisibility(View.VISIBLE);
			Toast.makeText(this, "Ops, seus dados não foram obtidos", Toast.LENGTH_SHORT).show();
		}

		bd.disconnectDB();




	}
		

	
}
