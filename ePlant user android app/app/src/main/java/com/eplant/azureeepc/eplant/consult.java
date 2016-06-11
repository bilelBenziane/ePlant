package com.eplant.azureeepc.eplant;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;


public class consult extends Activity
{
    int TAKE_PHOTO_CODE = 0;
    public static int count = 0;

    final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picFolder/";;
    File newdir;

    protected JSONObject jObj;
    protected JSONArray jArray;
    protected JSONObject jObject;

    private static  final String TAG = MainActivity.class.getSimpleName();
    private static  String responseInsider="";



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult);
        newdir = new File(dir);
        newdir.mkdirs();
        EditText editTextDiscription=(EditText)findViewById(R.id.discription);

        try
        {
            new DownloadTask().execute(CheckCharacteristics.ServerLink+"addConsult.php?d=\"I have insects in my plants\""
                            +"&ff"+Math.random()
                                       /*+editTextDiscription.getText()*/);
            Toast.makeText(getApplicationContext(), "Request sent!", Toast.LENGTH_LONG).show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Something went wrong, try again!", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_consult, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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



    public void submitClickHandler(View view)
    {
        //EditText textViewdescription=(EditText)findViewById(R.id.description);
         EditText editTextDiscription=(EditText)findViewById(R.id.discription);
        try
        {
            new DownloadTask().execute(CheckCharacteristics.ServerLink+"addConsult.php?d=\""
                    +editTextDiscription.getText() +"\"&ff"+Math.random());
            Toast.makeText(getApplicationContext(), "Request sent!"+responseInsider, Toast.LENGTH_LONG).show();
            Intent submit=new Intent(this,Choices.class);
            startActivity(submit);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Something went wrong, try again!", Toast.LENGTH_LONG).show();
        }
    }

    public void photoClickou(View view)
    {
        count++;
        String file = dir+count+".jpg";
        File newfile = new File(file);
        try{newfile.createNewFile();}
        catch (IOException e){ }
        Uri outputFileUri = Uri.fromFile(newfile);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);

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
