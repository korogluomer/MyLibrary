package com.example.omer.mylibrary.UserActivities;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.omer.mylibrary.Adapter.FillAdapter;
import com.example.omer.mylibrary.BookTransactions.MultiListing;
import com.example.omer.mylibrary.ListingActivities.BooksActivity;
import com.example.omer.mylibrary.Other.ExampleDialogListener;
import com.example.omer.mylibrary.Other.SearchDialog;
import com.example.omer.mylibrary.R;
import com.example.omer.mylibrary.UserTransactions.UserFind;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity implements ExampleDialogListener {

    TextView txtUsername, txtName, txtSurname;
    private String uId;
    ListView listView;
    FillAdapter adapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txtUsername = findViewById(R.id.txtProfileUsername);
        txtName = findViewById(R.id.txtProfileName);
        txtSurname = findViewById(R.id.txtProfileSurname);
        listView = findViewById(R.id.profileBookListView);
        Intent intent = getIntent();

        uId = intent.getStringExtra("userId");//Gelen UserId Main mi search mü

        Query usersQuery = FirebaseDatabase.getInstance().getReference("users").child(uId);

        usersQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                txtUsername.setText(dataSnapshot.child("username").getValue().toString());
                txtName.setText(dataSnapshot.child("name").getValue().toString());
                txtSurname.setText(dataSnapshot.child("surname").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final MultiListing multiListing = new MultiListing(uId);
        multiListing.Listing();
        adapter = new FillAdapter(this);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Query usersQuery = FirebaseDatabase.getInstance().getReference("users").child(uId).child("books").child(MultiListing.getBookId().get(position));

                usersQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        HashMap<String, String> hashMap = (HashMap<String, String>) dataSnapshot.getValue();
                        if (hashMap.get("bookName") != null) {
                            Intent intent = new Intent(ProfileActivity.this, BooksActivity.class);
                            intent.putExtra("bookName", hashMap.get("bookName"));
                            intent.putExtra("authorName", hashMap.get("authorName"));
                            intent.putExtra("bookPageNumber", hashMap.get("bookPageNumber"));
                            intent.putExtra("bookNote", hashMap.get("bookNote"));
                            intent.putExtra("buyDate", hashMap.get("buyDate"));
                            intent.putExtra("buyLocation", hashMap.get("buyLocation"));
                            intent.putExtra("bookImage", hashMap.get("bookImage"));
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else if (item.getItemId() == R.id.search) {
            SearchDialog searchDialog = new SearchDialog();
            searchDialog.show(getSupportFragmentManager(), "example dialog");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void applyText(String usernname) {
        new UserFind(usernname, ProfileActivity.this).execute("naber");
    }
}
