package com.solution.pharmeasy;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServiceHandler {
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    // constructor
    public ServiceHandler() {

    }

    public JSONObject getJSONFromUrl(String url) {

        BufferedReader reader = null;

        try {
            URL urlp = new URL(url);

            try {
                HttpURLConnection c = (HttpURLConnection) urlp.openConnection();
                c.setRequestMethod("GET");
                c.setReadTimeout(10000);
                c.connect();
                reader = new BufferedReader(new InputStreamReader(c.getInputStream(), "UTF-8"));

                StringBuilder buf = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    buf.append(line);
                }

                json = buf.toString();
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }

        } catch (IOException e) {
// } catch (MalformedURLException e) {

            Log.e("MalformedURLException", "MalformedURLException " + e.toString());
        }

// try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

// return JSON String
        return jObj;
    }
}