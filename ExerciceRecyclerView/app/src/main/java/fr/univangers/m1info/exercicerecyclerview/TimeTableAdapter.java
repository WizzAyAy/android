package fr.univangers.m1info.exercicerecyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
----------------------------------------------------
Ce morceau de code source permet d'afficher un élément de type Date sous
la forme d'une chaîne de caractères comme il est demandé. C'est cette
chaîne que vous devrez afficher dans chaque ligne de votre RecyclerView

        String format = "EEE HH:mm";
        SimpleDateFormat formater = new SimpleDateFormat(format);
        String str_date = formater.format(sd.m_date);

----------------------------------------------------
Ce morceau permet de trier par ordre croissant un tableau de SlotDescription :

        monTableau.sort(new Comparator<SlotDescription>() {
            @Override
            public int compare(SlotDescription entry1, SlotDescription entry2) {
                Date date1 = entry1.m_date;
                Date date2 = entry2.m_date;
                return date1.compareTo(date2);
            }
        });
 */

public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.SlotViewHolder> {
    private Context context;
    private ArrayList<SlotDescription> list;



    public TimeTableAdapter(Context context) {
        this.context = context;
        list = new ArrayList<SlotDescription>();
    }

    // ..................
    // À COMPLÉTER
    // ..................

    public void add(SlotDescription slot) {
        TimeTableAdapter.SlotDescription item = new TimeTableAdapter.SlotDescription();
        item.m_date = date;
        item.m_author = author;
        item.m_title = title;

        list.add(item);

        this.notifyItemInserted(list.size() - 1);
    }

    public void remove(int id) {
        list.remove(id);
        this.notifyItemRemoved(id);
        //attention !!!
        notifyItemRangeChanged(id, list.size() - 1);
    }

    @NonNull
    @Override
    public SlotViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SlotViewHolder slotViewHolder, int i) {
        SlotDescription tachesItem = list.get(i);

        slotViewHolder.date.setText(StringtachesItem.m_date);

        slotViewHolder.itemView.setTag(i);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    // Classe statique imbriquée pour gérer la création de ViewHolders
    static class SlotViewHolder extends RecyclerView.ViewHolder {
        private TextView date;
        private TextView title;
        private TextView author;




        public SlotViewHolder(View item_view) {
            super(item_view);
            date = (TextView) itemView.findViewById(R.id.tv_date);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            author = (TextView) itemView.findViewById(R.id.tv_author);
        }
    }


    // Class static pour contenir la description d'une tâche
    static public class SlotDescription {
        public Date m_date;
        public String m_author;
        public String m_title;

        public SlotDescription(String date, String author, String title)
        {
            String format = "dd/MM/yyyy HH:mm";
            SimpleDateFormat formater = new SimpleDateFormat(format);
            try{
                m_date = formater.parse(date);
            } catch (ParseException e) {
                m_date = null;
                Log.d("TimeTable error","Can't parse date");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
            m_author = author;
            m_title = title;
        }
    }
}
