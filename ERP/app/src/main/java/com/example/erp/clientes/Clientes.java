package com.example.erp.clientes;

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
import com.example.erp.proveedores.Proveedores;
import com.example.erp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Clientes extends AppCompatActivity {

    protected static SQLiteDatabase bd;
    protected static final String nombreBD = "ERP";
    protected final AdminBD admin = new AdminBD(Clientes.this, nombreBD, null, 1);
    public static ArrayList<Cliente> clientes;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public NavigationView navigationView;
    final int[] count = {0};
    private TextView mostrar_id;
    private TextView mostrar_nombre;
    private TextView mostrar_apellidos;
    private TextView mostrar_fnacim;
    private TextView mostrar_telefono;
    private TextView mostrar_email;


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Enlazamos los TextView para mostrar los datos
        mostrar_id = findViewById(R.id.mostrar_id);
        mostrar_nombre = findViewById(R.id.mostrar_nombre);
        mostrar_apellidos = findViewById(R.id.mostrar_apellidos);
        mostrar_fnacim = findViewById(R.id.mostrar_fnacim);
        mostrar_telefono = findViewById(R.id.mostrar_telefono);
        mostrar_email = findViewById(R.id.mostrar_email);

        // Obtenemos los clientes de la base de datos y los guardamos en el ArrayList
        clientes = new ArrayList<>();
        getClientes();

        // Rellenamos los datos con el primer cliente del arraylist si no esta vacio
        if(clientes.size() > 0) {
            mostrar_id.setText(String.valueOf(clientes.get(count[0]).getId()));
            mostrar_nombre.setText(clientes.get(count[0]).getNombre());
            mostrar_apellidos.setText(clientes.get(count[0]).getApellidos());
            mostrar_fnacim.setText(clientes.get(count[0]).getFecha_nacimiento());
            mostrar_telefono.setText(String.valueOf(clientes.get(count[0]).getTelefono()));
            mostrar_email.setText(clientes.get(count[0]).getEmail());
        }

        // Obtenemos el id introducido y buscamos en el ArrayList
         EditText input_id = findViewById(R.id.input_id);
         ImageView buscar = findViewById(R.id.buscar);
         buscar.setOnClickListener(v -> {
            int aux = Integer.parseInt(input_id.getText().toString());
            for (int i = 0; i < clientes.size(); i++) {
                if(clientes.get(i).getId() == aux) {
                    mostrar_id.setText(String.valueOf(clientes.get(i).getId()));
                    mostrar_nombre.setText(clientes.get(i).getNombre());
                    mostrar_apellidos.setText(clientes.get(i).getApellidos());
                    mostrar_fnacim.setText(clientes.get(i).getFecha_nacimiento());
                    mostrar_telefono.setText(String.valueOf(clientes.get(i).getTelefono()));
                    mostrar_email.setText(clientes.get(i).getEmail());
                    count[0] = i;
                }
            }
         });

         // Botones para avanzar o retroceder entre los clientes
        FloatingActionButton adelante = findViewById(R.id.adelante);
        FloatingActionButton atras = findViewById(R.id.atras);
        adelante.setOnClickListener(v -> {
            if(count[0] == clientes.size() -  1) return;
            else {
                count[0]++;
                mostrar_id.setText(String.valueOf(clientes.get(count[0]).getId()));
                mostrar_nombre.setText(clientes.get(count[0]).getNombre());
                mostrar_apellidos.setText(clientes.get(count[0]).getApellidos());
                mostrar_fnacim.setText(clientes.get(count[0]).getFecha_nacimiento());
                mostrar_telefono.setText(String.valueOf(clientes.get(count[0]).getTelefono()));
                mostrar_email.setText(clientes.get(count[0]).getEmail());
            }
        });
        atras.setOnClickListener(v -> {
            if (count[0] == 0) return;
            else{
                count[0]--;
                mostrar_id.setText(String.valueOf(clientes.get(count[0]).getId()));
                mostrar_nombre.setText(clientes.get(count[0]).getNombre());
                mostrar_apellidos.setText(clientes.get(count[0]).getApellidos());
                mostrar_fnacim.setText(clientes.get(count[0]).getFecha_nacimiento());
                mostrar_telefono.setText(String.valueOf(clientes.get(count[0]).getTelefono()));
                mostrar_email.setText(clientes.get(count[0]).getEmail());
            }
        });

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
                case R.id.ajustes:
                    intent = new Intent(Clientes.this, Ajustes.class);
                    startActivity(intent);
                    finish();
            }
            return false;
        });
    }

    // Metodo que usaremos para obtener los clientes de la base de datos
    // y guardarlos en el ArrayList
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

    // Metodo para añadir clientes
    @SuppressWarnings("NonAsciiCharacters")
    public void añadirCliente(){
        AnhadirCliente aux = new AnhadirCliente(Clientes.this);
        aux.show(getSupportFragmentManager(), "Añadir cliente");
    }

    // Metodo para modificar clientes
    public void modificarCliente(){
        ModificarCliente aux = new ModificarCliente(Clientes.this, clientes.get(count[0]).getId());
        aux.show(getSupportFragmentManager(), "Modificar cliente");
    }

    // Metodo para eliminar clientes
    public void eliminarCliente(){

        // Eliminamos el cliente de la base de datos
        bd = admin.getWritableDatabase();
        bd.delete("clientes", "id = ?", new String[]{String.valueOf(clientes.get(count[0]).getId())});
        bd.close();

        // Eliminamos el cliente del ArrayList
        clientes.remove(count[0]);
        Toast.makeText(this, "Cliente eliminado correctamente", Toast.LENGTH_SHORT).show();

        // Borramos los datos de la interfaz
        mostrar_id.setText("");
        mostrar_nombre.setText("");
        mostrar_apellidos.setText("");
        mostrar_fnacim.setText("");
        mostrar_telefono.setText("");
        mostrar_email.setText("");
    }

    // Cargamos el menu de los clientes
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_clientes, menu);
        return true;
    }

    // Controlamos el menu de clientes
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.anhadir_cliente:
                añadirCliente();
                break;
            case R.id.modificar_cliente:
                modificarCliente();
                break;
            case R.id.eliminar_cliente:
                eliminarCliente();
                break;
        }
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}