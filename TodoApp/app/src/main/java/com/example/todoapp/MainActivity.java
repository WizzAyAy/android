package com.example.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todoapp.FakeData;

import com.example.todoapp.FakeData;

public class MainActivity extends AppCompatActivity {

    private static final int CHILD_ACTIVITY_ID = 1;
    private int activity_main;
    private ListView listViewTache;
    public String[] _tabString;
    private TachesAdapter mon_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linear_layout);


        listViewTache = (ListView)findViewById(R.id.lv_taches);
        mon_adapter = new TachesAdapter(this);
        listViewTache.setAdapter(mon_adapter);
        listViewTache.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TachesAdapter.TachesItem item = mon_adapter.getItem(i);
                Toast.makeText(MainActivity.this, item.textTache+ " "+item.priority, Toast.LENGTH_SHORT).show();
            }
        });

        ajouterListeText();

        
    }

    public void ajouterListeText(){
        _tabString = FakeData.get_tasks();
        String ligne;
        String prio;


        for (int i = 0; i < _tabString.length; i++) {
            ligne = _tabString[i];
            prio = ligne.substring(1,2);
            ligne = ligne.substring(3);

            if(prio.equals("1"))
                mon_adapter.ajoute(ligne, "basse");
            else if(prio.equals("2"))
                mon_adapter.ajoute(ligne,"moyenne");
            else if(prio.equals("3"))
                mon_adapter.ajoute(ligne,"haute");

        }

    }

    public void ajouterItemText(String prio, String text){
        TextView textView = findViewById(R.id.textList);
        String itemText = prio + " " + text;
        textView.setText(textView.getText()+ "\n" + itemText);
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
                Toast msg = Toast.makeText(this,"ajouter un autre item", Toast.LENGTH_SHORT);
                msg.show();
                Intent childIntent = new Intent(MainActivity.this, addAcivity.class);
                startActivityForResult(childIntent, CHILD_ACTIVITY_ID);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
             if (requestCode == CHILD_ACTIVITY_ID) {

                 if (resultCode == RESULT_OK) {
                     String prio = data.getStringExtra("prio");
                     String text = data.getStringExtra("text");

                     mon_adapter.ajoute(text,prio);
                 }
             }
    }

}