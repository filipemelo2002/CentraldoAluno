package com.feathercompany.www.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.feathercompany.www.R;

import java.util.List;
import java.util.Random;

public class MyHorarioAdapter extends RecyclerView.Adapter<MyHorarioAdapter.MyViewHolder> {

    private Context context;
    private List<String> dataset;
    public MyHorarioAdapter(Context context,  List<String> dataset){
        this.context  = context;
        this.dataset = dataset;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.horario_entry,viewGroup, false);
        MyViewHolder myViewHolder  = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        String[] colors = {"#eeeef0","#ffffff"};

        myViewHolder.classText.setText(dataset.get(i));

        if(i%5==0||(i+3)%5==0||(i+6)%5==0){
            myViewHolder.cardView.setCardBackgroundColor(Color.parseColor(colors[0]));
        }else{
            myViewHolder.cardView.setCardBackgroundColor(Color.parseColor(colors[1]));
        }


    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView  classText;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            classText  = itemView.findViewById(R.id.classText);
            cardView = itemView.findViewById(R.id.cardClass);
        }
    }
}
