package com.example.tpdatabase_1;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FakeData {
    public static void insert_fake_data(SQLiteDatabase db){
        if(db == null){
            return;
        }

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);

        //create a list of fake guests
        List<ContentValues> list = new ArrayList<ContentValues>();

        ContentValues cv = new ContentValues();
        cv.put(ContratDBAttente.Personnes.COLUMN_NAME, "Julien");
        cv.put(ContratDBAttente.Personnes.COLUMN_DATE, strDate);
        list.add(cv);

        cv = new ContentValues();
        cv.put(ContratDBAttente.Personnes.COLUMN_NAME, "Claire");
        cv.put(ContratDBAttente.Personnes.COLUMN_DATE, strDate);
        list.add(cv);

        cv = new ContentValues();
        cv.put(ContratDBAttente.Personnes.COLUMN_NAME, "Stéphanie");
        cv.put(ContratDBAttente.Personnes.COLUMN_DATE, strDate);
        list.add(cv);

        cv = new ContentValues();
        cv.put(ContratDBAttente.Personnes.COLUMN_NAME, "Christophe");
        cv.put(ContratDBAttente.Personnes.COLUMN_DATE, strDate);
        list.add(cv);

        cv = new ContentValues();
        cv.put(ContratDBAttente.Personnes.COLUMN_NAME, "Cécile");
        cv.put(ContratDBAttente.Personnes.COLUMN_DATE, strDate);
        list.add(cv);

        //insert all guests in one transaction
        try
        {
            db.beginTransaction();
            //clear the table first
            db.delete (ContratDBAttente.Personnes.TABLE_NAME,null,null);
            //go through the list and add one by one
            for(ContentValues c:list){
                db.insert(ContratDBAttente.Personnes.TABLE_NAME, null, c);
            }
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
}