package com.example.jsonsample;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Main extends AppCompatActivity{

    ArrayList <String> users;
    ArrayList <String> emails;
    ArrayList <String> images;
    String TAG = "Main Activity";
    UserRecyclerView adapter;
    SnapHelper snap;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        snap = new PagerSnapHelper();
        users = new ArrayList<>();
        emails = new ArrayList<>();
        images = new ArrayList<>();
        RecyclerView rv = findViewById(R.id.users_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new UserRecyclerView(this,users,emails, images);
        rv.setAdapter(adapter);
        snap.attachToRecyclerView(rv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.load_button:
                new GetUsers().execute();
                break;
        }
        return true;
    }

    private class GetUsers extends AsyncTask<Void, Void, String>{

        protected void onPreExecute(){
            super.onPreExecute();
            View parent = findViewById(android.R.id.content);
            Snackbar.make(parent, "Loading Users", Snackbar.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(Void... voids){
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String jsonUrl = "http://192.168.43.220/JSONSam/Users/read.php";
            try {
                URL url = new URL(jsonUrl);
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                Log.i(TAG, "Connection opened");
                if (connection.getResponseCode()== HttpURLConnection.HTTP_OK){
                    InputStream stream  = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();

                    String line = "";

                    while ((line = reader.readLine())!=null){
                        buffer.append(line+"\n");
                    }
                    return buffer.toString();
                }
                else{
                    Log.i(TAG, "Error code:"+connection.getResponseCode());
                }
            }
            catch (FileNotFoundException e){
                e.printStackTrace();
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader!=null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        protected void onPostExecute(String result){
            try {
                JSONObject obj = new JSONObject(result);
                JSONArray ja = obj.getJSONArray("Users");
                for (int i = 0; i<ja.length();i++){
                    int x = i+1;
                    JSONObject userJsonObj = (JSONObject) ja.get(i);
                    String name = userJsonObj.getString("Name");
                    String email = userJsonObj.getString("Email");
                    String image = userJsonObj.getString("Image");
                    String toadd = name;
                    users.add(toadd);
                    emails.add(email);
                    images.add(image);
                    adapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            catch (NullPointerException e){
                e.printStackTrace();
            }
            View parent = findViewById(android.R.id.content);
            Snackbar.make(parent, "Finished", Snackbar.LENGTH_LONG).show();
        }
    }
}
