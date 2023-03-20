package com.example.erp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.erp.clientes.Clientes;
import com.example.erp.proveedores.Proveedores;
import com.google.android.material.navigation.NavigationView;

public class Contabilidad extends AppCompatActivity {

    protected static SQLiteDatabase bd;
    protected static final String nombreBD = "ERP";
    protected final AdminBD admin = new AdminBD(Contabilidad.this, nombreBD, null, 1);
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public NavigationView navigationView;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contabilidad);

        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TextView mostrar_clientes = findViewById(R.id.mostrar_clientes);
        TextView mostrar_remesa = findViewById(R.id.mostrar_remesa);

        // Obtenemos el numero de clientes activos y los mostramos en pantalla
        bd = admin.getWritableDatabase();
        Cursor c = bd.rawQuery("select count(*) from clientes;", null);
        c.moveToFirst();
        int clientes = c.getInt(0);
        c.close();
        mostrar_clientes.setText(String.valueOf(clientes));

        // Calculamos la siguiente remesa y la mostramos por pantalla
        double remesa = clientes * 29.90;
        mostrar_remesa.setText(remesa + "0€");

        // Controlamos el menu lateral
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            Intent intent;
            switch (id){
                case R.id.contabilidad:
                    intent = new Intent(Contabilidad.this, Contabilidad.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.clientes:
                    intent = new Intent(Contabilidad.this, Clientes.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.proveedores:
                    intent = new Intent(Contabilidad.this, Proveedores.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.correo_masivo:
                    intent = new Intent(Contabilidad.this, CorreoMasivo.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.ajustes:
                    intent = new Intent(Contabilidad.this, Ajustes.class);
                    startActivity(intent);
                    finish();
            }
            return false;
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}