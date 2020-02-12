package com.feathercompany.www.Adapters;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.*;
import android.content.*;

import java.text.DecimalFormat;
import java.util.*;
import android.view.*;

import com.feathercompany.www.Dialogs.FaltasCountDialog;
import com.feathercompany.www.Faltas;
import com.feathercompany.www.FaltasCount;
import com.feathercompany.www.InternalDB.TabelaFaltas;
import com.feathercompany.www.R;

import az.plainpie.PieView;
import az.plainpie.animation.PieAngleAnimation;

public class MyFaltasAdapter extends ArrayAdapter<Float>
{
	private Context context;
	private int ano;
	public MyFaltasAdapter(Context context, List<Float> list, int ano){
		super(context, R.layout.faltas_entry, list);
		this.context = context;
		this.ano = ano;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.faltas_entry, parent, false);
		final int positionAux = position+1;
		TextView txt1 = view.findViewById(R.id.principalText);
		txt1.setText((position+1)+"° BIMESTRE");
		String[] colors = {"#0f8c26","#00a1ff","#047EC5","#4d0977","#770924","#774309","#cc7300","#0c7a43","#a814a8" };
		Random a  = new Random();
		RelativeLayout card = view.findViewById(R.id.card);
		int index  = (a.nextInt((colors.length-1)-0 )+0);
		card.setBackgroundColor(Color.parseColor(colors[index]));
		PieView animatedPie =  view.findViewById(R.id.pieView);
        animatedPie .setPercentage(getItem(position));
		DecimalFormat formatter = new DecimalFormat("#.00");
		String aux = "0";
		if(getItem(position)<1){
			animatedPie.setInnerText(aux+formatter.format(getItem(position))+"%");
		}else{

			animatedPie.setInnerText(formatter.format(getItem(position))+"%");
		}
        PieAngleAnimation animation = new PieAngleAnimation(animatedPie );
        animation.setDuration(1000); //This is the duration of the animation in millis
        animatedPie .startAnimation(animation);

        view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				FragmentActivity activity = (FragmentActivity)(context);
				FragmentManager fm = activity.getSupportFragmentManager();
				TabelaFaltas bd = new TabelaFaltas(getContext());
				bd.createDB();
				List<FaltasCount> dataSet = bd.getFaltasCount(ano, positionAux);
				bd.disconnectDB();
				Bundle b  = new Bundle();
				b.putString("Unidade", String.valueOf(positionAux));
				FaltasCountDialog dialog = new FaltasCountDialog(dataSet);
				dialog.setArguments(b);
				dialog.show(fm, "falas_count");
				}
		});

		return view;
	}
	

}
