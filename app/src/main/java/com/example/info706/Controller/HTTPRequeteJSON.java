package com.example.info706.Controller;

import android.os.AsyncTask;
import android.util.Log;

import com.example.info706.View.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPRequeteJSON extends AsyncTask<String, String, JSONObject> {
    private MainActivity mainActivity;
    private String url;

    public HTTPRequeteJSON(String url, MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        try {
            URL url = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();

            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String content = "";
            String line;
            while ((line = rd.readLine()) != null) {
                content += line + "\n";
            }
            Log.d("contentJ",content);
            return new JSONObject(content);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        this.mainActivity.onResponse(jsonObject);
    }
}
