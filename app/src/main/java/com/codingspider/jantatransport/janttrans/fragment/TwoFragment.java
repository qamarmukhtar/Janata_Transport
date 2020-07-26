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
import com.codingspider.jantatransport.janttrans.adapter.Adapter_Less_Half;
import com.codingspider.jantatransport.janttrans.data.DataCate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;


public class TwoFragment extends Fragment {

    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private TwoFragment mContext;
    //All about internet
    ProgressDialog pDialog;



    SharedPreferences sharedpreferences;
    SharedPreferences prefs1;
    String booktype ="LHS";

    String phone, result;
    private RecyclerView mRVFishPrice;
    private Adapter_Less_Half mAdapter;

    TextView responseMessageTwo;
    String id1, phone1, email1, fname1, lname1, date1, two_phone1, isLoggedin;

    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        View view = inflater.inflate(R.layout.fragment_two, container, false);

        //return inflater.inflate(R.layout.fragment_two, container, false);
        mRVFishPrice = (RecyclerView) view.findViewById(R.id.twoList);
        responseMessageTwo = (TextView) view.findViewById(R.id.responseMessageTwo);

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
                    JSONArray jArray = jobject.getJSONArray("Sharing");
                    System.out.println("ListSharing =" + result);

                    if (result.compareToIgnoreCase("{\"Sharing\":\"No Bookings available\"}")==0) {
                        responseMessageTwo.setVisibility(View.VISIBLE);
                        mRVFishPrice.setVisibility(View.GONE);

                    } else {
                        mRVFishPrice.setVisibility(View.VISIBLE);
                        responseMessageTwo.setVisibility(View.GONE);

                        // Extract data from json and store into ArrayList as class objects
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json_data = jArray.getJSONObject(i);

                            two_phone1 = json_data.getString("phone");
                            System.out.println("two_phone1" + two_phone1);

                            if (two_phone1.compareToIgnoreCase(phone1)==0) {

                            } else {

                                DataCate fishData = new DataCate();

                                fishData.two_cate_Id = json_data.getString("category_Id");
                                fishData.two_book_id = json_data.getString("book_id");
                                fishData.two_loc_from = json_data.getString("loc_from");
                                fishData.two_loc_to = json_data.getString("loc_to");
                                fishData.two_est_time = json_data.getString("est_time");
                                fishData.two_distance = json_data.getString("distance");
                                fishData.two_price = json_data.getString("price");
                                fishData.two_bookDate = json_data.getString("bookDate");
                                fishData.two_bookTime = json_data.getString("bookTime");
                                fishData.two_bookType = json_data.getString("bookType");

                                System.out.println("two_cate_Id" + fishData.two_cate_Id);
                                System.out.println("two_book_id" + fishData.two_book_id);
                                System.out.println("two_loc_from" + fishData.two_loc_from);
                                System.out.println("two_loc_to" + fishData.two_loc_to);
                                System.out.println("two_est_time" + fishData.two_est_time);
                                System.out.println("two_distance" + fishData.two_distance);
                                System.out.println("two_price" + fishData.two_price);
                                // System.out.println("BOOKING TIME" + fishData.two_);
                                System.out.println("two_bookDate" + fishData.two_bookDate);
                                System.out.println("two_bookTime" + fishData.two_bookTime);
                                System.out.println("two_bookType" + fishData.two_bookType);

                                fishData.two_fname1 = fname1;
                                System.out.println("two_fname1" + fishData.two_fname1);

                                data.add(fishData);
                            }

                        }
                        // Setup and Handover data to recyclerview
                        //  mRVFishPrice = (RecyclerView) mRVFishPrice.findViewById(R.id.eatlist);
                        mAdapter = new Adapter_Less_Half(getActivity(), data);
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

