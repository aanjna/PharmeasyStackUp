package com.solution.pharmeasy;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    // JSON Node names
    private static final String TAG_NAME = "oner";
    private static final String TAG_IMAGE = "image";
    private static final String TAG_HEADING = "heading";
    private static final String TAG_SUBHEADING = "subheding";

    private static String url = " https://api.stackexchange.com/2.2/search/advanced?order=desc&sort=activity&accepted=False&answers=0&tagged=android&site=stackoverflow";
    ArrayAdapter<String> adapter;
    EditText inputSearch;
    // contacts JSONArray
    JSONArray contacts = null;
    // Hashmap for ListView
    ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
    private ProgressDialog pDialog;
    private ListView list;
    ImageView img;
    TextView heading, subheding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactList = new ArrayList<HashMap<String, String>>();

        Button Btngetdata = (Button)findViewById(R.id.getdata);
        Btngetdata.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new JSONParse().execute();
            }
        });
        // Calling async task to get json
    }

    private class JSONParse extends AsyncTask<String, String, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            img =(ImageView)findViewById(R.id.iv_img);
            heading = (TextView) findViewById(R.id.tv_heading);
            subheding = (TextView) findViewById(R.id.tv_subheding);
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... arg0) {
            // Creating service handler class instance
            ServiceHandler jParser = new ServiceHandler();
            JSONObject json = jParser.getJSONFromUrl(url);
            return json;

        }

        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {
                // Getting JSON Array from URL
                contacts = json.getJSONArray(TAG_NAME);
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);

                    // Storing  JSON item in a Variable
                    String heading = c.getString(TAG_HEADING);
                    String subheding = c.getString(TAG_SUBHEADING);

                    // Adding value HashMap key => value

                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put(TAG_HEADING, heading);
                    map.put(TAG_SUBHEADING, subheding);

                    contactList.add(map);
                    list = (ListView) findViewById(R.id.list_view);

                    ListAdapter adapter = new SimpleAdapter(MainActivity.this, contactList,
                            R.layout.activity_likelist,
                            new String[]{TAG_IMAGE, TAG_HEADING, TAG_SUBHEADING}, new int[]{
                            R.id.iv_img, R.id.tv_heading, R.id.tv_subheding});

                    list.setAdapter(adapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}