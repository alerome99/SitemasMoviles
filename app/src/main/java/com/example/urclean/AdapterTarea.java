package com.example.urclean;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.urclean.model.Tarea;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdapterTarea extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private Context contexto;
    private ArrayList<Tarea> tareas;
    private Tarea tarea;
    private String condicion;
    private int[] imagenes = {R.drawable.baseline_low_priority_black_18dp,R.drawable.baseline_refresh_black_18dp,
        R.drawable.baseline_task_alt_black_18dp};


    public AdapterTarea(Context contexto, ArrayList<Tarea> tareas) {

        this.contexto = contexto;
        this.tareas = tareas;
        inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final View vista = inflater.inflate(R.layout.elementolista,null);
        ImageView imagen = (ImageView) vista.findViewById(R.id.imageView5);
        TextView nombre = (TextView) vista.findViewById(R.id.textViewNameTarea);
        TextView estado = (TextView) vista.findViewById(R.id.textViewEstadoTarea);
        TextView direccion = (TextView) vista.findViewById(R.id.textViewDireccionTarea);

        tarea = tareas.get(position);
        nombre.setText(tarea.getName());
        estado.setText(tarea.getEstado());
        direccion.setText(tarea.getCalle());
        condicion = tarea.getEstado();
        imagen.setTag(position);

        if(condicion.equals("SinAsignar")){

            imagen.setImageResource(imagenes[0]);

        }else if(condicion.equals("EnCurso")){

            imagen.setImageResource(imagenes[1]);

        } else if(condicion.equals("Completada")){

        } else{

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
