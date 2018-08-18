package com.app.angkasapura.angkasapurapk;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class Detail extends AppCompatActivity {
    Cursor cursor;
    DBHelper dbcenter;
    String aircraft;
    String type;
    String foam;
    String[] content;

    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expandable);
        dbcenter = new DBHelper(this);
        Intent intent=getIntent(); 
        aircraft=intent.getExtras().getString("pselectedAircraft");
        type=intent.getExtras().getString("pselectedType");
        foam=intent.getExtras().getString("selectedFoam");
        tvTitle=findViewById(R.id.foamDetail);
        if(foam.equals("FOAM LEVEL B")){
            tvTitle.setText(R.string.FoamB);
        }else{
            tvTitle.setText(R.string.FoamC);
        }

        GetData(intent);
    }
    public void GetData(Intent intent){

        SQLiteDatabase db = dbcenter.getReadableDatabase();

        aircraft=intent.getExtras().getString("pselectedAircraft");
        type=intent.getExtras().getString("pselectedType");
        foam=intent.getExtras().getString("selectedFoam");

        TextView tvName=findViewById(R.id.tvName);
        TextView tvKat=findViewById(R.id.tvKat);
        TextView tvLength=findViewById(R.id.tvLength);//M
        TextView tvWidth=findViewById(R.id.tvWidth);//M
        TextView tvPCA=findViewById(R.id.tvPCA); //m2
        TextView tvTCA=findViewById(R.id.tvTCA);//m2

        TextView tvTotal=findViewById(R.id.totalWater);
        TextView tvQ1=findViewById(R.id.tvQ1);//LPM
        TextView tvQ2=findViewById(R.id.tvQ2);//LPM

        TextView tvAdd=findViewById(R.id.tvAdditionVal);
        TextView tvDischarge=findViewById(R.id.tvDischarge);
        TextView tvNote=findViewById(R.id.Notes);

        TextView tvWater=findViewById(R.id.tvWater);
        TextView tvDischargeRate=findViewById(R.id.tvDischargeRate);
        TextView tvDrate=findViewById(R.id.tvDrate);



        cursor = db.rawQuery("SELECT * FROM AFF WHERE AIRCRAFT = '"+aircraft+"' AND TYPE= '"+type+"' ",null);
        content = new String[cursor.getCount()];
        System.out.println("coba1"+cursor.getCount());
        cursor.moveToFirst();
        for (int cc=0; cc < cursor.getCount(); cc++) {
            cursor.moveToPosition(cc);
            String NameType=cursor.getString(1)+" "+cursor.getString(2);
            String Length=cursor.getString(4)+" "+getString(R.string.satuanm);
            String Width=cursor.getString(5)+" "+getString(R.string.satuanm);
            String TCA=cursor.getString(6)+" "+getString(R.string.satuanm2);
            String PCA=cursor.getString(7)+" "+getString(R.string.satuanm2);


            String Q1val,Q2val,Add,Discharge,Total,Waterval,DischargeRate,DrateVal,Notes1;


            //tes
            tvName.setText(NameType);
            tvKat.setText(cursor.getString(3));
            tvLength.setText(Length);
            tvWidth.setText(Width);
            tvPCA.setText(PCA);
            tvTCA.setText(TCA);

            if(foam.equals("FOAM LEVEL B")){

                Waterval=cursor.getString(8);
                DischargeRate=cursor.getString(9);
                Q1val=cursor.getString(10);
                Q2val=cursor.getString(11);
                Total=cursor.getString(12);
                DrateVal=cursor.getString(13);
                if(cursor.getString(14).equals("")){
                    Add="-";
                }else{
                     Add=cursor.getString(14);
                }

                if(cursor.getString(15).equals("")){
                    Discharge="-";
                }else{
                    Discharge=cursor.getString(15);
                }
                Notes1=cursor.getString(16);

            }else{
                Waterval=cursor.getString(17);
                DischargeRate=cursor.getString(18);

                Q1val=cursor.getString(19);
                Q2val=cursor.getString(20);
                Total=cursor.getString(21);
                DrateVal=cursor.getString(22);
                if(cursor.getString(23).equals("")){
                    Add="-";
                }else{
                    Add=cursor.getString(23);

                }

                if(cursor.getString(24).equals("")){
                    Discharge="-";
                }else{
                    Discharge=cursor.getString(24);

                }
                Notes1=cursor.getString(25);

            }

            String AddVal=Add+" "+getString(R.string.satuanliter);
            String DischargeVal=Discharge+" "+getString(R.string.satuanliter);
            String Q1=Q1val+" "+getString(R.string.satuanliter);
            String Q2=Q2val+" "+getString(R.string.satuanliter);
            String TotalVal=Total+" "+getString(R.string.satuanliter);
            String WaterTxt=Waterval+" "+getString(R.string.satuanliter);
            String DischargeRateTxt=DischargeRate+" "+getString(R.string.satuanperminute);
            String DRateTxt=DrateVal+" "+getString(R.string.satuanperminute);

            if(Notes1.equals("Compliance")){
                AddVal="-";
                DischargeVal="-";
            }else{
                tvNote.setTextColor(getResources().getColor(R.color.colorAccent));
            }

             tvTotal.setText(TotalVal);
             tvQ1.setText(Q1);
             tvQ2.setText(Q2);
             tvWater.setText(WaterTxt);
             tvDischargeRate.setText(DischargeRateTxt);
             tvDrate.setText(DRateTxt);
             tvNote.setText(Notes1);

            tvAdd.setText(AddVal);
            tvDischarge.setText(DischargeVal);


        }




    }
}
