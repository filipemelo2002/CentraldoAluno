package com.feathercompany.www;

import android.app.*;
import android.os.*;
import android.text.InputType;
import android.view.*;
import android.graphics.*;
import android.widget.*;
import android.content.*;
import android.net.*;

import com.feathercompany.www.ApiRequest.RequestInterface;
import com.feathercompany.www.ApiRequest.RestApi;
import com.feathercompany.www.InternalDB.TabelaUserDados;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends Activity
{
	private InterstitialAd mInterstitialAd;
	private String INTERTITIAL_ADS = "ca-app-pub-4776387538701328/9928084466";
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginform);

		AdsHandler adsHandler = new AdsHandler(this);
		AdView adView = adsHandler.loadBanner("ca-app-pub-4776387538701328/3622352958");
		LinearLayout ll =findViewById(R.id.loginAds);
		ll.addView(adView);
		mInterstitialAd = adsHandler.loadInterstitialAds(INTERTITIAL_ADS);

		final EditText passInput = findViewById(R.id.pass);
		final ImageView icon = findViewById(R.id.passwordInputInteract);
		final int PASSWORD_TYPE = passInput.getInputType();
		LinearLayout frame = findViewById(R.id.displayPassIconFrame);

		frame.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View view) {

				if(passInput.getInputType()== PASSWORD_TYPE){
					passInput.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
					passInput.setSelection(passInput.getText().toString().length());
					icon.setImageResource(R.drawable.ic_hide_password);
				}else{
					passInput.setInputType(PASSWORD_TYPE);
					passInput.setSelection(passInput.getText().toString().length());
					icon.setImageResource(R.drawable.ic_show_password);
				}


			}
		});


	}
	public void login(View view){

		if (mInterstitialAd.isLoaded()) {
			mInterstitialAd.show();
		}

		TextView email = findViewById(R.id.email);
		TextView pass = findViewById(R.id.pass);
		String user = email.getEditableText().toString();
		String senha = pass.getEditableText().toString();
		if(user.length()>0 && senha.length()>0){
			RestApi request = new RestApi(this, view);
			FormBody formBody = new FormBody.Builder()
					.add("email", user)
					.add("senha", senha)
					.build();

			final ProgressDialog dialog = ProgressDialog.show(this, "Carregando os dados", "Por favor, aguarde...", true);
			RequestInterface service = request.request();
			service.sessions(formBody).enqueue(new Callback<String>() {
				@Override
				public void onResponse(Call<String> call, Response<String> response) {

					if(response.isSuccessful()){
						String answer = response.body();

						try {
							JSONObject obj = new JSONObject(answer);
							TabelaUserDados tb = new TabelaUserDados(getApplication());
							tb.createDB();
							tb.saveUserData(obj.getString("userToken"), obj.getString("email"), obj.getString("senha"));
							tb.disconnectDB();

							Intent intent = new Intent(Login.this, MainActivity.class);
							startActivity(intent);
							finish();
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}else{
						Toast.makeText(getApplicationContext(),"Usuário não autorizado", Toast.LENGTH_SHORT).show();
					}
					dialog.dismiss();
				}

				@Override
				public void onFailure(Call<String> call, Throwable t) {
					Toast.makeText(getApplicationContext(),"Ops, algo deu errado. Tente realizar esta novamente ação mais tarde.", Toast.LENGTH_SHORT).show();
					dialog.dismiss();
				}
			});

		}else{
			Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
		}
		
	}

	public void onCadastro(View view){
		if (mInterstitialAd.isLoaded()) {
			mInterstitialAd.show();
		}

		Button cadastroText = findViewById(R.id.cadastro);
		cadastroText.setTextColor(Color.parseColor("#00A5E3"));
		Uri uri = Uri.parse("http://www.siepe.educacao.pe.gov.br/GerenciadorAcessoWeb/cadastroUsuarioAction.do?actionType=iniciar&origem=Portal");
    	Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}
	@Override
	public void onBackPressed()
	{
	
		finishAffinity();
	}
	

	
	
	
	
}
