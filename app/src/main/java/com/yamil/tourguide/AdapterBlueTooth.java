package com.yamil.tourguide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yamil on 4/5/16.
 */
public class AdapterBlueTooth extends BaseAdapter {
    ArrayList<ListBlueTooth> datos;
    private Context context;
    private LayoutInflater inflater;

    public AdapterBlueTooth(Context context, ArrayList<ListBlueTooth> datos) {
        this.datos = datos;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datos.size();
    }

    @Override
    public Object getItem(int position) {
        return datos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = inflater.inflate(R.layout.listabluetooth, null);
            viewHolder = new ViewHolder();
            viewHolder.nombre = (TextView) view.findViewById(R.id.txt_nombre_dato);
            viewHolder.macAddress = (TextView) view.findViewById(R.id.txt_macAddress_dato);
            viewHolder.estado = (TextView) view.findViewById(R.id.txt_estado_dato);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.nombre.setText(datos.get(position).getNombre());
        viewHolder.macAddress.setText(datos.get(position).getMacAddress());
        viewHolder.estado.setText(datos.get(position).getEstado());
        return view;
    }
    public class ViewHolder{
        TextView nombre;
        TextView macAddress;
        TextView estado;
    }
}

