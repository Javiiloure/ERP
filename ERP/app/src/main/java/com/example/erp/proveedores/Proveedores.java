package com.example.erp.proveedores;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erp.AdminBD;
import com.example.erp.Ajustes;
import com.example.erp.Contabilidad;
import com.example.erp.CorreoMasivo;
import com.example.erp.R;
import com.example.erp.clientes.Clientes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

    TextView mostrar_id;
    TextView mostrar_nombre;
    TextView mostrar_telefono;
    TextView mostrar_email;
    TextView mostrar_direccion;
    TextView mostrar_servicio;

    @SuppressLint("NonConstantResourceId")
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
        mostrar_id = findViewById(R.id.mostrar_id);
        mostrar_nombre = findViewById(R.id.mostrar_nombre);
        mostrar_telefono = findViewById(R.id.mostrar_telefono);
        mostrar_email = findViewById(R.id.mostrar_email);
        mostrar_direccion = findViewById(R.id.mostrar_direccion);
        mostrar_servicio = findViewById(R.id.mostrar_servicio);

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

        // Obtenemos el id introducido y buscamos en el ArrayList
        EditText input_id = findViewById(R.id.input_id);
        ImageView buscar = findViewById(R.id.buscar);
        buscar.setOnClickListener(v -> {
            int aux = Integer.parseInt(input_id.getText().toString());
            for (int i = 0; i < proveedores.size(); i++) {
                if(proveedores.get(i).getId() == aux) {
                    mostrar_id.setText(String.valueOf(proveedores.get(i).getId()));
                    mostrar_nombre.setText(proveedores.get(i).getNombre());
                    mostrar_telefono.setText(String.valueOf(proveedores.get(i).getTelefono()));
                    mostrar_email.setText(proveedores.get(i).getEmail());
                    mostrar_direccion.setText(proveedores.get(i).getDireccion());
                    mostrar_servicio.setText(proveedores.get(i).getServicio());
                    count[0] = i;
                }
            }
        });

        // Botones para avanzar o retroceder entre los clientes
        FloatingActionButton adelante = findViewById(R.id.adelante);
        FloatingActionButton atras = findViewById(R.id.atras);
        adelante.setOnClickListener(v -> {
            if(count[0] == proveedores.size() -  1) return;
            else {
                count[0]++;
                mostrar_id.setText(String.valueOf(proveedores.get(count[0]).getId()));
                mostrar_nombre.setText(proveedores.get(count[0]).getNombre());
                mostrar_telefono.setText(String.valueOf(proveedores.get(count[0]).getTelefono()));
                mostrar_email.setText(proveedores.get(count[0]).getEmail());
                mostrar_direccion.setText(proveedores.get(count[0]).getDireccion());
                mostrar_servicio.setText(proveedores.get(count[0]).getServicio());
            }
        });
        atras.setOnClickListener(v -> {
            if (count[0] == 0) return;
            else{
                count[0]--;
                mostrar_id.setText(String.valueOf(proveedores.get(count[0]).getId()));
                mostrar_nombre.setText(proveedores.get(count[0]).getNombre());
                mostrar_telefono.setText(String.valueOf(proveedores.get(count[0]).getTelefono()));
                mostrar_email.setText(proveedores.get(count[0]).getEmail());
                mostrar_direccion.setText(proveedores.get(count[0]).getDireccion());
                mostrar_servicio.setText(proveedores.get(count[0]).getServicio());
            }
        });

        // Controlamos el menu lateral
        navigationView = findViewById(R.id.navigationView);
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
                case R.id.ajustes:
                    intent = new Intent(Proveedores.this, Ajustes.class);
                    startActivity(intent);
                    finish();
            }
            return false;
        });
    }

    @SuppressWarnings("NonAsciiCharacters")
    public void añadirProveedor(){
        AnhadirProveedor aux = new AnhadirProveedor(Proveedores.this);
        aux.show(getSupportFragmentManager(), "Añadir Proveedor");
    }

    public void modificarProveedor(){
        ModificarProveedor aux = new ModificarProveedor(Proveedores.this, proveedores.get(count[0]).getId());
        aux.show(getSupportFragmentManager(), "Modificar Proveedor");
    }

    public void eliminarProveedor() {

        // Eliminamos el proveedor de la base de datos
        bd = admin.getWritableDatabase();
        bd.delete("proveedores", "id = ?", new String[]{String.valueOf(proveedores.get(count[0]).getId())});
        bd.close();

        // Eliminamos el proveedor del ArrayList
        proveedores.remove(count[0]);
        Toast.makeText(this, "Cliente eliminado correctamente", Toast.LENGTH_SHORT).show();

        // Borramos los datos de la interfaz
        mostrar_id.setText("");
        mostrar_nombre.setText("");
        mostrar_telefono.setText("");
        mostrar_email.setText("");
        mostrar_direccion.setText("");
        mostrar_servicio.setText("");
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

    // Cargamos el menu de los proveedores
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_proveedores, menu);
        return true;
    }

    // Controlamos el menu de proveedores
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.anhadir_proveedor:
                añadirProveedor();
                break;
            case R.id.modificar_proveedor:
                modificarProveedor();
                break;
            case R.id.eliminar_proveedor:
                eliminarProveedor();
                break;
        }
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}