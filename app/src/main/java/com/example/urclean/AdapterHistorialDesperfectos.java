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

public class AdapterHistorialDesperfectos extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private Context contexto;
    private ArrayList<Desperfecto> desperfectos;
    private Desperfecto desperfecto;
    private String condicion;
    private int[] imagenes = {R.drawable.baseline_low_priority_black_18dp,R.drawable.baseline_refresh_black_18dp,
            R.drawable.baseline_task_alt_black_18dp};

    public AdapterHistorialDesperfectos(Context contexto, ArrayList<Desperfecto> desperfectos) {
        this.contexto = contexto;
        this.desperfectos = desperfectos;
        inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final View vista = inflater.inflate(R.layout.elementolistahistorialincidencias,null);
        ImageView imagen = (ImageView) vista.findViewById(R.id.imageView);
        TextView nombre = (TextView) vista.findViewById(R.id.textViewNameIncidencia);
        TextView descripcion = (TextView) vista.findViewById(R.id.textViewDescripcionIncidencia);
        TextView direccion = (TextView) vista.findViewById(R.id.textViewDireccionIncidencia);
        TextView estado = (TextView) vista.findViewById(R.id.textViewEstadoIncidencia);


        desperfecto = desperfectos.get(position);

        nombre.setText(desperfecto.getTitulo());
        descripcion.setText(desperfecto.getDescripcion());
        direccion.setText(desperfecto.getDireccion());
        estado.setText(desperfecto.getEstado().toString());

        condicion = desperfecto.getEstado().toString();
        imagen.setTag(position);

        if(condicion.equals("NOTIFICADA")){
            imagen.setImageResource(imagenes[0]);
        }else if(condicion.equals("RECIBIDA")){
            imagen.setImageResource(imagenes[1]);
        }else{
            imagen.setImageResource(imagenes[2]);
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
