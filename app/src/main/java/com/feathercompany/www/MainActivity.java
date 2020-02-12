package com.feathercompany.www;
import android.app.*;
import android.os.*;
import android.view.*;
import android.graphics.*;
import android.widget.*;
import android.content.*;
import android.support.v7.app.*;

import com.feathercompany.www.ApiRequest.RequestInterface;
import com.feathercompany.www.ApiRequest.RestApi;
import com.feathercompany.www.InternalDB.BDhandler;
import com.feathercompany.www.InternalDB.TabelaUserDados;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{

	private String userToken;
	private String INTERTITIAL_ADS = "ca-app-pub-4776387538701328/2155791258";
	private InterstitialAd mInterstitialAd;
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {

		super.onCreate ( savedInstanceState ) ;
		setContentView ( R.layout.main ) ;
		FirebaseMessaging.getInstance().subscribeToTopic("all");





		TextView welcome = findViewById(R.id.welcome);
		Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Aller/Aller_Bd.ttf");
		welcome.setTypeface(typeface);


		AdsHandler adsHandler = new AdsHandler(this);

		AdView adView = adsHandler.loadMainActivityBanner();
		mInterstitialAd = adsHandler.loadInterstitialAds(INTERTITIAL_ADS);
		LinearLayout ll =findViewById(R.id.root);
		ll.addView(adView);


		TabelaUserDados bd = new TabelaUserDados(this);
		bd.createDB();
		userToken = bd.getUserToken();
		bd.disconnectDB();
		if(userToken==null){
			Intent intent = new Intent(this, Login.class);
			startActivity(intent);
			finish();
		}
	}


	public void btn1(View view){

		if (mInterstitialAd.isLoaded()) {
			mInterstitialAd.show();
		}

		RestApi request = new RestApi(this, view);
		final ProgressDialog dialog = ProgressDialog.show(this, "Carregando os dados", "Por favor, aguarde...", true);
		RequestInterface service = request.request();
		service.boletins(userToken).enqueue(new Callback<String>() {
			@Override
			public void onResponse(Call<String> call, Response<String> response) {

				if(response.isSuccessful()&&response.code() != 400){
					String answer = response.body();
					Intent intent = new Intent(MainActivity.this, BoletimList.class);
					intent.putExtra("apiResponse", answer);
					startActivity(intent);
				}else{
					BDhandler bd = new BDhandler(getBaseContext());
					bd.criarBD();
					bd.clearTables();
					bd.disconnect();
					Intent intent = new Intent(MainActivity.this, Login.class);
					startActivity(intent);
					SharedPreferences.Editor prefsEditor = getSharedPreferences("app_prefs", 0).edit();
					prefsEditor.clear();
					prefsEditor.apply();
					Toast.makeText(getApplicationContext(), response.body(), Toast.LENGTH_SHORT).show();
				}

				dialog.dismiss();
			}

			@Override
			public void onFailure(Call<String> call, Throwable t) {
				//Toast.makeText(getApplicationContext(),"Conexão com o servidor não estabelecida.", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(MainActivity.this, BoletimList.class);
				startActivity(intent);

				dialog.dismiss();
			}
		});


	}
	public void btn2(View view){

		if (mInterstitialAd.isLoaded()) {
			mInterstitialAd.show();
		}

		RestApi request = new RestApi(this, view);
		final ProgressDialog dialog = ProgressDialog.show(this, "Carregando os dados", "Por favor, aguarde...", true);
		RequestInterface service = request.request();
		service.boletins(userToken).enqueue(new Callback<String>() {
			@Override
			public void onResponse(Call<String> call, Response<String> response) {
				if(response.isSuccessful()&&response.code() != 400){
					String answer = response.body();
					Intent intent = new Intent(MainActivity.this, FaltasBoletim.class);
					intent.putExtra("apiResponse", answer);
					startActivity(intent);
				}else{
					BDhandler bd = new BDhandler(getBaseContext());
					bd.criarBD();
					bd.clearTables();
					bd.disconnect();
					Intent intent = new Intent(MainActivity.this, Login.class);
					startActivity(intent);
					SharedPreferences.Editor prefsEditor = getSharedPreferences("app_prefs", 0).edit();
					prefsEditor.clear();
					prefsEditor.apply();
					Toast.makeText(getApplicationContext(), response.body(), Toast.LENGTH_SHORT).show();
				}

				dialog.dismiss();
			}

			@Override
			public void onFailure(Call<String> call, Throwable t) {

				Intent intent = new Intent(MainActivity.this, FaltasBoletim.class);
				startActivity(intent);

				dialog.dismiss();
			}
		});
	}
	public void btn3(View view){

		if (mInterstitialAd.isLoaded()) {
			mInterstitialAd.show();
		}
		RestApi request = new RestApi(this, view);
		final ProgressDialog dialog = ProgressDialog.show(this, "Carregando os dados", "Por favor, aguarde...", true);
		RequestInterface service = request.request();
		service.horarios(userToken).enqueue(new Callback<String>() {
			@Override
			public void onResponse(Call<String> call, Response<String> response) {
				if(response.isSuccessful()&&response.code() != 400){
					String answer = response.body();
					Intent intent = new Intent(MainActivity.this, Horario.class);
					intent.putExtra("apiResponse", answer);
					startActivity(intent);
				}else{
					BDhandler bd = new BDhandler(getBaseContext());
					bd.criarBD();
					bd.clearTables();
					bd.disconnect();
					Intent intent = new Intent(MainActivity.this, Login.class);
					startActivity(intent);
					SharedPreferences.Editor prefsEditor = getSharedPreferences("app_prefs", 0).edit();
					prefsEditor.clear();
					prefsEditor.apply();
					Toast.makeText(getApplicationContext(), response.body(), Toast.LENGTH_SHORT).show();
				}

				dialog.dismiss();
			}

			@Override
			public void onFailure(Call<String> call, Throwable t) {
				Intent intent = new Intent(MainActivity.this, Horario.class);
				startActivity(intent);
				dialog.dismiss();
			}
		});



	}
	public void btn4(View view){
        Toast.makeText(this, "Ops, as funcionalidades do App encontra-se suspensas até o  dia 11/11. Desculpe o transtorno", Toast.LENGTH_SHORT).show();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// TODO: Implement this method
		MenuInflater  inflate = getMenuInflater();
		inflate.inflate(R.menu.main_menu,menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{

		if (item.getItemId() == R.id.sair) {
			BDhandler bd = new BDhandler(this);
			bd.criarBD();
			bd.clearTables();
			bd.disconnect();
			Intent intent = new Intent(MainActivity.this, Login.class);
			startActivity(intent);
			SharedPreferences.Editor prefsEditor = getSharedPreferences("app_prefs", 0).edit();
			prefsEditor.clear();
			prefsEditor.apply();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	
	
		
}


