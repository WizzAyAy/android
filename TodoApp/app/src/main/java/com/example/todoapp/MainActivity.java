package com.example.todoapp;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int CHILD_ACTIVITY_ID = 1;
    private int activity_main;
    private RecyclerView RecyclerViewTache;
    public String[] _tabString;
    private TachesAdapter mon_adapter;

    static final String TACHE_BUNDLE_KEY = "MES_TACHES";
    private ArrayList<ParcelableTask> Tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linear_layout);

        RecyclerViewTache = (RecyclerView)findViewById(R.id.rv_taches);
        RecyclerViewTache.setLayoutManager(new LinearLayoutManager(this));
        mon_adapter = new TachesAdapter(this);
        RecyclerViewTache.setAdapter(mon_adapter);

        Tasks = new ArrayList<ParcelableTask>();

        ItemTouchHelper.SimpleCallback item_touch_helper_callback =
                new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = (int) viewHolder.itemView.getTag();
                        mon_adapter.supprmie(position);
                        Tasks.remove(position);
                    }
                };
        new ItemTouchHelper(item_touch_helper_callback).attachToRecyclerView(RecyclerViewTache);

        if(savedInstanceState == null)
            ajouterListeText();

        else {
            Tasks = savedInstanceState.getParcelableArrayList(TACHE_BUNDLE_KEY);
        }
            for(ParcelableTask pt : Tasks)
                    mon_adapter.ajoute(pt.text, pt.prio);
    }

    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(TACHE_BUNDLE_KEY,Tasks);
    }

    public void ajouterListeText(){
        _tabString = FakeData.get_tasks();
        String ligne;
        String prio;

        for (int i = 0; i < _tabString.length; i++) {
            ligne = _tabString[i];
            prio = ligne.substring(1,2);
            ligne = ligne.substring(3);

            if(prio.equals("1")){
                Tasks.add(new ParcelableTask(ligne,"basse"));
            }

            else if(prio.equals("2")){
                Tasks.add(new ParcelableTask(ligne,"moyenne"));
            }

            else if(prio.equals("3")){
                Tasks.add(new ParcelableTask(ligne,"haute"));
            }


        }
    }

    public void addToArrayParcelable (String text, String prio){
        Tasks.add(new ParcelableTask(text, prio));
    }

    /*public void ajouterItemText(String prio, String text){
        TextView textView = findViewById(R.id.textList);
        String itemText = prio + " " + text;
        textView.setText(textView.getText()+ "\n" + itemText);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemid = item.getItemId();

        switch (itemid){
            case R.id.menu_action_1:
                Toast msg = Toast.makeText(this,"ajouter un autre item", Toast.LENGTH_SHORT);
                msg.show();
                Intent childIntent = new Intent(MainActivity.this, addAcivity.class);
                startActivityForResult(childIntent, CHILD_ACTIVITY_ID);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
             if (requestCode == CHILD_ACTIVITY_ID) {

                 if (resultCode == RESULT_OK) {
                     String prio = data.getStringExtra("prio");
                     String text = data.getStringExtra("text");

                     //mon_adapter.ajoute(text,prio);
                     addToArrayParcelable(text,prio);
                     mon_adapter.ajoute(text,prio);
                 }
             }
    }

    static class ParcelableTask implements Parcelable {
        private String text, prio;

        public ParcelableTask(String text, String prio){
            this.text = text;
            this.prio = prio;
        }
        protected ParcelableTask(Parcel in) {
            text = in.readString();
            prio = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(text);
            dest.writeString(prio);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<ParcelableTask> CREATOR = new Creator<ParcelableTask>() {
            @Override
            public ParcelableTask createFromParcel(Parcel in) {
                return new ParcelableTask(in);
            }

            @Override
            public ParcelableTask[] newArray(int size) {
                return new ParcelableTask[size];
            }
        };
    }
}