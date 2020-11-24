package com.example.tpdatabase_1;

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
    private ArrayList<personneList> list;

    public Adapter (Context context){
        this.context = context;
        list = new ArrayList<personneList>();
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.ligne,viewGroup,false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder AdapterViewHolder, int i) {
        personneList Item = list.get(i);

        AdapterViewHolder.nom.setText(Item.nom);
        AdapterViewHolder.date.setText(Item.date);

        //setTag pour stocker l'id de l'item directemnt avec la vue
        AdapterViewHolder.itemView.setTag(i);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void ajoute(String nom, String date){
        Adapter.personneList item = new Adapter.personneList();
        item.nom = nom;
        item.date = date;

        list.add(item);

        this.notifyItemInserted(list.size() - 1);
    }

    public void supprmie(int position){
        list.remove(position);
        this.notifyItemRemoved(position);
        //attention !!!
        notifyItemRangeChanged(position, list.size() - 1);
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView nom;
        private TextView date;

        public AdapterViewHolder (View itemView){
            super(itemView);
            nom = (TextView) itemView.findViewById(R.id.tv_nom);
            date = (TextView) itemView.findViewById(R.id.tv_date);
        }
    }

    static class personneList {
        String nom;
        String date;
    }
}
