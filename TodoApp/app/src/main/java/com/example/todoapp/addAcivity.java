package com.example.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class addAcivity extends Activity {

    @Override
    public void onCreate(Bundle state){
        super.onCreate(state);
        setContentView(R.layout.activity_add);

        Button btAjouter = findViewById(R.id.buttonAjouter);
        btAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donneMessage();
            }
        });
    }

    public void donneMessage(){
        EditText edList = findViewById(R.id.listeTexte);
        String text = edList.getText().toString();

        RadioGroup radio = findViewById(R.id.radio);
        int selectedId = radio.getCheckedRadioButtonId();

        if(text.isEmpty()) {
            Toast.makeText(this,"Entrez du texte",Toast.LENGTH_SHORT ).show();
            return;
        }

        if (selectedId == -1) {
            Toast.makeText(this,"Selectionez une priorité",Toast.LENGTH_SHORT ).show();
            return;
        }

        Button btselected = findViewById(selectedId);
        String prio = btselected.getText().toString();

        Toast.makeText(this,"vous avez ajouter : " + text +"\n"+ "avec une prioritée " + prio,Toast.LENGTH_SHORT ).show();

        Intent returnIntent = new Intent();
        returnIntent.putExtra("text",text);
        returnIntent.putExtra("prio",prio);

        setResult(RESULT_OK,returnIntent);

        finish();

    }



}
