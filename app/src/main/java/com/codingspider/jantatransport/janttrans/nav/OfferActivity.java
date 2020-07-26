package com.codingspider.jantatransport.janttrans.nav;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.codingspider.jantatransport.janttrans.R;

public class OfferActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_offer_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
