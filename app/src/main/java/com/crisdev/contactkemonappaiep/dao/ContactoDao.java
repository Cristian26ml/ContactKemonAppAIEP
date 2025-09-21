package com.crisdev.contactkemonappaiep.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.Delete;
import androidx.room.Query;

import com.crisdev.contactkemonappaiep.model.Contacto;

import java.util.List;

@Dao
public interface ContactoDao {

    @Insert
    void insertar(Contacto contacto);

    @Update
    void actualizar(Contacto contacto);

    @Delete
    void eliminar(Contacto contacto);

    @Query("SELECT * FROM contactos")
    List<Contacto> obtenerTodos();

    @Query("SELECT * FROM contactos WHERE favorito = 1")
    List<Contacto> obtenerFavoritos();

    @Query("SELECT * FROM contactos WHERE id = :id LIMIT 1")
    Contacto buscarPorId(int id);

    @Query("SELECT * FROM contactos ORDER BY favorito DESC, nombre ASC")
    List<Contacto> obtenerOrdenados();

    @Query("SELECT * FROM contactos WHERE nombre LIKE '%' || :nombre || '%'")
    List<Contacto> buscarPorNombre(String nombre);

    @Query("SELECT COUNT(*) FROM contactos WHERE favorito = 1")
    int contarFavoritos();
}
