package com.example.cyclevie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.TextLog);

        if(savedInstanceState != null){
            //rechargement des données
            text.setText(savedInstanceState.getString("logs"));
        }
        else {
            text.setText(text.getText() + "app created, ");
        }
    }

    protected void onStart() {
        super.onStart();
        text.setText(text.getText() + "app started, ");
    }

    protected void onResume() {
        super.onResume();
        text.setText(text.getText() + "app resumed, ");
    }

    protected void onRestart() {
        super.onRestart();
        text.setText(text.getText() + "app restarted, ");
    }

    protected void onPause() {
        super.onPause();
        text.setText(text.getText() + "app paused, ");
    }

    protected void onStop() {
        super.onStop();
        text.setText(text.getText() + "app stoped, ");
    }

    protected void onDestroy() {
        super.onDestroy();
        text.setText(text.getText() + "app destroyed, ");
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("logs", (String) text.getText());
    }
}