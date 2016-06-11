package com.eplant.azureeepc.eplant;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;


public class CheckCharacteristics extends Activity
{

    protected JSONObject jObj;
    protected  JSONArray jArray;
    protected JSONObject jObject;

    private static  String waterRecall="";
    private static  String humidityRecall="";
    private static  String tempRecall="";
    private static  String gasRecall="";
    private static  String lightRecall="";
    private static  final String TAG = MainActivity.class.getSimpleName();
    private static  String responseInsider="";
    public  static  String ServerLink="http://192.168.173.1/eplant/";//http://127.0.0.1/eplant/check.php

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_characteristics);

        try
        {
            new DownloadTask().execute(ServerLink+"check.php");
            jObject = new JSONObject(responseInsider);
            jArray = jObject.getJSONArray("item");
            jObj=new JSONObject();

            jObj = jArray.getJSONObject(0);
            tempRecall=jObj.getString("temp");

            /*for (int i = 0; i < jArray.length(); i++)
            {
                jObj = jArray.getJSONObject(i);
                passRecall=jObj.getString("temp").toString();

            }*/
            Toast.makeText(getApplicationContext(),responseInsider, Toast.LENGTH_LONG).show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_check_characteristics, menu);
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


    public void refreshClickHandler(View view)
    {

        //EditText editTextUser_Name=(EditText) findViewById(R.id.temp);
        TextView textViewTemp=(TextView) findViewById(R.id.temp);
        TextView textViewHumidity=(TextView) findViewById(R.id.humidity);
        TextView textViewGas=(TextView) findViewById(R.id.gas);
        TextView textViewLight=(TextView) findViewById(R.id.light);
        TextView textViewwater=(TextView) findViewById(R.id.water);

        try
        {
            new DownloadTask().execute(ServerLink+"check.php");
            jObject= new JSONObject(responseInsider);
            jArray= jObject.getJSONArray("item");
            jObj = jArray.getJSONObject(0);

            gasRecall=jObj.getString("gas");
            tempRecall=jObj.getString("temp");
            lightRecall=jObj.getString("light");
            humidityRecall=jObj.getString("humidity");
            waterRecall=jObj.getString("water");



            textViewTemp.setText(tempRecall+"°C");
            textViewwater.setText(waterRecall+"%");
            textViewGas.setText(gasRecall+"%");
            textViewLight.setText(lightRecall+"");
            textViewHumidity.setText(humidityRecall+"%");

            Toast.makeText(getApplicationContext(),tempRecall, Toast.LENGTH_LONG).show();

        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    private class DownloadTask extends AsyncTask<String, Void, String>
    {


        @Override
        protected String doInBackground(String... params)
        {
            //do your request in here so that you don't interrupt the UI thread
            try { return downloadContent(params[0]); }
            catch (IOException e) { return "Unable to retrieve data. URL may be invalid.";}
        }

        @Override
        protected void onPostExecute(String result)
        {
            //Here you are done with the task
            //Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
            responseInsider=result;
        }
    }

    private String downloadContent(String myurl) throws IOException
    {
        InputStream is = null;
        int length = 500;

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
        finally  { if (is != null) {is.close();}}
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
