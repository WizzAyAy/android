package fr.univangers.m1info.exercicerecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView RecyclerViewTache;
    private TimeTableAdapter mon_adapter;
    private FakeData Tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerViewTache = (RecyclerView)findViewById(R.id.rv_schedule);
        RecyclerViewTache.setLayoutManager(new LinearLayoutManager(this));

        mon_adapter = new TimeTableAdapter(this);
        RecyclerViewTache.setAdapter(mon_adapter);

        RecyclerViewTache.setAdapter(mon_adapter);
    }
}
