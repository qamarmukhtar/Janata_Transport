package com.codingspider.jantatransport.janttrans;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codingspider.jantatransport.janttrans.adapter.AdapterCate;
import com.codingspider.jantatransport.janttrans.data.DataCate;
import com.codingspider.jantatransport.janttrans.login_and_reg.LoginActivity;
import com.codingspider.jantatransport.janttrans.nav.AboutUs;
import com.codingspider.jantatransport.janttrans.nav.Bookings;
import com.codingspider.jantatransport.janttrans.nav.OfferActivity;
import com.codingspider.jantatransport.janttrans.nav.Profile;
import com.codingspider.jantatransport.janttrans.nav.SharingActivity;
import com.codingspider.jantatransport.janttrans.nav.Write_to_Us;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private RecyclerView mRVFishPrice;
    private AdapterCate mAdapter;



    // Editor for Shared preferences
    String id1, phone1, email1, fname1, lname1, date1, isLoggedin;


    /*// Session Manager Class
    SessionManager session;*/

    TextView Uname, Uemail;
    String fullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        //concat fname and lname
        fullName = fname1 +" "+ lname1;

        System.out.println("IsLoggedIn On= " + isLoggedin);
        System.out.println("phone1 On= " + phone1);
        System.out.println("email1 On= " + email1);
        System.out.println("fname1 On= " + fname1);
        System.out.println("lname1 On= " + lname1);
        System.out.println("date1 On= " + date1);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header=navigationView.getHeaderView(0);
        Uname = (TextView)header.findViewById(R.id.textViewName);
        Uemail = (TextView)header.findViewById(R.id.textViewEmail);
        Uname.setText(fullName);
        Uemail.setText(email1);



    }



    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);
                    }
                }).create().show();

       /* DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout_button) {

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_offer) {
            Intent intent = new Intent(getApplicationContext(), OfferActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_bookings) {
            Intent intent = new Intent(getApplicationContext(),Bookings.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_sharing) {
            Intent intent = new Intent(getApplicationContext(), SharingActivity.class);
            startActivity(intent);

        }else if (id == R.id.nav_aboutus) {
            Intent intent = new Intent(getApplicationContext(), AboutUs.class);
            startActivity(intent);

        } else if (id == R.id.nav_Profile) {
            Intent intent = new Intent(getApplicationContext(), Profile.class);
            startActivity(intent);

        } /*else if (id == R.id.nav_contactus) {
            Intent intent = new Intent(getApplicationContext(), ContactUs.class);
            startActivity(intent);

        }*/ else if (id == R.id.write_to_us) {
            Intent intent = new Intent(getApplicationContext(), Write_to_Us.class);
            startActivity(intent);

       /* } else if (id == R.id.write_to_us) {
            Intent intent = new Intent(getApplicationContext(), Write_to_Us.class);
            startActivity(intent);*/

        }else if (id == R.id.nav_logout) {


            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
