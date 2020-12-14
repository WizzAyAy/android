package com.example.todoapp;

import android.content.Context;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TachesAdapter extends RecyclerView.Adapter<TachesAdapter.TachesAdapterViewHolder> {
    private Context context;
    private ArrayList<TachesItem> list;
    public String taille;
    public boolean prioVisible;

    public TachesAdapter (Context context){
        this.context = context;
        list = new ArrayList<TachesItem>();
    }

    public void setTaille(String taille){
        this.taille = taille;
        System.out.println("taille apres le set " + this.taille);
    }

    public void setPrioVisible(boolean b){
        prioVisible = b;
        System.out.println("prio apres le set " + prioVisible);
    }

    @NonNull
    @Override
    public TachesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.ligne,viewGroup,false);
        return new TachesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TachesAdapterViewHolder tachesAdapterViewHolder, int i) {
        TachesItem tachesItem = list.get(i);

        tachesAdapterViewHolder.tache.setText(tachesItem.textTache);

        if(tachesItem.priority.equals("basse"))
            tachesAdapterViewHolder.couleur.setBackgroundColor(0xFFdfeb36);
        if(tachesItem.priority.equals("moyenne"))
            tachesAdapterViewHolder.couleur.setBackgroundColor(0xFFf2cb2e);
        if(tachesItem.priority.equals("haute"))
            tachesAdapterViewHolder.couleur.setBackgroundColor(0xFFe33e32);

        if(taille != null) {
            System.out.println("taile et prio dans onBindViewHolder = " + taille + " " + prioVisible);
            tachesAdapterViewHolder.tache.setTextSize(Float.parseFloat(taille));
            if (prioVisible == false) tachesAdapterViewHolder.couleur.setVisibility(View.INVISIBLE);
            tachesAdapterViewHolder.couleur.setVisibility(View.VISIBLE);
        }

        //setTag pour stocker l'id de l'item directemnt avec la vue
        tachesAdapterViewHolder.itemView.setTag(i);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void ajoute(String text, String priority){
        TachesAdapter.TachesItem item = new TachesAdapter.TachesItem();
        item.textTache = text;
        item.priority = priority;

        list.add(item);

        this.notifyItemInserted(list.size() - 1);
    }

    public void supprmie(int position){
        list.remove(position);
        this.notifyItemRemoved(position);
        //attention !!!
        notifyItemRangeChanged(position, list.size() - 1);
    }

    public class TachesAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView tache;
        private View couleur;

        public TachesAdapterViewHolder (View itemView){
            super(itemView);
            tache = (TextView) itemView.findViewById(R.id.textTache);
            couleur = (View) itemView.findViewById(R.id.coloredSquare);
        }
    }
    static class TachesItem {
        String textTache;
        String priority;
    }
}
