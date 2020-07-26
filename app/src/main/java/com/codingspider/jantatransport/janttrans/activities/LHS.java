package com.codingspider.jantatransport.janttrans.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.codingspider.jantatransport.janttrans.MainActivity;
import com.codingspider.jantatransport.janttrans.R;

public class LHS extends AppCompatActivity {

    String origin,destination,CateId,f="0",booktype ="LHS";
    String origin1,destination1,CateId1,KMPL,phone2,hours,Date, Time,cate_price,catename1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.less_half_activity);

        Button Done = (Button)findViewById(R.id.book1) ;
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        Bundle bundle = getIntent().getExtras();

        origin1 = bundle.getString("TO", "");
        destination1 = bundle.getString("FROM", "");
        CateId1 = bundle.getString("CAT_ID","");
        KMPL = bundle.getString("KMPL","");
        hours = bundle.getString("HOUR","");
        phone2 = bundle.getString("PHONE","");
        Date = bundle.getString("DATE","");
        Time = bundle.getString("TIME","");
        cate_price = bundle.getString("Cate_price","");
        catename1  = bundle.getString("CATE_NAME", "");

        System.out.println("origin"+origin1);
        System.out.println("origin"+destination1);
        System.out.println("origin"+CateId1);
        System.out.println("origin"+KMPL);
        System.out.println("origin"+hours);
        System.out.println("origin"+phone2);
        System.out.println("origin"+Date);
        System.out.println("origin"+Time);
        System.out.println("origin"+cate_price);


       // ((TextView) findViewById(R.id.from)).setText(origin1);
        ((TextView) findViewById(R.id.from_2)).setText(origin1);
        //((TextView) findViewById(R.id.to)).setText(destination1);
        ((TextView) findViewById(R.id.to_2)).setText(destination1);
       // ((TextView) findViewById(R.id.time)).setText(hours);
      //  ((TextView) findViewById(R.id.date)).setText(Date);
       // ((TextView) findViewById(R.id.price)).setText(cate_price);
        ((TextView) findViewById(R.id.textCateName)).setText(CateId1);
       // ((TextView) findViewById(R.id.type)).setText(booktype);
//        ((TextView) findViewById(R.id.date)).setText(Date);
        ((TextView) findViewById(R.id.date_2)).setText(Date);
      //  ((TextView) findViewById(R.id.time2)).setText(Time);
        ((TextView) findViewById(R.id.time_3)).setText(Time);
        ((TextView) findViewById(R.id.textCateName)).setText(catename1);

    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
