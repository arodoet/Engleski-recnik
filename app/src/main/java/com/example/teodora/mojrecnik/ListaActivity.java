package com.example.teodora.mojrecnik;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class ListaActivity extends AppCompatActivity {

    ArrayList<Rec> lista;
    RecnikHelper recnikHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);


        TextView prazanView = (TextView)findViewById(R.id.empty_view);  // ovo mi je ako je lista prazna



        recnikHelper = new RecnikHelper(getApplicationContext());
        lista = recnikHelper.dajSveReci();


        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycle_lista);
        recyclerView.setHasFixedSize(true);


        if (lista == null) {
            recyclerView.setVisibility(View.GONE);
            prazanView.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            prazanView.setVisibility(View.GONE);
        }


        Adapter adapter = new Adapter(lista,this, prazanView);
        recyclerView.setAdapter(adapter);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

    }
}


