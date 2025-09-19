package com.crisdev.contactkemonappaiep;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.Button;
import androidx.room.Room;
import com.crisdev.contactkemonappaiep.database.AppDatabase;
import com.crisdev.contactkemonappaiep.model.Contacto;
import com.crisdev.contactkemonappaiep.dao.ContactoDao;

public class FormularioActivity extends AppCompatActivity {

    private EditText edtNombre, edtTelefono, edtEmail;
    private CheckBox chkFavorito;
    private Button btnGuardar;
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
            if (contactoExistente == null) {
                Contacto nuevo = new Contacto();
                nuevo.nombre = edtNombre.getText().toString();
                nuevo.telefono = edtTelefono.getText().toString();
                nuevo.email = edtEmail.getText().toString();
                nuevo.favorito = chkFavorito.isChecked();
                db.contactoDao().insertar(nuevo);
            } else {
                contactoExistente.nombre = edtNombre.getText().toString();
                contactoExistente.telefono = edtTelefono.getText().toString();
                contactoExistente.email = edtEmail.getText().toString();
                contactoExistente.favorito = chkFavorito.isChecked();
                db.contactoDao().actualizar(contactoExistente);
            }

            finish(); // Volver a MainActivity
        });
    }
}