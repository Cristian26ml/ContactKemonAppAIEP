package com.crisdev.contactkemonappaiep.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "contactos")
public class Contacto {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "nombre")
    public String nombre;

    @ColumnInfo(name = "telefono")
    public String telefono;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "favorito")
    public boolean favorito;
}
