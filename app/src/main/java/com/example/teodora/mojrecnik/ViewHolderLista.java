package com.example.teodora.mojrecnik;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolderLista  extends RecyclerView.ViewHolder{

    protected TextView rec;
    protected TextView znacenje;

    public ViewHolderLista(View itemView) {
        super(itemView);

        rec = (TextView)itemView.findViewById(R.id.rec_cardview);
        znacenje = (TextView)itemView.findViewById(R.id.definicija_cardview);
    }

    public void updateUI(Rec r){

        rec.setText(r.getZadataRec().toUpperCase());
        znacenje.setText(r.getDefinicijaZadateReci());

    }
}
