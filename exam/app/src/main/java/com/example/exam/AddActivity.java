package com.example.exam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AddActivity extends Activity {
    public void onCreate(Bundle state){
        super.onCreate(state);
        setContentView(R.layout.activity_add);

        Button btAdd = findViewById(R.id.buttonAdd);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donneMessage();
            }
        });
    }

    private void donneMessage() {
        //recuperation des champs pour les renvoyer dans la main activity

        EditText edList = findViewById(R.id.editText);
        String text = edList.getText().toString();

        Intent returnIntent = new Intent();
        returnIntent.putExtra("text",text);
        //returnIntent.putExtra("prio",prio);

        setResult(RESULT_OK,returnIntent);

        finish();

    }
}
