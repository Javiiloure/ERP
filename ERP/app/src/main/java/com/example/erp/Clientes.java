package com.example.erp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Clientes extends AppCompatActivity {

    protected static SQLiteDatabase bd;
    protected static final String nombreBD = "ERP";
    protected final AdminBD admin = new AdminBD(Clientes.this, nombreBD, null, 1);
    public ArrayList<Cliente> clientes;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Obtenemos los clientes de la base de datos y los guardamos en el ArrayList
        clientes = new ArrayList<>();
        getClientes();

        // Controlamos el menu lateral
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            Intent intent;
            switch (id){
                case R.id.contabilidad:
                    intent = new Intent(Clientes.this, Contabilidad.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.clientes:
                    intent = new Intent(Clientes.this, Clientes.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.proveedores:
                    intent = new Intent(Clientes.this, Proveedores.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.correo_masivo:
                    intent = new Intent(Clientes.this, CorreoMasivo.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.RRHH:
                    intent = new Intent(Clientes.this, RRHH.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.ajustes:
                    intent = new Intent(Clientes.this, Ajustes.class);
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

    // Metodo para obtener los clientes de la base de datos
    public void getClientes() {
        bd = admin.getReadableDatabase();
        Cursor c = bd.rawQuery("Select * from clientes", null);
        Cliente aux;
        while(c.moveToNext()) {
            aux = new Cliente(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getInt(4), c.getString(5));
            clientes.add(aux);
        }
        c.close();
    }
}