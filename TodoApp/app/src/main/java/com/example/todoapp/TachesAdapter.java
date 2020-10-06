package com.example.todoapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TachesAdapter extends ArrayAdapter<TachesAdapter.TachesItem> {
    private Context context;

    public TachesAdapter(@NonNull Context context) {
        super(context, R.layout.ligne); //R.layout est useless
        this.context = context;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        TachesItem item = getItem(position);
        View racine = convertView;

        if(racine == null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            racine = layoutInflater.inflate(R.layout.ligne,parent,false);
        }

        //recup views
        TextView tvTache = (TextView)racine.findViewById(R.id.textTache);
        View vTache = (View)racine.findViewById(R.id.coloredSquare);

        // saisie des valeurs


        tvTache.setText(item.textTache);

        if(item.priority.equals("basse"))
            vTache.setBackgroundColor(0xFFFFFF36);
        if(item.priority.equals("moyenne"))
            vTache.setBackgroundColor(0xFFf2cb2e);
        if(item.priority.equals("haute"))
            vTache.setBackgroundColor(0xFFe33e32);


        return racine;
    }

    public void ajoute(String text, String priority){
        TachesItem item = new TachesItem();
        item.textTache = text;
        item.priority = priority;

        add(item);
    }
    //classse pour les taches stockés
    static class TachesItem {
        String textTache;
        String priority;
    }
}
