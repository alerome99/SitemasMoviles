package com.example.urclean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.urclean.model.Queja;

import java.util.ArrayList;

public class AdapterQueja extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private Context contexto;
    private ArrayList<Queja> quejas;
    private Queja queja;
    private String condicion;
    private int[] imagenes = {R.drawable.baseline_low_priority_black_18dp,R.drawable.baseline_refresh_black_18dp,
            R.drawable.baseline_task_alt_black_18dp};


    public AdapterQueja(Context contexto, ArrayList<Queja> quejas) {

        this.contexto = contexto;
        this.quejas = quejas;
        inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final View vista = inflater.inflate(R.layout.elementolistaqueja,null);
        ImageView imagen = (ImageView) vista.findViewById(R.id.imageView5);
        TextView nombre = (TextView) vista.findViewById(R.id.textViewNameQueja);
        TextView estado = (TextView) vista.findViewById(R.id.textViewEstadoQueja);
        TextView email = (TextView) vista.findViewById(R.id.textViewEmailQueja);

        queja = quejas.get(position);

        nombre.setText(queja.getTitulo());
        estado.setText(queja.getEstado().toString());
        email.setText(queja.getEmail());
        condicion = queja.getEstado().toString();
        imagen.setTag(position);

        if(condicion.equals("NOTIFICADA")){

            imagen.setImageResource(imagenes[0]);

        }else if(condicion.equals("RECIBIDA")){

            imagen.setImageResource(imagenes[1]);

        }
        return vista;
    }



    @Override
    public int getCount() {
        return quejas.size();
    }

    @Override
    public Object getItem(int position) {
        return quejas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
