package com.codingspider.jantatransport.janttrans.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.codingspider.jantatransport.janttrans.R;
import com.codingspider.jantatransport.janttrans.adapter.AdapterShare;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;



public class Book_Less_Share extends AppCompatActivity {


    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private RecyclerView mRVFishPrice;
    private AdapterShare mAdapter;
    String f="0",booktype ="LHS";
    String origin1,destination1,CateId1,KMPL,phone2,hours,Date, Time,cate_price,catename1,est_time;
    String phone,result;
    TextView from;
    TextView to;
    TextView KM1;
    TextView date;
    TextView price;
    TextView time;
    private Button btnBook;
    SharedPreferences sharedpreferences;
    SharedPreferences prefs1;
    String id1, phone1, email1, fname1, lname1, date1, isLoggedin;

    ////place to from url///
    private static final String HALF_SHARE = "http://jt.codingspider.com/jt_app/jt_book.php";
    private static final String REGISTER_URL = "http://jt.codingspider.com/jt_app/jt_bookings.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activities_book__less__share);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        prefs1 = this.getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        //isLoggedin = prefs1.getString("loggedIn"," ");
        id1 = prefs1.getString("pref_id"," ");
        phone1 = prefs1.getString("pref_name"," ");
        email1 = prefs1.getString("pref_email"," ");
        fname1 = prefs1.getString("pref_fname"," ");
        lname1 = prefs1.getString("pref_lname"," ");
        date1 = prefs1.getString("pref_date"," ");

        //System.out.println("IsLoggedIn On= " + isLoggedin);
        System.out.println("phone1 On= " + phone1);
        System.out.println("email1 On= " + email1);
        System.out.println("fname1 On= " + fname1);
        System.out.println("lname1 On= " + lname1);
        System.out.println("date1 On= " + date1);


        Bundle bundle = getIntent().getExtras();

        origin1 = bundle.getString("putFrom", "");
        destination1 = bundle.getString("putTo", "");
        KMPL = bundle.getString("putKm","");
        hours = bundle.getString("putest_time","");
        // phone2 = bundle.getString("PHONE","");
        Date = bundle.getString("putDate","");
        Time = bundle.getString("putTime","");
        cate_price = bundle.getString("putPrice","");
        catename1  = bundle.getString("CATE_NAME", "");
        CateId1 = bundle.getString("putID","");
        // est_time = bundle.getString("putest_time","");

        System.out.println("origin"+origin1);
        System.out.println("destination1"+destination1);
        System.out.println("CateId1"+CateId1);
        System.out.println("KMPL"+KMPL);
        System.out.println("hours"+hours);
        System.out.println("phone2"+phone2);
        System.out.println("Date"+Date);
        System.out.println("cate_price"+cate_price);
        System.out.println("Time"+Time);
        // System.out.println("putest_time"+est_time);
        System.out.println("BOOKTYPE = "+booktype);



        /*time= (TextView) findViewById(R.id.time);
        date= (TextView) findViewById(R.id.date);
        price= (TextView) findViewById(R.id.price);*/


        ((TextView) findViewById(R.id.from)).setText(origin1);
        ((TextView) findViewById(R.id.to)).setText(destination1);
        ((TextView) findViewById(R.id.time2)).setText(Time);
        ((TextView) findViewById(R.id.KM1)).setText(KMPL);
        ((TextView) findViewById(R.id.date1)).setText(Date);
        ((TextView) findViewById(R.id.price)).setText(cate_price);

        btnBook = (Button)findViewById(R.id.book1);
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PlaceFromTo1();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


                Intent i = new Intent(getApplicationContext(), HS.class);

                i.putExtra("TO",origin1);
                i.putExtra("FROM",destination1);
                i.putExtra("CAT_ID",CateId1);
                i.putExtra("Cate_price",cate_price);
                i.putExtra("KMPL",KMPL);
                i.putExtra("HOUR",hours);
                i.putExtra("PHONE",phone2);
                i.putExtra("DATE",Date);
                i.putExtra("TIME",Time);
                i.putExtra("CATE_NAME",catename1);

                startActivity(i);


            }
        });

    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
    ///////////////////////////////////////////////place details From TO of HALF SHARE//////////////
    private void PlaceFromTo1() throws UnsupportedEncodingException {



        register1(URLEncoder.encode(origin1,"UTF-8"),URLEncoder.encode(destination1, "UTF-8"),CateId1,KMPL,hours,f,phone1,cate_price,booktype,Date,Time);
        System.out.println("BOOKTYPE = "+booktype);
    }

    private void register1(String origin, String destination, String cateId, String KMPL, String duration, String f, String phone2, String cate_price, String booktype, String date, String time) {
        String urlSuffix = "?cat_id="+cateId+"&mobile="+phone2+"&from="+origin+"&to="+destination+"&distance="+KMPL+"&price="+cate_price+"&time="+duration+"&status="+f+"&booktype="+booktype+"&bookDate="+date+"&bookTime="+time;
        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Book_Less_Share.this, "Please Wait",null, true, true);
            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(HALF_SHARE+s);
                    System.out.println("url = "+url);
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
              /*  Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
                if (s.equalsIgnoreCase("Booking Confirmed")){



                }*/
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(urlSuffix);
    }


////////////////////////////////////////////////////////////////////////////////////////////////////
}

