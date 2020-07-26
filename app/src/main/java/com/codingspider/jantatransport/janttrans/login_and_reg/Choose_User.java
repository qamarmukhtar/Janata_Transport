package com.codingspider.jantatransport.janttrans.login_and_reg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.codingspider.jantatransport.janttrans.R;

public class Choose_User extends AppCompatActivity {

    Button btn_choose;
    Spinner spinner_chooseuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_choose_user);

        btn_choose = (Button) findViewById(R.id.choose_btn);
        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

            }
        });
    }
}
