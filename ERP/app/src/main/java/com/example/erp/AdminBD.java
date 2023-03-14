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
        db.execSQL("Create table clientes(id integer primary key autoincrement, nombre text, apellidos text, fecha_nacimiento text, telefono integer, email text)");
        ContentValues cv = new ContentValues();
        cv.put("nombre", "Javier");
        cv.put("apellidos", "Loureiro Perez");
        cv.put("fecha_nacimiento", "29/10/1999");
        cv.put("telefono", 634616442);
        cv.put("email", "javier.loureiro52@gmail.com");
        db.insert("Clientes", null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}