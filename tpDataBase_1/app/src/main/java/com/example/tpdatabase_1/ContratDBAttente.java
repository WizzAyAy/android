package com.example.tpdatabase_1;

import android.provider.BaseColumns;

public class ContratDBAttente {
    // Mettre le constructeur privé empêche que la classe soit instanciée
    private ContratDBAttente() {}
    /* Classe imbriquée pour la table */
    public static class Personnes implements BaseColumns {
        public static final String TABLE_NAME = "listeAttente";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_NAME = "prénom";
    }
}
