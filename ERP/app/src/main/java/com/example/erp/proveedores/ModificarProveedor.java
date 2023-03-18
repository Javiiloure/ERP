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

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.erp.AdminBD;
import com.example.erp.R;
import com.example.erp.clientes.Clientes;

public class ModificarProveedor extends AppCompatDialogFragment {

    private Context context;
    private int id;

    protected SQLiteDatabase bd;
    protected static final String nombreBD = "ERP";
    public AdminBD admin;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.gestionar_proveedor, null);

        admin = new AdminBD(context, nombreBD, null, 1);

        EditText input_nombre = view.findViewById(R.id.input_nombre);
        EditText input_telefono = view.findViewById(R.id.input_telefono);
        EditText input_email = view.findViewById(R.id.input_email);
        EditText input_direccion = view.findViewById(R.id.input_direccion);
        EditText input_servicio = view.findViewById(R.id.input_servicio);


        // Buscamos en el ArrayList del activity el proveedor con el mismo id
        int i;
        for(i = 0; i < Proveedores.proveedores.size(); i++) {
            // Una vez encontrado rellenamos los datos
            if(Proveedores.proveedores.get(i).getId() == id) {
                input_nombre.setText(Proveedores.proveedores.get(i).getNombre());
                input_telefono.setText(String.valueOf(Proveedores.proveedores.get(i).getTelefono()));
                input_email.setText(Proveedores.proveedores.get(i).getEmail());
                input_direccion.setText(Proveedores.proveedores.get(i).getDireccion());
                input_servicio.setText(Proveedores.proveedores.get(i).getServicio());
                break;
            }
        }

        int finalI = i;
        builder.setView(view).setTitle("Modificar cliente").setNegativeButton("Cancelar", (dialog, which) -> {
            Toast.makeText(context, "Cancelado", Toast.LENGTH_LONG).show();
        }).setPositiveButton("Guardar", (dialog, which) -> {

            String nombre = input_nombre.getText().toString();
            int telefono = Integer.valueOf(input_telefono.getText().toString());
            String email = input_email.getText().toString();
            String direccion = input_direccion.getText().toString();
            String servicio = input_servicio.getText().toString();


            // Modificamos el cliente en el ArrayList
            Proveedores.proveedores.get(finalI).setNombre(nombre);
            Proveedores.proveedores.get(finalI).setTelefono(telefono);
            Proveedores.proveedores.get(finalI).setEmail(email);
            Proveedores.proveedores.get(finalI).setDireccion(direccion);
            Proveedores.proveedores.get(finalI).setServicio(servicio);

            // Modificamos el cliente en la base da datos
            bd = admin.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("nombre", nombre);
            cv.put("telefono", telefono);
            cv.put("email", email);
            cv.put("direccion", direccion);
            cv.put("servicio", servicio);
            bd.update("proveedores", cv, "id = ?", new String[]{String.valueOf(id)});
            bd.close();

            Toast.makeText(context, "Cliente actualizado", Toast.LENGTH_LONG).show();
        });

        return builder.create();
    }

    public ModificarProveedor(Context context, int id) {
        this.context = context;
        this.id = id;
    }
}
