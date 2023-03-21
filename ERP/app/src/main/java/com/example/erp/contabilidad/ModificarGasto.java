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

public class ModificarGasto extends AppCompatDialogFragment {

    private Context context;
    private int id;
    protected SQLiteDatabase bd;
    protected static final String nombreBD = "ERP";
    public AdminBD admin;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.gestionar_gasto, null);

        admin = new AdminBD(context, nombreBD, null, 1);

        EditText input_importe = view.findViewById(R.id.input_importe);
        EditText input_fecha = view.findViewById(R.id.input_fecha);
        EditText input_concepto = view.findViewById(R.id.input_concepto);

        // Buscamos en el ArrayList del activity el gasto con el misma id
        int i;
        for(i = 0; i < Contabilidad.gastos.size(); i++) {
            // Una vez encontrado rellenamos los datos
            if(Contabilidad.gastos.get(i).getId() == id) {
                input_importe.setText(String.valueOf(Contabilidad.gastos.get(i).getImporte()));
                input_fecha.setText(Contabilidad.gastos.get(i).getFecha());
                input_concepto.setText(Contabilidad.gastos.get(i).getConcepto());
                break;
            }
        }

        int finalI = i;
        builder.setView(view).setTitle("Modificar gasto").setNegativeButton("Cancelar", (dialog, which) -> {
            Toast.makeText(context, "Cancelado", Toast.LENGTH_LONG).show();
        }).setPositiveButton("Guardar", (dialog, which) -> {

            Double importe = Double.parseDouble(input_importe.getText().toString());
            String fecha = input_fecha.getText().toString();
            String concepto = input_concepto.getText().toString();

            // Modificamos el gasto en el ArrayList
            Contabilidad.gastos.get(finalI).setImporte(importe);
            Contabilidad.gastos.get(finalI).setFecha(fecha);
            Contabilidad.gastos.get(finalI).setConcepto(concepto);

            // Modificamos el gasto en la base da datos
            bd = admin.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("importe", importe);
            cv.put("fecha", fecha);
            cv.put("concepto", concepto);
            bd.update("gastos", cv, "id = ?", new String[]{String.valueOf(id)});
            bd.close();

            Toast.makeText(context, "Cliente actualizado", Toast.LENGTH_LONG).show();
        });
        return builder.create();
    }
    public ModificarGasto(Context context, int id) {
        this.context = context;
        this.id = id;
    }

}
