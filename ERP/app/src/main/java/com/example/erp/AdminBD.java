package com.example.erp;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminBD extends SQLiteOpenHelper {

    public AdminBD(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Creamos tabla de clientes e insertamos datos de prueba
        db.execSQL("Create table clientes(id integer primary key autoincrement, nombre text, apellidos text, fecha_nacimiento text, telefono integer, email text)");
        ContentValues cv = new ContentValues();
        cv.put("nombre", "Javier");
        cv.put("apellidos", "Loureiro Perez");
        cv.put("fecha_nacimiento", "29/10/1999");
        cv.put("telefono", 634616442);
        cv.put("email", "javier.loureiro52@gmail.com");
        db.insert("clientes", null, cv);

        // Creamos tabla de proveedores e insertamos datos de prueba
        db.execSQL("Create table proveedores(id integer primary key autoincrement, nombre text, telefono integer,email text, direccion text, servicio text)");
        cv.clear();
        cv.put("nombre", "Limpiezas Lei");
        cv.put("telefono", 986486134);
        cv.put("email", "limpiezaslei@gmail.com");
        cv.put("direccion", "Avenida de Madrid 40 4ºE 36214");
        cv.put("servicio", "Empresa de limpieza de todo tipo de instalaciones");
        db.insert("proveedores", null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}