package com.example.teodora.mojrecnik;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import javax.xml.transform.Templates;

public class Adapter extends RecyclerView.Adapter<ViewHolderLista> {


    private TextView prazanView;

    private ArrayList<Rec> listaReci;
    private Context context;

    public Adapter(ArrayList<Rec> listaReci, Context context, TextView textView) {

        this.listaReci = listaReci;
        this.context = context;
        this.prazanView = textView;
    }



    @Override
    public ViewHolderLista onCreateViewHolder(ViewGroup parent, int viewType) {

        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_cardview,parent,false);
        return new ViewHolderLista(card);
    }



    @Override
    public int getItemCount() {
        if (listaReci == null)return 0;
        return listaReci.size();
    }




    @Override
    public void onBindViewHolder(final ViewHolderLista holder, final int position) {

        Rec detaljiReci = listaReci.get(position);
        holder.updateUI(detaljiReci);


        holder.rec.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setMessage("Da li želite da izbrišete ovu reč?")
                        .setCancelable(false)
                        .setNegativeButton("NE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("DA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                RecnikHelper recnikHelper = new RecnikHelper(context);
                                listaReci = recnikHelper.izbrisiIzBaze(listaReci.get(position).getZadataRec());   // ova fja obavi brisanje i vraca listu reci kako izgleda posle brisanja

                                if (listaReci == null){

                                    prazanView.setVisibility(View.VISIBLE);

                                }else {

                                    notifyItemRemoved(position);
                                }

                            }
                        });


                AlertDialog alertDialog = builder.create();
                alertDialog.setTitle("Brisanje izabrane reči");
                alertDialog.show();


                return false;
            }
        });

    }

}
