package com.example.erp.contabilidad;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.erp.AdminBD;
import com.example.erp.R;

public class AnhadirGasto extends AppCompatDialogFragment {
    private Context context;
    protected SQLiteDatabase bd;
    protected static final String nombreBD = "ERP";
    public AdminBD admin;

    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.gestionar_gasto, null);

        admin = new AdminBD(context, nombreBD, null, 1);

        EditText input_importe = view.findViewById(R.id.input_importe);
        EditText input_fecha = view.findViewById(R.id.input_fecha);
        EditText input_concepto = view.findViewById(R.id.input_concepto);

        builder.setView(view).setTitle("Añadir gasto").setNegativeButton("Cancelar", (dialog, which) -> {
            Toast.makeText(context, "Cancelado", Toast.LENGTH_LONG).show();
        }).setPositiveButton("Guardar", (dialog, which) -> {

            // Obtenemos el id del ultimo gasto y lo incrementamos en 1
            int id = 0;
            for(int i = 0; i < Contabilidad.gastos.size(); i++) {
                id = Contabilidad.gastos.get(i).getId();
            }
            id++;

            double importe = Double.parseDouble(input_importe.getText().toString());
            String fecha = input_fecha.getText().toString();
            String concepto = input_concepto.getText().toString();

            // Creamos un nuevo objeto Cliente y los añadimos al ArrayList
            Gasto gasto = new Gasto(id,  fecha, concepto, importe);
            Contabilidad.gastos.add(gasto);

            // Insertamos el cliente en la base de datos
            bd = admin.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("fecha", fecha);
            cv.put("concepto", concepto);
            cv.put("importe", importe);
            bd.insert("Gastos", null, cv);
            bd.close();

            Contabilidad.adapter.notifyDataSetChanged();
            Toast.makeText(context, "Gasto guardado", Toast.LENGTH_LONG).show();
        });
        return builder.create();
    }

    public AnhadirGasto(Context context) {
        this.context = context;
    }
}
