package com.codingspider.jantatransport.janttrans.book;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codingspider.jantatransport.janttrans.MainActivity;
import com.codingspider.jantatransport.janttrans.R;
import com.codingspider.jantatransport.janttrans.adapter.AdapterShare;
import com.codingspider.jantatransport.janttrans.data.DataCate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.codingspider.jantatransport.janttrans.book.BookVehicle.MY_PREFS_NAME;


public class ShareActivity extends AppCompatActivity {

    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    private RecyclerView mRVFishPrice;
    private AdapterShare mAdapter;
    //String f="0", booktype ="LHS";
    String origin1,destination1,CateId1,KMPL,phone2,hours,Date, Time,cate_price,catename1,f,booktype,dataStatus = "1",Gsttotal,tot_mins;
    String result;
    TextView time;
    TextView date;
    TextView price;
    private Button btnBookCard;
    private Button btnBookRecy;
    private CardView cardView;

    SharedPreferences sharedpreferences;
    SharedPreferences prefs1;

    private static final String SHOW_SHARE_URL = "http://jt.codingspider.com/jt_app/jt_bookings.php";
    ////place to from url///
    private static final String Book_URL = "http://jt.codingspider.com/jt_app/jt_book.php";

    String BookFull_url = "http://jt.codingspider.com/jt_app/jt_book.php?cat_id=";
    String BookFull_url1 = "&mobile=";
    String BookFull_url2 = "&from=";
    String BookFull_url3 = "&to=";
    String BookFull_url4 = "&distance=";
    String BookFull_url5 = "&price=";
    String BookFull_url6 = "&time=";
    String BookFull_url7 = "&status=";
    String BookFull_url8 = "&booktype=";
    String BookFull_url9 = "&bookDate=";
    String BookFull_url10 = "&bookTime=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_share);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
        prefs1 = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        CateId1 = prefs1.getString("prefBookV_CAT_ID", "");
        phone2 = prefs1.getString("prefBookV_PHONE", "");
        origin1 = prefs1.getString("prefBookV_FROM", "");
        destination1 = prefs1.getString("prefBookV_TO", "");
        KMPL = prefs1.getString("prefBookV_KMPL", "");
        Gsttotal = prefs1.getString("prefBookV_Cate_price", "");
        tot_mins = prefs1.getString("prefBookV_tot_mins", "");

        f = prefs1.getString("prefBookV_Status", "");
        booktype = prefs1.getString("prefBookV_BookType", "");
        Date = prefs1.getString("prefBookV_DATE", "");
        Time = prefs1.getString("prefBookV_TIME", "");

        tot_mins = prefs1.getString("prefBookV_HOUR", "");
        catename1 = prefs1.getString("prefBookV_CATE_NAME", "");


        System.out.println("catename1Share = "+catename1);
        System.out.println("CateId1Share = "+CateId1);
        System.out.println("phone2Share = "+phone2);

        System.out.println("origin1Share = "+origin1);
        System.out.println("destination1Share = "+destination1);

        System.out.println("GsttotalShare = "+Gsttotal);
        System.out.println("tot_mins_Share = "+tot_mins);

        System.out.println("KMPLShare = "+KMPL);
        System.out.println("hoursShare = "+hours);

        System.out.println("DateShare = "+Date);
        System.out.println("TimeShare = "+Time);
        System.out.println("f_Share = "+f);
        System.out.println("booktypeShare = "+booktype);

        time= (TextView) findViewById(R.id.time);
        date= (TextView) findViewById(R.id.date);
        price= (TextView) findViewById(R.id.price);
        cardView = (CardView)findViewById(R.id.CardviewDisplay);
        mRVFishPrice = (RecyclerView)findViewById(R.id.fishPriceList);

        ((TextView) findViewById(R.id.from)).setText(origin1);
        ((TextView) findViewById(R.id.to)).setText(destination1);
        ((TextView) findViewById(R.id.time)).setText(Time);
        ((TextView) findViewById(R.id.date)).setText(Date);
        ((TextView) findViewById(R.id.price)).setText(Gsttotal);

        try {
            PlaceFromTo();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }



        btnBookCard = (Button)findViewById(R.id.book_card);
        btnBookCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //PlaceFromTo1();
                new AsyncFetchBook().execute();
            }
        });

                try {

                    Intent i1=getIntent();
                    dataStatus = i1.getStringExtra("S_DataStatus");

                    if (dataStatus.contains("1"))
                    {

                    }
                    else{

                        Intent i = getIntent();
                        catename1 = i.getStringExtra("S_Vname");
                        CateId1 = i.getStringExtra("S_CateId");
                        origin1 = i.getStringExtra("S_from");
                        destination1 = i.getStringExtra("S_to");
                        KMPL = i.getStringExtra("S_dist");
                        phone2 = i.getStringExtra("S_phone");
                        Date = i.getStringExtra("S_date");
                        hours = i.getStringExtra("S_estTime");
                        Time = i.getStringExtra("S_time");
                        Gsttotal = i.getStringExtra("S_price");
                        booktype = i.getStringExtra("S_bType");
                        f = i.getStringExtra("S_Status");

                        System.out.println("origin1Intent = "+origin1);
                        System.out.println("destination1Intent = "+destination1);
                        System.out.println("CateId1Intent = "+CateId1);
                        System.out.println("KMPLIntent = "+KMPL);
                        System.out.println("hoursIntent = "+hours);
                        System.out.println("phone2Intent = "+phone2);
                        System.out.println("DateIntent = "+Date);
                        System.out.println("TimeIntent = "+Time);
                        System.out.println("GsttotalIntent = "+Gsttotal);
                        System.out.println("booktypeIntent = "+booktype);
                        System.out.println("f_Intent = "+f);
                        System.out.println("dataStatus_Intent = "+dataStatus);

                            //PlaceFromTo1();
                            new AsyncFetchBook().execute();
                    }
                }catch (NullPointerException ne)
                {
                    ne.printStackTrace();
                }
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

   /*place details From TO*/
    private void PlaceFromTo() throws UnsupportedEncodingException {
        register(URLEncoder.encode(origin1, "UTF-8"), URLEncoder.encode(destination1, "UTF-8"),CateId1,f,booktype);
    }

    private void register(String origin11, String destination11, String cateId1, String f, String booktype) {
        String urlSuffix = "?cat_id="+cateId1+"&from="+origin11+"&to="+destination11+"&status="+f+"&booktype="+booktype;
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog pdLoading = new ProgressDialog(ShareActivity.this);

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
                    URL url = new URL(SHOW_SHARE_URL+s);
                    System.out.println("url = "+url);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    //TODO: complete this function with all data


                    result = bufferedReader.readLine();
                    System.out.println("result  share = "+result);

                    return result;
                }catch(Exception e){
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                //this method will be running on UI thread

                System.out.println("resultsss ="+result);
                pdLoading.dismiss();
                List<DataCate> data=new ArrayList<>();

                try {

                    JSONObject jobject = new JSONObject(result);
                    JSONArray jArray = jobject.getJSONArray("success");
                    System.out.println("Show jArray = "+jArray);

                    if (result.contains("{\"success\":null}"))

                    {
                        cardView.setVisibility(View.VISIBLE);
                        mRVFishPrice.setVisibility(View.GONE);

                    }else {

                        cardView.setVisibility(View.GONE);
                        mRVFishPrice.setVisibility(View.VISIBLE);

                        // Extract data from json and store into ArrayList as class objects
                        for (int i = 0; i < jArray.length(); i++) {

                            JSONObject json_data = jArray.getJSONObject(i);
                            System.out.println("Show_json_data = " + json_data);

                            DataCate fishData = new DataCate();

                            fishData.Share_id = json_data.getString("id");
                            fishData.Share_book_id = json_data.getString("book_id");
                            fishData.Share_category_Id = json_data.getString("category_Id");
                            fishData.Share_phone = json_data.getString("phone");

                            fishData.Share_loc_from = json_data.getString("loc_from");
                            fishData.Share_loc_to = json_data.getString("loc_to");
                            fishData.Share_distance = json_data.getString("distance");

                            /*fishData.Share_price = Gsttotal;*/
                            fishData.Share_price = json_data.getString("price");

                            fishData.Share_est_time = json_data.getString("est_time");
                            fishData.Share_bookDate = json_data.getString("bookDate");
                            fishData.Share_bookTime = json_data.getString("bookTime");
                            fishData.Share_bookType = json_data.getString("bookType");
                            fishData.Share_status = json_data.getString("status");

                            System.out.println("Share_id = " + fishData.Share_id);
                            System.out.println("Share_book_id = " + fishData.Share_book_id);
                            System.out.println("Share_category_Id = " + fishData.Share_category_Id);
                            System.out.println("Share_phone = " + fishData.Share_phone);
                            System.out.println("Share_loc_from = " + fishData.Share_loc_from);
                            System.out.println("Share_loc_to = " + fishData.Share_loc_to);
                            System.out.println("Share_distance = " + fishData.Share_distance);
                            System.out.println("Share_price = " + fishData.Share_price);
                            System.out.println("Share_est_time = " + fishData.Share_est_time);
                            System.out.println("Share_bookDate = " + fishData.Share_bookDate);
                            System.out.println("Share_bookTime = " + fishData.Share_bookTime);
                            System.out.println("Share_bookType = " + fishData.Share_bookType);
                            System.out.println("Share_status = " + fishData.Share_status);

                            data.add(fishData);
                        }

                        // Setup and Handover data to recyclerview
                        mAdapter = new AdapterShare(ShareActivity.this, data);
                        mRVFishPrice.setAdapter(mAdapter);
                        mRVFishPrice.setLayoutManager(new LinearLayoutManager(ShareActivity.this));
                    }
                } catch (JSONException e) {
                   // Toast.makeText(ShareActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                }

            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(urlSuffix);
    }

    /*place details From TO of HALF SHARE*/
    private void PlaceFromTo1() {
        register1(origin1,destination1,CateId1,KMPL,hours,f,phone2,Gsttotal,booktype,Date,Time);
        System.out.println("BOOKTYPE = "+booktype);
    }

    private void register1(String origin, String destination, String cateId, String KMPL, String hours, String f, String phone2, String cate_price, String booktype, String date, String time) {
        String urlSuffix = "?cat_id="+cateId+"&mobile="+phone2+"&from="+origin+"&to="+destination+"&distance="+KMPL+"&price="+cate_price+"&time="+hours+"&status="+f+"&booktype="+booktype+"&bookDate="+date+"&bookTime="+time;
        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ShareActivity.this, "Please Wait",null, true, true);
            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(Book_URL+s);
                    System.out.println("urlHalf = "+url);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    //TODO: complete this function with all data
                    String result;

                    result = bufferedReader.readLine();
                    System.out.println("result = "+result);

                    return result;
                }catch(Exception e){
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
               /* Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
                if (s.equalsIgnoreCase("Booking Confirmed")){



                }*/
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(urlSuffix);
    }

    private class AsyncFetchBook extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(ShareActivity.this);
        HttpURLConnection conn;
        URL url = null;

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
            try {

                // Enter URL address where your json file resides
                // Even you can make call to php file which returns json data
                System.out.println("CateId1_URL = "+CateId1);
                System.out.println("phone2_URL = "+phone2);
                System.out.println("origin1_URL = "+origin1);
                System.out.println("destination1_URL = "+destination1);
                System.out.println("KMPL_URL = "+KMPL);
                System.out.println("Gsttotal_URL = "+Gsttotal);
                System.out.println("tot_mins_URL = "+tot_mins);
                System.out.println("f_URL = "+f);
                System.out.println("booktype_URL = "+booktype);
                System.out.println("Date_URL = "+Date);
                System.out.println("Time_URL = "+Time);


                url = new URL(BookFull_url+CateId1+BookFull_url1+phone2+BookFull_url2+URLEncoder.encode(origin1, "utf-8")+BookFull_url3+URLEncoder.encode(destination1, "utf-8")+BookFull_url4+KMPL+BookFull_url5+Gsttotal+BookFull_url6+tot_mins+BookFull_url7+f+BookFull_url8+booktype+BookFull_url9+Date+BookFull_url10+Time);
                System.out.println("ShareBook_URL = "+url);

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {

                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                // setDoOutput to true as we recieve data from json file
                conn.setDoOutput(true);

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread
            pdLoading.dismiss();

            //List<DataFish> data=new ArrayList<>();

            pdLoading.dismiss();
            try {
                JSONObject object = new JSONObject(result);
                System.out.println("object = "+object);

                String confirm = object.toString();
                System.out.println("confirm = "+confirm);
                if (confirm.contains("Booking Confirmed")){

                    Intent i = new Intent(getApplicationContext(), SuccessBooking_Page.class);

                    i.putExtra("CATE_NAME",catename1);
                    i.putExtra("CAT_ID",CateId1);

                    i.putExtra("TO",origin1);
                    i.putExtra("FROM",destination1);
                    i.putExtra("DATE",Date);
                    i.putExtra("TIME",Time);

                    i.putExtra("PRICE",tot_mins);
                    i.putExtra("KM",KMPL);

                    i.putExtra("HOUR",hours);
                    i.putExtra("PHONE",phone2);

                    System.out.println("origin1 = "+confirm);

                    startActivity(i);
                    Toast.makeText(getApplication(), "Booking Confirmed".toString(), Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Booking Not Confirmed".toString(), Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }

            } catch (JSONException mess) {
                Toast.makeText(ShareActivity.this, "Something went wrong".toString(), Toast.LENGTH_LONG).show();
                System.out.println("Exception1 = "+mess);
            }

        }
    }
}
