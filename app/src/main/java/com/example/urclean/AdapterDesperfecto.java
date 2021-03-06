package com.example.urclean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.urclean.model.Desperfecto;

import java.util.ArrayList;

public class AdapterDesperfecto extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private Context contexto;
    private ArrayList<Desperfecto> desperfectos;
    private Desperfecto desperfecto;
    private String condicion;
    private int[] imagenes = {R.drawable.baseline_low_priority_black_18dp,R.drawable.baseline_refresh_black_18dp,
            R.drawable.baseline_task_alt_black_18dp};


    public AdapterDesperfecto(Context contexto, ArrayList<Desperfecto> desperfectos) {

        this.contexto = contexto;
        this.desperfectos = desperfectos;
        inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View vista = inflater.inflate(R.layout.elementolistadesperfecto,null);
        ImageView imagen = (ImageView) vista.findViewById(R.id.imageView5);
        TextView nombre = (TextView) vista.findViewById(R.id.textViewNameDesperfecto);
        TextView estado = (TextView) vista.findViewById(R.id.textViewEstadoDesperfecto);
        TextView email = (TextView) vista.findViewById(R.id.textViewEmailDesperfecto);
        TextView direccion = (TextView) vista.findViewById(R.id.textViewDireccionDesperfecto);

        desperfecto = desperfectos.get(position);

        nombre.setText(desperfecto.getTitulo());
        estado.setText(desperfecto.getEstado().toString());
        email.setText(desperfecto.getEmail());
        direccion.setText(desperfecto.getDireccion());
        condicion = desperfecto.getEstado().toString();
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
        return desperfectos.size();
    }

    @Override
    public Object getItem(int position) {
        return desperfectos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
