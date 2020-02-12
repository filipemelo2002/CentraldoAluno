package com.feathercompany.www.InternalDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TabelaHorario extends DBHandler {

    private Context context;
    private BDcore sqlhelper;
    private SQLiteDatabase database;
    public TabelaHorario(Context context){
        sqlhelper = new BDcore(context);
        this.context = context;
    }
    @Override
    public void createDB() {
        database = sqlhelper.getWritableDatabase();
    }

    @Override
    public void disconnectDB() {
        database.close();
    }

    @Override
    public void saveData(int position, String data) {

    }

    public boolean isThereItensAlready(int pos){
        String[] columns = new String[] {"dados"};
        String[] valor = new String[]{String.valueOf(pos)};
        Cursor cursor = database.query("horario",columns,"id=?",valor,null,null,null,null);
        return cursor.getCount() > 0;
    }
    public void clearTable(){
        database.delete("horario", null, null);
    }


    public void autalizarInformacao(String dados,int id){
        ContentValues cv = new ContentValues();
        cv.put("dados",dados);
        String[] valor = new String[]{String.valueOf(id)};
        database.update("boletins",cv,"id=?",valor);
    }
    public void salvarInformacao(String dados, int id)
    {
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("dados",dados);
        database.insert("horario",null,cv);
    }

    public List<String> getHorarioData(){
        List<String> list = new ArrayList<>();
        String[] columns = new String[] {"dados"};
        Cursor cursor = database.query("horario",columns,null,null,null,null,"id");
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                try {
                    JSONArray array = new JSONArray(cursor.getString(0));
                    for(int i=0; i<array.length(); i++){
                        JSONObject obj = array.getJSONObject(i);
                        list.add(obj.getString("seg"));
                        list.add(obj.getString("ter"));
                        list.add(obj.getString("quar"));
                        list.add(obj.getString("quin"));
                        list.add(obj.getString("sext"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }while(cursor.moveToNext());
        }



        return list;
    }
}
