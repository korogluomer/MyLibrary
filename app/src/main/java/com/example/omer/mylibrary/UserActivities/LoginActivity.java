package com.example.omer.mylibrary.UserActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.omer.mylibrary.Login.Login;
import com.example.omer.mylibrary.R;
import com.example.omer.mylibrary.WelcomeActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText txtEmail,txtPassword;
    Button btnLogin,btnSignup;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            Toast.makeText(LoginActivity.this,"Welcome"+FirebaseAuth.getInstance().getCurrentUser().getEmail(),Toast.LENGTH_LONG).show();
            Intent intent =new Intent(LoginActivity.this,WelcomeActivity.class);
            startActivity(intent);
        }

        mAuth = FirebaseAuth.getInstance();

        txtEmail=findViewById(R.id.txtLoginEmail);
        txtPassword=findViewById(R.id.txtLoginPassword);

        btnLogin=findViewById(R.id.btnLoginLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login l=new Login(LoginActivity.this,txtEmail.getText().toString(),txtPassword.getText().toString(),mAuth);
            }
        });

        btnSignup=findViewById(R.id.btnLoginSignup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}
