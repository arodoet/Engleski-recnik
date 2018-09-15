package com.example.teodora.mojrecnik;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class RecnikZahtev extends AsyncTask<String, Integer, String>{

    String konacnaDefinicijaReci;

    TextView defReci;

    public RecnikZahtev(TextView defReci ) {
        this.defReci = defReci;
    }


    @Override
    protected String doInBackground(String... params) {

        final String app_id = "e3295af9";
        final String app_key = "3994b9ec5535ce11651494683186fd06";
        try {
            URL url = new URL(params[0]);

            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setRequestProperty("app_id",app_id);
            urlConnection.setRequestProperty("app_key",app_key);

            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            return stringBuilder.toString();

        }
        catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }



    @Override
    protected void onPostExecute(String result) {   // ova fja prima string od doInBackground()
        super.onPostExecute(result);

        Log.d("Definicija ", result);

        try{

            JSONObject objekat = new JSONObject(result);

            JSONArray niz = objekat.getJSONArray("results");

            JSONObject obj = niz.getJSONObject(0);

            JSONArray niz1 = obj.getJSONArray("lexicalEntries");

            JSONObject obj1 = niz1.getJSONObject(0);

            JSONArray niz2 = obj1.getJSONArray("entries");

            JSONObject obj2 = niz2.getJSONObject(0);

            JSONArray niz3 = obj2.getJSONArray("senses");

            JSONObject obj3 = niz3.getJSONObject(0);

            JSONArray niz4 = obj3.getJSONArray("definitions");

            konacnaDefinicijaReci= niz4.getString(0);

            defReci.setAlpha(1);
            defReci.setText(konacnaDefinicijaReci);


        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
