package com.example.omer.mylibrary;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.omer.mylibrary.BarcodeActivities.BarcodeScanActivity;
import com.example.omer.mylibrary.BookTransactions.MultiListing;
import com.example.omer.mylibrary.ListingActivities.BooksListActivity;
import com.example.omer.mylibrary.Other.ExampleDialogListener;
import com.example.omer.mylibrary.Other.SearchDialog;
import com.example.omer.mylibrary.TransactionsActivities.DeleteActivity;
import com.example.omer.mylibrary.TransactionsActivities.ManuelEklemeActivity;
import com.example.omer.mylibrary.TransactionsActivities.UpdateActivity;
import com.example.omer.mylibrary.UserActivities.LoginActivity;
import com.example.omer.mylibrary.UserActivities.ProfileActivity;
import com.example.omer.mylibrary.UserTransactions.UserFind;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements ExampleDialogListener {
    Toolbar toolbar;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private static MultiListing multiListing;
    Button btnProfile, btnLibrary, btnAdd, btnDelete, btnEdit, btnBarcode;
    RelativeLayout relativeLayout;
    String mind = "asad";

    public static MultiListing getMultiListing() {
        return multiListing;
    }

    public static void setMultiListing() {
        MainActivity.multiListing = new MultiListing(FirebaseAuth.getInstance().getUid());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Kamera için kullanıcıdan izin isteniyor
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
            }
        }

        database = FirebaseDatabase.getInstance();

        btnAdd = findViewById(R.id.btnMainAddBook);
        btnBarcode = findViewById(R.id.btnMainBarcode);
        btnDelete = findViewById(R.id.btnMainDelete);
        btnEdit = findViewById(R.id.btnMainEditBook);
        btnLibrary = findViewById(R.id.btnMainLibrary);
        btnProfile = findViewById(R.id.btnMainProfile);
        relativeLayout = findViewById(R.id.relative);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Veriler Firebase den çekilene kadar loading ekranı
        final Thread thread = new Thread() {
            @Override
            public void run() {
                for (; ; ) {
                    if (toolbar.getSubtitle() != null) {
                        relativeLayout.setVisibility(View.INVISIBLE);
                    }
                }
            }

        };
        thread.start();

        //Toolbara kullanıcının adı ve maili yazdırmak için sorgu
        myRef = database.getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                toolbar.setSubtitle(dataSnapshot.child("name").getValue().toString() + " " + dataSnapshot.child("surname").getValue().toString());
                toolbar.setTitle(dataSnapshot.child("username").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        setMultiListing();//Multilisting oluşturuldu
        getMultiListing().Listing();

        //Edit den mi yoksa delete den mi geldiği kontrolü
        mind = getIntent().getStringExtra("mind");
        if (mind != null && mind.equals("edit")) {
            Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
            startActivity(intent);
        } else if (mind != null && mind.equals("delete")) {
            Intent intent = new Intent(MainActivity.this, DeleteActivity.class);
            startActivity(intent);
        }
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
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            finish();
            startActivity(intent);
        } else if (item.getItemId() == R.id.search) {
            SearchDialog searchDialog = new SearchDialog();
            searchDialog.show(getSupportFragmentManager(), "example dialog");
        }

        return super.onOptionsItemSelected(item);
    }

    public void ToLibrary(View view) {
        Intent intent = new Intent(MainActivity.this, BooksListActivity.class);
        startActivity(intent);
        getMultiListing().Listing();
    }

    public void ToAddBook(View view) {
        Intent intent = new Intent(MainActivity.this, ManuelEklemeActivity.class);
        startActivity(intent);
    }

    public void ToEdit(View view) {
        Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
        startActivity(intent);
        getMultiListing().Listing();
    }

    public void ToBarcode(View view) {
        Intent intent = new Intent(MainActivity.this, BarcodeScanActivity.class);
        startActivity(intent);
        getMultiListing().Listing();
    }

    public void ToDelete(View view) {
        Intent intent = new Intent(MainActivity.this, DeleteActivity.class);
        startActivity(intent);
        getMultiListing().Listing();
    }

    public void ToProfile(View view) {
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        intent.putExtra("userId", FirebaseAuth.getInstance().getUid());
        startActivity(intent);
    }

    @Override
    public void applyText(String usernname) {
        new UserFind(usernname, MainActivity.this).execute("naber");
    }

}