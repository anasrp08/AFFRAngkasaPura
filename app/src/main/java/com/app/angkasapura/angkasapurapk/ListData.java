package com.app.angkasapura.angkasapurapk;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
//ravi androidhive
public class ListData extends AppCompatActivity implements AircraftAdapter.AircraftAdapterListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<AircraftType> AircraftList;
    private AircraftAdapter mAdapter;
    private SearchView searchView;
    DBHelper dbcenter;
    Cursor cursor;
    String pSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);

        dbcenter = new DBHelper(this);
        recyclerView = findViewById(R.id.recycler_view);
        AircraftList = new ArrayList<>();
        mAdapter = new AircraftAdapter(this, AircraftList, this);
//        whiteNotificationBar(recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 36));
        recyclerView.setAdapter(mAdapter);
        Intent inten=getIntent();
        fetchContacts(inten);
    }

    private void fetchContacts(Intent intent) {
        JSONArray arrResult=new JSONArray();
        pSelected=intent.getExtras().getString("pAircraft");

        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM AFF WHERE AIRCRAFT = '"+pSelected+"'",null);

        cursor.moveToFirst();
        int columnLength=cursor.getColumnCount();

        System.out.println(cursor.getString(1));
        while (cursor.isAfterLast()==false)
        {

            JSONObject rowObject=new JSONObject();

            for(int j=0;j<columnLength;j++)
            {

                if(cursor.getColumnName(j)!=null)
                {
                    try
                    {
                        if(cursor.getString(j)!=null)
                        {
                            rowObject.put("aircraftname",cursor.getString(1));
                            rowObject.put("aircrafttype",cursor.getString(2));
                        }else{
                            rowObject.put("aircraftname",cursor.getString(1));
                            rowObject.put("aircrafttype",cursor.getString(2));
                        }
                    } catch (JSONException e) {
                        Log.d("TAG_NAME", e.getMessage()  );
                    }

                }
            }
            arrResult.put(rowObject);
            cursor.moveToNext();

        }
        List<AircraftType> items = new Gson().fromJson(arrResult.toString(), new TypeToken<List<AircraftType>>() {
        }.getType());
        AircraftList.clear();
        AircraftList.addAll(items);

        // refreshing recycler view
        mAdapter.notifyDataSetChanged();
        cursor.close();
        Log.d("TAG_NAME1", arrResult.toString()  );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    @Override
    public void onAircraftSelected(AircraftType contact) {

//        Toast.makeText(getApplicationContext(), "Selected: " + contact.getAircraftname() + ", " + contact.getAircrafttype(), Toast.LENGTH_LONG).show();
        Intent movePage = new Intent(ListData.this, DetailActivity.class);

        movePage.putExtra("pselectedType", contact.getAircrafttype());
        movePage.putExtra("pselectedAircraft", pSelected);
        startActivity(movePage);
    }
}
