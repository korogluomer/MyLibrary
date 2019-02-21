package com.example.omer.mylibrary.BarcodeActivities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.omer.mylibrary.BookTransactions.BarcodeAdd;
import com.example.omer.mylibrary.BookTransactions.IBookAdd;
import com.example.omer.mylibrary.GoogleBooks.FindBook;
import com.example.omer.mylibrary.Other.ExampleDialogListener;
import com.example.omer.mylibrary.UserActivities.LoginActivity;
import com.example.omer.mylibrary.Other.SearchDialog;
import com.example.omer.mylibrary.R;
import com.example.omer.mylibrary.UserTransactions.UserFind;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class BarcodeAddActivity extends AppCompatActivity implements ExampleDialogListener {

    private static EditText txtBookName;

    public static String getTxtBookName() {
        return txtBookName.getText().toString();
    }

    public static String getTxtAuthorName() {
        return txtAuthorName.getText().toString();
    }

    public static String getTxtNote() {
        return txtNote.getText().toString();
    }

    public static String getTxtLocation() {
        return txtLocation.getText().toString();
    }

    public static String getTxtDate() {
        return txtDate.getText().toString();
    }

    private static EditText txtAuthorName;
    private static EditText txtNote;
    private static EditText txtLocation;
    private static TextView txtDate;
    private static Button btnAdd, btnCancel, btnPickDate;
    private FirebaseDatabase database;
    private static Calendar calendar;
    private static DatePickerDialog dpd;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_add);

        database = FirebaseDatabase.getInstance();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnAdd = findViewById(R.id.btnBarcodeAddBook);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IBookAdd bookAdd = new BarcodeAdd(database, BarcodeAddActivity.this);
                bookAdd.Add();
            }
        });

        btnCancel = findViewById(R.id.btnBarcodeCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnPickDate = findViewById(R.id.btnBarcodePickDate);

        btnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePick(txtDate, BarcodeAddActivity.this);
            }
        });

        txtBookName = findViewById(R.id.txtBarcodeBookName);
        txtAuthorName = findViewById(R.id.txtBarcodeAuthorName);
        txtDate = findViewById(R.id.txtBarcodeDate);
        txtLocation = findViewById(R.id.txtBarcodeYer);
        txtNote = findViewById(R.id.txtBarcodeNot);

        txtBookName.setText(FindBook.getTitle());
        if (FindBook.getAuthors() != "")
            txtAuthorName.setText(FindBook.getAuthors().substring(2, FindBook.getAuthors().length() - 2));

    }

    public static void DatePick(final TextView txtmDate, Context context) {
        calendar = Calendar.getInstance();
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        int mMonth = calendar.get(Calendar.MONTH);
        int mYear = calendar.get(Calendar.YEAR);

        dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                txtmDate.setText((dayOfMonth) + "/" + (month + 1) + "/" + (year));

            }
        }, mYear, mMonth, mDay);
        dpd.show();
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
            Intent intent = new Intent(BarcodeAddActivity.this, LoginActivity.class);
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
        new UserFind(usernname, BarcodeAddActivity.this).execute("naber");
    }

}
