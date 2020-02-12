package com.feathercompany.www.InternalDB;
import android.database.sqlite.*;
import android.content.*;

public class BDhandler
{
	private BDcore sqlhelper;
	private SQLiteDatabase database;
	private Context context;
	public BDhandler(Context context){
		sqlhelper = new BDcore(context);
	    this.context = context;
	}
	
	public void criarBD(){
		
		try{
		database = sqlhelper.getWritableDatabase();
		}catch(Exception e){
			
		}
	}
	public void clearTables(){
		database.delete("user",null,null);
		database.delete("boletins", null, null);
		database.delete("boletinNotas", null,null);
		database.delete("faltas", null,null);
		database.delete("horario", null,null);
	}
	public void disconnect(){
		database.close();
	}
	
}
