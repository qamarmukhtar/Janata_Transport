package com.codingspider.jantatransport.janttrans.nav;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.codingspider.jantatransport.janttrans.MainActivity;
import com.codingspider.jantatransport.janttrans.R;



public class Profile extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    SharedPreferences prefs1;
    String id1, phone1, email1, fname1, lname1, date1;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_edit_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        prefs1 = this.getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        id1 = prefs1.getString("pref_id"," ");
        phone1 = prefs1.getString("pref_name"," ");
        email1 = prefs1.getString("pref_email"," ");
        fname1 = prefs1.getString("pref_fname"," ");
        lname1 = prefs1.getString("pref_lname"," ");
        date1 = prefs1.getString("pref_date"," ");

        System.out.println("phone1 On= " + phone1);
        System.out.println("email1 On= " + email1);
        System.out.println("fname1 On= " + fname1);
        System.out.println("lname1 On= " + lname1);
        System.out.println("date1 On= " + date1);

        ((TextView) findViewById(R.id.updatePhone)).setText(phone1);
        ((TextView) findViewById(R.id.updateEmail)).setText(email1);
        ((TextView) findViewById(R.id.updateFirstName)).setText(fname1);
        ((TextView) findViewById(R.id.updateLastName)).setText(lname1);

        back =(Button)findViewById(R.id.update_btn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
