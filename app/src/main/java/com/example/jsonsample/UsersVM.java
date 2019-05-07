package com.example.jsonsample;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.ViewModel;

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
import java.util.List;

public class UsersVM extends ViewModel {
    private ArrayList<User> users;

    public List<User> getUser(){
        if (users == null){
            users = new ArrayList();
            new GetUsers().execute();
        }
        return users;
    }

    private class GetUsers extends AsyncTask<Void, Void, String> {

        protected void onPreExecute(){
            super.onPreExecute();
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
                Log.i("VM", "Connection opened");
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
                    Log.i("VM", "Error code:"+connection.getResponseCode());
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
                    JSONObject userJsonObj = (JSONObject) ja.get(i);
                    String name = userJsonObj.getString("Name");
                    String email = userJsonObj.getString("Email");
                    String image = userJsonObj.getString("Image");
                    User newuser = new User(name,email,image);
                    users.add(newuser);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            catch (NullPointerException e){
                e.printStackTrace();
            }
        }
    }

}
