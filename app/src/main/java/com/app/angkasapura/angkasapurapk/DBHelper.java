package com.app.angkasapura.angkasapurapk;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="AP.db";
    private static final int DATABASE_VERSION=1;
    public static final String TABLE_EMPLOYEES = "AFF";
    public static final String COLUMN_ID = "NO";
    public static final String COLUMN_MERK = "AIRCRAFT";
    public static final String COLUMN_TYPE = "TYPE";
    public static final String COLUMN_PANJANG = "CATEGORY";
    public static final String COLUMN_LENGTH= "LENGTH";
    public static final String COLUMN_WIDTH= "WIDTH";
    public static final String COLUMN_CA= "CRITICAL_AREA";
    public static final String COLUMN_PA= "PRACTICAL_AREA ";
    public static final String COLUMN_Water_B= "WATER_B";
    public static final String COLUMN_Discharge_B= "DISCHARGE_RATE_B";
    public static final String COLUMN_DRate_B= "DRate_B";
    public static final String COLUMN_Q1_B= "Q1_B";
    public static final String COLUMN_Q2_B= "Q2_B";
    public static final String COLUMN_TOTAL_B= "TOTAL_B";
    public static final String COLUMN_ADDREQ_B= "ADDREQ_B";
    public static final String COLUMN_IRB= "INDISCHRG_RATE_B";
    public static final String COLUMN_NOTES_B= "NOTES_B";
    public static final String COLUMN_Water_C= "WATER_C";
    public static final String COLUMN_Discharge_C= "DISCHARGE_RATE_C";
    public static final String COLUMN_DRate_C= "DRate_C";
    public static final String COLUMN_Q1C= "Q1_C";
    public static final String COLUMN_Q2C= "Q2_C";
    public static final String COLUMN_TOTAL_C= "TOTAL_C";
    public static final String COLUMN_ADDREQ_C= "ADDREQ_C";
    public static final String COLUMN_IRC= "INDISCHRG_RATE_C";
    public static final String COLUMN_NOTES_C= "NOTES_C";

    Context ctx;
    public DBHelper(Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
        ctx=context;
    }
    DBHelper dbcenter;


    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_EMPLOYEES + " (" +
                    COLUMN_ID + " TEXT, " +
                    COLUMN_MERK + " TEXT, " +
                    COLUMN_TYPE+ " TEXT, " +
                    COLUMN_PANJANG + " TEXT, " +
                    COLUMN_LENGTH + " TEXT, " +
                    COLUMN_WIDTH + " TEXT, " +
                    COLUMN_CA + " TEXT, " +
                    COLUMN_PA + " TEXT, " +
                    COLUMN_Water_B + " TEXT, " +
                    COLUMN_Discharge_B + " TEXT, " +

                    COLUMN_Q1_B + " TEXT, " +
                    COLUMN_Q2_B + " TEXT, " +
                    COLUMN_TOTAL_B + " TEXT, " +
                    COLUMN_DRate_B + " TEXT, " +
                    COLUMN_ADDREQ_B + " TEXT, " +
                    COLUMN_IRB + " TEXT, " +
                    COLUMN_NOTES_B + " TEXT, " +
                    COLUMN_Water_C + " TEXT, " +
                    COLUMN_Discharge_C + " TEXT, " +

                    COLUMN_Q1C + " TEXT, " +
                    COLUMN_Q2C + " TEXT, " +
                    COLUMN_TOTAL_C + " TEXT, " +
                    COLUMN_DRate_C + " TEXT, " +
                    COLUMN_ADDREQ_C + " TEXT, " +
                    COLUMN_IRC + " TEXT, " +
                    COLUMN_NOTES_C + " TEXT " +
                    ")";

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_CREATE);
        String sql="SELECT * FROM AFF";
        db.execSQL(sql);
        String mCSVfile = "MASTERFINAL1.csv";
        android.content.res.AssetManager manager = ctx.getAssets();
        java.io.InputStream inStream = null;
        try {
            inStream = manager.open(mCSVfile);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        java.io.BufferedReader buffer = new java.io.BufferedReader(new java.io.InputStreamReader(inStream));
        String line = "";
        db.beginTransaction();
        try {
            while ((line = buffer.readLine()) != null) {

                String[] colums = line.split(",");
                if (colums.length != 26) {
//                    System.out.println(line);
                    android.util.Log.d("CSVParser", "Skipping Bad CSV Row");
                    continue;
                }
                android.content.ContentValues cv = new android.content.ContentValues(5);
                cv.put("NO", colums[0].trim());
                cv.put("AIRCRAFT", colums[1].trim());
                cv.put("TYPE", colums[2].trim());
                cv.put("CATEGORY", colums[3].trim());
                cv.put("LENGTH", colums[4].trim());
                cv.put("WIDTH", colums[5].trim());
                cv.put("CRITICAL_AREA", colums[6].trim());
                cv.put("PRACTICAL_AREA", colums[7].trim());
                cv.put("WATER_B", colums[8].trim());
                cv.put("DISCHARGE_RATE_B", colums[9].trim());
                cv.put("Q1_B", colums[10].trim());
                cv.put("Q2_B", colums[11].trim());
                cv.put("TOTAL_B", colums[12].trim());
                cv.put("DRate_B", colums[13].trim());
                cv.put("ADDREQ_B", colums[14].trim());
                cv.put("INDISCHRG_RATE_B", colums[15].trim());
                cv.put("NOTES_B", colums[16].trim());
                cv.put("WATER_C", colums[17].trim());
                cv.put("DISCHARGE_RATE_C", colums[18].trim());
                cv.put("Q1_C", colums[19].trim());
                cv.put("Q2_C", colums[20].trim());
                cv.put("TOTAL_C", colums[21].trim());
                cv.put("DRate_C", colums[22].trim());
                cv.put("ADDREQ_C", colums[23].trim());
                cv.put("INDISCHRG_RATE_C", colums[24].trim());
                cv.put("NOTES_C", colums[25].trim());


                db.insert("AFF", null, cv);
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        db.setTransactionSuccessful();
        db.endTransaction();



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        // Create tables again
        onCreate(db);
    }


}
