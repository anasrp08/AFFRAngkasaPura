package com.app.angkasapura.angkasapurapk;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import android.widget.Toast;


public class DetailActivity extends AppCompatActivity {

    String type;
    String[] content;
        Button searchbutton;
    DBHelper dbcenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        searchbutton=findViewById(R.id.Search);

        dbcenter = new DBHelper(this);

        Intent intent=getIntent();
        final String pSelectedAircraft=intent.getExtras().getString("pselectedAircraft");
        final String pSelectedType=intent.getExtras().getString("pselectedType");


        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items ;
                items=getResources().getStringArray(R.array.FoamType);
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                builder.setTitle("Choose Foam Type");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        Intent movePage = new Intent(DetailActivity.this, Detail.class);
                        movePage.putExtra("selectedFoam", items[item]);
                        movePage.putExtra("pselectedAircraft", pSelectedAircraft);
                        movePage.putExtra("pselectedType", pSelectedType);
                        Toast.makeText(DetailActivity.this, items[item], Toast.LENGTH_SHORT).show();
                        startActivity(movePage);

                        dialog.dismiss();
                    }
                }).show();
            }
        });
    }


}
