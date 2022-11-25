package com.android.carrentalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class HomeActivity extends AppCompatActivity {

    HashMap<String,String> params = new HashMap<>();

    ProgressDialog progressDialog;
    ArrayList<item_product> arrayList = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        recyclerView = findViewById(R.id.rv_carsavailableforrent);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL, false));
        params.put("Catogery", "cars");

        try {
           // progressDialog = ProgressDialog.show(HomeActivity.this, "Loading...", "please wait",true,true);

            String request = new PerformNetworkRequest(API.URL_GET_CATOGERY, params, 1024).execute().get();
            JSONObject object = new JSONObject(request);
            if (object.getBoolean("error")) {

            } else {
                JSONArray jsonArray = object.getJSONArray("products");



                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(j);

                    item_product item_product = new item_product(
                            jsonObject.getInt("id"),
                            jsonObject.getString("url"),
                            jsonObject.getString("code"),
                            jsonObject.getString("companyname"),
                            jsonObject.getString("modelname"),
                            jsonObject.getString("bits"),
                            jsonObject.getString("catogery"));
                    arrayList.add(item_product);
                }


            }

            adapter_products adapter = new adapter_products(getApplicationContext(), arrayList,"20");
            recyclerView.setAdapter(adapter);
           // progressDialog.dismiss();

        } catch (JSONException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {

        //the url where we need to send the request
        String url;

        //the parameters
        HashMap<String, String> params;

        //the request code to define whether it is a GET or POST
        int requestCode;

        //constructor to initialize values
        PerformNetworkRequest(String url,HashMap<String, String> params, int requestCode) {
            this.url = url;

            this.params = params;

            this.requestCode = requestCode;
        }

        //when the task started displaying a
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        //this method will give the response from the request
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }



        //the network operation will be performed in background
        @Override
        protected String doInBackground(Void... voids) {
            requestHandler RequestHandler = new requestHandler();

            return  RequestHandler.sendPostRequest(url, params);
        }


    }



    }





