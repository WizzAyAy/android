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

    public Adapter (Context context){
        this.context = context;
        list = new ArrayList<>();
    }

    public void ajoute(){
        Adapter.Item item = new Adapter.Item();

        /*item.textTache = text;
        item.priority = priority;*/

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
        //tachesAdapterViewHolder.couleur.setBackgroundColor(0xFFe33e32);


        //setTag pour stocker l'id de l'item directemnt avec la vue
        //AdapterViewHolder.itemView.setTag(i);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        //private TextView tache
        public View itemView;

        public AdapterViewHolder (View itemView){
            super(itemView);
            this.itemView = itemView;
            //ici les itemView.findViewById(R.id. ... );
            //tache = (TextView) itemView.findViewById(R.id.textTache);
        }
    }

    static class Item {
        //ici les differents item qu'on va afficher dans le recycler view
        //String textTache;
    }

}
