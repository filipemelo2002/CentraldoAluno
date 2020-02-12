package com.feathercompany.www;


import android.content.Context;
import android.content.Intent;

import java.util.List;

public class ClipboardCopyHandler {
    private List<DataSet> data;
    private Context context;
    public ClipboardCopyHandler(Context context, List<DataSet> data){
        this.context = context;
        this.data = data;
    }

    private String convertDataToString(){
        String dataSet = "";


        for(int i =0; i <data.size(); i++){
            DataSet materia  = data.get(i);
            String line ="Matéria: "+materia.getMateria()+"\n" +
                    "   1ª unidade: "+materia.getNota_p1()+" \n" +
                    "   2ª unidade: "+materia.getNota_p2()+"\n" +
                    "   3ª unidade: "+materia.getNota_p3()+"\n" +
                    "   4ª unidade: "+materia.getNota_p4()+"\n" +
                    "   Nota rec. final: "+materia.getNota_rec()+"\n" +
                    "   Nota final: "+materia.getNota_rf();
            if(i!=data.size()){
                line +="\n\n";
            }
            dataSet += line;
        }
        return dataSet;
    }

    public void saveDataToClipboard(){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, convertDataToString());
        context.startActivity(Intent.createChooser(sharingIntent, "Compartilhar boletin:"));
    }
}
