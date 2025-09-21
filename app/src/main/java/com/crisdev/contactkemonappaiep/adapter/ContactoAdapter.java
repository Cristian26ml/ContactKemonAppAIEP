package com.crisdev.contactkemonappaiep.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crisdev.contactkemonappaiep.model.Contacto;

import java.util.List;
import com.crisdev.contactkemonappaiep.R;
import android.widget.ImageView;

import com.crisdev.contactkemonappaiep.dao.ContactoDao;



public class ContactoAdapter extends RecyclerView.Adapter<ContactoAdapter.ContactoViewHolder> {

    private List<Contacto> lista;
    private OnContactoClickListener listener;
    private ContactoDao contactoDao;

    public ContactoAdapter(List<Contacto> lista, OnContactoClickListener listener, ContactoDao contactoDao) {
        this.lista = lista;
        this.listener = listener;
        this.contactoDao = contactoDao;
    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contacto, parent, false);
        return new ContactoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) {
        Contacto c = lista.get(position);
        holder.txtNombre.setText(c.nombre);
        holder.txtTelefono.setText("📞 " + c.telefono);
        holder.txtEmail.setText("✉️ " + c.email);


        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onContactoClick(c);
            }
        });
        if (c.isFavorito()) {
            holder.imgFavorito.setImageResource(R.drawable.ic_star);
        } else {
            holder.imgFavorito.setImageResource(R.drawable.ic_star_border);
        }
        holder.imgFavorito.setOnClickListener(v -> {
            boolean nuevoEstado = !c.favorito;
            c.favorito = nuevoEstado;

            // Actualizar visualmente
            if (nuevoEstado) {
                holder.imgFavorito.setImageResource(R.drawable.ic_star);
            } else {
                holder.imgFavorito.setImageResource(R.drawable.ic_star_border);
            }

            // Actualizar en Room (necesitas acceso al DAO aquí)
            if (contactoDao != null) {
                contactoDao.actualizar(c);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ContactoViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtTelefono, txtEmail;
        ImageView imgFavorito;

        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtTelefono = itemView.findViewById(R.id.txtTelefono);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            imgFavorito = itemView.findViewById(R.id.imgFavorito);
        }
    }
    public interface OnContactoClickListener {
        void onContactoClick(Contacto contacto);
    }
}
