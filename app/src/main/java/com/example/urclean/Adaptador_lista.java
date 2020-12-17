package com.example.urclean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class Adaptador_lista extends BaseAdapter {

    private static LayoutInflater inflater = null;

    Context contexto;
    String[] datos;

    public Adaptador_lista(Context conexto, String[] datos) {
        this.contexto = conexto;
        this.datos = datos;

        inflater = (LayoutInflater) conexto.getSystemService(conexto.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        final View vista = inflater.inflate(R.layout.elemento_lista, null);

        TextView textView7 = (TextView) vista.findViewById(R.id.textView7);

        textView7.setText(datos[i]);

        return vista;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
