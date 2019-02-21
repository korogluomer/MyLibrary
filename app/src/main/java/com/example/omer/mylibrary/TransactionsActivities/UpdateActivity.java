package com.example.omer.mylibrary.TransactionsActivities;

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

import com.example.omer.mylibrary.Adapter.FillAdapter;
import com.example.omer.mylibrary.BookTransactions.BookFind;
import com.example.omer.mylibrary.Other.ExampleDialogListener;
import com.example.omer.mylibrary.UserActivities.LoginActivity;
import com.example.omer.mylibrary.Other.SearchDialog;
import com.example.omer.mylibrary.R;
import com.example.omer.mylibrary.UserTransactions.UserFind;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static java.lang.Math.toIntExact;

public class UpdateActivity extends AppCompatActivity implements ExampleDialogListener {

    ListView listView;
    FillAdapter adapter;
    Toolbar toolbar;

    private static String[] arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Listing();
        listView = findViewById(R.id.bookUpdateListView);

        adapter = new FillAdapter(this);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BookFind b = new BookFind();
                b.Finding(arr[position], FirebaseAuth.getInstance().getUid());
                Intent intent = new Intent(UpdateActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });

    }

//Kitap id lerini listeler ve diziye atar
    public String[] Listing() {

        Query bookQuery = FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("books");

        bookQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int sayac = 0;
                if (dataSnapshot.getChildrenCount() > 0) {
                    arr = new String[toIntExact(dataSnapshot.getChildrenCount())];
                    for (DataSnapshot snapshot : dataSnapshot.getChildren())
                        arr[sayac++] = snapshot.getKey();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return arr;
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
            Intent intent = new Intent(UpdateActivity.this, LoginActivity.class);
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
        new UserFind(usernname, UpdateActivity.this).execute("naber");
    }
}
