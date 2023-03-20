package com.example.erp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.erp.clientes.Clientes;
import com.example.erp.proveedores.Proveedores;
import com.google.android.material.navigation.NavigationView;

public class CorreoMasivo extends AppCompatActivity {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public NavigationView navigationView;

    // Booleanos para controlar los switch
    boolean empleados;
    boolean proveedores;
    boolean clientes;
    EditText asunto;
    EditText mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correo_masivo);

        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        asunto = findViewById(R.id.input_asunto);
        mensaje = findViewById(R.id.input_mensaje);

        // Controlamos que destinatarios estan activados
        Switch switch_e = findViewById(R.id.enviar_empleados);
        Switch switch_p = findViewById(R.id.enviar_proveedores);
        Switch switch_c = findViewById(R.id.enviar_clientes);
        switch_e.setOnCheckedChangeListener((buttonView, isChecked) -> {
            empleados = isChecked;
        });
        switch_p.setOnCheckedChangeListener((buttonView, isChecked) -> {
            proveedores = isChecked;
        });
        switch_c.setOnCheckedChangeListener((buttonView, isChecked) -> {
            clientes = isChecked;
        });

        // Controlamos el boton de enviar
        Button enviar = findViewById(R.id.enviar);
        enviar.setOnClickListener(v -> {
            if(empleados == false && clientes == false && proveedores == false) {
                Toast.makeText(this, "No hay destinatarios seleccionados", Toast.LENGTH_SHORT).show();
            } else if (mensaje.getText().toString().equals("")) {
                Toast.makeText(this, "El cuerpo del mensaje esta vacio", Toast.LENGTH_SHORT).show();
            } else if(asunto.getText().toString().equals("")) {
                Toast.makeText(this, "El cuerpo del asunto esta vacio", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Correo enviado", Toast.LENGTH_SHORT).show();
            }
        });

        // Controlamos el menu lateral
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            Intent intent;
            switch (id){
                case R.id.contabilidad:
                    intent = new Intent(CorreoMasivo.this, Contabilidad.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.clientes:
                    intent = new Intent(CorreoMasivo.this, Clientes.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.proveedores:
                    intent = new Intent(CorreoMasivo.this, Proveedores.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.correo_masivo:
                    intent = new Intent(CorreoMasivo.this, CorreoMasivo.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.ajustes:
                    intent = new Intent(CorreoMasivo.this, Ajustes.class);
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