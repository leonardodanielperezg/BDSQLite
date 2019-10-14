package com.leop.bdsqlite;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {

    ArrayList<Contacto> lista;
    DAOContacto dao;
    Contacto c;
    Activity a;
    int id = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Adaptador(Activity a, ArrayList<Contacto> lista, DAOContacto dao){
        this.lista = lista;
        this.a = a;
        this.dao = dao;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Contacto getItem(int i) {
        c = lista.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        c = lista.get(i);
        return c.getId();
    }

    @Override
    public View getView(int posicion, View view, ViewGroup viewGroup) {
         View v = view;
         if (v==null){
             LayoutInflater li = (LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             v = li.inflate(R.layout.item, null);
         }
         c = lista.get(posicion);

        TextView usuario = (TextView) v.findViewById(R.id.t_usuario);
        TextView email = (TextView) v.findViewById(R.id.t_email);
        TextView telefono = (TextView) v.findViewById(R.id.t_telefono);
        TextView fechaNac = (TextView) v.findViewById(R.id.t_fechaNacimiento);
        Button editar = (Button) v.findViewById(R.id.editar);
        Button eliminar = (Button) v.findViewById(R.id.eliminar);
        usuario.setText(c.getUsuario());
        email.setText(c.getEmail());
        telefono.setText(c.getTelefono());
        fechaNac.setText(c.getFechaNacimiento());
        editar.setTag(posicion);
        eliminar.setTag(posicion);
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Diálogo de editar dialogo.xml
                int pos = Integer.parseInt(view.getTag().toString());
                final Dialog dialogo = new Dialog(a);
                dialogo.setTitle("Registro nuevo");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.dialogo);
                dialogo.show();
                final EditText usuario = (EditText) dialogo.findViewById(R.id.usuario);
                final EditText email = (EditText) dialogo.findViewById(R.id.email);
                final EditText telefono = (EditText) dialogo.findViewById(R.id.telefono);
                final EditText fechaNac = (EditText) dialogo.findViewById(R.id.fecha);
                Button guardar = (Button) dialogo.findViewById(R.id.d_agregar);
                guardar.setText("Guardar");
                Button cancelar = (Button) dialogo.findViewById(R.id.d_cancelar);
                c = lista.get(pos);
                setId(c.getId());
                usuario.setText(c.getUsuario());
                email.setText(c.getEmail());
                telefono.setText(c.getTelefono());
                fechaNac.setText(c.getFechaNacimiento());
                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            c = new Contacto(getId(), usuario.getText().toString(),
                                    email.getText().toString(), telefono.getText().toString(),
                                    fechaNac.getText().toString());
                            dao.editar(c);
                            lista = dao.verTodos();
                            notifyDataSetChanged();
                            dialogo.dismiss();
                        }catch (Exception e){
                            Toast.makeText(a, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogo.dismiss();

                    }
                });
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Diálogo para confirmar si o no
                int pos = Integer.parseInt(view.getTag().toString());
                c = lista.get(pos);
                setId(c.getId());
                AlertDialog.Builder del = new AlertDialog.Builder(a);
                del.setMessage("¿Desea eliminar?");
                del.setCancelable(false);
                del.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao.eliminar(getId());
                        lista = dao.verTodos();
                        notifyDataSetChanged();
                    }
                });
                del.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                del.show();
            }
        });

        return v;
    }
}
