package com.leop.bdsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DAOContacto dao;
    ArrayList<Contacto> lista;
    Adaptador adapter;
    Contacto c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dao = new DAOContacto(this);
        lista = dao.verTodos();
        adapter = new Adaptador(this, lista, dao);
        final ListView list = (ListView) findViewById(R.id.lista);
        Button agregar = (Button) findViewById(R.id.agregar);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //diálogo para ver vista previa de registro vista.xml
            }
        });
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Diálogo de agregar dialogo.xml
                final Dialog dialogo = new Dialog(MainActivity.this);
                dialogo.setTitle("Registro nuevo");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.dialogo);
                dialogo.show();
                final EditText usuario = (EditText) dialogo.findViewById(R.id.usuario);
                final EditText email = (EditText) dialogo.findViewById(R.id.email);
                final EditText telefono = (EditText) dialogo.findViewById(R.id.telefono);
                final EditText fechaNac = (EditText) dialogo.findViewById(R.id.fecha);
                Button guardar = (Button) dialogo.findViewById(R.id.d_agregar);
                guardar.setText("Agregar");
                Button cancelar = (Button) dialogo.findViewById(R.id.d_cancelar);
                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            c = new Contacto(usuario.getText().toString(),
                                    email.getText().toString(), telefono.getText().toString(),
                                    fechaNac.getText().toString());
                            dao.insertar(c);
                            lista = dao.verTodos();
                            adapter.notifyDataSetChanged();
                            dialogo.dismiss();
                        }catch (Exception e){
                            Toast.makeText(getApplication(), "Error", Toast.LENGTH_SHORT).show();
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
    }
}
