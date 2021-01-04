package com.example.urclean;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.urclean.model.CodigoPostal;
import com.example.urclean.model.Queja;

import java.util.ArrayList;

public class AdapterGrupo extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private Context contexto;
    private ArrayList<CodigoPostal> codigos;
    private CodigoPostal codigo;
    private String condicion;

    public AdapterGrupo(Context contexto, ArrayList<CodigoPostal> codigos) {

        this.contexto = contexto;
        this.codigos = codigos;
        inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.e("CONTEXTGW","Inside getView");
        final View vista = inflater.inflate(R.layout.elementolistacodigopostal,null);
        TextView zonaView = (TextView) vista.findViewById(R.id.textViewZona);
        TextView codigoView = (TextView) vista.findViewById(R.id.textViewCodigo);

        codigo = codigos.get(position);

        codigoView.setText(codigo.getCodigo());
        zonaView.setText(codigo.getZona());
        return vista;
    }



    @Override
    public int getCount() {
        return codigos.size();
    }

    @Override
    public Object getItem(int position) {
        return codigos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
