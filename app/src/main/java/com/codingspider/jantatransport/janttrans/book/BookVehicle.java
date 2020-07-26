package com.codingspider.jantatransport.janttrans.book;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.codingspider.jantatransport.janttrans.MainActivity;
import com.codingspider.jantatransport.janttrans.R;
import com.codingspider.jantatransport.janttrans.data.DataCate;
import com.codingspider.jantatransport.janttrans.map.DirectionFinder;
import com.codingspider.jantatransport.janttrans.map.DirectionFinderListener;
import com.codingspider.jantatransport.janttrans.map.Route;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

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
import java.util.Calendar;
import java.util.List;

/**
 * Created by CS-001 on 20-Sep-17.
 */

public class BookVehicle extends AppCompatActivity implements OnMapReadyCallback, DirectionFinderListener, Spinner.OnItemSelectedListener, View.OnClickListener {

    String CAL_URL = "http://jt.codingspider.com/jt_app/calculation.php?km=";
    String PRICE_URL ="&catid=";

    String BookSH_url = "http://jt.codingspider.com/jt_app/jt_bookings.php?cat_id=";
    String BookSH_url1 = "&from=";
    String BookSH_url2 = "&to=";
    String BookSH_url3 = "&status=";
    String BookSH_url4 = "&booktype=";

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
    String booktype = "LHS";
    String message;
    /*String booktype1 ="LHS", booktype2 ="HS";*/

    String CateId,CateName,phone2,UserName;
    TextView price;
    TextView enquiry;
    TextView pricevalue;
    TextView priceGst;
    String priceLH;
    private ProgressDialog progressDialog;

    private GoogleMap mMap;
    String origin,destination;
    String origin1,destination1;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();

    int hr1, min1, cov_mins, tot_minsint;
    String tot_mins;
    String hr, mins;
    String Duration, str1, hours, distanceKMPL, str, KMPL, cate_price;
    float cate_P;
    String Gsttotal;

    private RadioGroup radioGroup;
    private RadioButton Share,Full;

    private RadioGroup radioGroupG;
    private RadioButton lHalf,Half;
    String f1="0";

    //Declaring an Spinner
    Spinner spinner;//An ArrayList for Spinner Items
    ArrayList<String> students;
    String sp1;

    private EditText datepick,timepick;
    private Button datepicker,timepicker;
    private int mYear, mMonth, mDay;
    String Date, Time;

    Button buttonDistance;
    Button buttonBook;
    Button buttonProceedBookHS;
    Button buttonProceedBookLHS;

    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    public static final String MY_PREFS_NAME = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_vehicle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pricevalue = (TextView) findViewById(R.id.textViewPrice);
        price = (TextView) findViewById(R.id.textViewPricetype);
        enquiry = (TextView) findViewById(R.id.textViewEnquirytype);
        priceGst = (TextView) findViewById(R.id.textViewTotalPrice1);

        Bundle bundle = getIntent().getExtras();
        phone2 = bundle.getString("key1", "");
        CateId = bundle.getString("key", "");
        CateName = bundle.getString("key2", "");
        UserName = bundle.getString("key3", "");

        System.out.println("CATE_ID = "+CateId);
        System.out.println("Phone2 = "+phone2);
        System.out.println("CateName = "+CateName);
        System.out.println("UserName = "+UserName);

        buttonBook = (Button)findViewById(R.id.btn_book);
        buttonBook.setOnClickListener(this);
        buttonProceedBookHS = (Button)findViewById(R.id.btn_proceedHS);
        buttonProceedBookHS.setOnClickListener(this);
        buttonProceedBookLHS = (Button)findViewById(R.id.btn_proceedLHS);
        buttonProceedBookLHS.setOnClickListener(this);

        if (CateName.contains("4000")) {
            price.setVisibility(View.GONE);
            enquiry.setVisibility(View.VISIBLE);
            pricevalue.setVisibility(View.GONE);
        }
        else {
            price.setVisibility(View.VISIBLE);
            enquiry.setVisibility(View.GONE);
        }

        System.out.println("f1_status1 = "+f1);
        // Retrieve the PlaceAutocompleteFragment.

        PlaceAutocompleteFragment Places = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        Places.setHint("Pick");
        Places.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                Toast.makeText(getApplicationContext(),place.getAddress(),Toast.LENGTH_SHORT).show();
                origin = String.valueOf(place.getAddress());
                origin1 = String.valueOf(place.getName());
                System.out.println("Orign = "+origin);
                System.out.println("Orign1 = "+origin1);
            }

            @Override
            public void onError(Status status) {

                Toast.makeText(getApplicationContext(),status.toString(),Toast.LENGTH_SHORT).show();

            }
        });
        //
        PlaceAutocompleteFragment Places1 = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment1);
        Places1.setHint("Drop");

        Places1.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                Toast.makeText(getApplicationContext(),place.getAddress(),Toast.LENGTH_SHORT).show();
                destination =String.valueOf(place.getAddress());
                destination1 =String.valueOf(place.getName());
                System.out.println("Destination = "+destination);
                System.out.println("destination1 = "+destination1);
            }

            @Override
            public void onError(Status status) {
                Toast.makeText(getApplicationContext(),status.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        //map program
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        radioGroup = (RadioGroup) findViewById(R.id.radio);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if(checkedId == R.id.radioShare) {
                    radioGroupG.setVisibility(View.VISIBLE);

                    f1 ="0";
                }
                else if(checkedId == R.id.radioFull)
                {
                    radioGroupG.setVisibility(View.GONE);
                    f1 ="1";
                    booktype = "F";
                    System.out.println("book type = "+booktype);
                    System.out.println("f1_status = "+f1);
                }
            }
        });
        Share = (RadioButton) findViewById(R.id.radioShare);
        Full = (RadioButton) findViewById(R.id.radioFull);

        radioGroupG = (RadioGroup) findViewById(R.id.radioG);
        radioGroupG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if(checkedId == R.id.radio_lesshalf)
                {
                    f1 ="0";
                    booktype = "LHS";
                    System.out.println("book type = "+booktype);
                    System.out.println("f1_status = "+f1);

                }
                else if(checkedId == R.id.radio_half)
                {
                    f1 ="0";
                    booktype = "HS";
                    System.out.println("book type = "+booktype);
                    System.out.println("f1_status = "+f1);

                }
            }
        });
        lHalf = (RadioButton) findViewById(R.id.radio_lesshalf);
        Half = (RadioButton) findViewById(R.id.radio_half);

        /*spinner = (Spinner)findViewById(R.id.spinnerLabour);
        //spinner.setAdapter(new ArrayAdapter<String>(BookVehicle.this, R.layout.spinner_item_layout, students));
        spinner.setOnItemSelectedListener(BookVehicle.this);
        spinner.setPrompt("-----Select User------");*/

        spinner = (Spinner) findViewById(R.id.spinnerLabour);
        final String[] spstr = getResources().getStringArray(R.array.labour);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,R.layout.spinner_item_layout,spstr);
        aa.setDropDownViewResource(R.layout.spinner_menu_dropdown_item);
        spinner.setAdapter(aa);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String s=((TextView)view).getText().toString();
                if(s.equals("0 Labour Charge"))
                    sp1 = "0";
                if(s.equals("1 Labour Charge"))
                    sp1 = "200";
                if(s.equals("2 Labour Charge"))
                    sp1 = "400";
                if(s.equals("3 Labour Charge"))
                    sp1 = "600";
                if(s.equals("4 Labour Charge"))
                    sp1 = "800";
                if(s.equals("5 Labour Charge"))
                    sp1 = "1000";
                if(s.equals("6 Labour Charge"))
                    sp1 = "1200";
                if(s.equals("7 Labour Charge"))
                    sp1 = "1400";
                if(s.equals("8 Labour Charge"))
                    sp1 = "1600";
                if(s.equals("9 Labour Charge"))
                    sp1 = "1800";
                if(s.equals("10 Labour Charge"))
                    sp1 = "2000";

                System.out.println("s1 = "+sp1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

            public void onClick(View v)
            {
                // TODO Auto-generated method stub
            }
        });

        datepick = (EditText)findViewById(R.id.datepicker);
        datepick.setInputType(InputType.TYPE_NULL);
        datepick.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(BookVehicle.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        datepick.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        Date =  datepick.getText().toString();

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });
        timepick = (EditText)findViewById(R.id.timepicker);
        timepick.setInputType(InputType.TYPE_NULL);
        timepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(BookVehicle.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timepick.setText(selectedHour + ":" + selectedMinute);
                        Time =  timepick.getText().toString();
                        System.out.println("TIME = "+Time);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        datepicker = (Button)findViewById(R.id.datepickimg) ;
        datepicker.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(BookVehicle.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        datepick.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        Date =  datepick.getText().toString();

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        timepicker = (Button)findViewById(R.id.timepickerimg) ;
        timepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(BookVehicle.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timepick.setText(selectedHour + ":" + selectedMinute);
                        Time =  timepick.getText().toString();
                        System.out.println("TIME = "+Time);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        buttonDistance = (Button)findViewById(R.id.getdetails);
        buttonDistance.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if (booktype.contains("F")) {
                    buttonBook.setVisibility(View.VISIBLE);
                    buttonProceedBookHS.setVisibility(View.GONE);
                    buttonProceedBookLHS.setVisibility(View.GONE);
                }
                if (booktype.contains("HS")) {
                    buttonProceedBookHS.setVisibility(View.VISIBLE);
                    buttonBook.setVisibility(View.GONE);
                    buttonProceedBookLHS.setVisibility(View.GONE);
                }
                if (booktype.contains("LHS")) {
                    buttonProceedBookLHS.setVisibility(View.VISIBLE);
                    buttonBook.setVisibility(View.GONE);
                    buttonProceedBookHS.setVisibility(View.GONE);
                }

                pricevalue.setText("");
                priceGst.setText("");
                sendRequest();
                //new AsyncFetchLH().execute();
            }
        });

    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_book) {
            new AsyncFetchBook().execute();
        }
        if (v.getId() == R.id.btn_proceedLHS) {
            new AsyncFetchLHSBook().execute();
        }
        if (v.getId() == R.id.btn_proceedHS) {
            new AsyncFetchLHSBook().execute();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    ////////Finde the distacnce between origin and destination///////
    private void sendRequest() {

        if (TextUtils.isEmpty(origin)){
            Toast.makeText(getApplicationContext(), "Fields Cannot be EMPTY!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(destination)){
            Toast.makeText(getApplicationContext(), "Fields Cannot be EMPTY!!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            new DirectionFinder(this, origin, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding direction..!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
            ((TextView) findViewById(R.id.textViewEstTime)).setText(route.duration.text);
            ((TextView) findViewById(R.id.textViewDist)).setText(route.distance.text);

            Duration = (route.duration.text).toString();
            str1 = Duration;
            System.out.println("DURATION  = " + str1);

            String[] splitStr1 = str1.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
            hours = splitStr1[0];
            System.out.println("Hours  = " + hours);
            try {

                for (int i = 0; i < splitStr1.length; i++) {
                    System.out.println("1 : " + i + "=" + splitStr1[i]);
                    System.out.println("Length ="+splitStr1.length);
                }
                if(splitStr1.length==4) {
                    hr = splitStr1[0];
                    mins = splitStr1[2];

                    hr1 = Integer.parseInt(hr);
                    min1 = Integer.parseInt(mins);


                    cov_mins = hr1 * 60;
                    tot_minsint = cov_mins + min1;
                    System.out.println("tot_minsint = " + tot_minsint);

                    tot_mins = String.valueOf(tot_minsint);
                    System.out.println("tot_mins = " + tot_mins);
                }
                if(splitStr1.length==2)
                {
                    hours = splitStr1[0];
                    tot_mins =hours;
                    System.out.println("Hours  = " + tot_mins);
                }
            }catch (ArrayIndexOutOfBoundsException ae)
            {
                System.out.println("Excep = "+ae);
                 Toast.makeText(getApplicationContext(), ae.toString(),Toast.LENGTH_LONG).show();
            }

                distanceKMPL = (route.distance.text).toString();
                System.out.println("distanceKMPL = " + distanceKMPL);
                str = distanceKMPL;
                String[] splitStr = str.split("\\s+");
                KMPL = splitStr[0];
                System.out.println("KMPL = " + KMPL);

                originMarkers.add(mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                        .title(route.startAddress)
                        .position(route.startLocation)));
                destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                        .title(route.endAddress)
                        .position(route.endLocation)));

                PolylineOptions polylineOptions = new PolylineOptions().
                        geodesic(true).
                        color(Color.BLUE).
                        width(10);

                for (int i = 0; i < route.points.size(); i++)
                    polylineOptions.add(route.points.get(i));

                polylinePaths.add(mMap.addPolyline(polylineOptions));

        }
        new AsyncPrice().execute();
    }

    //get details of KM and catID and passing to the server to get price
    private class AsyncPrice extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog(BookVehicle.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // buttonLESSHALF.setVisibility(View.VISIBLE);
            // buttonHALFShare.setVisibility(View.VISIBLE);

            if (CateName.contains("4000"))
            {
                price.setVisibility(View.GONE);
                enquiry.setVisibility(View.VISIBLE);
            }
            else {
                price.setVisibility(View.VISIBLE);
                enquiry.setVisibility(View.GONE);
                radioGroup.setVisibility(View.VISIBLE);
            }

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
                url = new URL(CAL_URL+KMPL+PRICE_URL+CateId);
                System.out.println("URL = " +url);
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
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
            List<DataCate> data = new ArrayList<>();

            pdLoading.dismiss();
            try {
                JSONObject jobject = new JSONObject(result);

                JSONArray jArray = jobject.getJSONArray("result");
                System.out.println("resultPrice = " + result);


                // Extract data from json and store into ArrayList as class objects
                for(int i=0;i<jArray.length();i++) {
                    JSONObject json_data = jArray.getJSONObject(i);

                    if (booktype.compareToIgnoreCase("F") == 0) {

                        cate_price = json_data.optString("success");
                        System.out.println("cate_price = " + cate_price);

                        //gst price
                        float labour = Float.parseFloat(sp1);  //with labour charge
                        float gstLH = Float.parseFloat(cate_price);
                        float cate_total = gstLH + labour;
                        String est_total = String.valueOf(cate_total);
                        pricevalue.setText(est_total);
                        System.out.println("price = " + pricevalue);
                        float c = 12;
                        float gstprice = (c / 100) * cate_total;

                        float total = cate_total + gstprice ;

                        Gsttotal = String.valueOf(total);
                        System.out.println("Gsttotal =" + Gsttotal);

                        priceGst.setText(Gsttotal);
                    }
                    if (booktype.compareToIgnoreCase("LHS")==0) {
                        cate_price = json_data.optString("success");
                        cate_P = Float.parseFloat(json_data.optString("success"));
                        float res = (cate_P);
                        System.out.println("res =" + res);
                        //*************
                        float a = 70, b = 100;
                        float res1 = (a / b) * res;
                        System.out.println("res1 = " + res1);

                        float res2 = Math.round(res - res1);
                        System.out.println("res2 = " + res2);

                        priceLH = String.valueOf(res2);
                        System.out.println("cate_p =" + priceLH);


                        //gst price
                        float labour = Float.parseFloat(sp1);   //with labour charge
                        float gstLH = res2;
                        float cate_total = gstLH + labour;
                        String est_total = String.valueOf(cate_total);

                        pricevalue.setText(est_total);
                        System.out.println("cate_p =" + est_total);

                        float c = 12;
                        float gstprice = (c / b) * cate_total;


                        float total = cate_total + gstprice ;
                        Gsttotal = String.valueOf(total);
                        System.out.println("Gsttotal =" + Gsttotal);

                        priceGst.setText(Gsttotal);
                    }

                    if (booktype.compareToIgnoreCase("HS")==0) {
                        cate_price = json_data.optString("success");
                        cate_P = Float.parseFloat(json_data.optString("success"));
                        float res = (cate_P);
                        System.out.println("res =" + res);
                        float a = 50, b = 100;
                        float res1 = (a / b) * res;
                        System.out.println("res1 = " + res1);

                        float res2 = Math.round(res - res1);
                        System.out.println("res2 = " + res2);

                        priceLH = String.valueOf(res2);
                        System.out.println("cate_p =" + priceLH);

                        //gst price
                        float labour = Float.parseFloat(sp1);   //with labour charge
                        float gstLH = res2;
                        float cate_total = gstLH + labour;
                        String est_total = String.valueOf(cate_total);

                        pricevalue.setText(est_total);
                        System.out.println("cate_p =" + est_total);
                        float c = 12;
                        float gstprice = (c / b) * cate_total;

                        float total = cate_total + gstprice ;
                        Gsttotal = String.valueOf(total);
                        System.out.println("Gsttotal =" + Gsttotal);

                        priceGst.setText(Gsttotal);
                        System.out.println("Gsttotal =" + Gsttotal);
                    }
                }

            } catch (JSONException e) {
                Toast.makeText(BookVehicle.this, e.toString(), Toast.LENGTH_LONG).show();
            }
            catch (ArrayIndexOutOfBoundsException aee)
            {
                aee.printStackTrace();
            }
        }
    }

    private class AsyncFetchBook extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(BookVehicle.this);
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

                /*String BookFull_url = "http://jt.codingspider.com/jt_app/jt_book.php?cat_id=";
    String BookFull_url1 = "&mobile=";
    String BookFull_url2 = "&from=";
    String BookFull_url3 = "&to=";
    String BookFull_url4 = "&distance=";
    String BookFull_url5 = "&price=";
    String BookFull_url6 = "&time=";
    String BookFull_url7 = "&status=";
    String BookFull_url8 = "&booktype=";
    String BookFull_url9 = "&bookDate=";
    String BookFull_url10 = "&bookTime=";*/
                url = new URL(BookFull_url+CateId+BookFull_url1+phone2+BookFull_url2+URLEncoder.encode(origin, "utf-8")+BookFull_url3+URLEncoder.encode(destination, "utf-8")+BookFull_url4+KMPL+BookFull_url5+Gsttotal+BookFull_url6+tot_mins+BookFull_url7+f1+BookFull_url8+booktype+BookFull_url9+Date+BookFull_url10+Time);

                System.out.println("Book_URL = "+url);
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

                    i.putExtra("CATE_NAME",CateName);
                    i.putExtra("USERNAME",UserName);
                    i.putExtra("TO",origin);
                    i.putExtra("FROM",destination1);
                    i.putExtra("KM",distanceKMPL);
                    i.putExtra("PRICE",Gsttotal);
                    i.putExtra("PRICE",Gsttotal);
                    i.putExtra("DATE",Date);
                    i.putExtra("TIME",Time);

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
                Toast.makeText(BookVehicle.this, "Something went wrong".toString(), Toast.LENGTH_LONG).show();
                System.out.println("Exception1 = "+mess);
            }

        }
    }

    private class AsyncFetchLHSBook extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(BookVehicle.this);
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

                /*String BookSH_url = "http://jt.codingspider.com/jt_app/jt_bookings.php?cat_id=";
    String BookSH_url1 = "&from=";
    String BookSH_url2 = "&to=";
    String BookSH_url3 = "&status=";
    String BookSH_url4 = "&booktype=";*/
                url = new URL(BookSH_url+CateId+BookSH_url1+URLEncoder.encode(origin, "utf-8")+BookSH_url2+URLEncoder.encode(destination, "utf-8")+BookSH_url3+f1+BookSH_url4+booktype);

                System.out.println("Book_URLSH = "+url);
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
                if (confirm.contains("null")){

                    Toast.makeText(getApplication(), "No Vehicles Matched".toString(), Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplication(), "Book a New Vehicle".toString(), Toast.LENGTH_LONG).show();

                    Intent i = new Intent(getApplication(), ShareActivity.class);
                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

                    editor.putString("prefBookV_CATE_NAME", CateName);
                    editor.putString("prefBookV_CAT_ID", CateId);
                    editor.putString("prefBookV_PHONE", phone2);

                    editor.putString("prefBookV_FROM",origin);
                    editor.putString("prefBookV_TO", destination);

                    editor.putString("prefBookV_KMPL", KMPL);
                    editor.putString("prefBookV_Cate_price", Gsttotal);
                    editor.putString("prefBookV_tot_mins", tot_mins);

                    editor.putString("prefBookV_Status", f1);
                    editor.putString("prefBookV_BookType", booktype);
                    editor.putString("prefBookV_DATE", Date);
                    editor.putString("prefBookV_TIME", Time);

                    editor.putString("prefBookV_HOUR", hours);
                    //editor.putString("prefBookV_Cate_price", cate_price);
                    //editor.putString("prefBookV_Gsttotal", Gsttotal);

                    editor.commit();

                    System.out.println("2 "+CateName);
                    System.out.println("2 "+CateId);
                    System.out.println("2 "+phone2);

                    System.out.println("2 "+origin);
                    System.out.println("2 "+destination);

                    System.out.println("2 "+KMPL);
                    System.out.println("2 "+Gsttotal);
                    System.out.println("2 "+tot_mins);

                    System.out.println("2 "+f1);
                    System.out.println("2 "+booktype);
                    System.out.println("2 "+Date);
                    System.out.println("2 "+Time);

                    System.out.println("2 "+hours);
                    startActivity(i);
                }
                else {

                    Toast.makeText(getApplicationContext(), "showing Available vehicles".toString(), Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplication(), ShareActivity.class);

                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

                    editor.putString("prefBookV_CATE_NAME", CateName);
                    editor.putString("prefBookV_CAT_ID", CateId);
                    editor.putString("prefBookV_PHONE", phone2);

                    editor.putString("prefBookV_FROM",origin);
                    editor.putString("prefBookV_TO", destination);

                    editor.putString("prefBookV_KMPL", KMPL);
                    editor.putString("prefBookV_Cate_price", Gsttotal);
                    editor.putString("prefBookV_tot_mins", tot_mins);

                    editor.putString("prefBookV_Status", f1);
                    editor.putString("prefBookV_BookType", booktype);
                    editor.putString("prefBookV_DATE", Date);
                    editor.putString("prefBookV_TIME", Time);

                    editor.putString("prefBookV_HOUR", hours);
                    //editor.putString("prefBookV_Cate_price", cate_price);
                    //editor.putString("prefBookV_Gsttotal", Gsttotal);

                    editor.commit();

                    System.out.println("1 "+CateName);
                    System.out.println("1 "+CateId);
                    System.out.println("1 "+phone2);

                    System.out.println("1 "+origin);
                    System.out.println("1 "+destination);

                    System.out.println("1 "+KMPL);
                    System.out.println("1 "+Gsttotal);
                    System.out.println("1 "+tot_mins);

                    System.out.println("1 "+f1);
                    System.out.println("1 "+booktype);
                    System.out.println("1 "+Date);
                    System.out.println("1 "+Time);

                    System.out.println("1 "+hours);


                    startActivity(i);

                }

            } catch (JSONException mess) {
                Toast.makeText(BookVehicle.this, "Something went wrong".toString(), Toast.LENGTH_LONG).show();
                System.out.println("Exception1 = "+mess);
            }

        }
    }
}
