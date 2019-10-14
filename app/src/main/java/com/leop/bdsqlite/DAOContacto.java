package com.leop.bdsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DAOContacto {
    SQLiteDatabase cx;
    ArrayList<Contacto> lista = new ArrayList<Contacto>();
    Contacto c;
    Context ct;
    String nombreBD = "BDContactos";
    /*String tabla = "create table if not exists Contactos (" +
            "_id integer primary key autoincrement," +
            "usuario text not null," +
            "email text not null," +
            "tel text not null," +
            "fecNacimiento text not null);"
            ;*/
    String tabla = "create table if not exists Contactos(_id integer primary key autoincrement, usuario text not null, email text not null, tel text not null,fechaNacimiento text not null)";
    public DAOContacto(Context c){
        this.ct = c;
        cx = c.openOrCreateDatabase(nombreBD, c.MODE_PRIVATE, null);
        cx.execSQL(tabla);
    }

    public boolean insertar(Contacto c){
        ContentValues contenedor = new ContentValues();
        contenedor.put("usuario", c.getUsuario());
        contenedor.put("email", c.getEmail());
        contenedor.put("tel", c.getTelefono());
        contenedor.put("fechaNacimiento", c.getFechaNacimiento());
        return (cx.insert("Contactos", null, contenedor)) > 0;
        //return true;
    }

    public boolean eliminar(int id){

        return (cx.delete("Contactos", "id="+id, null)) > 0;
    }

    public boolean editar(Contacto c){
        ContentValues contenedor = new ContentValues();
        contenedor.put("usuario", c.getUsuario());
        contenedor.put("email", c.getEmail());
        contenedor.put("telefono", c.getTelefono());
        contenedor.put("fechaNacimiento", c.getFechaNacimiento());
        return (cx.update("Contactos", contenedor, "id="+c.getId(), null)) > 0;
        //return true;
    }

    public ArrayList<Contacto> verTodos(){
        lista.clear();
        Cursor cursor = cx.rawQuery("select * from Contactos", null);
        if (cursor!=null && cursor.getCount()>0) {
            cursor.moveToFirst();
            do{
                lista.add(new Contacto(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)));
            }while (cursor.moveToNext());
        }
        return lista;
    }

    public Contacto verUno(int posicion) {
        Cursor cursor = cx.rawQuery("select * from Contactos", null);
        cursor.moveToPosition(posicion);
        c = new Contacto(cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3));
        return c;
    }
}
