package com.crisdev.contactkemonappaiep;

import android.os.Bundle;
import android.view.View;
//import android.widget.Button;

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

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ContactoAdapter adapter;
    private List<Contacto> listaContactos;
    private AppDatabase db;
    private FloatingActionButton btnAgregarFlotante;
    private EditText edtBuscar;

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
        btnAgregarFlotante = findViewById(R.id.btnAgregarFlotante);
        edtBuscar = findViewById(R.id.edtBuscar);

        edtBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrarContactos(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Configurar RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listaContactos = new ArrayList<>(db.contactoDao().obtenerTodos());
        adapter = new ContactoAdapter(listaContactos, contacto -> {
            Intent intent = new Intent(MainActivity.this, FormularioActivity.class);
            intent.putExtra("contacto_id", contacto.id);
            startActivity(intent);
        }, db.contactoDao());
        recyclerView.setAdapter(adapter);

        btnAgregarFlotante.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FormularioActivity.class);
            startActivity(intent);
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

    private void filtrarContactos(String texto) {
        List<Contacto> filtrados = new ArrayList<>();
        for (Contacto c : db.contactoDao().obtenerTodos()) {
            if (c.nombre.toLowerCase().contains(texto.toLowerCase()) ||
                    c.telefono.contains(texto) ||
                    c.email.toLowerCase().contains(texto.toLowerCase())) {
                filtrados.add(c);
            }
        }
        listaContactos.clear();
        listaContactos.addAll(filtrados);
        adapter.notifyDataSetChanged();
    }
}