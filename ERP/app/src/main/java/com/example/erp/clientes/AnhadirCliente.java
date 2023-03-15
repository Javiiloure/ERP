package com.example.erp.clientes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.erp.R;

// https://www.youtube.com/watch?v=dxIV_25Nb-Q&ab_channel=CursosAndroidANT

public class AnhadirCliente extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.anhadir_cliente, null);

        builder.setView(view).setTitle("Añadir cliente").setNegativeButton("Cancelar", (dialog, which) -> {

        }).setPositiveButton("Guardar", (dialog, which) -> {

        });
        return builder.create();
    }
}
