package com.codingspider.jantatransport.janttrans.nav;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codingspider.jantatransport.janttrans.R;
import com.codingspider.jantatransport.janttrans.adapter.AdapterBooked;
import com.codingspider.jantatransport.janttrans.data.DataCate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;



public class Bookings extends AppCompatActivity {



    String result;
    private RecyclerView mRVFishPrice;
    private AdapterBooked mAdapter;
    String id1, phone1, email1, fname1, lname1, date1, isLoggedin, booked_phone;

    SharedPreferences sharedpreferences;
    SharedPreferences prefs1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookings_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        prefs1 = this.getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        id1 = prefs1.getString("pref_id"," ");
        phone1 = prefs1.getString("pref_name"," ");
        email1 = prefs1.getString("pref_email"," ");
        fname1 = prefs1.getString("pref_fname"," ");
        lname1 = prefs1.getString("pref_lname"," ");
        date1 = prefs1.getString("pref_date"," ");

        //concat fname and lname
        /*fullName = fname1 +" "+ lname1;*/

        System.out.println("IsLoggedIn On= " + isLoggedin);
        System.out.println("phone1 On= " + phone1);
        System.out.println("email1 On= " + email1);
        System.out.println("fname1 On= " + fname1);
        System.out.println("lname1 On= " + lname1);
        System.out.println("date1 On= " + date1);

        PlaceFromTo();
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


    /////////////////////////place details From TO/////////////////////////////////////////////
    private void PlaceFromTo() {
        register(phone1);
    }

    private void register(final String phone) {
        String urlSuffix = "?mobile=" + phone;
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog pdLoading = new ProgressDialog(Bookings.this);

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
//                    URL url = new URL(REGISTER_URL + s);
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

                    JSONArray jArray = jobject.getJSONArray("bookings");
                    // Extract data from json and store into ArrayList as class objects
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.getJSONObject(i);

                        booked_phone = json_data.getString("phone");
                        System.out.println("booked_phone" +booked_phone);

                        if (booked_phone.contains(phone1)) {
                            DataCate fishData = new DataCate();

                            fishData.booked_loc_from = json_data.getString("loc_from");
                            fishData.booked_loc_to = json_data.getString("loc_to");
                            fishData.booked_distance = json_data.getString("distance");
                            fishData.booked_price = json_data.getString("price");
                            fishData.booking_Esttime = json_data.getString("est_time");
                            fishData.booked_bookDate = json_data.getString("bookDate");
                            fishData.booked_bookTime = json_data.getString("bookTime");
                            fishData.booked_bookType = json_data.getString("bookType");

                            System.out.println("FROM" +fishData.booked_loc_from);
                            System.out.println("TO" +fishData.booked_loc_to);
                            System.out.println("DISTANCE" +fishData.booked_distance);
                            System.out.println("PRICE" +fishData.booked_price);
                            System.out.println("Esttime" +fishData.booking_Esttime);
                            System.out.println("bookDate" +fishData.booked_bookDate);
                            System.out.println("bookTime" +fishData.booked_bookTime);
                            System.out.println("bookType" +fishData.booked_bookType);
                            // System.out.println("BOOKING TIME" + json_data.getString("booking_time"));

                            data.add(fishData);
                        }else {


                        }
                    }

                    // Setup and Handover data to recyclerview
                    mRVFishPrice = (RecyclerView) findViewById(R.id.booked_List);
                    mAdapter = new AdapterBooked(Bookings.this, data);
                    mRVFishPrice.setAdapter(mAdapter);
                    mRVFishPrice.setLayoutManager(new LinearLayoutManager(Bookings.this));

                } catch (JSONException e) {
                    //  Toast.makeText(Half_Share.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(urlSuffix);
    }

}
