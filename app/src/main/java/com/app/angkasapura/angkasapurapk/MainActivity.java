package com.app.angkasapura.angkasapurapk;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;


public class MainActivity extends AppCompatActivity {
    DBHelper dbcenter;

    Button searchbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbcenter = new DBHelper(this);

        searchbutton=findViewById(R.id.Search1);



        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SimpleSearchDialogCompat(MainActivity.this, "Find Aircraft Name",
                        "Entry Aircraft Name", null, createSampleData(),
                        new SearchResultListener<SearchModel>() {
                            @Override
                            public void onSelected(BaseSearchDialogCompat dialog,
                                                    SearchModel item, int position) {
                                Intent movePage = new Intent(MainActivity.this, ListData.class);


                movePage.putExtra("pAircraft", item.getTitle());
                startActivity(movePage);
                                Toast.makeText(MainActivity.this, item.getTitle(),
                                        Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }).show();

            }
        });

    }

    private ArrayList<SearchModel> createSampleData() {

        ArrayList<SearchModel> items= new ArrayList<>();
        items.add(new SearchModel("Aero Commander"));
        items.add(new SearchModel("Airbus"));
        items.add(new SearchModel("Antonov"));
        items.add(new SearchModel("ATR"));
        items.add(new SearchModel("BAe System"));
        items.add(new SearchModel("Beechcraft"));
        items.add(new SearchModel("Boeing"));
        items.add(new SearchModel("Bombardier"));
        items.add(new SearchModel("Britten Norman"));
        items.add(new SearchModel("Cessna"));
        items.add(new SearchModel("Convair"));
        items.add(new SearchModel("Dassault"));
        items.add(new SearchModel("De Havilland"));
        items.add(new SearchModel("Dornier"));
        items.add(new SearchModel("Embraer"));
        items.add(new SearchModel("Fokker	"));
        items.add(new SearchModel("Grumman"));
        items.add(new SearchModel("Hawker	"));
        items.add(new SearchModel("Ilyushin"));
        items.add(new SearchModel("Let Kunovice"));
        items.add(new SearchModel("Lockheed"));
        items.add(new SearchModel("McDonnell"));
        items.add(new SearchModel("NAMC"));
        items.add(new SearchModel("Piaggio"));
        items.add(new SearchModel("Pilatus"));
        items.add(new SearchModel("Piper PA"));
        items.add(new SearchModel("Raytheon"));
        items.add(new SearchModel("Robin"));
        items.add(new SearchModel("Saab"));
        items.add(new SearchModel("Short Brothers"));
        items.add(new SearchModel("Sukhoi Superjet"));
        items.add(new SearchModel("Tupolev"));
        items.add(new SearchModel("Xi'an"));
        items.add(new SearchModel("Yakovlev"));

        return items;
    }


}
