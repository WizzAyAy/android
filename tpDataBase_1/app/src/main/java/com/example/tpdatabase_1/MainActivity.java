package com.example.tpdatabase_1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Object ContratDBListe;
    private Object s;
    private  ListeDbHelper mDbHelper;
    private SQLiteDatabase db;
    private FakeData fk;
    private RecyclerView RecyclerViewList;
    private Adapter mon_adapter;
    static final String TACHE_BUNDLE_KEY = "MES_TACHES";
    private ArrayList<ParcelableTask> ListAtt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerViewList = (RecyclerView)findViewById(R.id.recycleView);
        RecyclerViewList.setLayoutManager(new LinearLayoutManager(this));
        mon_adapter = new Adapter(this);
        RecyclerViewList.setAdapter(mon_adapter);

        ListAtt = new ArrayList<ParcelableTask>();

        Button btAjouter = findViewById(R.id.bt_ajout);
        btAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajout();
            }

            private void ajout() {


                EditText et = (EditText)findViewById(R.id.et_text);
                String nom = et.getText().toString();

                Date date = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                String strDate = dateFormat.format(date);

                addToArrayParcelable(nom,strDate);
                mon_adapter.ajoute(nom,strDate);

                try
                {
                    ContentValues cv = new ContentValues();
                    cv.put(ContratDBAttente.Personnes.COLUMN_NAME, nom);
                    cv.put(ContratDBAttente.Personnes.COLUMN_DATE, strDate);
                    db.beginTransaction();
                    //clear the table first
                    db.delete (ContratDBAttente.Personnes.TABLE_NAME,null,null);
                    db.insert(ContratDBAttente.Personnes.TABLE_NAME, null, cv);
                    db.setTransactionSuccessful();
                }
                catch (SQLException e) {
                    //too bad :(
                }
                finally
                {
                    db.endTransaction();
                }
            }
        });



            ItemTouchHelper.SimpleCallback item_touch_helper_callback =
                    new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                        @Override
                        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                            return false;
                        }

                        @Override
                        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                            int position = (int) viewHolder.itemView.getTag();
                            try {
                                mon_adapter.supprmie(position);
                                ListAtt.remove(position);
                            }
                            catch (IndexOutOfBoundsException e){
                                System.out.println("execption e : " + e.toString());
                            }
                        }
                    };


            new ItemTouchHelper(item_touch_helper_callback).attachToRecyclerView(RecyclerViewList);


        mDbHelper = new ListeDbHelper(this);
        db = mDbHelper.getWritableDatabase();

        String[] projection = {
                BaseColumns._ID,
                ContratDBAttente.Personnes.COLUMN_NAME,
                ContratDBAttente.Personnes.COLUMN_DATE
        };

        // Filtrer les résultats WHERE "nom" = 'Dupont'
        // Trier les résultas
        String sortOrder = ContratDBAttente.Personnes.COLUMN_NAME + " DESC";
        Cursor cursor = db.query(
                ContratDBAttente.Personnes.TABLE_NAME, // La table
                projection, // Les colonnes à récupérer (null pour toutes)
                null, // La clause WHERE
                null, // Les valeurs pour la clause WHERE
                null, // Grouper les lignes ?
                null, // Filtrer les lignes ?
                sortOrder // Trier les lignes
        );



        db.delete(ContratDBAttente.Personnes.TABLE_NAME,null,null);

        if(savedInstanceState == null)
            fk.insert_fake_data(db);

        else {
            ListAtt = savedInstanceState.getParcelableArrayList(TACHE_BUNDLE_KEY);
        }


        while(cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(ContratDBAttente.Personnes.COLUMN_NAME));
            String d = cursor.getString(cursor.getColumnIndexOrThrow(ContratDBAttente.Personnes.COLUMN_DATE));
            addToArrayParcelable(name,d);
        }

        for(ParcelableTask pt : ListAtt)
            mon_adapter.ajoute(pt.nom, pt.date);

        cursor.close();
}

    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(TACHE_BUNDLE_KEY,ListAtt);
    }



    public void addToArrayParcelable (String text, String prio){
        ListAtt.add(new ParcelableTask(text, prio));
    }



    protected void onDestroy() {
        mDbHelper.close();
        super.onDestroy();
    }





    static class ParcelableTask implements Parcelable {
        private String nom, date;

        public ParcelableTask(String nom, String date){
            this.nom = nom;
            this.date = date;
        }
        protected ParcelableTask(Parcel in) {
            nom = in.readString();
            date = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(nom);
            dest.writeString(date);
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
