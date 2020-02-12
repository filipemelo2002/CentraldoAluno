package com.feathercompany.www.Dialogs;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.feathercompany.www.Adapters.MyFaltasCountAdaper;
import com.feathercompany.www.FaltasCount;
import com.feathercompany.www.InternalDB.TabelaFaltas;
import com.feathercompany.www.R;

import java.util.List;


@SuppressLint("ValidFragment")

public class FaltasCountDialog extends DialogFragment {

    private List<FaltasCount> data;
    public FaltasCountDialog(List<FaltasCount> data){
        this.data = data;
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light);


    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog  = new Dialog(getContext(), DialogFragment.STYLE_NORMAL);
        dialog.setTitle("Faltas no "+getArguments().getString("Unidade")+"º Bimestre");
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.faltas_count,container);
        ListView list  = view.findViewById(R.id.lisId);
        if(data.size()>0){
            MyFaltasCountAdaper adapter = new MyFaltasCountAdaper(getContext(),data);
            list.setAdapter(adapter);
        }else{
            Toast.makeText(getContext(), "Desculpe, ocorreu um erro ao ler os dados!", Toast.LENGTH_SHORT).show();
            dismiss();
        }
        return view;
    }

}
