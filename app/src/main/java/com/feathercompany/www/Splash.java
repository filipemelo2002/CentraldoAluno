package com.feathercompany.www;

import android.annotation.SuppressLint;
import android.app.*;
import android.os.*;
import android.view.*;

import android.widget.*;
import android.content.*;

import com.feathercompany.www.InternalDB.TabelaUserDados;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class Splash extends Activity
{
	final Context context = this;
	@Override
	protected void onCreate (Bundle savedInstancesState){
		
		super.onCreate ( savedInstancesState ) ;
		setContentView ( R.layout.splash ) ;
		MobileAds.initialize(getApplicationContext(), "ca-app-pub-4776387538701328~5824324554");
		AdView adView = new AdView(getApplicationContext());
		adView.setAdSize(AdSize.LARGE_BANNER);
		adView.setAdUnitId("ca-app-pub-4776387538701328/5735997516");
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
		LinearLayout ll =findViewById(R.id.splashAds);
		ll.addView(adView);
		View decorView = getWindow().getDecorView();
		int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
			| View.SYSTEM_UI_FLAG_FULLSCREEN;
		decorView.setSystemUiVisibility(uiOptions);
		startHeavyProcessing();

	}
	
	private void startHeavyProcessing(){
		new LongOperation().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class LongOperation extends AsyncTask<String, Void, String> {
		
		  @Override
        protected String doInBackground(String... params) {
           	 try {
					
                    Thread.sleep(2000);
                } catch (Exception e) {

                }
            return null;
        }
		

        @Override
        protected void onPostExecute(String result) {


			TabelaUserDados bd = new TabelaUserDados(context);
			bd.createDB();
			String authorizedUser = bd.getUserToken();
			bd.disconnectDB();

			if(authorizedUser==null){
				Intent intent = new Intent(context, Login.class);
				startActivity(intent);
			}else{
				Intent intent = new Intent(context, MainActivity.class);
				startActivity(intent);
			}
			
            finish();
        }

    }

}
