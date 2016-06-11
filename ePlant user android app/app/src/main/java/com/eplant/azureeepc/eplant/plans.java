package com.eplant.azureeepc.eplant;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class plans extends Activity
{
    private  static final String TAG = MainActivity.class.getSimpleName();
    private  static  String responseInsider="";
    protected JSONObject jObj;
    protected JSONObject jObject;
    protected JSONArray jArray;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);
        try
        {
            //MainPage.ServerLink+"index.php?format="+MainPage.FromPageParam+"&passCategorie="+Selector.passCategorie+"&random="
            new DownloadTask().execute(CheckCharacteristics.ServerLink+"checkPlans.php");//checkPlans
            //Toast.makeText(getApplicationContext(), responseInsider, Toast.LENGTH_LONG).show();
            jObject = new JSONObject(responseInsider);
            jArray = jObject.getJSONArray("item");
            ArrayList<String> items = new ArrayList<String>();
            for (int i = 0; i < jArray.length(); i++)
            {
                jObj = jArray.getJSONObject(i);
                String p = jObj.getString("twoweek").toString();
                items.add(p);
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
            ListView listView = (ListView) findViewById(R.id.listView111);
            listView.setAdapter(arrayAdapter);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_plans, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void planClickHandler(View view)
    {
        try
        {
            //MainPage.ServerLink+"index.php?format="+MainPage.FromPageParam+"&passCategorie="+Selector.passCategorie+"&random="
            new DownloadTask().execute(CheckCharacteristics.ServerLink+"checkPlans.php");//checkPlans
            //Toast.makeText(getApplicationContext(),responseInsider, Toast.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(), responseInsider, Toast.LENGTH_LONG).show();
            jObject = new JSONObject(responseInsider);
            jArray = jObject.getJSONArray("item");

            ArrayList<String> items = new ArrayList<String>();
            for (int i = 0; i < jArray.length(); i++)
            {
                jObj = jArray.getJSONObject(i);
                String p = jObj.getString("twoweek");
                items.add(p);
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_black_text,R.id.list_content, items);

            ListView listView = (ListView) findViewById(R.id.listView111);

            listView.setAdapter(arrayAdapter);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            //Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private class DownloadTask extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... params)
        {
            //do your request in here so that you don't interrupt the UI thread
            try { return downloadContent(params[0]);}
            catch (IOException e){ return "Unable to retrieve data. URL may be invalid.";}
        }

        @Override
        protected void onPostExecute(String result)
        {
            //Here you are done with the task
            //Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
            responseInsider=result;
        }
    }

    private String downloadContent(String myurl) throws IOException {
        InputStream is = null;
        int length = 2000;

        try
        {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(TAG, "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = convertInputStreamToString(is, length);
            return contentAsString;
        }
        finally{if (is != null){is.close();}}
    }

    public String convertInputStreamToString(InputStream stream, int length) throws IOException, UnsupportedEncodingException
    {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[length];
        reader.read(buffer);
        return new String(buffer);
    }

}
