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

public class OthersVM extends ViewModel {
    private ArrayList<Other> others;

    public ArrayList<Other> getOther(){
        if (others == null){
            others = new ArrayList();
            new GetUsers().execute();
            Log.i("Main", "Running2");
        }
        return others;
    }

    private class GetUsers extends AsyncTask<Void, Void, String> {

        protected void onPreExecute(){
            super.onPreExecute();
            Log.i("Main", "Boo");
        }

        @Override
        protected String doInBackground(Void... voids){
            HttpURLConnection connection = null;
            BufferedReader reader = null;
                String jsonUrl = "http://192.168.43.220/JSONSam/Others/read.php";
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
                JSONArray ja = obj.getJSONArray("Others");
                for (int i = 0; i<ja.length();i++){
                    JSONObject otherJsonObj = (JSONObject) ja.get(i);
                    String name = otherJsonObj.getString("Name");
                    String gender = otherJsonObj.getString("Gender");
                    Other newother = new Other(name,gender);
                    others.add(newother);
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
