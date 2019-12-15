package com.example.info706.Controller;

import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.example.info706.R;
import com.example.info706.View.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Classe permettant de gérer la connexion au serveur avec une AsyncTask
 * @author Brozzoni Vincent / Jugand Théo
 */
public class HTTPRequeteJSON extends AsyncTask<String, String, JSONObject> {
    /**
     * Instance de classe MainActivity
     */
    private MainActivity mainActivity;
    /**
     * Url du serveur
     */
    private String url;

    /**
     * Constructeur de la classe HTTPRequeteJSON
     * @param url Url du serveur
     * @param mainActivity Instance de la classe MainActivity
     */
    public HTTPRequeteJSON(String url, MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * Méthode permettant de récupérer un JSONObject à partir des données sur le serveur
     * @param strings
     * @return
     */
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

    /**
     * Méthode permettant de récupérer un JSONObject dans la classe MainActivity et de prévenir l'utilisateur si le périphérique n'est pas connecté à internet
     * @param jsonObject
     */
    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        if(jsonObject != null) {
            this.mainActivity.onResponse(jsonObject);
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this.mainActivity);
            View viewLayout = this.mainActivity.getLayoutInflater().inflate(R.layout.internet_dialog, null);
            builder.setCancelable(false);
            builder.setView(viewLayout);
            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }
    }
}
