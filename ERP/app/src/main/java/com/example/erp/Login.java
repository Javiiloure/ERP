package com.example.erp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.erp.contabilidad.Contabilidad;

public class Login extends AppCompatActivity {

    protected SQLiteDatabase bd;
    protected static final String nombreBD = "ERP";
    public final AdminBD admin = new AdminBD(Login.this, nombreBD, null, 1);

    String usuario;
    String contraseña;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bd = admin.getWritableDatabase();

        ImageView login = findViewById(R.id.login);
        EditText input_user = findViewById(R.id.input_usuario);
        EditText input_contraseña = findViewById(R.id.input_contraseña);

        login.setOnClickListener(v -> {
            boolean encontrado = false;
            bd = admin.getReadableDatabase();
            Cursor c = bd.rawQuery("Select * from usuarios", null);
            while (c.moveToNext()) {
                usuario = c.getString(1);
               contraseña = c.getString(2);
               if(input_user.getText().toString().equals(usuario) && input_contraseña.getText().toString().equals(contraseña)) {
                  Intent intent = new Intent(Login.this, Contabilidad.class);
                    startActivity(intent);
                    encontrado = true;
                    finish();
                }
           }
            if(!encontrado) Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
        });
    }
}