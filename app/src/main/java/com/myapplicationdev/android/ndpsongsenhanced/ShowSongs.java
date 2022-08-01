package com.myapplicationdev.android.ndpsongsenhanced;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Set;

public class ShowSongs extends AppCompatActivity {
    ArrayList<Songs> al;
    ArrayList<Songs> alYear;
    Set<Songs> alYearWithoutDuplicates;
    ArrayList<String> alYearString;
    // ArrayAdapter<Songs> aa;
    ArrayAdapter<Songs> aaYear;
    Spinner year;
    ListView lv;
    ToggleButton tbStar;
    Adapter adapter;

    @Override
    protected void onResume() {
        super.onResume();

        DBHelper dbh = new DBHelper(ShowSongs.this);
        al.clear();
        al.addAll(dbh.getAllSongs());

        adapter.notifyDataSetChanged();

        year = findViewById(R.id.yearFilter);

        alYear = dbh.getAllYears();
//        alYearWithoutDuplicates = new LinkedHashSet<>(alYear);
//        alYearWithoutDuplicates.addAll(alYear);
//        alYear.clear();
//        alYear.addAll(alYearWithoutDuplicates);
        aaYear = new ArrayAdapter(this,android.R.layout.simple_spinner_item,alYear);
        aaYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(aaYear);

        aaYear.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_songs);

        lv = findViewById(R.id.lv);
        year = findViewById(R.id.yearFilter);
        tbStar = findViewById(R.id.tbFiveStars);

        al = new ArrayList<Songs>();
        // aa = new ArrayAdapter<Songs>(this,
        //android.R.layout.simple_list_item_1, al);
        adapter = new Adapter(this, R.layout.row, al);
        lv.setAdapter(adapter);
        // lv.setAdapter(aa);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long identity) {
                Songs data = al.get(position);
                Intent i = new Intent(ShowSongs.this,
                        ModifySong.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });

        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String filterText = year.getSelectedItem().toString();

                al.clear();
                DBHelper dbh = new DBHelper(ShowSongs.this);
                al.addAll(dbh.getAllSongs(filterText));

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                al.clear();
                DBHelper dbh = new DBHelper(ShowSongs.this);
                al.addAll(dbh.getAllSongs());

                adapter.notifyDataSetChanged();
            }
        });

        tbStar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                String filterText = "5";

                DBHelper dbh = new DBHelper(ShowSongs.this);

                al.clear();
                if(checked){
                    al.addAll(dbh.getAllFiveStar(filterText));
                }else{
                    al.addAll(dbh.getAllSongs());
                }

                adapter.notifyDataSetChanged();
            }
        });
    }
}