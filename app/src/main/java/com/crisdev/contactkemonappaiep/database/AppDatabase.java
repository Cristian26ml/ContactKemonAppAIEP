package com.crisdev.contactkemonappaiep.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.crisdev.contactkemonappaiep.model.Contacto;
import com.crisdev.contactkemonappaiep.dao.ContactoDao;

@Database(entities = {Contacto.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContactoDao contactoDao();
}

