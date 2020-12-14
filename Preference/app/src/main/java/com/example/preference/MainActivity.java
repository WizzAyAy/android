package com.example.preference;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.support.v7.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    private TextView tv;
    private TextView tv_chupa;
    private SharedPreferences app_prefs;
    private Boolean pref_value;
    private String taille;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.text);
        tv_chupa = (TextView) findViewById(R.id.chupa);

        app_prefs = PreferenceManager.getDefaultSharedPreferences(this);

        pref_value = app_prefs.getBoolean(getString(R.string.cle1),true);
        taille = app_prefs.getString(String.valueOf((R.string.cle2)),"DEFAULT");

        tv.setText("init des preferences ... \n");
        if(pref_value) tv.append("vous etes un bg ! \n");
        else tv.append("ah enfaite non :/\n");

        tv_chupa.setText("votre chupa fait "+taille);
    }

    public void launch_preferences(View v){
        Intent start_activity = new Intent(this, SettingsActivity.class);
        startActivity(start_activity);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        System.out.println("test onSharedPreferenceChanged");
        if (s.equals(getString(R.string.cle1))) {
            pref_value = sharedPreferences.getBoolean(s,true);
            if(pref_value) tv.append("vous etes un bg ! \n");
            else tv.append("ah enfaite non :/\n");
        }

        else if(s.equals(getString(R.string.cle2))){
            taille = sharedPreferences.getString(s,"DEFAULT");
            System.out.println(taille);
            tv_chupa.setText("votre chupa fait "+taille);
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