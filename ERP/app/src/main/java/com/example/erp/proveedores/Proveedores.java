package com.example.erp.proveedores;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.TextureView;
import android.widget.TextView;

import com.example.erp.AdminBD;
import com.example.erp.Ajustes;
import com.example.erp.Contabilidad;
import com.example.erp.CorreoMasivo;
import com.example.erp.R;
import com.example.erp.RRHH;
import com.example.erp.clientes.Cliente;
import com.example.erp.clientes.Clientes;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Proveedores extends AppCompatActivity {

    protected static SQLiteDatabase bd;
    protected static final String nombreBD = "ERP";
    protected final AdminBD admin = new AdminBD(Proveedores.this, nombreBD, null, 1);
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public NavigationView navigationView;
    public static ArrayList<Proveedor> proveedores;

    final int[] count = {0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedores);

        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Enlazamos los TextView del layout
        TextView mostrar_id = findViewById(R.id.mostrar_id);
        TextView mostrar_nombre = findViewById(R.id.mostrar_nombre);
        TextView mostrar_telefono = findViewById(R.id.mostrar_telefono);
        TextView mostrar_email = findViewById(R.id.mostrar_email);
        TextView mostrar_direccion = findViewById(R.id.mostrar_direccion);
        TextView mostrar_servicio = findViewById(R.id.mostrar_servicio);

        // Obtenemos los proveedores de la base de datos y los guardamos en el ArrayList
        proveedores = new ArrayList<>();
        getProveedores();

        // Rellenamos los datos con el primer proveedor del ArrayList si no esta vacio
        if(proveedores.size() > 0) {
            mostrar_id.setText(String.valueOf(proveedores.get(count[0]).getId()));
            mostrar_nombre.setText(proveedores.get(count[0]).getNombre());
            mostrar_telefono.setText(String.valueOf(proveedores.get(count[0]).getTelefono()));
            mostrar_email.setText(proveedores.get(count[0]).getEmail());
            mostrar_direccion.setText(proveedores.get(count[0]).getDireccion());
            mostrar_servicio.setText(proveedores.get(count[0]).getServicio());
        }

        // Controlamos el menu lateral
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            Intent intent;
            switch (id){
                case R.id.contabilidad:
                    intent = new Intent(Proveedores.this, Contabilidad.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.clientes:
                    intent = new Intent(Proveedores.this, Clientes.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.proveedores:
                    intent = new Intent(Proveedores.this, Proveedores.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.correo_masivo:
                    intent = new Intent(Proveedores.this, CorreoMasivo.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.RRHH:
                    intent = new Intent(Proveedores.this, RRHH.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.ajustes:
                    intent = new Intent(Proveedores.this, Ajustes.class);
                    startActivity(intent);
                    finish();
            }
            return false;
        });
    }

    public void getProveedores() {
        bd = admin.getReadableDatabase();
        Cursor c = bd.rawQuery("Select * from proveedores", null);
        Proveedor aux;
        while(c.moveToNext()) {
            aux = new Proveedor(c.getInt(0), c.getString(1), c.getInt(2), c.getString(3), c.getString(4), c.getString(5));
            proveedores.add(aux);
        }
        c.close();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}