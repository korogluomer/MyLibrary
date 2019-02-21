package com.example.omer.mylibrary.ListingActivities;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.omer.mylibrary.Adapter.BooksAdapter;
import com.example.omer.mylibrary.Adapter.BooksFillFragment;
import com.example.omer.mylibrary.BookTransactions.MultiListing;
import com.example.omer.mylibrary.Other.ExampleDialogListener;
import com.example.omer.mylibrary.UserActivities.LoginActivity;
import com.example.omer.mylibrary.Other.HingeTransformation;
import com.example.omer.mylibrary.Other.SearchDialog;
import com.example.omer.mylibrary.R;
import com.example.omer.mylibrary.UserTransactions.UserFind;
import com.google.firebase.auth.FirebaseAuth;

public class BooksListActivity extends AppCompatActivity implements ExampleDialogListener {

    ViewPager viewPager;
    BooksAdapter pagerAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        pagerAdapter = new BooksAdapter(getSupportFragmentManager());
        addingFragmentsTOpagerAdapter();
        viewPager.setAdapter(pagerAdapter);
        HingeTransformation hingeTransformation = new HingeTransformation();

        viewPager.setPageTransformer(true, hingeTransformation);
    }

    private void addingFragmentsTOpagerAdapter() {
        for (int i = 0; i < MultiListing.getBookName().size(); i++) {
            pagerAdapter.addFragments(new BooksFillFragment(i));
        }
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
            Intent intent = new Intent(BooksListActivity.this, LoginActivity.class);
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
        new UserFind(usernname, BooksListActivity.this).execute("naber");
    }
}
