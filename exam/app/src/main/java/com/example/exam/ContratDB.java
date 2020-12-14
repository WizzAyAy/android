package com.example.exam;

import android.provider.BaseColumns;

public class ContratDB {
    // Mettre le constructeur privé empêche que la classe soit instanciée
    private ContratDB() {}
    /* Classe imbriquée pour la table */
    public static class item implements BaseColumns {
        public static final String TABLE_NAME = "item";
        public static final String COLUMN_TEXT = "text";
        //public static final String COLUMN_PRIO = "prio";
    }
}