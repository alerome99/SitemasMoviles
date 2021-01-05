package com.example.urclean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.urclean.model.Tarea;

import java.util.ArrayList;

public class AdapterHistorialTareas extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private Context contexto;
    private ArrayList<Tarea> tareas;
    private Tarea tarea;
    private String condicion;
    private int[] imagenes = {R.drawable.baseline_low_priority_black_18dp,R.drawable.baseline_refresh_black_18dp,
            R.drawable.baseline_task_alt_black_18dp};

    public AdapterHistorialTareas(Context contexto, ArrayList<Tarea> tareas) {
        this.contexto = contexto;
        this.tareas = tareas;
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


        tarea = tareas.get(position);

        nombre.setText(tarea.getName());
        descripcion.setText(tarea.getDescripcion());
        direccion.setText(tarea.getCalle());
        estado.setText(tarea.getEstado());

        condicion = tarea.getEstado();
        imagen.setTag(position);

        if(condicion.equals("SinRealizar")){
            imagen.setImageResource(imagenes[0]);
        }else if(condicion.equals("EnCurso")){
            imagen.setImageResource(imagenes[1]);
        }else{
            imagen.setImageResource(imagenes[2]);
        }
        return vista;
    }

    @Override
    public int getCount() {
        return tareas.size();
    }

    @Override
    public Object getItem(int position) {
        return tareas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
