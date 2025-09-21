package com.crisdev.contactkemonappaiep;

import android.os.Bundle;
//import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.crisdev.contactkemonappaiep.adapter.ContactoAdapter;
import com.crisdev.contactkemonappaiep.database.AppDatabase;
import com.crisdev.contactkemonappaiep.model.Contacto;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.widget.TextView;

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
        listaContactos = new ArrayList<>(db.contactoDao().obtenerOrdenados());
        adapter = new ContactoAdapter(listaContactos, contacto -> {
            Intent intent = new Intent(MainActivity.this, AddEditContactActivity.class);
            intent.putExtra("contacto_id", contacto.id);
            startActivity(intent);
        }, db.contactoDao());
        recyclerView.setAdapter(adapter);

        btnAgregarFlotante.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditContactActivity.class);
            startActivity(intent);
        });
    }
    @Override
    protected void onResume() {
        super.onResume();

        // Esto permite actualizar lista al volver a MainActivity
        listaContactos.clear();
        listaContactos.addAll(db.contactoDao().obtenerOrdenados());
        adapter.notifyDataSetChanged();

        TextView txtResumen = findViewById(R.id.txtResumen);
        int totalFavoritos = db.contactoDao().contarFavoritos();
        txtResumen.setText("Favoritos: " + totalFavoritos);

    }

    private void filtrarContactos(String texto) {
        List<Contacto> filtrados = db.contactoDao().buscarPorNombre(texto);
        listaContactos.clear();
        listaContactos.addAll(filtrados);
        adapter.notifyDataSetChanged();
    }
}