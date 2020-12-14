package com.example.exam;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.AdapterViewHolder> {

    private Context context;
    private ArrayList<Item> list;
    public String taille;

    public Adapter (Context context){
        this.context = context;
        list = new ArrayList<>();
    }

    public void ajoute(String t){
        Adapter.Item item = new Adapter.Item();

        /*item.textTache = text;
        item.priority = priority;*/

        item.text = t;

        list.add(item);
        this.notifyItemInserted(list.size() - 1);
    }

    public void supprmie(int position){
        list.remove(position);
        this.notifyItemRemoved(position);
        //attention !!!
        notifyItemRangeChanged(position, list.size() - 1);
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.ligne,viewGroup,false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder adapterViewHolder, int i) {
        Item Item = list.get(i);

        //AdapterViewHolder.tache.setText(tachesItem.textTache);
        //AdapterViewHolder.couleur.setBackgroundColor(0xFFe33e32);
        System.out.println("============== " + taille + " ==============");

        adapterViewHolder.textView.setText(Item.text);
        adapterViewHolder.textView.setTextSize(Float.parseFloat(taille));


        //setTag pour stocker l'id de l'item directemnt avec la vue
        adapterViewHolder.itemView.setTag(i);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        //private TextView tache
        private TextView textView;

        public AdapterViewHolder (View itemView){
            super(itemView);
            //ici les itemView.findViewById(R.id. ... );
            //tache = (TextView) itemView.findViewById(R.id.textTache);

            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }

    static class Item {
        //ici les differents item qu'on va afficher dans le recycler view
        //String textTache;
        String text;
    }

}
