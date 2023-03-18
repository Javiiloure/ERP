package com.example.erp.proveedores;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.erp.AdminBD;
import com.example.erp.R;

public class AnhadirProveedor extends AppCompatDialogFragment {

    private Context context;
    protected SQLiteDatabase bd;
    protected static final String nombreBD = "ERP";
    public AdminBD admin;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.gestionar_proveedor, null);

        admin = new AdminBD(context, nombreBD, null, 1);

        EditText input_nombre = view.findViewById(R.id.input_nombre);
        EditText input_telefono = view.findViewById(R.id.input_telefono);
        EditText input_email = view.findViewById(R.id.input_email);
        EditText input_direccion = view.findViewById(R.id.input_direccion);
        EditText input_servicio = view.findViewById(R.id.input_servicio);

        builder.setView(view).setTitle("Añadir cliente").setNegativeButton("Cancelar", (dialog, which) -> {
            Toast.makeText(context, "Cancelado", Toast.LENGTH_LONG).show();
        }).setPositiveButton("Guardar", (dialog, which) -> {

            // Obtenemos el id del ultimo proveedor y lo incrementamos en 1
            int id = 0;
            for(int i = 0; i < Proveedores.proveedores.size(); i++) {
                id = Proveedores.proveedores.get(i).getId();
            }
            id++;

            String nombre = input_nombre.getText().toString();
            int telefono = Integer.parseInt(input_telefono.getText().toString());
            String email = input_email.getText().toString();
            String direccion = input_direccion.getText().toString();
            String servicio = input_servicio.getText().toString();

            // Creamos un nuevo objeto Proveedor y los añadimos al ArrayList
            Proveedor proveedor = new Proveedor(id, nombre, telefono, email, direccion, servicio);
            Proveedores.proveedores.add(proveedor);

            // Insertamos el cliente en la base de datos
            bd = admin.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("nombre", nombre);
            cv.put("telefono", telefono);
            cv.put("email", email);
            cv.put("direccion", direccion);
            cv.put("servicio", servicio);
            bd.insert("Proveedores", null, cv);
            bd.close();

            Toast.makeText(context, "Cliente agregado", Toast.LENGTH_LONG).show();
        });
        return builder.create();
    }
    public AnhadirProveedor(Context context) {
        this.context = context;
    }
}
