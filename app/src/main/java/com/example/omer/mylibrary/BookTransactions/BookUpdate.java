package com.example.omer.mylibrary.BookTransactions;

import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class BookUpdate implements IBookUpdate {

    private DatabaseReference bid;
    private DatabaseReference bookname;
    private DatabaseReference buydate;
    private DatabaseReference buylocation;
    private DatabaseReference booknote;
    private FirebaseDatabase database;
    private TextView txtDate;
    private EditText txtBookName, txtYer, txtNot;
    private Context context;
    private String bookId;

    public BookUpdate(TextView txtDate, EditText txtBookName, EditText txtYer, EditText txtNot, Context context,FirebaseDatabase database,String bookId) {
        this.txtDate = txtDate;
        this.txtBookName = txtBookName;
        this.txtYer = txtYer;
        this.txtNot = txtNot;
        this.context = context;
        this.database = database;
        this.bookId=bookId;
    }


    //BookId si parametre olarak alınan kitabı girilen değerlere göre günceller
    @Override
    public void Update() {
        bid = database.getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("books").child(bookId);
        bookname = bid.child("bookName");
        buydate = bid.child("buyDate");
        buylocation = bid.child("buyLocation");
        booknote = bid.child("bookNote");
        bookname.setValue(txtBookName.getText().toString());
        buydate.setValue(txtDate.getText().toString());
        buylocation.setValue(txtYer.getText().toString());
        booknote.setValue(txtNot.getText().toString());

        Toast.makeText(context.getApplicationContext(), "Book Updated", Toast.LENGTH_LONG).show();

    }
}
