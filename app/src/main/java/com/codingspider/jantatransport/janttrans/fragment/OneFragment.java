package com.codingspider.jantatransport.janttrans.fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codingspider.jantatransport.janttrans.R;
import com.codingspider.jantatransport.janttrans.adapter.Adapter_Half;
import com.codingspider.jantatransport.janttrans.data.DataCate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class OneFragment extends Fragment {

    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private TwoFragment mContext;
    //All about internet
    ProgressDialog pDialog;
    TextView responseMessage;


    private static final String REGISTER_URL = "http://jt.codingspider.com/jt_app/jt_sharing.php";
    SharedPreferences sharedpreferences;
    SharedPreferences prefs1;
    String id1, phone1, email1, fname1, lname1, date1, two_phone1, isLoggedin;

    String booktype ="HS";

    String phone, result;
    private RecyclerView mRVFishPrice;
    private Adapter_Half mAdapter;

    TextView responseMessageOne;
    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      /*  sharedpreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        phone = sharedpreferences.getString("editTextphone1", "");
        System.out.println("SHARED PHONE VALUE =" + phone);*/

        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
//        prefs1 = this.getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        //responseMessage = (TextView) view.findViewById(R.id.responseMessage);

        //return inflater.inflate(R.layout.fragment_two, container, false);
        mRVFishPrice = (RecyclerView) view.findViewById(R.id.OneList);
        responseMessageOne = (TextView) view.findViewById(R.id.responseMessageOne);

        pDialog = new ProgressDialog(getActivity(), ProgressDialog.STYLE_SPINNER);
        pDialog.setMessage("Fetching Shops around you");
        PlaceFromTo();
        return view;
    }

    /////////////////////////place details From TO/////////////////////////////////////////////
    private void PlaceFromTo() {
        register(booktype);
    }

    private void register(String booktype) {
        String urlSuffix = "?booktype="+booktype;
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog pdLoading = new ProgressDialog(getActivity());

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
                    URL url = new URL(REGISTER_URL + s);
                    System.out.println("url = " + url);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
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
                    JSONArray jArray = jobject.getJSONArray("Sharing");
                    System.out.println("ListSharing =" + result);

                        if (result.compareToIgnoreCase("{\"Sharing\":\"No Bookings available\"}")==0) {
                            responseMessageOne.setVisibility(View.VISIBLE);
                            mRVFishPrice.setVisibility(View.GONE);

                        } else {
                            mRVFishPrice.setVisibility(View.VISIBLE);
                            responseMessageOne.setVisibility(View.GONE);

                            // Extract data from json and store into ArrayList as class objects
                            for (int i = 0; i < jArray.length(); i++) {
                                JSONObject json_data = jArray.getJSONObject(i);

                                two_phone1 = json_data.getString("phone");
                                System.out.println("two_phone1 value" + two_phone1);

                                if (two_phone1.compareToIgnoreCase(phone1)==0) {
                                } else {

                                DataCate fishData = new DataCate();

                                fishData.one_cate_Id = json_data.getString("category_Id");
                                fishData.one_book_id = json_data.getString("book_id");
                                fishData.one_loc_from = json_data.getString("loc_from");
                                fishData.one_loc_to = json_data.getString("loc_to");
                                fishData.one_est_time = json_data.getString("est_time");
                                fishData.one_distance = json_data.getString("distance");
                                fishData.one_price = json_data.getString("price");
                                fishData.one_bookDate = json_data.getString("bookDate");
                                fishData.one_bookTime = json_data.getString("bookTime");
                                fishData.one_bookType = json_data.getString("bookType");

                                System.out.println("one_loc_from" + fishData.one_loc_from);
                                System.out.println("one_cate_Id" + fishData.one_cate_Id);
                                System.out.println("one_est_time" + fishData.one_est_time);
                                System.out.println("one_loc_to" + fishData.one_loc_to);
                                System.out.println("one_distance" + fishData.one_distance);
                                System.out.println("one_price" + fishData.one_price);
                                System.out.println("one_bookDate" + fishData.one_bookDate);
                                System.out.println("one_bookTime" + fishData.one_bookTime);
                                System.out.println("one_book_id" + fishData.one_book_id);
                                System.out.println("one_bookType" + fishData.one_bookType);

                                fishData.one_fname1 = fname1;
                                System.out.println("one_fname1" + fishData.one_fname1);

                                //fishData.booking_time = json_data.getString("booking_time");
                                // System.out.println("BOOKING TIME" + json_data.getString("booking_time"));
                                data.add(fishData);
                            }
                        }
                    // Setup and Handover data to recyclerview
                    // mRVFishPrice = (RecyclerView)mRVFishPrice.findViewById(R.id.eatlist);
                    mAdapter = new Adapter_Half(getActivity(), data);
                    mRVFishPrice.setAdapter(mAdapter);
                    mRVFishPrice.setLayoutManager(new LinearLayoutManager(getActivity()));
                }
                } catch (JSONException e) {
                    //  Toast.makeText(Half_Share.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(urlSuffix);
    }
}