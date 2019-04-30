package com.example.jsonsample;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Main extends AppCompatActivity{

    ArrayList <String> users;
    String TAG = "Main Activity";
    UserRecyclerView adapter;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        users = new ArrayList<>();
        Button load = findViewById(R.id.loadbtn);
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start Async method to retrieve data
                new GetUsers().execute();
            }
        });
        RecyclerView rv = findViewById(R.id.users_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserRecyclerView(this,users);
        rv.setAdapter(adapter);
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
            String jsonUrl = "https://jsonplaceholder.typicode.com/users";
            try {
                URL url = new URL(jsonUrl);
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                Log.i(TAG, "Connection opened");

                InputStream stream  = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";

                while ((line = reader.readLine())!=null){
                    buffer.append(line+"\n");
                }
                return buffer.toString();
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
                JSONArray ja = new JSONArray(result);
                for (int i = 0; i<ja.length();i++){
                    int x = i+1;
                    JSONObject jObject = (JSONObject) ja.get(i);
                    String name = jObject.getString("name");
                    String toadd = x+". "+name;
                    users.add(toadd);
                    adapter.notifyDataSetChanged();
                    Log.i("Added:", name);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            View parent = findViewById(android.R.id.content);
            Snackbar.make(parent, "Finished", Snackbar.LENGTH_LONG).show();
        }
    }
}
