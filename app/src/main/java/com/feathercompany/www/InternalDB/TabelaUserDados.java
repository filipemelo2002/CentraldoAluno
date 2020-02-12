package com.feathercompany.www.InternalDB;
import android.content.*;
import android.database.sqlite.*;
import android.database.*;

import com.feathercompany.www.InternalDB.BDcore;
import com.feathercompany.www.InternalDB.DBHandler;

import java.util.*;

public class TabelaUserDados extends DBHandler
{
	private Context context;
	private BDcore sqlhelper;
	private SQLiteDatabase database; 
	public TabelaUserDados(Context context){
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
	public void saveData(int position, String data)
	{
		
	}
	public void saveUserData(String userToken, String email, String senha){
		String[] columns = new String[]{"id","userToken","email", "senha"};
		ContentValues cv = new ContentValues();
		Cursor cursor = database.query("user",columns,null,null,null,null,null,"1");
		if(cursor.getCount()<=0){
			cv.put("id",1);
			cv.put("userToken",userToken);
			cv.put("email",email);
			cv.put("senha",senha);
			database.insert("user",null,cv);
		}else{
			cv.put("userToken",userToken);
			cv.put("email",email);
			cv.put("senha",senha);
			String[] valor = new String[]{String.valueOf(1)};
			database.update("user",cv,"id=?",valor);
		}
	}
	public String getUserToken(){
		String userToken = null;
		String[] columns = new String[] {"userToken"};
		String[] valor = new String[]{String.valueOf(1)};
		Cursor cursor = database.query("user",columns,"id=?",valor,null,null,"id");
	 	if(cursor.getCount()>0){
			cursor.moveToFirst();
			do{
				userToken = cursor.getString(0);
			}while(cursor.moveToNext());
		}
		return userToken;
	}

	
}
