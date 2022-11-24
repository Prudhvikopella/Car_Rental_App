package com.android.carrentalapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class adapter_products extends RecyclerView.Adapter {
    ArrayList<item_product> item_product;
    Context context;
    ProgressDialog progressDialog;
    String  helper;
    String Count,productId;
   // TextView bitscount;
    String myCount,percenttoDisplay;
    int bits;

    public adapter_products(Context context, ArrayList<item_product> item_product, String myCount) {
        this.context = context;
        this.item_product = item_product;
        this.myCount = myCount;
    }

    @Override
    public myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rent_vehicles, parent, false);
        myviewholder vh = new myviewholder(v);
        //vh.setIsRecyclable(false);
        return vh;

    }

    @Override
    public void onBindViewHolder(
            RecyclerView.ViewHolder holder, int position) {


        Picasso.with(context.getApplicationContext()).load(String.valueOf(item_product.get(position).getImageurl())).centerCrop().fit().into(myviewholder.image);
        //// Picasso.with(context.getApplicationContext()).load("http://192.168.0.106/blancocars/images/123.jpeg").into(myviewholder.image);
        myviewholder.carname.setText(item_product.get(position).getCompanyname());
        myviewholder.varient.setText(item_product.get(position).getModelname());
        bits  = Integer.parseInt(item_product.get(position).getBits());
        percenttoDisplay = Integer.toString(bits/100000);
        myviewholder.availabilitytext.setText(percenttoDisplay + "%");






//        myviewholder.vote.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = holder.getAdapterPosition();
//                productId = item_product.get(position).getcode().toString();
//                String Unid = productId;
//                SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
//                String userid = sharedPreferences.getString("uniqueid", "");
//
//
//                try {
//                    HashMap<String, String> params = new HashMap<>();
//                    params.put("code", Unid);
//                    params.put("uniqueid", userid);
//                    String request = new PerformNetworkRequest2(API.URL_CREATE_USER, params, 1025).execute().get();
//                    JSONObject object = new JSONObject(request);
//
//                    if (!object.getBoolean("error")) {
//                        String count = object.getString("response");
//                        Count = count;
//                        helper = "1";
//
//                    } else {
//                        helper = "0";
//                        Count = "0";
//
//                    }
//                } catch (Exception e) {
//                }
                //Bottomsheet intializing
//                BottomSheetDialog bottomsheet = new BottomSheetDialog(v.getContext(), R.style.BottomSheetDialogTheme);
//                View bottomsheetview = LayoutInflater.from(v.getContext()).inflate(R.layout.bottomsheet_ind_payment, (RelativeLayout) v.findViewById(R.id.bottom_sheet));
//                //Bottomsheet variables declaration
//                EditText value = bottomsheetview.findViewById(R.id.et_value);
//                TextView bitscount = bottomsheetview.findViewById(R.id.tv_count);
//                TextView productName = bottomsheetview.findViewById(R.id.tv_productName);
//                Button payment = bottomsheetview.findViewById(R.id.bt_payment);
//                //setting the amount of bits user had on the product and setting to the text view
//                bitscount.setText(Count);
//                productName.setText(item_product.get(position).getCompanyname()+ " " + item_product.get(position).getModelname());
//                //setting on clicklistener for the payment button
//                bottomsheetview.findViewById(R.id.bt_payment).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String Bit = value.getText().toString();
//                        if (Bit.equals("") || Bit.equals("0")) {
//                            Toast.makeText(v.getContext(), "enter quantity to continue", Toast.LENGTH_SHORT).show();
//                            return;
//                        } else {
//                            String Unid = productId;
//                            SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
//                            String userid = sharedPreferences.getString("uniqueid", "");
//                            HashMap<String, String> params = new HashMap<>();
//                            params.put("code", Unid);
//                            params.put("uniqueid", userid);
//                            params.put("bits", Bit);
//                            PerformNetworkRequest2 performNetworkRequest2 = new PerformNetworkRequest2(API.URL_PRODUCT_PURCHASE, params, 1025);
//                            performNetworkRequest2.execute();
//
//                        }
//                    }
//                });
//
//                bottomsheet.setContentView(bottomsheetview);
//                bottomsheet.show();
//            }
         //   }


      //  });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();


            }
        });


    }

    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {

        //the url where we need to send the request
        String url;

        //the parameters
        HashMap<String, String> params;

        //the request code to define whether it is a GET or POST
        int requestCode;

        String condition;

        //constructor to initialize values
        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;

            this.params = params;

            this.requestCode = requestCode;
        }

        //when the task started displaying a
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        public void onPostExecute(String s) {
            super.onPostExecute(s);
        }


        //the network operation will be performed in background
        @Override
        protected String doInBackground(Void... voids) {
            requestHandler RequestHandler = new requestHandler();
            String result = RequestHandler.sendPostRequest(url, params);

            return result;

        }

    }


    private class PerformNetworkRequest2 extends AsyncTask<Void, Void, String> {

        //the url where we need to send the request
        String url;

        //the parameters
        HashMap<String, String> params2;

        //the request code to define whether it is a GET or POST
        int requestCode;

        //constructor to initialize values
        PerformNetworkRequest2(String url, HashMap<String, String> params2, int requestCode) {
            this.url = url;

            this.params2 = params2;

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
            try {

                JSONObject object = new JSONObject(s);


                if (!object.getBoolean("error")) {
                    Toast.makeText(context, object.getString("response"), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, object.getString("response"), Toast.LENGTH_SHORT).show();

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        //the network operation will be performed in background
        @Override
        protected String doInBackground(Void... voids) {
            requestHandler RequestHandler = new requestHandler();
            String result = RequestHandler.sendPostRequest(url, params2);

            return result;
        }

    }

    private class Bottomsheetdialog {
        String Count;

        void changeText(String Count){

        }
    }

    @Override
    public int getItemCount() {
        return item_product.size();
    }

    public static class myviewholder extends RecyclerView.ViewHolder {
        static ImageView image;
        static TextView carname,varient,price,availabilitytext;
        static Button vote;

        public myviewholder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.iv_itemimage);
            carname = (TextView) itemView.findViewById(R.id.tv_carname);
            varient = (TextView) itemView.findViewById(R.id.tv_carvarient);
            price = (TextView) itemView.findViewById(R.id.tv_price);
            availabilitytext = (TextView) itemView.findViewById(R.id.tv_availabilitytext);


        }
    }
}




