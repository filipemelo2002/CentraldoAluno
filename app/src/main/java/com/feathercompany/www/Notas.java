package com.feathercompany.www;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

import com.feathercompany.www.Adapters.MyNotasAdapter;
import com.feathercompany.www.InternalDB.TabelaNotas;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class Notas extends AppCompatActivity
{
	private MyNotasAdapter adapter;
	private List<DataSet> data;
	private int boletinId;
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {

		super.onCreate ( savedInstanceState ) ;
		setContentView ( R.layout.showdata ) ;

		AdsHandler adsHandler = new AdsHandler(this);
		AdView mAdView = findViewById(R.id.adView);
		mAdView.loadAd(adsHandler.getAdRequest());

		TabelaNotas bd = new TabelaNotas(this);
		bd.createDB();
		Intent intent = getIntent();
        boletinId = intent.getExtras().getInt("boletinId");
        int positionBoletin  =  intent.getExtras().getInt("boletinPosition");
		if(intent.hasExtra("apiResponse")){
			String response  = intent.getExtras().getString("apiResponse");
            bd.clearTable();
            if(bd.isThereItensAlready(positionBoletin)){
                bd.autalizarInformacao(positionBoletin, response);
            }else{
                bd.salvarInformacao(response, positionBoletin);
            }
        }else{
			Toast.makeText(this,"Você está vendo os dados em modo offline!",Toast.LENGTH_LONG).show();
		}
		ListView lista = findViewById(R.id.notasList);
        data = bd.getAllData(positionBoletin);
        if(data.size()==0){
            lista.setVisibility(View.INVISIBLE);
            ImageView img = findViewById(R.id.notfound);
            img.setVisibility(View.VISIBLE);
        }
		adapter = new MyNotasAdapter(this,data);
		bd.disconnectDB();
		lista.setAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.search_view, menu);
		MenuItem item = menu.findItem(R.id.sv);
		android.widget.SearchView searchView = (android.widget.SearchView) item.getActionView();
		searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener(){

				@Override
				public boolean onQueryTextSubmit(String p1)
				{
					// TODO: Implement this method
					return false;
				}

				@Override
				public boolean onQueryTextChange(String p1)
				{
					adapter.filter(p1);
					
					
					return false;
				}
		}
		);
		return super.onCreateOptionsMenu(menu);
		
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if(item.getItemId()==R.id.copy){
			if(!data.isEmpty()){
				ClipboardCopyHandler handler = new ClipboardCopyHandler(this,data);
				handler.saveDataToClipboard();
			}else{
				Toast.makeText(this, "Não há nada para copiar ;/", Toast.LENGTH_SHORT).show();
			}

		}
		return true;
	}
}
