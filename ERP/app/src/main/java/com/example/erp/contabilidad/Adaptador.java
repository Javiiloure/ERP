package com.example.erp.contabilidad;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.erp.R;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {

    private Context context;
    private ArrayList<Gasto> listItems;

    public Adaptador(Context context, ArrayList<Gasto> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.item, null);

        TextView concepto = convertView.findViewById(R.id.concepto);
        TextView importe = convertView.findViewById(R.id.importe);

        Gasto gasto = (Gasto) getItem(position);

        concepto.setText(gasto.getConcepto());
        importe.setText(gasto.getImporte() + "0€");

        return convertView;
    }
}