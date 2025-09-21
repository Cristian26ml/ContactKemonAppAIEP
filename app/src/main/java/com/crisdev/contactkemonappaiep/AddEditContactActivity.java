package com.crisdev.contactkemonappaiep;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.Button;
import androidx.room.Room;
import com.crisdev.contactkemonappaiep.database.AppDatabase;
import com.crisdev.contactkemonappaiep.model.Contacto;

import android.util.Patterns;
import android.widget.Toast;

public class AddEditContactActivity extends AppCompatActivity {

    private EditText edtNombre, edtTelefono, edtEmail;
    private CheckBox chkFavorito;
    private Button btnGuardar, btnCancelar;
    private AppDatabase db;
    private Contacto contactoExistente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        edtNombre = findViewById(R.id.edtNombre);
        edtTelefono = findViewById(R.id.edtTelefono);
        edtEmail = findViewById(R.id.edtEmail);
        chkFavorito = findViewById(R.id.chkFavorito);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelar = findViewById(R.id.btnCancelar);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "mi_db")
                .allowMainThreadQueries()
                .build();

        // Si viene un contacto para editar
        int contactoId = getIntent().getIntExtra("contacto_id", -1);
        if (contactoId != -1) {
            contactoExistente = db.contactoDao().buscarPorId(contactoId);
            if (contactoExistente != null) {
                edtNombre.setText(contactoExistente.nombre);
                edtTelefono.setText(contactoExistente.telefono);
                edtEmail.setText(contactoExistente.email);
                chkFavorito.setChecked(contactoExistente.favorito);
            }
        }

        btnGuardar.setOnClickListener(v -> {
            if (!validarCampos()) return;
            if (contactoExistente == null) {
                Contacto nuevo = new Contacto();
                nuevo.nombre = edtNombre.getText().toString();
                nuevo.telefono = edtTelefono.getText().toString();
                nuevo.email = edtEmail.getText().toString();
                nuevo.favorito = chkFavorito.isChecked();
                db.contactoDao().insertar(nuevo);
                Toast.makeText(this, "Contacto guardado", Toast.LENGTH_SHORT).show();
            } else {
                contactoExistente.nombre = edtNombre.getText().toString();
                contactoExistente.telefono = edtTelefono.getText().toString();
                contactoExistente.email = edtEmail.getText().toString();
                contactoExistente.favorito = chkFavorito.isChecked();
                db.contactoDao().actualizar(contactoExistente);
                Toast.makeText(this, "Contacto actualizado", Toast.LENGTH_SHORT).show();
            }

            finish(); // Volver a MainActivity
        });
        btnCancelar.setOnClickListener(v -> finish());
    }
    private boolean validarCampos() {
        String nombre = edtNombre.getText().toString().trim();
        String telefono = edtTelefono.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();

        if (nombre.isEmpty() || telefono.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (telefono.length() < 8) {
            Toast.makeText(this, "El teléfono debe tener al menos 8 dígitos", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email no válido", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}