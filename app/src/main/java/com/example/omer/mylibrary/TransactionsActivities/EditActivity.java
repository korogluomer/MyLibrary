package com.example.omer.mylibrary.TransactionsActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.omer.mylibrary.BookTransactions.BookFind;
import com.example.omer.mylibrary.BookTransactions.BookUpdate;
import com.example.omer.mylibrary.MainActivity;
import com.example.omer.mylibrary.R;
import com.google.firebase.database.FirebaseDatabase;

public class EditActivity extends AppCompatActivity {

    EditText txtBookName,txtBuyLocation,txtBookNote;
    TextView txtBuyDate;
    Button btnUpdate,btnPickDate,btnCancel;
    private FirebaseDatabase database;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        database = FirebaseDatabase.getInstance();
        txtBookName=(EditText)findViewById(R.id.txtEditBookName);
        txtBookNote=findViewById(R.id.txtEditNot);
        txtBuyDate=findViewById(R.id.txtEditDate);
        txtBuyLocation=findViewById(R.id.txtEditYer);

        btnUpdate=findViewById(R.id.btnEditBook);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookUpdate bookUpdate=new BookUpdate(txtBuyDate,txtBookName,txtBuyLocation,txtBookNote,EditActivity.this,database,BookFind.getBookId());
                bookUpdate.Update();
                //Güncel liste için tekrar listele
                MainActivity.getMultiListing().Listing();
                finish();
                Intent intent=new Intent(EditActivity.this,MainActivity.class);
                intent.putExtra("mind","edit");
                startActivity(intent);
            }
        });

        btnPickDate=findViewById(R.id.btnEditPickDate);

        btnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManuelEklemeActivity.DatePick(txtBuyDate,EditActivity.this);
            }
        });
        btnCancel=findViewById(R.id.btnEditCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        txtBookName.setText(BookFind.getBookName());
        txtBuyLocation.setText(BookFind.getBuyLocation());
        txtBuyDate.setText(BookFind.getBuyDate());
        txtBookNote.setText(BookFind.getBookNote());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
