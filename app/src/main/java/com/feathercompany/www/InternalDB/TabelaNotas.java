package com.feathercompany.www.InternalDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.feathercompany.www.DataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TabelaNotas extends DBHandler {

    private Context context;
    private BDcore sqlhelper;
    private SQLiteDatabase database;

    public TabelaNotas(Context context){
        this.context = context;
        sqlhelper = new BDcore(context);

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
    public void autalizarInformacao(int id, String data){
        ContentValues cv = new ContentValues();
        cv.put("dados",data);
        String[] valor = new String[]{String.valueOf(id)};
        database.update("boletinNotas",cv, "id=?",valor);
    }
    public void salvarInformacao(String data, int id)
    {
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("dados",data);
        database.insert("boletinNotas",null,cv);
    }
    public boolean isThereItensAlready(int pos){
        String[] columns = new String[] {"dados"};
        String[] valor = new String[]{String.valueOf(pos)};
        Cursor cursor = database.query("boletinNotas",columns,"id=?",valor,null,null,null,null);
        return cursor.getCount() > 0;
    }

    public List<DataSet> getAllData(int boletinId){
        List<DataSet> list = new ArrayList<>();
        String[] columns = new String[] {"dados"};
        Cursor cursor = database.query("boletinNotas",columns,"id=?", new String[]{String.valueOf(boletinId)},null,null,"id");
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                try {
                    JSONObject array  = new JSONObject(cursor.getString(0));
                    JSONArray notasArray  = array.getJSONArray("data");

                    for (int i =0; i < notasArray.length(); i++){

                        JSONObject obj = notasArray.getJSONObject(i);

                        DataSet notas = new DataSet();
                        notas.setMateria(obj.getString("materia"));
                        notas.setNota_p1(obj.getString("nota_p1"));
                        notas.setNota_p2(obj.getString("nota_p2"));
                        notas.setNota_p3(obj.getString("nota_p3"));
                        notas.setNota_p4(obj.getString("nota_p4"));
                        notas.setNota_rec(obj.getString("nota_rec"));
                        notas.setNota_rf(obj.getString("nota_rf"));
                        list.add(notas);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }while(cursor.moveToNext());
        }

        return list;
    }
    public void clearTable(){
        database.delete("boletinNotas", null, null);
    }
}
