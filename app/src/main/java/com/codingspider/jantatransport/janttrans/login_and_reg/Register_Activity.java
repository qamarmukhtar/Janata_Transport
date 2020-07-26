package com.codingspider.jantatransport.janttrans.login_and_reg;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.codingspider.jantatransport.janttrans.MainActivity;
import com.codingspider.jantatransport.janttrans.R;
import com.codingspider.jantatransport.janttrans.modules.userdata;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register_Activity extends AppCompatActivity {

    //firebase
    private FirebaseAuth auth;
    //our database reference object
    DatabaseReference databaseuser;

    private ProgressBar progressBar;

    EditText editTextphone;
    EditText editTextemail;
    EditText editTextFname;
    EditText editTextLname;
    EditText editTextPassword;
    Button btn_register;
    TextView text_alreadyuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_register);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(Register_Activity.this, MainActivity.class));
            finish();
        }

        //getting the reference of artists node
        databaseuser = FirebaseDatabase.getInstance().getReference("userdata");

        editTextphone = (EditText) findViewById(R.id.textPhone);
        editTextemail = (EditText) findViewById(R.id.textEmail);
        editTextFname = (EditText) findViewById(R.id.textFirstName);
        editTextLname = (EditText) findViewById(R.id.textLastName);
        editTextPassword = (EditText) findViewById(R.id.textPassword);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);



        btn_register = (Button) findViewById(R.id.register_btn);


        text_alreadyuser = (TextView) findViewById(R.id.textAlreadyUser);
        text_alreadyuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

            }
        });



        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = editTextemail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Register_Activity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(Register_Activity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(Register_Activity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {

                                    adddata();
                                    startActivity(new Intent(Register_Activity.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });

            }
        });


    }



    private void adddata() {
            String phone = editTextphone.getText().toString();
            String email = editTextemail.getText().toString();
            String fname = editTextFname.getText().toString();
            String lname = editTextLname.getText().toString();
            String password = editTextPassword.getText().toString();

            //checking if the value is provided
            if (!TextUtils.isEmpty(phone)) {

                    //getting a unique id using push().getKey() method
                    //it will create a unique id and we will use it as the Primary Key for our Artist
                    String id = databaseuser.push().getKey();

                    //creating an Artist Object
                    userdata Userdata = new userdata(id, phone, email,fname,lname,password);

                    //Saving the Artist
                    databaseuser.child(id).setValue(Userdata);



                    //displaying a success toast
                    Toast.makeText(this, "Artist added", Toast.LENGTH_LONG).show();
                } else {
                    //if the value is not given displaying a toast
                    Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
                }

    }
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

}
