package com.example.exam;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    // Si le schéma de la base changer, il faut incrémenter cette valeur
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "items.db";

    public String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ContratDB.item.TABLE_NAME + " (" +
                    ContratDB.item._ID + " INTEGER PRIMARY KEY," +
                    ContratDB.item.COLUMN_TEXT + " TEXT )";

    public String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ContratDB.item.TABLE_NAME;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
// Constante String SQL_CREATE_ENTRIES à définir ...
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Constante String SQL_DELETE_ENTRIES à définir ...
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}

