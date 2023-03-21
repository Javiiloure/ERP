package com.example.erp.contabilidad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erp.AdminBD;
import com.example.erp.Ajustes;
import com.example.erp.CorreoMasivo;
import com.example.erp.R;
import com.example.erp.clientes.Clientes;
import com.example.erp.proveedores.Proveedores;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Contabilidad extends AppCompatActivity {

    protected static SQLiteDatabase bd;
    protected static final String nombreBD = "ERP";
    protected final AdminBD admin = new AdminBD(Contabilidad.this, nombreBD, null, 1);
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public NavigationView navigationView;
    public static ArrayList<Gasto> gastos;
    public static Adaptador adapter;

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

        // Obtenemos los gastos de la base de datos y los mostramos en el ListView
        ListView mostrar_gastos = findViewById(R.id.mostrar_gastos);
        gastos = new ArrayList<>();
        getGastos();
        adapter = new Adaptador(this, gastos);
        mostrar_gastos.setAdapter(adapter);

        final boolean [] longClick = {false};
        //Eliminamos un gasto al hacer click largo sobre este
        mostrar_gastos.setOnItemLongClickListener((adapterView, view, i, l) -> {
            longClick[0] = true;

            AlertDialog.Builder alerta = new AlertDialog.Builder(Contabilidad.this);
            alerta.setTitle("Importante");
            alerta.setMessage("¿ Eliminar este gasto ?");
            alerta.setCancelable(false);
            alerta.setPositiveButton("Confirmar", (alerta1, id) -> {
                Gasto aux = gastos.get(i);
                gastos.remove(i);
                bd = admin.getWritableDatabase();
                bd.execSQL("delete from gastos where id = " + aux.getId() + ";");
                bd.close();
                adapter.notifyDataSetChanged();
                longClick[0] = false;
                Toast.makeText(this, "Gasto eliminado", Toast.LENGTH_SHORT).show();
            });
            alerta.setNegativeButton("Cancelar", (alerta2, id) ->
                    longClick[0] = false);
            alerta.show();
            return false;
        });

        // Modificamos un gasto cuando se haga click sobre este
        mostrar_gastos.setOnItemClickListener((parent, view, position, id) -> {
            if(longClick[0]) return;
            Gasto gasto = (Gasto) mostrar_gastos.getItemAtPosition(position);
            ModificarGasto aux = new ModificarGasto(this, gasto.getId());
            aux.show(getSupportFragmentManager(), "Modificar Gasto");
        });

        // Calculamos los gastos totales y los mostramos en la pantalla
        TextView total_gastos = findViewById(R.id.total_gastos);
        double total = 0;
        for(int i = 0; i < gastos.size(); i++) {
            total += gastos.get(i).getImporte();
        }
        total_gastos.setText(total + "0€");

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

    // Metodo para obtener los gastos de la base de datos
    public void getGastos() {
        bd = admin.getReadableDatabase();
        Cursor c = bd.rawQuery("Select * from gastos", null);
        Gasto aux;
        while(c.moveToNext()) {
            aux = new Gasto(c.getInt(0), c.getString(1), c.getString(2), c.getDouble(3));
            gastos.add(aux);
        }
    }

    // Cargamos el menu de contabilidad
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contabilidad, menu);
        return true;
    }

    // Controlamos el menu de contabilidad
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.anhadir_gasto) {
            AnhadirGasto aux = new AnhadirGasto(this);
            aux.show(getSupportFragmentManager(), "Añadir gasto");
        }
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}