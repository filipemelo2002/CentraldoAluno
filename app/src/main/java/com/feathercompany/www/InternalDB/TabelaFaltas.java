package com.feathercompany.www.InternalDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.feathercompany.www.FaltasCount;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TabelaFaltas extends DBHandler {
    private Context context;
    private BDcore sqlhelper;
    private SQLiteDatabase database;

    public TabelaFaltas(Context context){
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
    public void salvarInformacoes(int id, String data){
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("dados",data);
        database.insert("faltas",null,cv);
    }
    public boolean isThereItemAlready(int id){
        String[] columns = new String[] {"id","dados"};
        String[] valor = new String[]{String.valueOf(id)};
        Cursor cursor = database.query("faltas",columns,"id=?",valor,null,null,null,null);
        return cursor.getCount() > 0;
    }
    public void updateItemAtPosition(int id, String data){
        ContentValues cv = new ContentValues();
        cv.put("dados",data);
        String[] valor = new String[]{String.valueOf(id)};
        database.update("faltas",cv,"id=?",valor);
    }

    public void clearTable(){
        database.delete("faltas", null, null);
    }

    public List<Float> getPercentList(int position){
        List<Float> list = new ArrayList<>();

        String[] columns = new String[] {"dados"};
        String[] valor = new String[]{String.valueOf(position)};
        Cursor cursor = database.query("faltas",columns,"id=?",valor,null,null,"id");
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                try {
                    DecimalFormat df = new DecimalFormat();
                    df.setMaximumFractionDigits(2);

                    JSONObject obj = new JSONObject(cursor.getString(0));
                    JSONObject percents = obj.getJSONObject("percent");
                    list.add(Float.parseFloat(df.format(percents.getDouble("perc1")).replace(",",".")));
                    list.add(Float.parseFloat(df.format(percents.getDouble("perc2")).replace(",",".")));
                    list.add(Float.parseFloat(df.format(percents.getDouble("perc3")).replace(",",".")));
                    list.add(Float.parseFloat(df.format(percents.getDouble("perc4")).replace(",",".")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }while(cursor.moveToNext());
        }

        return list;
    }

    public List<FaltasCount> getFaltasCount(int id, int bimester){
        List<FaltasCount> list = new ArrayList<>();
        String[] columns = new String[] {"dados"};
        String[] valor = new String[]{String.valueOf(id)};
        Cursor cursor = database.query("faltas",columns,"id=?",valor,null,null,"id");

        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                try {

                    JSONObject obj = new JSONObject(cursor.getString(0));
                    JSONArray values = obj.getJSONArray("details");
                    for(int i=0; i<values.length(); i++){
                        JSONObject json = values.getJSONObject(i);
                        FaltasCount faltas = new FaltasCount();
                        faltas.setMateria(json.getString("materia"));
                        System.out.println(json.getString("materia"));
                        String keyFj = "fj_p"+bimester;
                        String keyFnj = "fnj_p"+bimester;
                        faltas.setFj(json.getInt(keyFj));
                        faltas.setFnj(json.getInt(keyFnj));
                        list.add(faltas);
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }while(cursor.moveToNext());
        }



        return list;
    }
}
