package com.feathercompany.www.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.feathercompany.www.ApiRequest.RequestInterface;
import com.feathercompany.www.ApiRequest.RestApi;
import com.feathercompany.www.Boletim;
import com.feathercompany.www.Faltas;
import com.feathercompany.www.InternalDB.TabelaUserDados;
import com.feathercompany.www.R;

import java.util.List;
import java.util.Random;

import okhttp3.FormBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FaltasBoletimAdapter extends ArrayAdapter<Boletim> {
    private Context context;
    public FaltasBoletimAdapter(Context context, List<Boletim> list){
        super(context,R.layout.activity_faltas_boletim ,list);
        this.context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.entry_boletim,parent, false);
        TextView txt = view.findViewById(R.id.txtEntry);
        String[] colors = {"#0f8c26","#00a1ff","#047EC5","#4d0977","#770924","#774309","#cc7300","#0c7a43","#a814a8" };
        Random a  = new Random();
        RelativeLayout card = view.findViewById(R.id.card);
        int index  = (a.nextInt((colors.length-1)-0 )+0);
        card.setBackgroundColor(Color.parseColor(colors[index]));
        txt.setText(getItem(position).getLabel());

        final int ano = getItem(position).getAno();
        final int p = position;
        final int boletinId = getItem(position).getBoletimId();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RestApi request = new RestApi(getContext(), view);
                final ProgressDialog dialog = ProgressDialog.show(getContext(), "Carregando os dados", "Por favor, aguarde...", true);
                RequestInterface service = request.request();
                TabelaUserDados bd = new TabelaUserDados(getContext());
                bd.createDB();
                String userToken = bd.getUserToken();
                bd.disconnectDB();

                service.faltas(userToken, boletinId, ano).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Intent intent = new Intent(context, Faltas.class);
                        intent.putExtra("apiResponse", response.body());
                        intent.putExtra("boletinId", boletinId);
                        intent.putExtra("boletinPosition", p);
                        getContext().startActivity(intent);
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Intent intent = new Intent(context, Faltas.class);
                        intent.putExtra("boletinId", boletinId);
                        intent.putExtra("boletinPosition", p);
                        getContext().startActivity(intent);
                        dialog.dismiss();
                    }
                });


            }
        });


        return view;
    }
}
