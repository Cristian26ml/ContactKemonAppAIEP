package com.crisdev.contactkemonappaiep;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.crisdev.contactkemonappaiep.adapter.ContactoAdapter;
import com.crisdev.contactkemonappaiep.dao.ContactoDao;
import com.crisdev.contactkemonappaiep.database.AppDatabase;
import com.crisdev.contactkemonappaiep.model.Contacto;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ContactoAdapter adapter;
    private List<Contacto> listaContactos;
    private AppDatabase db;
    private Button btnAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar Room con base persistente
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "mi_db")
                .allowMainThreadQueries()
                .build();

        // Referencias UI
        recyclerView = findViewById(R.id.recyclerContactos);
        btnAgregar = findViewById(R.id.btnAgregar);

        // Configurar RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listaContactos = new ArrayList<>(db.contactoDao().obtenerTodos());
        adapter = new ContactoAdapter(listaContactos, contacto -> {
            Intent intent = new Intent(MainActivity.this, FormularioActivity.class);
            intent.putExtra("contacto_id", contacto.id);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        // Acción del botón: agregar contacto de prueba
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contacto nuevo = new Contacto();
                nuevo.nombre = "Contacto solo de prueba aiep";
                nuevo.telefono = "912345678";
                nuevo.email = "pruebaaiep@correo.com";
                nuevo.favorito = false;

                db.contactoDao().insertar(nuevo);

                // Actualizar lista
                listaContactos.clear();
                listaContactos.addAll(db.contactoDao().obtenerTodos());
                adapter.notifyDataSetChanged();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();

        // Actualizar lista al volver a MainActivity
        listaContactos.clear();
        listaContactos.addAll(db.contactoDao().obtenerTodos());
        adapter.notifyDataSetChanged();
    }
}