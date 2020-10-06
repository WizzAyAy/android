package com.example.exo3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;

import com.example.exo3.FakeData;


public class MainActivity extends AppCompatActivity {

    private int activity_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ajouterListeText();
    }

    public void ajouterListeText(){
        _tabString = FakeData.get_tasks();
        String fullString = _tabString[0];
        TextView textView = findViewById(R.id.textList);

        for (int i = 1; i < _tabString.length; i++) {
            fullString += "\n"+ _tabString[i];
        }
        textView.setText(fullString);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemid = item.getItemId();

        switch (itemid){
            case R.id.menu_action_1:
                Toast msg = Toast.makeText(this,"ptit msg test", Toast.LENGTH_SHORT);
                msg.show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    String[] _tabString;
}