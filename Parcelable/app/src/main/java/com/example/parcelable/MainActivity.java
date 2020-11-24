package com.example.parcelable;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static final String TUPLE_BUNDLE_KEY = "MES_TUPLES";
    private ArrayList<Tuple> nTuples;
    private TextView tv_display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nTuples = new ArrayList<Tuple>();

        tv_display = (TextView) findViewById(R.id.tv_affichage);

        if(savedInstanceState == null) {
            for (int i = 0; i < 100; i++) {
                int r1 = (int) (Math.random() * 100);
                int r2 = (int) (Math.random() * 100);
                nTuples.add(new Tuple(r1, r2));
            }
        }
        else {
            nTuples = savedInstanceState.getParcelableArrayList(TUPLE_BUNDLE_KEY);
        }

        for(Tuple t : nTuples)
            tv_display.setText(tv_display.getText() + t.to_String() +  "\n");
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(TUPLE_BUNDLE_KEY, nTuples);
    }


    static class Tuple implements Parcelable {
        private int n1,n2;

        public Tuple(int n1, int n2){
            this.n1 = n1;
            this.n2 = n2;
        }

        public String to_String (){
            return String.valueOf(n1) + " + " + String.valueOf(n2) + " = " + String.valueOf(n1+n2);
        }

        protected Tuple(Parcel in) {
            n1 = in.readInt();
            n2 = in.readInt();
        }

        public static final Creator<Tuple> CREATOR = new Creator<Tuple>() {
            @Override
            public Tuple createFromParcel(Parcel in) {
                return new Tuple(in);
            }

            @Override
            public Tuple[] newArray(int size) {
                return new Tuple[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(n1);
            parcel.writeInt(n2);
        }
    }
}