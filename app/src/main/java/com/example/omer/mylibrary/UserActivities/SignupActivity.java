package com.example.omer.mylibrary.UserActivities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.omer.mylibrary.MainActivity;
import com.example.omer.mylibrary.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {

    private EditText name, password, email, surname, username;
    private Button btnSignup;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference users;
    private DatabaseReference uid;
    private DatabaseReference usernames;
    private DatabaseReference names;
    private DatabaseReference surnames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = (EditText) findViewById(R.id.txtSignupName);
        surname = (EditText) findViewById(R.id.txtSignupSurname);
        email = (EditText) findViewById(R.id.txtSignupEmail);
        password = (EditText) findViewById(R.id.txtSignupPassword);
        btnSignup = (Button) findViewById(R.id.btnSignupSignup);
        username = (EditText) findViewById(R.id.txtSignupUsername);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Query usernameQuery = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("username").equalTo(username.getText().toString());
                usernameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getChildrenCount() > 0) {//aynı kullanıcı adından var ise
                            Toast.makeText(SignupActivity.this, "Choose diffrent username", Toast.LENGTH_LONG).show();
                        } else {
                            //kullanıcı kaydı
                            mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                                    .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {//kullanıcı oluşturuldu
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {

                                            if (task.isSuccessful()){
                                            user = mAuth.getCurrentUser();

                                            //kullanıcının verilerinin database yüklenmesi
                                            users = database.getReference("users");
                                            uid = users.child(user.getUid());
                                            usernames = uid.child("username");
                                            names = uid.child("name");
                                            surnames = uid.child("surname");

                                            uid.setValue(user.getUid());
                                            names.setValue(name.getText().toString());
                                            surnames.setValue(surname.getText().toString());
                                            usernames.setValue(username.getText().toString());

                                            Toast.makeText(getApplicationContext(), user.getEmail(), Toast.LENGTH_LONG).show();
                                            finish();
                                            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            }
                                            else{
                                            }
                                        }
                                    }).addOnFailureListener(SignupActivity.this, new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(SignupActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
