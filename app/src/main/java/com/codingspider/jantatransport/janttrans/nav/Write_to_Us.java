package com.codingspider.jantatransport.janttrans.nav;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.codingspider.jantatransport.janttrans.R;
import com.codingspider.jantatransport.janttrans.data.DataCate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;



public class Write_to_Us extends AppCompatActivity {


    SharedPreferences sharedpreferences;
    String phone,comment1,result;
    TextView comment;
    Button btn_comment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_to_us_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        phone = sharedpreferences.getString("editTextphone1", "");
        System.out.println("SHARED PHONE VALUE ="+phone);
        comment = (TextView) findViewById(R.id.comment);


        btn_comment = (Button) findViewById(R.id.btncomment);
        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PlaceFromTo();




            }
        });






    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    /////////////////////////place details From TO/////////////////////////////////////////////
    private void PlaceFromTo() {

        comment1 = comment.getText().toString();

        register(phone,comment1);
    }

    private void register(String phone, String comment1) {
        String urlSuffix = "?mobile=" + phone+"&comments="+comment1;
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog pdLoading = new ProgressDialog(Write_to_Us.this);

            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //this method will be running on UI thread
                pdLoading.setMessage("\tLoading...");
                pdLoading.setCancelable(false);
                pdLoading.show();
            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;
                try {
                   // URL url = new URL(REGISTER_URL + s);
//                    System.out.println("url = " + url);
//                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
//                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    //TODO: complete this function with all data


                    result = bufferedReader.readLine();
                    System.out.println("result  share = " + result);

                    return result;
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {

                //this method will be running on UI thread

                pdLoading.dismiss();
                List<DataCate> data = new ArrayList<>();


                try {

                    JSONObject jobject = new JSONObject(result);

                    JSONArray jArray = jobject.getJSONArray("success");
                    // Extract data from json and store into ArrayList as class objects
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.getJSONObject(i);
                        DataCate fishData = new DataCate();


                        data.add(fishData);
                    }

                  /*  // Setup and Handover data to recyclerview
                    mRVFishPrice = (RecyclerView) findViewById(R.id.fishPriceList);
                    mAdapter = new AdapterShare(Write_to_Us.this, data);
                    mRVFishPrice.setAdapter(mAdapter);
                    mRVFishPrice.setLayoutManager(new LinearLayoutManager(Write_to_Us.this));*/

                } catch (JSONException e) {
                    //  Toast.makeText(Half_Share.this, e.toString(), Toast.LENGTH_LONG).show();
                }

            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(urlSuffix);
    }

}
//////////////////////////////////////////////////////////////////////////////////////////////////


