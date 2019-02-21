package com.example.omer.mylibrary.TransactionsActivities;

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

import com.example.omer.mylibrary.BookTransactions.IBookAdd;
import com.example.omer.mylibrary.BookTransactions.ManuelAdd;
import com.example.omer.mylibrary.Other.ExampleDialogListener;
import com.example.omer.mylibrary.UserActivities.LoginActivity;
import com.example.omer.mylibrary.Other.SearchDialog;
import com.example.omer.mylibrary.R;
import com.example.omer.mylibrary.UserTransactions.UserFind;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class ManuelEklemeActivity extends AppCompatActivity implements ExampleDialogListener {

    private Button btnDate, btnAdd, btnCancel;
    private TextView txtDate;
    private EditText txtBookName, txtYer, txtNot, txtAuthorName;
    private static Calendar calendar;
    private static DatePickerDialog dpd;
    private FirebaseDatabase database;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manuel_ekleme);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Kitap Eklebilirsiniz");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        database = FirebaseDatabase.getInstance();

        txtDate = findViewById(R.id.txtManuelDate);
        txtBookName = findViewById(R.id.txtManuelBookName);
        txtAuthorName = findViewById(R.id.txtManuelAuthorName);
        txtAuthorName = findViewById(R.id.txtManuelAuthorName);
        txtDate = findViewById(R.id.txtManuelDate);
        txtYer = findViewById(R.id.txtManuelYer);
        txtNot = findViewById(R.id.txtManuelNot);

        btnDate = findViewById(R.id.btnManuelPickDate);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePick(txtDate, ManuelEklemeActivity.this);
            }
        });

        btnAdd = findViewById(R.id.btnManuelAddBook);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IBookAdd b = new ManuelAdd(database, txtDate, txtBookName, txtYer, txtAuthorName, txtNot, getApplicationContext());
                ((ManuelAdd) b).execute("naber");//Asenkron ekleme
            }
        });

        btnCancel = findViewById(R.id.btnManuelCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
            Intent intent = new Intent(ManuelEklemeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else if (item.getItemId() == R.id.search) {
            SearchDialog searchDialog = new SearchDialog();
            searchDialog.show(getSupportFragmentManager(), "example dialog");
        }
        return super.onOptionsItemSelected(item);
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
    public void applyText(String usernname) {
        new UserFind(usernname, ManuelEklemeActivity.this).execute("naber");
    }

}
