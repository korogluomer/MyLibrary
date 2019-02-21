package com.example.omer.mylibrary.ListingActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.omer.mylibrary.Other.ExampleDialogListener;
import com.example.omer.mylibrary.Other.SearchDialog;
import com.example.omer.mylibrary.R;
import com.example.omer.mylibrary.UserTransactions.UserFind;
import com.squareup.picasso.Picasso;

public class BooksActivity extends AppCompatActivity implements ExampleDialogListener {
    private TextView txtBookName, txtBookDate, txtBookLocation, txtAuthorName, txtBookPage, txtBookNote;
    private ImageView imgBook;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        txtBookName = findViewById(R.id.txtBooksBook);
        txtAuthorName = findViewById(R.id.txtBookAuthor);
        txtBookPage = findViewById(R.id.txtBookPage);
        txtBookDate = findViewById(R.id.txtBookDate);
        txtBookLocation = findViewById(R.id.txtBookLocation);
        txtBookNote = findViewById(R.id.txtBookNote);
        imgBook = findViewById(R.id.imgBookBook);

        FillTexts();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void FillTexts() {
        Intent intent = getIntent();

        txtBookName.setText(intent.getStringExtra("bookName"));
        txtAuthorName.setText(intent.getStringExtra("authorName"));
        txtBookPage.setText(intent.getStringExtra("bookPageNumber"));
        txtBookDate.setText(intent.getStringExtra("buyDate"));
        txtBookLocation.setText(intent.getStringExtra("buyLocation"));
        txtBookNote.setText(intent.getStringExtra("bookNote"));
        if (intent.getStringExtra("bookImage") == null)
            Picasso.get().load(R.drawable.noimage).into(imgBook);
        else
            Picasso.get().load(intent.getStringExtra("bookImage")).into(imgBook);
    }

    @Override
    public void applyText(String usernname) {
        new UserFind(usernname, BooksActivity.this).execute("naber");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
