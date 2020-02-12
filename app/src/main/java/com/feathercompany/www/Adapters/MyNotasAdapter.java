package com.feathercompany.www.Adapters;
import android.annotation.SuppressLint;
import android.widget.*;
import java.util.*;
import android.content.*;
import android.view.*;
import android.graphics.*;
import android.os.*;
import org.json.*;
import android.app.*;

import com.feathercompany.www.DataSet;
import com.feathercompany.www.R;

public class MyNotasAdapter extends ArrayAdapter<DataSet>
{
	Context context;
	private List<DataSet> myList;
	private ArrayList<DataSet> arraylist;

	public MyNotasAdapter(Context context, List<DataSet> list){
		super(context, R.layout.custom_entry, list);
		this.context = context;
		this.arraylist = new ArrayList<>();
		myList = list;
		this.arraylist.addAll(myList);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
	    LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.custom_entry, parent, false);
		final DataSet dados = getItem(position);

		TextView needed = view.findViewById(R.id.needed);
		String status = dados.neeededToApproved();
		if(status.contains("Aprovado(a)!")){
			ImageView icon = view.findViewById(R.id.imgFlag0);
			icon.setImageResource(R.drawable.ic_done_all_black_24dp);
			needed.setVisibility(View.INVISIBLE);
			TextView statusMsg = view.findViewById(R.id.statusMsg);
			statusMsg.setText(status);
		}else{
			needed.setText(status);
		}

		TextView materia = view.findViewById(R.id.txtMateria);
		materia.setText(dados.getMateria().toUpperCase());
		TextView b1 = view.findViewById(R.id.bimestre1);
		b1.setText(String.valueOf(dados.getNota_p1()));
		TextView b2 = view.findViewById(R.id.bimestre2);
		b2.setText(String.valueOf(dados.getNota_p2()));
		TextView b3 = view.findViewById(R.id.bimestre3);
		b3.setText(String.valueOf(dados.getNota_p3()));
		TextView b4 = view.findViewById(R.id.bimestre4);
		b4.setText(String.valueOf(dados.getNota_p4()));
		TextView rec = view.findViewById(R.id.rec);
		rec.setText(String.valueOf(dados.getNota_rec()));
		TextView fi = view.findViewById(R.id.notaFinal);
		fi.setText(String.valueOf(dados.getNota_rf()));

		String[] colors = {"#0f8c26","#00a1ff","#047EC5","#4d0977","#770924","#774309","#cc7300","#0c7a43","#a814a8" };
		Random a  = new Random();
		RelativeLayout card = view.findViewById(R.id.card);
		int index  = (a.nextInt((colors.length-1)-0 )+0);
		card.setBackgroundColor(Color.parseColor(colors[index]));
		return view;
	}

	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		myList.clear();
		if (charText.length() == 0) {
			myList.addAll(arraylist);
		}
		else
		{
			for (DataSet wp : arraylist) {
				if (wp.getMateria().toLowerCase(Locale.getDefault()).contains(charText)) {
					myList.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}
}
