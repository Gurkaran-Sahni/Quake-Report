package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Earthquake>> {

    private EarthQuakeAdapter mAdapter;
    private ListView earthquakeListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        earthquakeListView = (ListView) this.findViewById(R.id.list);
//        earthquakeListView.setEmptyView(findViewById(R.id.empty_view));

        setContentView(R.layout.activity_main);
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(1,null,this);
        Log.v("MainActivity","initLoader");
//        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
//        task.execute(SAMPLE_JSON_RESPONSE);

//        ArrayList<Earthquake> Earthquakes = QueryUtils.fetchEarthquakeData(SAMPLE_JSON_RESPONSE);
//        ListView earthquakeListView = (ListView) findViewById(R.id.list);
//        EarthQuakeAdapter adapter = new EarthQuakeAdapter(this, Earthquakes);
//        earthquakeListView.setAdapter(adapter);
    }


    private static final String SAMPLE_JSON_RESPONSE = "https://earthquake.usgs.gov/fdsnws/event/1/query";

    @Override
    public Loader<ArrayList<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        Log.v("MainActivity","onCreateLoader");

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude = sharedPrefs.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default));

        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        Uri baseUri = Uri.parse(SAMPLE_JSON_RESPONSE);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "10");
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("orderby", orderBy);

        return new EarthquakeLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Earthquake>> loader, ArrayList<Earthquake> earthquakes) {
//        mAdapter.clear();
        if(earthquakes == null){
            return;
        }
        Log.v("MainActivity","onLoadFinished");
        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        EarthQuakeAdapter adapter = new EarthQuakeAdapter(MainActivity.this, earthquakes);
        earthquakeListView.setAdapter(adapter);
        findViewById(R.id.loading_spinner).setVisibility(View.GONE);
//        TextView emptyView = findViewById(R.id.empty_view);
//        emptyView.setText("No Earthquakes found");
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Earthquake>> loader) {
//        mAdapter.clear();
        Log.v("MainActivity","onLoaderReset");
    }


//    private class EarthquakeAsyncTask extends AsyncTask<String,Void,ArrayList<Earthquake>> {
//
//        @Override
//        protected ArrayList<Earthquake> doInBackground(String... urls) {
//            if(urls[0]==null || urls.length<1){
//                return null;
//            }
//
//            ArrayList<Earthquake> earthquakes = QueryUtils.fetchEarthquakeData(urls[0]);
//            return earthquakes;
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<Earthquake> earthquakes) {
//            if(earthquakes == null){
//                return;
//            }
//            ListView earthquakeListView = (ListView) findViewById(R.id.list);
//            EarthQuakeAdapter adapter = new EarthQuakeAdapter(MainActivity.this, earthquakes);
//            earthquakeListView.setAdapter(adapter);
//
//
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}




