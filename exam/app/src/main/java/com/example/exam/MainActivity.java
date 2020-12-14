package com.example.exam;

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
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final int CHILD_ACTIVITY_ID = 1;
    private RecyclerView recyclerView;
    private Adapter mon_adapter;

    private DbHelper mDbHelper;
    private SQLiteDatabase db;

    static final String BUNDLE_KEY = "MA_LISTE";
    private ArrayList<ParcelableTask> listParcelable;

    private SharedPreferences app_prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linear_layout);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mon_adapter = new Adapter(this);
        recyclerView.setAdapter(mon_adapter);

        listParcelable = new ArrayList<>();

        app_prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mon_adapter.taille = app_prefs.getString("text","5");


        //============================================ BDD ============================================ //
        mDbHelper = new DbHelper(this);
        db = mDbHelper.getWritableDatabase();

        String[] projection = {
                BaseColumns._ID,
                ContratDB.item.COLUMN_TEXT
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
        //db.delete(ContratDB.item.TABLE_NAME,null,null);

        while(cursor.moveToNext()) {
            String text = cursor.getString(cursor.getColumnIndexOrThrow(ContratDB.item.COLUMN_TEXT));
            addToArrayParcelable(text);
        }
    //======================================================================================== //

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

                        String selection = ContratDB.item.COLUMN_TEXT + " LIKE ?";
                        try{
                            String[] selectionArgs = {listParcelable.get(position).text};
                            //supp toutes les lignes du meme noms ! car pas de primary key
                            db.delete(ContratDB.item.TABLE_NAME, selection, selectionArgs);
                            listParcelable.remove(position);
                            mon_adapter.supprmie(position);
                        }
                        catch (IndexOutOfBoundsException e){
                            db.delete(ContratDB.item.TABLE_NAME,null,null);
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
            mon_adapter.ajoute(pt.text);
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
                //String prio = data.getStringExtra("prio");
                String text = data.getStringExtra("text");


                //ajouter les bon champss
                addToArrayParcelable(text);
                mon_adapter.ajoute(text);
                addToDataBase(text);
            }
        }
    }

    //======================================================================================== //

    //============================================ ParcelableTask ============================================ //
    static class ParcelableTask implements Parcelable {
        private String text;

        public ParcelableTask(String text) {
            /*this.text = text;
            this.prio = prio;*/
            this.text = text;
        }

        protected ParcelableTask(Parcel in) {
            text = in.readString();
            //prio = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(text);
            //dest.writeString(prio);
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

        private void addToArrayParcelable(String text) {
        //ajouter les bons champs
            listParcelable.add(new ParcelableTask(text));
        }
    //======================================================================================== //

    //============================================ BDD ============================================ //
    public void addToDataBase(String text) {
        ContentValues cv = new ContentValues();

        cv.put(ContratDB.item.COLUMN_TEXT, text);

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
    //======================================================================================== //

    //============================================ PREFERENCES ============================================ //

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        System.out.println("test onSharedPreferenceChanged");

        //si ya des chgment a faire on le fait dans l'adapter et pas ici directement
      if (s.equals(getString(R.string.pref_2))) {
           System.out.println("test onSharedPreferenceChanged changement de taille");
           mon_adapter.taille = sharedPreferences.getString(s,"25");
           System.out.println("taille = " + mon_adapter.taille);
        }
        mon_adapter.notifyDataSetChanged();
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

    //======================================================================================== //
}