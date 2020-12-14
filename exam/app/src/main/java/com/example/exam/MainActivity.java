package com.example.exam;

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
    private RecyclerView recyclerView;
    private Adapter mon_adapter;

    static final String BUNDLE_KEY = "MA_LISTE";
    private ArrayList<ParcelableTask> listParcelable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linear_layout);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mon_adapter = new Adapter(this);
        recyclerView.setAdapter(mon_adapter);

        listParcelable = new ArrayList<>();

        //============================================ GESTION SWIPE ============================================ //
        ItemTouchHelper.SimpleCallback item_touch_helper_callback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = (int) viewHolder.itemView.getTag();

                        try{
                            listParcelable.remove(position);
                            mon_adapter.supprmie(position);
                        }
                        catch (IndexOutOfBoundsException e){

                        }
                    }
                };
        new ItemTouchHelper(item_touch_helper_callback).attachToRecyclerView(recyclerView);

        //======================================================================================== //

        if (savedInstanceState == null){
            //ajouterListeText();
            //si on a pas d'instance saved on ajoute les lognes de fake data
        }
        else {
            listParcelable = savedInstanceState.getParcelableArrayList(BUNDLE_KEY);
        }
        for (ParcelableTask pt : listParcelable)
            mon_adapter.ajoute();
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(BUNDLE_KEY, listParcelable);
    }

    //============================================ MENU ============================================ //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemid = item.getItemId();

        switch (itemid) {
            case R.id.menu_action_1:
                Toast msg = Toast.makeText(this, "ajouter un autre item", Toast.LENGTH_SHORT);
                msg.show();
                Intent childIntent = new Intent(MainActivity.this, AddActivity.class);
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


                //ajouter les bon champss
                addToArrayParcelable();
                mon_adapter.ajoute();
            }
        }
    }

    //======================================================================================== //

    //============================================ ParcelableTask ============================================ //
    static class ParcelableTask implements Parcelable {
        private String text, prio;

        public ParcelableTask() {
            /*this.text = text;
            this.prio = prio;*/
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

        private void addToArrayParcelable() {
        //ajouter les bons champs
            listParcelable.add(new ParcelableTask());
        }
    //======================================================================================== //
}