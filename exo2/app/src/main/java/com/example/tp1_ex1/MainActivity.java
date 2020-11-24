package com.example.tp1_ex1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void displayMsg(){
        EditText edList = findViewById(R.id.listeTexte);
        String text = edList.getText().toString();

        RadioGroup radio = findViewById(R.id.radio);
        int selectedId = radio.getCheckedRadioButtonId();
        Button btselected = findViewById(selectedId);

        String prio = btselected.getText().toString();


        Toast.makeText(this,"vous avez ajouter : " + text +"\n"+ "avec une prioritée " + prio,Toast.LENGTH_SHORT ).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btAjouter = findViewById(R.id.buttonAjouter);
        btAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayMsg();
            }
        });

    }


}