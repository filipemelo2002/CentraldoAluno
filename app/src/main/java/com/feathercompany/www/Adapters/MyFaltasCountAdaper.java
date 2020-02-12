package com.feathercompany.www.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.feathercompany.www.FaltasCount;
import com.feathercompany.www.R;

import java.util.List;

public class MyFaltasCountAdaper extends ArrayAdapter<FaltasCount> {

    private List<FaltasCount> objects;
    public MyFaltasCountAdaper(Context context, List<FaltasCount> objects) {
        super(context, R.layout.faltas_count_entry, objects);
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        View view = inflater.inflate(R.layout.faltas_count_entry, parent, false);

        TextView materia = view.findViewById(R.id.textMateria);
        materia.setText(objects.get(position).getMateria());

        int sum = objects.get(position).getFnj() + objects.get(position).getFj();
        TextView fnj = view.findViewById(R.id.textFalta);
        fnj.setText(String.valueOf(sum));

       return view;
    }
}
