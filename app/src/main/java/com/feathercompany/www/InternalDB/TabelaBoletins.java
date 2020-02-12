package com.feathercompany.www.InternalDB;
import android.content.*;
import android.database.sqlite.*;
import android.database.*;
import android.util.Log;

import com.feathercompany.www.Boletim;
import com.feathercompany.www.InternalDB.BDcore;
import com.feathercompany.www.InternalDB.DBHandler;

import java.util.ArrayList;
import java.util.List;

public class TabelaBoletins extends DBHandler
{
    private Context context;
	private BDcore sqlhelper;
	private SQLiteDatabase database; 
	public TabelaBoletins(Context context){
		 sqlhelper = new BDcore(context);
		 this.context = context;
	}
	@Override
	public void createDB()
	{
		database = sqlhelper.getWritableDatabase();
	}

	@Override
	public void disconnectDB()
	{
		database.close();
	}

	@Override
	public void saveData(int position, String data) {

	}

	public void autalizarInformacao(String label, int ano, int boletimId, int position){
		ContentValues cv = new ContentValues();
		cv.put("label",label);
		cv.put("ano",ano);
		cv.put("boletimId",boletimId);
		String[] valor = new String[]{String.valueOf(position)};
		database.update("boletins",cv,"id=?",valor);
	}
	public void salvarInformacao(String label, int ano, int boletimId, int id)
	{
		ContentValues cv = new ContentValues();
		cv.put("id", id);
		cv.put("label",label);
		cv.put("ano",ano);
		cv.put("boletimId",boletimId);
		database.insert("boletins",null,cv);
	}
	public boolean isThereItensAlready(int pos){
		String[] columns = new String[] {"label", "ano","boletimId"};
		String[] valor = new String[]{String.valueOf(pos)};
		Cursor cursor = database.query("boletins",columns,"id=?",valor,null,null,null,null);
        return cursor.getCount() > 0;
    }
	public void clearTable(){
		database.delete("boletins", null, null);
	}

	public List<Boletim> getBoletimsData(){
		List<Boletim> data = new ArrayList<>();
		String[] columns = new String[] {"label", "ano","boletimId"};
		Cursor cursor = database.query("boletins",columns,null,null,null,null,"id");
		if(cursor.getCount()>0){
			cursor.moveToFirst();
			do{
				Boletim boletim = new Boletim();
				boletim.setLabel(cursor.getString(0));
				boletim.setAno(cursor.getInt(1));
				boletim.setBoletimId(cursor.getInt(2));
				data.add(boletim);
			}while(cursor.moveToNext());
		}

		return data;
	}

}
