package com.example.todoapp;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final int CHILD_ACTIVITY_ID = 1;
    private int activity_main;
    private RecyclerView RecyclerViewTache;
    public String[] _tabString;
    private TachesAdapter mon_adapter;

    private DbHelper mDbHelper;
    private SQLiteDatabase db;

    static final String TACHE_BUNDLE_KEY = "MES_TACHES";
    private ArrayList<ParcelableTask> Tasks;

    private SharedPreferences app_prefs;

    private String taille;
    private Boolean prioVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linear_layout);

        RecyclerViewTache = (RecyclerView) findViewById(R.id.rv_taches);
        RecyclerViewTache.setLayoutManager(new LinearLayoutManager(this));
        mon_adapter = new TachesAdapter(this);
        RecyclerViewTache.setAdapter(mon_adapter);

        app_prefs = PreferenceManager.getDefaultSharedPreferences(this);

        mDbHelper = new DbHelper(this);
        db = mDbHelper.getWritableDatabase();

        String[] projection = {
                BaseColumns._ID,
                ContratDB.item.COLUMN_TEXT,
                ContratDB.item.COLUMN_PRIO
        };

        // Filtrer les résultats WHERE "nom" = 'Dupont'
        // Trier les résultas
        String sortOrder = ContratDB.item.COLUMN_TEXT + " DESC";
        Cursor cursor = db.query(
                ContratDB.item.TABLE_NAME, // La table
                projection, // Les colonnes à récupérer (null pour toutes)
                null, // La clause WHERE
                null, // Les valeurs pour la clause WHERE
                null, // Grouper les lignes ?
                null, // Filtrer les lignes ?
                sortOrder // Trier les lignes
        );

        Tasks = new ArrayList<ParcelableTask>();

        //db.delete(ContratDB.item.TABLE_NAME,null,null);

        while(cursor.moveToNext()) {
            String text = cursor.getString(cursor.getColumnIndexOrThrow(ContratDB.item.COLUMN_TEXT));
            String prio = cursor.getString(cursor.getColumnIndexOrThrow(ContratDB.item.COLUMN_PRIO));
            addToArrayParcelable(text,prio);
        }



        ItemTouchHelper.SimpleCallback item_touch_helper_callback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = (int) viewHolder.itemView.getTag();

                        String selection = ContratDB.item.COLUMN_TEXT + " LIKE ?";
                        // Specify arguments in placeholder order
                        try {
                            String[] selectionArgs = {Tasks.get(position).text};

                            // Issue SQL statement.
                            int deletedRows = db.delete(ContratDB.item.TABLE_NAME, selection, selectionArgs);


                        Tasks.remove(position);
                        mon_adapter.supprmie(position);
                        }
                        catch (IndexOutOfBoundsException e){
                            db.delete(ContratDB.item.TABLE_NAME,null,null);
                            System.out.println("base de donée supp car bug sur last item supp");
                        }
                    }
                };
        new ItemTouchHelper(item_touch_helper_callback).attachToRecyclerView(RecyclerViewTache);

        if (savedInstanceState == null);
               //ajouterListeText();

        else {
            Tasks = savedInstanceState.getParcelableArrayList(TACHE_BUNDLE_KEY);
        }
        for (ParcelableTask pt : Tasks)
            mon_adapter.ajoute(pt.text, pt.prio);
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(TACHE_BUNDLE_KEY, Tasks);
    }

    public void ajouterListeText() {
        _tabString = FakeData.get_tasks();
        String ligne;
        String prio;

        for (int i = 0; i < _tabString.length; i++) {
            ligne = _tabString[i];
            prio = ligne.substring(1, 2);
            ligne = ligne.substring(3);
            String priotmp = "";

            if (prio.equals("1")) {
                priotmp = "basse";
            } else if (prio.equals("2")) {
                priotmp = "moyenne";
            } else if (prio.equals("3")) {
                priotmp = "haute";

            }

            Tasks.add(new ParcelableTask(ligne, priotmp));
            addToDataBase(ligne, priotmp);
        }
    }

    public void addToArrayParcelable(String text, String prio) {
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

        switch (itemid) {
            case R.id.menu_action_1:
                Toast msg = Toast.makeText(this, "ajouter un autre item", Toast.LENGTH_SHORT);
                msg.show();
                Intent childIntent = new Intent(MainActivity.this, addAcivity.class);
                startActivityForResult(childIntent, CHILD_ACTIVITY_ID);
                return true;
            case R.id.menu_action_2:
                Intent start_activity = new Intent(this, SettingsActivity.class);
                startActivity(start_activity);
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
                addToArrayParcelable(text, prio);
                mon_adapter.ajoute(text, prio);
                addToDataBase(text,prio);
            }
        }
    }

    public void addToDataBase(String text, String prio) {
        ContentValues cv = new ContentValues();

        cv.put(ContratDB.item.COLUMN_TEXT, text);
        cv.put(ContratDB.item.COLUMN_PRIO, prio);

        try {
            db.beginTransaction();
            db.insert(ContratDB.item.TABLE_NAME, null, cv);

            db.setTransactionSuccessful();
        } catch (SQLException e) {
            //too bad :(
        } finally {
            db.endTransaction();
        }
}
    static class ParcelableTask implements Parcelable {
        private String text, prio;

        public ParcelableTask(String text, String prio) {
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

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {


        System.out.println("test onSharedPreferenceChanged");
        if (s.equals(getString(R.string.option_prio))) {

            prioVisible = sharedPreferences.getBoolean(s,true);
            System.out.println("prio changée = " + prioVisible);
            mon_adapter.setPrioVisible(prioVisible);
            mon_adapter.notifyDataSetChanged();
        }

        else if(s.equals(getString(R.string.option_police))){

            taille = sharedPreferences.getString(s,"25");
            System.out.println("taille changée = " + taille );
            mon_adapter.setTaille(taille);
            mon_adapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
        //fragement getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
        //fragment getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

}