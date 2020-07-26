package com.codingspider.jantatransport.janttrans.nav;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.codingspider.jantatransport.janttrans.R;

public class AboutUs extends AppCompatActivity {

    TextView Phone11,Phone22;
    String Phone1,Phone2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_aboutus_activity);


        Phone11 = (TextView)findViewById(R.id.phone1);
        Phone22 = (TextView)findViewById(R.id.phone2);

        Phone11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+918884130000"));
                startActivity(intent);
            }
        });

        Phone22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+918884130000"));
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
