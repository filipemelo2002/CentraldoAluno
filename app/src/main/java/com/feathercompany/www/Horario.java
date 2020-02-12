package com.feathercompany.www;


import android.content.Intent;
import android.support.v7.app.*;
import android.os.*;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.feathercompany.www.Adapters.MyHorarioAdapter;
import com.feathercompany.www.InternalDB.TabelaHorario;

import java.util.List;


public class Horario extends  AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        setContentView(R.layout.horario);
		TabelaHorario horario = new TabelaHorario(this);
		horario.createDB();
		Intent intent = getIntent();
		RecyclerView horariosTable = findViewById(R.id.horariosTable);
		if(intent.hasExtra("apiResponse")){
			String response = intent.getExtras().getString("apiResponse");
			horario.clearTable();
			if(horario.isThereItensAlready(0)){
				horario.autalizarInformacao(response, 0);
			}else{
				horario.salvarInformacao(response, 0);
			}

		}else{
			Toast.makeText(this,"Você está vendo os dados em modo offline!",Toast.LENGTH_LONG).show();
		}
		List<String> dataset = horario.getHorarioData();
		if(dataset.size()>0){
			//Toast.makeText(this, String.valueOf(dataset.size()), Toast.LENGTH_SHORT).show();
			MyHorarioAdapter adapter = new MyHorarioAdapter(this, dataset);
			horariosTable.setAdapter(adapter);
			GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, 5);
			horariosTable.setLayoutManager(mGridLayoutManager);
			horariosTable.setHasFixedSize(true);

		}else{
			ImageView img = findViewById(R.id.notfound);
			img.setVisibility(View.VISIBLE);
			horariosTable.setVisibility(View.INVISIBLE);
		}
		horario.disconnectDB();

	}


}
