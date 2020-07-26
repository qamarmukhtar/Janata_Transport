package com.codingspider.jantatransport.janttrans.book;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.codingspider.jantatransport.janttrans.MainActivity;
import com.codingspider.jantatransport.janttrans.R;

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

public class ShareRecy_Book extends AppCompatActivity {

    String origin1,destination1,CateId1,KMPL,phone2,hours,Date, Time,catename1,f,booktype,dataStatus = "1",Gsttotal,tot_mins;

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

    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_share_recy_book);

        try {
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
            dataStatus = i.getStringExtra("S_DataStatus");

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

            if (dataStatus.contains("0"))
            {
                //PlaceFromTo1();
                new AsyncFetchBook().execute();
            }
        }catch (NullPointerException ne)
        {
            ne.printStackTrace();
        }
    }

    private class AsyncFetchBook extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(ShareRecy_Book.this);
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


                url = new URL(BookFull_url+CateId1+BookFull_url1+phone2+BookFull_url2+ URLEncoder.encode(origin1, "utf-8")+BookFull_url3+URLEncoder.encode(destination1, "utf-8")+BookFull_url4+KMPL+BookFull_url5+Gsttotal+BookFull_url6+tot_mins+BookFull_url7+f+BookFull_url8+booktype+BookFull_url9+Date+BookFull_url10+Time);
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
                Toast.makeText(ShareRecy_Book.this, "Something went wrong".toString(), Toast.LENGTH_LONG).show();
                System.out.println("Exception1 = "+mess);
            }

        }
    }
}
