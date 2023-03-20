package com.example.erp.clientes;

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

public class ModificarCliente extends AppCompatDialogFragment {

    private Context context;
    private int id;

    protected SQLiteDatabase bd;
    protected static final String nombreBD = "ERP";
    public AdminBD admin;

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.gestionar_cliente, null);

        admin = new AdminBD(context, nombreBD, null, 1);

        EditText input_nombre = view.findViewById(R.id.input_nombre);
        EditText input_apellidos = view.findViewById(R.id.input_apellidos);
        EditText input_fnacim = view.findViewById(R.id.input_fnacim);
        EditText input_telefono = view.findViewById(R.id.input_telefono);
        EditText input_email = view.findViewById(R.id.input_email);

        // Buscamos en el ArrayList del activity el cliente con el misma id
        int i;
        for(i = 0; i < Clientes.clientes.size(); i++) {
            // Una vez encontrado rellenamos los datos
            if(Clientes.clientes.get(i).getId() == id) {
                input_nombre.setText(Clientes.clientes.get(i).getNombre());
                input_apellidos.setText(Clientes.clientes.get(i).getApellidos());
                input_fnacim.setText(Clientes.clientes.get(i).getFecha_nacimiento());
                input_telefono.setText(String.valueOf(Clientes.clientes.get(i).getTelefono()));
                input_email.setText(Clientes.clientes.get(i).getEmail());
                break;
            }
        }

        int finalI = i;
        builder.setView(view).setTitle("Modificar cliente").setNegativeButton("Cancelar", (dialog, which) -> {
            Toast.makeText(context, "Cancelado", Toast.LENGTH_LONG).show();
        }).setPositiveButton("Guardar", (dialog, which) -> {

            String nombre = input_nombre.getText().toString();
            String apellidos = input_apellidos.getText().toString();
            String fnacim = input_fnacim.getText().toString();
            int telefono = Integer.parseInt(input_telefono.getText().toString());
            String email = input_email.getText().toString();

            // Modificamos el cliente en el ArrayList
            Clientes.clientes.get(finalI).setNombre(nombre);
            Clientes.clientes.get(finalI).setApellidos(apellidos);
            Clientes.clientes.get(finalI).setFecha_nacimiento(fnacim);
            Clientes.clientes.get(finalI).setTelefono(telefono);
            Clientes.clientes.get(finalI).setEmail(email);

            // Modificamos el cliente en la base da datos
            bd = admin.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("nombre", nombre);
            cv.put("apellidos", apellidos);
            cv.put("fecha_nacimiento", fnacim);
            cv.put("telefono", telefono);
            cv.put("email", email);
            bd.update("clientes", cv, "id = ?", new String[]{String.valueOf(id)});
            bd.close();

            Toast.makeText(context, "Cliente actualizado", Toast.LENGTH_LONG).show();
        });

        return builder.create();
    }

    public ModificarCliente(Context context, int id) {
        this.context = context;
        this.id = id;
    }
}
