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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.erp.AdminBD;
import com.example.erp.MainActivity;
import com.example.erp.R;

// https://www.youtube.com/watch?v=dxIV_25Nb-Q&ab_channel=CursosAndroidANT

public class AnhadirCliente extends AppCompatDialogFragment {

    private Context context;
    protected SQLiteDatabase bd;
    protected static final String nombreBD = "ERP";
    public AdminBD admin = new AdminBD(context, nombreBD, null, 1);

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        admin = new AdminBD(this.context, nombreBD, null, 1);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.anhadir_cliente, null);

        EditText input_nombre = view.findViewById(R.id.input_nombre);
        EditText input_apellidos = view.findViewById(R.id.input_apellidos);
        EditText input_fnacim = view.findViewById(R.id.input_fnacim);
        EditText input_telefono = view.findViewById(R.id.input_telefono);
        EditText input_email = view.findViewById(R.id.input_email);

        builder.setView(view).setTitle("Añadir cliente").setNegativeButton("Cancelar", (dialog, which) -> {




        }).setPositiveButton("Guardar", (dialog, which) -> {

            // Obtenemos el id del ultimo cliente y lo incrementamos en 1
            int id = 0;
           for(int i = 0; i < Clientes.clientes.size(); i++) {
               id = Clientes.clientes.get(i).getId();
           }
           id++;

           String nombre = input_nombre.getText().toString();
           String apellidos = input_apellidos.getText().toString();
           String fnacim = input_fnacim.getText().toString();
           int telefono = Integer.parseInt(input_telefono.getText().toString());
           String email = input_email.getText().toString();

          // Creamos un nuevo objeto Cliente y los añadimos al ArrayList
           Cliente cliente = new Cliente(id, nombre, apellidos, fnacim, telefono, email);
           Clientes.clientes.add(cliente);

          // Insertamos el cliente en la base de datos
            bd = admin.getWritableDatabase();
           ContentValues cv = new ContentValues();
           cv.put("nombre", nombre);
           cv.put("apellidos", apellidos);
           cv.put("fecha_nacimiento", fnacim);
           cv.put("telefono", telefono);
           cv.put("email", email);
           bd.insert("Clientes", null, cv);
           bd.close();
        });
        return builder.create();
    }

    public AnhadirCliente(Context context) {
        this.context = context;
    }
}
