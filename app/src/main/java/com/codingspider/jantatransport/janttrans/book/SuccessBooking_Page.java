package com.codingspider.jantatransport.janttrans.book;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.codingspider.jantatransport.janttrans.MainActivity;
import com.codingspider.jantatransport.janttrans.R;

public class SuccessBooking_Page extends AppCompatActivity {

    String uname1,origin1,destination1,distanceKMPL1,cate_price1,catename1,date1,time1;

    TextView username_text, catename_text, from_text, to_text, date_text, time_text, km_text, price_text;

    String SplitOri[], SpitDes[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_succes_page);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();

        uname1 = bundle.getString("USERNAME", "");
        catename1 = bundle.getString("CATE_NAME", "");
        origin1 = bundle.getString("TO", "");
        destination1 = bundle.getString("FROM", "");
        date1 = bundle.getString("DATE","");
        time1 = bundle.getString("TIME","");

        distanceKMPL1 = bundle.getString("KM","");
        cate_price1 = bundle.getString("PRICE","");

        /*String o1 = origin1.replaceAll(",", " ");
        String d1 = destination1.replaceAll(",", " ");
        System.out.println("o1="+o1);
        System.out.println("d1="+d1);*/

        SplitOri = origin1.split(",");
        SpitDes = destination1.split(",");

        System.out.println("SplitOri[0] = "+SplitOri[0]);
        System.out.println("SpitDes[0] = "+SpitDes[0]);
        System.out.println("uname1"+uname1);

        System.out.println("catename1"+catename1);
        System.out.println("origin"+origin1);
        System.out.println("destination1"+destination1);
        System.out.println("date1"+date1);
        System.out.println("time1"+time1);

        System.out.println("distanceKMPL1"+distanceKMPL1);
        System.out.println("cate_price1"+cate_price1);

        username_text  = (TextView) findViewById(R.id.textUserNamefull);
        catename_text  = (TextView) findViewById(R.id.textCateNamefull);
        from_text = (TextView) findViewById(R.id.textFromfull);
        to_text = (TextView) findViewById(R.id.textTofull);
        date_text = (TextView) findViewById(R.id.textDatefull);
        time_text = (TextView) findViewById(R.id.textTimefull);

        km_text = (TextView) findViewById(R.id.KM1);
        price_text = (TextView) findViewById(R.id.price);

        username_text.setText(uname1);
        catename_text.setText(catename1);
        from_text.setText(SplitOri[0]);
        to_text.setText(SpitDes[0]);
        date_text.setText(date1);
        time_text.setText(time1);

        Button Done = (Button)findViewById(R.id.book1) ;
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
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
