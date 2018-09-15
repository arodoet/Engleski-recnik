package com.example.teodora.mojrecnik;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class RecnikHelper extends SQLiteOpenHelper {

    ArrayList<Rec> listaReci;
    Rec elementListe;

    private  SQLiteDatabase db;


    public RecnikHelper(Context context) {

        super(context,"izgovoreneReci.db",null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table mojeReci(id integer primary key autoincrement , izgovorenaRec String, defReci String)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists mojeReci");
        onCreate(db);
    }



    public boolean ubaciUBazu(String rec, String def){

        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("izgovorenaRec", rec);
        values.put("defReci", def);


        long rezultat = db.insert("mojeReci",null,values);    // ova fja vraca id reda koji je ubacen; -1 ako je greska neka


        if (rezultat == -1) return false;
        else return true;
    }

    public boolean daLiPostojiRec(String rec){

        db = this.getReadableDatabase();

        String reci[]={rec};

        Cursor c = db.rawQuery("select * from mojeReci where izgovorenaRec = ?", reci);

        if (c.getCount() == 0) return false;
        else return true;
    }


    public ArrayList<Rec> dajSveReci(){

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from mojeReci", null);


        listaReci = new ArrayList<Rec>();

        if (cursor != null && cursor.getCount()>0) {

            cursor.moveToFirst();

            do {
                elementListe = new Rec(cursor.getString(1), cursor.getString(2));
                listaReci.add(elementListe);
            }
            while (cursor.moveToNext());

            return listaReci;
        }
        return null;
    }


    public ArrayList<Rec> izbrisiIzBaze(String recZaBrisanje){         // vraca listu reci posle brisanja


        db = this.getWritableDatabase();
        db.delete("mojeReci", "izgovorenaRec = ?", new String[]{recZaBrisanje});

        return  dajSveReci();
    }
}












