package com.example.omer.mylibrary.TransactionsActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.omer.mylibrary.Adapter.FillAdapter;
import com.example.omer.mylibrary.BookTransactions.BookDelete;
import com.example.omer.mylibrary.BookTransactions.IBookDelete;
import com.example.omer.mylibrary.Other.ExampleDialogListener;
import com.example.omer.mylibrary.UserActivities.LoginActivity;
import com.example.omer.mylibrary.Other.SearchDialog;
import com.example.omer.mylibrary.R;
import com.example.omer.mylibrary.UserTransactions.UserFind;
import com.google.firebase.auth.FirebaseAuth;

public class DeleteActivity extends AppCompatActivity implements ExampleDialogListener {

    ListView listView;
    FillAdapter adapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listView = findViewById(R.id.bookDeleteListView);
        adapter = new FillAdapter(this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IBookDelete b = new BookDelete(position, DeleteActivity.this, adapter, listView);
                b.Delete();
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
            Intent intent = new Intent(DeleteActivity.this, LoginActivity.class);
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
        new UserFind(usernname, DeleteActivity.this).execute("naber");
    }
}
