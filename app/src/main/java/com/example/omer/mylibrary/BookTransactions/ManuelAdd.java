package com.example.omer.mylibrary.BookTransactions;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omer.mylibrary.GoogleBooks.FindBook;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class ManuelAdd extends AsyncTask<String,Void,String> implements IBookAdd {

    private DatabaseReference userid;
    private DatabaseReference bid;
    private DatabaseReference bookname;
    private DatabaseReference buydate;
    private DatabaseReference buylocation;
    private DatabaseReference booknote;
    private DatabaseReference bookImage;
    private DatabaseReference authorName;
    private DatabaseReference bookPageNumber;
    private FirebaseDatabase database;

    private TextView txtDate;
    private EditText txtBookName, txtYer, txtNot, txtAuthorName;

    private Context context;


    public ManuelAdd(FirebaseDatabase database, TextView txtDate, EditText txtBookName, EditText txtYer, EditText txtAuthorName, EditText txtNot, Context context) {
        this.database = database;
        this.txtDate = txtDate;
        this.txtBookName = txtBookName;
        this.txtAuthorName = txtAuthorName;
        this.txtYer = txtYer;
        this.txtNot = txtNot;
        this.context = context;
    }
    @Override
    public void Add() {

        //Unique key üretme
        UUID uuid = UUID.randomUUID();
        String stringUUID = uuid.toString();

        userid = database.getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("books");
        bid = userid.child(stringUUID);
        bookname = bid.child("bookName");
        bookImage = bid.child("bookImage");
        authorName = bid.child("authorName");
        bookPageNumber = bid.child("bookPageNumber");
        buydate = bid.child("buyDate");
        buylocation = bid.child("buyLocation");
        booknote = bid.child("bookNote");

        bid.setValue(stringUUID);
        bookname.setValue(FindBook.getTitle());
        buydate.setValue(txtDate.getText().toString());
        buylocation.setValue(txtYer.getText().toString());
        booknote.setValue(txtNot.getText().toString());
        if (FindBook.getAuthors()!=null)
            authorName.setValue(FindBook.getAuthors().substring(2,FindBook.getAuthors().length()-2));
        else
            authorName.setValue("Sonuç Yok");
        bookPageNumber.setValue(FindBook.getPage());
        bookImage.setValue(FindBook.getImages());

        Toast.makeText(context.getApplicationContext(), "Book Added", Toast.LENGTH_LONG).show();
    }

    @Override
    protected String doInBackground(String... strings) {
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        String queryString = txtBookName.getText().toString() + " " + txtAuthorName.getText().toString();
        new FindBook(txtBookName.getText().toString(), txtAuthorName.getText().toString()).execute(queryString);

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Add();
    }
}
